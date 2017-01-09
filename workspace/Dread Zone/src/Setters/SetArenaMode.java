package Setters;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ArenaConfigUtils.ArenaConfigUtils;
import ConfigUtils.RightClickModeConfigUtils;
import Listeners.RightClickMode;

public class SetArenaMode implements CommandExecutor{
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.RED, "This is a user comand only!"));
			
			return CommandResult.success();
		}
		
		String arenaName = args.<String> getOne("arena name").get();
		
		if(!ArenaConfigUtils.isArenaInConfig(arenaName)){
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"The Dread Zone arena specified does not exists!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		String mode = args.<String> getOne("mode").get();
				
		if(mode.equals("TDM")){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"Stand where "));

			//the user is not actually going to be in a right click mode, but this will help the user set player spawn points
			RightClickModeConfigUtils.addToList(player.getName(), arenaName);
			
			RightClickMode.SAAN(arenaName);
	
			return CommandResult.success();
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"The mode specifed is not avaialable!"));
		
		return CommandResult.success();
	}
}
