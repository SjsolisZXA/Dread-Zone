package Executors;

import java.util.Set;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;

public class LeaveDreadZoneArenaExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.RED, "This is a user command only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		Set<Object> arenas = ArenaConfigUtils.getArenas();
		
		for(Object arena : arenas){
			
			Set<Object> contestants = ArenaConfigUtils.getArenaContestants(arena);
			
			for(Object contestant: contestants){
				
				if((contestant).equals(player.getName())){
					
					ArenaConfigUtils.removeContestant(arena,player.getName());
					
			    	player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
							TextColors.WHITE,"Thanks for playing!"));
					
					return CommandResult.success();
				}
			}
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
				TextColors.WHITE,"You're not in any Dread Zone arena! To join a Dread Zone arena, enter ",TextColors.DARK_RED,"/dzarenas"));
		
		return CommandResult.success();
	}
}