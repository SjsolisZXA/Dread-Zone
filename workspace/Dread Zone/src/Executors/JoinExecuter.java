package Executors;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;
import Listeners.PlayerBarrier;

public class JoinExecuter implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException{
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.RED, "This is a user command only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		String arenaName = args.<String> getOne("arena name").get();
		
		if(ArenaConfigUtils.isArenaInConfig(arenaName)){
			
			if(ArenaConfigUtils.isArenaOpen(arenaName)){
				
				ArenaConfigUtils.addContestant(arenaName,player.getName());
				
				PlayerBarrier.LN(arenaName);
				
				player.setLocationAndRotation(ArenaConfigUtils.getLobbySpawnLocation(arenaName), ArenaConfigUtils.getLobbySpawnLocationRotation(arenaName));
				
				Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
						TextColors.DARK_RED, player.getName(),TextColors.WHITE," joined Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE,"!"
								+ " To join, enter",TextColors.DARK_RED," /dzjoin ",TextColors.DARK_RED,arenaName));
				
				return CommandResult.success();
			}
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," is currently not avaiable."));
			
			return CommandResult.success();
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
				TextColors.WHITE,"Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," does not exists! "
						+ "To view a list of avaiable Dread Zone arenas, enter, ",TextColors.DARK_RED,"/dzarenas"));
		
		return CommandResult.success();
		
		/**player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"]",
				TextColors.WHITE," Choose your class ",TextColors.DARK_RED, player.getName(),"!"));

		 * 
		 * A method in which the player's inventory is saved, cleared and, and given back, once they leave the arena.
		 * 
		 * 
		 * 
		 */
	}
}
