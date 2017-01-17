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
import ConfigUtils.ContestantConfigUtils;
import ConfigUtils.LobbyConfigUtils;
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
		
		String mode = args.<String> getOne("mode").get();
		
		if(ArenaConfigUtils.isArenaInConfig(arenaName)){
			
			if(LobbyConfigUtils.isLobbyInConfig(arenaName, arenaName+"Lobby")){
			
				if(LobbyConfigUtils.doesLobbySpawnExist(arenaName)){
				
					if((mode.toUpperCase().equals("TDM"))){
						
						if(ArenaConfigUtils.getArenaData(arenaName, "Status").equals("Open")){
							
							ArenaConfigUtils.setArenaStatus(arenaName, "TDM");
						}
						
						ContestantConfigUtils.addContestant(arenaName,player.getName(),player.getTransform(),player.getWorld().getName());
						
						PlayerBarrier.AN(arenaName);
						
						player.setLocationAndRotation(LobbyConfigUtils.getLobbySpawnLocation(arenaName), LobbyConfigUtils.getLobbySpawnLocationRotation(arenaName));
						
						player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"]",
								TextColors.WHITE," Right click on the sign with your desired class."));
						
						Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
								TextColors.DARK_RED, player.getName(),TextColors.WHITE," joined Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE,"!"
										+ " To join, enter",TextColors.DARK_RED," /dzjoin ",TextColors.DARK_RED,arenaName));
						
						return CommandResult.success();
					}
					
					if((mode.toUpperCase().equals("NC"))){
						
						player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
								TextColors.WHITE,"Dread Zone Node Capture mode is currently not available, yet."));
						
						return CommandResult.success();
					}
					
					player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
							TextColors.WHITE,"The Dread Zone mode you have specified does not exist!"));
					
					return CommandResult.success();
				}
				
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
						TextColors.WHITE,"Dread Zone arena ",TextColors.DARK_RED,arenaName,
						TextColors.WHITE," needs a lobby spawn. To set the lobby spawn, enter ",TextColors.DARK_RED,"/cdzlsa ",arenaName));
					
				return CommandResult.success();
			}
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," does not have a lobby! "
							+ "To create ",TextColors.DARK_RED,arenaName,"'s",TextColors.WHITE," lobby, enter, ",TextColors.DARK_RED,"/dzmlobby ",arenaName));
			
			return CommandResult.success();
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
				TextColors.WHITE,"Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," does not exists! "
						+ "To view a list of avaiable Dread Zone arenas, enter ",TextColors.DARK_RED,"/dzarenas"));
		
		return CommandResult.success();
		
		/**
		 * 
		 * A method in which the player's inventory is saved, cleared and, and given back, once they leave the arena.
		 * 
		 * 
		 * 
		 */
	}
}
