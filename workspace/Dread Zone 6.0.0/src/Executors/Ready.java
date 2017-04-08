package Executors;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import ConfigUtils.PABConfigUtils;
import ConfigUtils.TDMConfigUtils;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class Ready implements CommandExecutor {
	
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    	
        if (!(src instanceof Player)) {
        	
            src.sendMessage(Text.of(TextColors.RED, "This is a user command only!"));
            
            return CommandResult.success();
        }
        
        Player player = (Player)src;
        
        if (ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation()) != null) {
        	
            String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
            
            if (ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())) {
            	
            	//TDM ready
                if(ArenaConfigUtils.getArenaStatus(arenaName).equals("TDM")){
                	
                    ContestantConfigUtils.isready(arenaName, player.getName());
                    
                    int numOfASpots = ArenaConfigUtils.getNumOfArenaTeamSpawnpoints(arenaName, "Team A Spawnpoints") + ArenaConfigUtils.getNumOfArenaTeamSpawnpoints(arenaName, "Team B Spawnpoints");
                    
                    if (ContestantConfigUtils.getNumOfReadyContestants(arenaName) < numOfASpots) {
                    	
                    	if(ContestantConfigUtils.getNumOfArenaContestants(arenaName)!=numOfASpots){
                    		
                    		player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                            		TextColors.WHITE, "This arena needs more Dread Zone Contestants! Invite your friends."));
                    		
                    		return CommandResult.success();
                    	}
                        player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                        		TextColors.WHITE, "Waiting on the other players..."));
                        
                        return CommandResult.success();
                    }
                    if (ContestantConfigUtils.getNumOfReadyContestants(arenaName) == numOfASpots) {
                    	
                        TDMConfigUtils.beingTDM(arenaName);
                    }
                    return CommandResult.success();
                }
                
                //PAB ready
                if(ArenaConfigUtils.getArenaStatus(arenaName).equals("PAB")){
                	
                	ContestantConfigUtils.isready(arenaName, player.getName());
                	
                	if(ContestantConfigUtils.getNumOfArenaContestants(arenaName)==ContestantConfigUtils.getNumOfReadyContestants(arenaName)){
                		
                		PABConfigUtils.beginPAB(arenaName);
                		
                		return CommandResult.success();
                	}
                	
                	player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", TextColors.WHITE, "Waiting on the other players..."));
                	
                	return CommandResult.success();
                }
            }
            player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", TextColors.WHITE, "This a Dread Zone contestant command only!"));
            
            return CommandResult.success();
        }
        player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", TextColors.WHITE, "This a Dread Zone contestant command only!"));
        
        return CommandResult.success();
    }
}