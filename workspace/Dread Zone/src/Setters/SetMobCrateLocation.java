package Setters;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.MobCreateConfigUtils;
import Utils.AsyncCommandExecutorBase;


public class SetMobCrateLocation extends AsyncCommandExecutorBase {
	
	@Override
	public void executeAsync(CommandSource src, CommandContext args){
		if(!(src instanceof Player)){
			src.sendMessage(Text.of(TextColors.RED, "Console already decides where mobs spawn."));
			return;
		}
		Player player = (Player)src;
				
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Right click on where you want a mob create to drop."));
		
		//create a blaze rod and give it to the user's inventory
		ItemStack zdrod = ItemStack.of(ItemTypes.BLAZE_ROD, 1);
		player.getInventory().offer(zdrod);
		
		//check to see if rod exists, if not create it in the configuration file 

		String groupName = args.<String> getOne("group name").get();
		String targetName = args.<String> getOne("target name").get();
		
		if (MobCreateConfigUtils.isTargetInConfig(groupName,targetName))
		{
			MobCreateConfigUtils.deleteTarget(groupName,targetName);
		}
		MobCreateConfigUtils.setTarget(player.getTransform(), player.getWorld().getName(), groupName, targetName);
		
		//Confirmation message
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Success, DZ Mob Create ",TextColors.DARK_RED,targetName,TextColors.WHITE, " added to group ", TextColors.DARK_RED, groupName,TextColors.WHITE," !"));
	}
	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "settarget" };
	}	
}
