package Setters;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.LightningConfigUtils;


public class SetLightningRodLocation implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		if(!(src instanceof Player)){
			src.sendMessage(Text.of(TextColors.RED, "Console already decides where lightning hits."));
			return CommandResult.success();
		}
		Player player = (Player)src;
				
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Right click on where you want lightning to hit."));
		
		//create a blaze rod and give it to the user's inventory
		ItemStack zdrod = ItemStack.of(ItemTypes.BLAZE_ROD, 1);
		player.getInventory().offer(zdrod);
		
		//check to see if rod exists, if not create it in the configuration file 
		String targetName = args.<String> getOne("target name").get();
		
		if (LightningConfigUtils.isTargetInConfig(targetName))
		{
			LightningConfigUtils.deleteTarget(targetName);
		}
		LightningConfigUtils.setTarget(player.getTransform(), player.getWorld().getName(), targetName);
		
		//Confirmation message
		src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE, "Success, ", TextColors.DARK_RED, targetName, TextColors.WHITE," DZ Rod created!"));
		
		return CommandResult.success();
	}
}
