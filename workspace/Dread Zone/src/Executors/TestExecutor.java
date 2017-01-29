package Executors;

import java.util.Optional;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ClassConfigUtils;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class TestExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) {
		
		Player player =(Player)src;	
		
		Optional<ItemStack> itemStackOptional = player.getItemInHand();
		
		ItemStack itemStack = (ItemStack)itemStackOptional.get();
		
		//itemStack.setQuantity(itemStackOptional.get().getQuantity());		
		
		player.sendMessage(Text.of(TextColors.DARK_GREEN,itemStackOptional));
		
		//player.sendMessage(Text.of(TextColors.AQUA,itemStack));
		
		try {
			player.sendMessage(Text.of(TextColors.DARK_BLUE,ClassConfigUtils.doesClassItemExist("NukeTown", "Bukkit", itemStack)));
		} catch (ObjectMappingException e) {

			e.printStackTrace();
		}
		
		/**try {
			ClassConfigUtils.offerClass(player, ClassConfigUtils.getClassItems("NukeTown", "Bukkit"));
		} catch (ObjectMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}**/
		
		return CommandResult.success();
	}
}


