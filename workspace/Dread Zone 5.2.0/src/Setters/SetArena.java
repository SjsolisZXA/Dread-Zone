package Setters;

import java.util.Optional;

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
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE, "This is a user command only!"));
			
			return CommandResult.success();
		}
		Player player = (Player)src;
		Optional<String> AN = args.<String> getOne("arena name");
		
		if(!AN.isPresent()){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Invalid usage! Valid usage: ",TextColors.DARK_RED, "/dz ca ARENA_NAME",TextColors.WHITE,"."));
			
			return CommandResult.success();
		}
		
		String arenaName = args.<String> getOne("arena name").get();
				
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Right click on a block to be a floor corner of the arena."));
		
		RightClickModeConfigUtils.addToList(player.getName(),"CA");
		
		if(ArenaConfigUtils.isArenaInConfig(arenaName)){
			
			ArenaConfigUtils.deleteArena(arenaName);;
		}
		
		RightClickMode.SAAN(arenaName);

		return CommandResult.success();
	}
}
