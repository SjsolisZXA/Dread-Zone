package Executors;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotPos;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import Utils.EntitySpawner;

public class TestExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) {
		
		Player player =(Player)src;	
		
		EntitySpawner.spawnNPC(player.getLocation(), EntityTypes.HUMAN, player.getUniqueId(), "Escobar");
		
		//EntitySpawner.spawnEntity(player.getLocation(), EntityTypes.LIGHTNING);
		
		/**try {
			ClassConfigUtils.offerClass(player, ClassConfigUtils.getClassItems("NukeTown", "Bukkit"));
		} catch (ObjectMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}**/
		
		
		

		Inventory vrmmenu = Inventory.builder().of(InventoryArchetypes.CHEST)
				.property(InventoryDimension.PROPERTY_NAM, new InventoryDimension(9, 2))
				.property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.builder("Vote Rewards").color(TextColors.DARK_AQUA).style(TextStyles.BOLD).build()))
				.build(Main.Main.dreadzone);
		ItemStack emer = ItemStack.of(ItemTypes.EMERALD, 1);
		emer.offer(Keys.DISPLAY_NAME, Text.of("Emerald"));
		List<Text> itemLore = new ArrayList<Text>();
		itemLore.add(Text.of(TextColors.GOLD,"1 Vote"));
		emer.offer(Keys.ITEM_LORE , itemLore);
		vrmmenu.query(new SlotPos(1,1)).set(emer);
		vrmmenu.query(new SlotPos(0,0)).set(ItemStack.of(ItemTypes.ACACIA_DOOR, 1));
		vrmmenu.query(new SlotPos(2,1)).set(ItemStack.of(ItemTypes.IRON_INGOT, 1));
		//TODO 
		
		player.openInventory(vrmmenu, Cause.of(NamedCause.owner(Main.Main.dreadzone)));
		
		
		
		//player.getInventory().offer(CHEST);
		
		//player.openInventory(createEmptyInventory("TestInventory", 9), null);
		
		return CommandResult.success();
	}
	
	/**public static ItemStack CHEST = getChest();

	private static ItemStack getChest(){
	  ItemStack.Builder builder = Sponge.getRegistry().createBuilder(ItemStack.Builder.class);
	  builder.itemType(ItemTypes.CHEST).quantity(1);
	  ItemStack stack = builder.build();
	  return stack;
	}
	
	public CustomInventory createEmptyInventory(String name, int size){
		  CustomInventory.Builder builder = Sponge.getRegistry().createBuilder(CustomInventory.Builder.class);
		  builder.name(new FixedTranslation(name)).size(size);
		  CustomInventory opInv = builder.build();

		  return opInv;
	}**/
}


