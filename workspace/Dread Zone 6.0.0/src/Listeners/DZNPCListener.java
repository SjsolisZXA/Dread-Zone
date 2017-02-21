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

public class DZNPCListener {
	
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
				
						Inventory inventory = Inventory.builder().of(InventoryArchetypes.CHEST).property(InventoryDimension.PROPERTY_NAME, new InventoryDimension(9, 1))
								.property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.builder(className+" Class").color(TextColors.DARK_RED).style(TextStyles.NONE).build()))
								.build(Main.Main.dreadzone);
						
						List<ItemStack> items = ClassConfigUtils.getClassItems(arenaName, className);
						
						int x = 0;
						
						for(ItemStack itemStack: items){
												
							inventory.query(new SlotPos(x,0)).set(itemStack);
							
							x++;
						}
						
						player.openInventory(inventory, Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						
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
			
			if(ClassConfigUtils.doesClassExist(ArenaConfigUtils.getUserArenaNameFromLocation(event.getTargetEntity().getLocation()), className)){
				
				event.setCancelled(true);;
			}
		}
	}
}
