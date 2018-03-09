package Modes;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.PABConfigUtils;
import ConfigUtils.RightClickModeConfigUtils;
import Listeners.RightClickMode;

public class PAB implements CommandExecutor{
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE, "This is a user command only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		if (RightClickModeConfigUtils.getUserMode(player.getName().toString()).equals("PA")){
			
			if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation())!=null){
				
				String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
				
				if(ArenaConfigUtils.doesArenaHaveMode(arenaName, "PAB")){
					
					PABConfigUtils.setPointA(player.getTransform(), arenaName);
					
					RightClickMode.PBAN(arenaName);
					
					player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
							TextColors.WHITE,"Success! Now right click on a block to be ",TextColors.DARK_RED,"Point B",
							TextColors.WHITE,". Players that arrive to this location will have had completed the Dread Zone Challange."));
					
					RightClickModeConfigUtils.deleteUsernameInList(player.getName());
					
					RightClickModeConfigUtils.addToList(player.getName(), "PB");
					
					return CommandResult.success();
				}
				
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
						TextColors.WHITE,"This arena has not had the PAB mode implemented!"));
				
				return CommandResult.success();
			}
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"You must be in a Dread Zone arena to execute this command!"));
			
			return CommandResult.success();
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
				TextColors.WHITE,"You must be in PAB mode to execute this command!"));
		
		return CommandResult.success();
	}
}
