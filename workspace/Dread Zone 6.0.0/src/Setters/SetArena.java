package Setters;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.RightClickModeConfigUtils;
import Listeners.RightClickMode;

public class SetArena implements CommandExecutor{
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		if(!(src instanceof Player)){
			src.sendMessage(Text.of(TextColors.RED, "This is a user comand only!"));
			return CommandResult.success();
		}
		Player player = (Player)src;
				
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Right click on a block to be a floor corner of the arena."));
		
		RightClickModeConfigUtils.addToList(player.getName(),"CA");
		
		String arenaName = args.<String> getOne("arena name").get();
		
		if(ArenaConfigUtils.isArenaInConfig(arenaName)){
			
			ArenaConfigUtils.deleteArena(arenaName);;
		}
		
		RightClickMode.SAAN(arenaName);

		return CommandResult.success();
	}
}
