package Executors;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
import org.spongepowered.api.data.manipulator.mutable.entity.GameModeData;
import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ClassConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import ConfigUtils.LobbyConfigUtils;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class JoinArena implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException{
		
		if(!(src instanceof Player)){
			
            src.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                    TextColors.WHITE, "Only players can join combat!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		Optional<String> AN = args.<String> getOne("arena name");
		
		if(!AN.isPresent()){
			
            player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                    TextColors.WHITE, "Invalid usage! Usage: ",TextColors.DARK_RED,"/dz join ARENA_NAME MODE",TextColors.WHITE,
                    ". To get a list of Dread Zone arenas, enter ",TextColors.DARK_RED,"/dz al",TextColors.WHITE,"."));
            
            return CommandResult.success();
		}
		
		String arenaName = args.<String> getOne("arena name").get();
		
		Optional<String> M = args.<String> getOne("mode");
		
		if(!M.isPresent()){
			
            player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                    TextColors.WHITE, "Invalid usage! Usage: ",TextColors.DARK_RED,"/dz join ARENA_NAME MODE",TextColors.WHITE,
                    ". To get a list of available modes for a Dread Zone arena, enter ",TextColors.DARK_RED,"/dz am ARENA_NAME",TextColors.WHITE,"."));
            
            return CommandResult.success();
		}
		
		String mode = args.<String> getOne("mode").get();
		
		if(ArenaConfigUtils.isArenaInConfig(arenaName)){
			
			if(LobbyConfigUtils.isLobbyInConfig(arenaName, arenaName+"Lobby")){
			
				if(LobbyConfigUtils.doesLobbySpawnExist(arenaName)){
				
					if(mode.toUpperCase().equals("TDM")){
						
						if(ArenaConfigUtils.doesArenaHaveMode(arenaName, mode.toUpperCase())){
						
				            int numOfSpots = ArenaConfigUtils.getNumOfArenaTeamSpawnpoints(arenaName, "Team A Spawnpoints") + 
				                    ArenaConfigUtils.getNumOfArenaTeamSpawnpoints(arenaName, "Team B Spawnpoints");
							
				            if (ContestantConfigUtils.getNumOfArenaContestants(arenaName) < numOfSpots){

				            	if(ArenaConfigUtils.getArenaData(arenaName, "Status").equals("Open")){
				            	 							
				            		ArenaConfigUtils.setArenaStatus(arenaName, "TDM");
				            	 								
	 								try {
	 									
										ClassConfigUtils.spawnArenaNPCs(player.getUniqueId(),arenaName);
	 									
	 								} catch (ObjectMappingException e) {
											
	 										e.printStackTrace();
	 								}
 								}

				            	join(player,arenaName);
								
					            player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "]", 
					                      TextColors.WHITE, " Left click on a Dread Zone NPC to choose a class. Right click on the Dread Zone NPC class for details. When you're ready, enter ", 
					                      TextColors.DARK_RED, "/dz ready"));
								
					              if ((numOfSpots - ContestantConfigUtils.getNumOfArenaContestants(arenaName) != 1) && (numOfSpots - ContestantConfigUtils.getNumOfArenaContestants(arenaName) != 0)){
					                  Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
					                		  TextColors.DARK_RED, player.getName(), TextColors.WHITE, " joined Dread Zone arena ", TextColors.DARK_RED, arenaName, TextColors.WHITE, "! ", 
					                		  TextColors.DARK_RED, (numOfSpots - ContestantConfigUtils.getNumOfArenaContestants(arenaName)), TextColors.WHITE, " spots left. To join, enter", 
					                		  TextColors.DARK_RED, " /dz join ", TextColors.DARK_RED, arenaName));
					                
					                return CommandResult.success();
					              }
					              if (numOfSpots - ContestantConfigUtils.getNumOfArenaContestants(arenaName) == 1){
					            	  
					                  Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
					                		  TextColors.DARK_RED, player.getName(), TextColors.WHITE, " joined Dread Zone arena ", TextColors.DARK_RED, arenaName, TextColors.WHITE, "! ", 
					                		  TextColors.DARK_RED, 1, TextColors.WHITE, " spot left! To join, enter", 
					                		  TextColors.DARK_RED, " /dz join ", TextColors.DARK_RED, arenaName));
					                
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
						
			            player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
			                    TextColors.WHITE, "Sorry, Dread Zone arena ", TextColors.DARK_RED, arenaName, TextColors.WHITE, " does not have a TDM mode implemented."));
			                  
			                  return CommandResult.success();
					}
					
					if((mode.toUpperCase().equals("NC"))){
						
						player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
								TextColors.WHITE,"Dread Zone Node Capture mode is unavailable, for now."));
						
						return CommandResult.success();
					}
					
					if((mode.toUpperCase().equals("PAB"))){
						
			            if(ArenaConfigUtils.getArenaData(arenaName, "Status").equals("Open")){
							
							ArenaConfigUtils.setArenaStatus(arenaName, "PAB");
			            }
			            
			            join(player,arenaName);
									
			            player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "]", 
			                      TextColors.WHITE, " Left click on a Dread Zone NPC to choose a class. Right click on the Dread Zone NPC class for details. When you're ready, enter ", 
			                      TextColors.DARK_RED, "/dz ready"));
						
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
	
	public static void join(Player player, String arenaName){
		
    	//get player's potion effects
		Optional<List<PotionEffect>> potions = player.get(Keys.POTION_EFFECTS);
		
		if(potions.isPresent()){
			
			List<PotionEffect> potionEffects = potions.get();
			
			//try {
				
				//ContestantConfigUtils.saveOriginalPotionEffects(potionEffects,arenaName,player.getName());
				
				potionEffects.clear(); 
				
				player.offer(Keys.POTION_EFFECTS, potionEffects);
				
			//} catch (ObjectMappingException e2) {
				
				//e2.printStackTrace();
			//}
		}
		
        //get and save original gamemode
        GameMode playerGamemode = player.get(Keys.GAME_MODE).get();
        
        //get and save original food level
        Integer foodLevel = player.get(Keys.FOOD_LEVEL).get();
        	            
		try {
			
			ContestantConfigUtils.addContestant(arenaName,player.getName(),player.getTransform(),player.getWorld().getName(),playerGamemode,foodLevel);
			
		} catch (ObjectMappingException e) {
			
			e.printStackTrace();
		}
		
		//clear the contestant's inventory
		player.getInventory().clear();
		
		//warp the contestant to the lobby
		player.setLocationAndRotation(LobbyConfigUtils.getLobbySpawnLocation(arenaName), LobbyConfigUtils.getLobbySpawnLocationRotation(arenaName));
		
		//max out the contestant's health
		HealthData maxHealth = player.getHealthData().set(Keys.HEALTH, player.get(Keys.MAX_HEALTH).get());
		player.offer(maxHealth);
		
		//max out food level
		FoodData maxFood = player.getFoodData().set(Keys.FOOD_LEVEL, 20);
		player.offer(maxFood);
		
		//set survival mode     			
		GameModeData survivalGamemode = player.getGameModeData().set(Keys.GAME_MODE, GameModes.SURVIVAL);
		player.offer(survivalGamemode);
	}
}
