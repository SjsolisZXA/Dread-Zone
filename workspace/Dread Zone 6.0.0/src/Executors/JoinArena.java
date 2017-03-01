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
import ConfigUtils.ClassConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import ConfigUtils.LobbyConfigUtils;
import Listeners.GeneralArenaListeners;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class JoinArena implements CommandExecutor {
	
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
						
			            int numOfSpots = ArenaConfigUtils.getNumOfArenaTeamSpawnpoints(arenaName, "Team A Spawnpoints") + 
			                    ArenaConfigUtils.getNumOfArenaTeamSpawnpoints(arenaName, "Team B Spawnpoints");
						
			            if (ContestantConfigUtils.getNumOfArenaContestants(arenaName) < numOfSpots){
			            		
							ContestantConfigUtils.addContestant(arenaName,player.getName(),player.getTransform(),player.getWorld().getName());
							
							GeneralArenaListeners.AN(arenaName);
							
							player.getInventory().clear();
							
							player.setLocationAndRotation(LobbyConfigUtils.getLobbySpawnLocation(arenaName), LobbyConfigUtils.getLobbySpawnLocationRotation(arenaName));
							
				            if(ArenaConfigUtils.getArenaData(arenaName, "Status").equals("Open")){
								
								ArenaConfigUtils.setArenaStatus(arenaName, "TDM");
								
								try {
									
									ClassConfigUtils.spawnArenaNPCs(player.getUniqueId(),arenaName);
									
								} catch (ObjectMappingException e) {
									
									e.printStackTrace();
								}
							}
							
				            player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "]", 
				                      TextColors.WHITE, " Left click on a Dread Zone NPC to choose a class. Right click on the Dread Zone NPC class for details. When you're ready, enter ", 
				                      TextColors.DARK_RED, "/dzready"));
							
				              if ((numOfSpots - ContestantConfigUtils.getNumOfArenaContestants(arenaName) != 1) && (numOfSpots - ContestantConfigUtils.getNumOfArenaContestants(arenaName) != 0)){
				                  Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
				                		  TextColors.DARK_RED, player.getName(), TextColors.WHITE, " joined Dread Zone arena ", TextColors.DARK_RED, arenaName, TextColors.WHITE, "! ", 
				                		  TextColors.DARK_RED, (numOfSpots - ContestantConfigUtils.getNumOfArenaContestants(arenaName)), TextColors.WHITE, " spots left. To join, enter", 
				                		  TextColors.DARK_RED, " /dzjoin ", TextColors.DARK_RED, arenaName));
				                
				                return CommandResult.success();
				              }
				              if (numOfSpots - ContestantConfigUtils.getNumOfArenaContestants(arenaName) == 1){
				            	  
				                  Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
				                		  TextColors.DARK_RED, player.getName(), TextColors.WHITE, " joined Dread Zone arena ", TextColors.DARK_RED, arenaName, TextColors.WHITE, "! ", 
				                		  TextColors.DARK_RED, 1, TextColors.WHITE, " spot left! To join, enter", 
				                		  TextColors.DARK_RED, " /dzjoin ", TextColors.DARK_RED, arenaName));
				                
				                return CommandResult.success();
				              }
				              
				              Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
				                      TextColors.WHITE, "Dread Zone arena ", TextColors.DARK_RED, arenaName, TextColors.WHITE, " closed!"));
				                    
				              return CommandResult.success();
			            }
			            
			            player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
			                    TextColors.WHITE, "Sorry, Dread Zone arena ", TextColors.DARK_RED, arenaName, TextColors.WHITE, " is full."));
			                  
			                  return CommandResult.success();
					}
					
					if((mode.toUpperCase().equals("NC"))){
						
						player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
								TextColors.WHITE,"Dread Zone Node Capture mode is unavailable, for now."));
						
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
	}
}
