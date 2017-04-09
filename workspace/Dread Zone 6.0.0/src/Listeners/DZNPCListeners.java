package Listeners;

import java.util.List;
import java.util.Optional;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotPos;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ClassConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class DZNPCListeners {
	
	//Right click on a DZ NPC to see class details
	@Listener
	public void onPlayerInteractBlock(InteractEntityEvent.Secondary.MainHand event, @First Player player) throws ObjectMappingException{
		
	    if (ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation()) != null){
	    	
			String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
	    	
		    if (ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())){
		    	
				Optional<Text> NPC = event.getTargetEntity().get(Keys.DISPLAY_NAME);
				
				if(NPC.isPresent()){
					
					Text classN = NPC.get();
					
					String className = classN.toPlain();
					
					if(ClassConfigUtils.doesClassExist(arenaName, className)){ 
						
						int dx = 9;
						int dy = 0;
						int numOfClassItmes = ClassConfigUtils.getNumOfClassItems(arenaName, className);
						
						if(numOfClassItmes<=9){
							dy=1;
						}
						
						if(numOfClassItmes>9&&numOfClassItmes<=18){
							dy=2;
						}
						
						if(numOfClassItmes>18&&numOfClassItmes<=36){
							dy=3;
						}
				
						Inventory inventory = Inventory.builder().of(InventoryArchetypes.CHEST).property(InventoryDimension.PROPERTY_NAME, new InventoryDimension(dx, dy))
								.property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.builder(className+" Class").color(TextColors.DARK_RED).style(TextStyles.NONE).build()))
								.build(Main.Main.Dreadzone);
						
						List<ItemStack> items = ClassConfigUtils.getClassItems(arenaName, className);
						
						int curr = 0;
						int a = 0;
						int b = 0;
						int c = 0;
						
						for(ItemStack itemStack: items){

							if(curr<9){
												
								inventory.query(new SlotPos(a,0)).set(itemStack);
								a++;
							}
							if(curr>8 && curr<18){
								
								inventory.query(new SlotPos(b,1)).set(itemStack);
								b++;
							}
							if(curr>17 && curr<36){
								
								inventory.query(new SlotPos(c,2)).set(itemStack);
								c++;
							}
							curr++;
						}
						
						player.openInventory(inventory, Cause.of(NamedCause.owner(Main.Main.Dreadzone)));
						
						return;
			        }
				}
	    	}
	    }
	}
	
	//Left click on a DZ NPC to receive class items
	@Listener
	public void onDZNPCInteract(InteractEntityEvent.Primary.MainHand event, @First Player player) throws ObjectMappingException{
		
	    if (ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation()) != null){
	    	
	    	String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
	    	
	        if (ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())){
	        
				Optional<Text> NPC = event.getTargetEntity().get(Keys.DISPLAY_NAME);
				
				if(NPC.isPresent()){
					
					Text classN = NPC.get();
					
					String className = classN.toPlain();
					
					if(ClassConfigUtils.doesClassExist(arenaName, className)){  
						
						player.getInventory().clear();
						
						List<ItemStack> items = ClassConfigUtils.getClassItems(arenaName, className);
						
						ClassConfigUtils.offerClass(player, items);
						
						player.sendMessage(ChatTypes.ACTION_BAR, Text.of(className, " class selected!"));
						
						return;
						
					}
				}
	        }
		}
	}
	
	//prevents DZ NPCs from moving
	@Listener
	public void onPlayerMoveNPC(MoveEntityEvent event){
		
		Optional<Text> NPC = event.getTargetEntity().get(Keys.DISPLAY_NAME);
		
		if(NPC.isPresent()){
			
			Text classN = NPC.get();
			
			String className = classN.toPlain();
			
			if(ArenaConfigUtils.getUserArenaNameFromLocation(event.getTargetEntity().getLocation())!=null){
				
				String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(event.getTargetEntity().getLocation());
			
				if(ClassConfigUtils.doesClassExist(arenaName, className)){
					
					event.setCancelled(true);
				}
			}
		}
	}
}
