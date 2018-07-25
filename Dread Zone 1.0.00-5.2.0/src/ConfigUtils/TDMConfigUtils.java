package ConfigUtils;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import Main.Main;
import Utils.TDMTimer;
import Utils.Teams;

import com.google.common.collect.Lists;

import ConfigFiles.ArenaFileConfig;
import ConfigFiles.Configurable;
import ConfigFiles.UnversalConfigs;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class TDMConfigUtils {

    public static Configurable ArenaConfig = ArenaFileConfig.getConfig();
    
    public static void beingTDM(String arenaName) {
    	
        List<Player> contestants = convertObjectContestantsToPlayers(arenaName);
        
        Collections.shuffle(contestants);
        
        List<Player> teamAContestants = Lists.newArrayList();
        List<Player> teamBContestants = Lists.newArrayList();
        
        int i = 0;
        
        int teamSize = contestants.size() / 2;
        
        for (Player contestant : contestants) {
        	
            if (i < teamSize) {
            	
                teamAContestants.add(contestant);
            }
            if (i >= teamSize) {
            	
                teamBContestants.add(contestant);
            }
            ++i;
        }
        
        Text teamAName = getRandomTeam();
        Text teamBName = getRandomTeamB(teamAName);
        
        teleportPlayersToArena(arenaName, teamAContestants, "Team A Spawnpoints");
        teleportPlayersToArena(arenaName, teamBContestants, "Team B Spawnpoints");
        
        Sponge.getScheduler().createTaskBuilder().interval(1, TimeUnit.SECONDS).delay(1, TimeUnit.SECONDS).execute(new 
        		TDMTimer(arenaName, contestants, teamAName, teamBName, teamAContestants, teamBContestants, 3)).submit(Main.Dreadzone);
    }
    
    private static void teleportPlayersToArena(Object arenaName, List<Player> teamContestants, String teamName) {
    	
        Set<Object> arenaTeamSpawnNames = ArenaConfigUtils.getArenaTeamSpawnpoints(arenaName, teamName);
        
        for (Object arenaSP : arenaTeamSpawnNames) {
        	
            for (Player player : teamContestants) {
            	
                player.setLocationAndRotation(ContestantConfigUtils.sendContestantToArenaLocation(arenaName, teamName, arenaSP).add(0.5, 0.0, 0.5), 
                		ContestantConfigUtils.sendContestantToArenaLocationRotaton(arenaName, teamName, arenaSP));
            }
        }
    }
    
    public static void respawnContestant(String arenaName, Player player){

    	int randomTInt = new Random().nextInt(2);
    	
    	int x = 0; 
    		
		if(x==randomTInt){
			
	    	Set<Object> arenaTeamSpawnNames = ArenaConfigUtils.getArenaTeamSpawnpoints(arenaName, "Team A Spawnpoints");
	    	
			int size = arenaTeamSpawnNames.size();
			
			int randomInt = new Random().nextInt(size);
			
			int i = 0;
			
			for(Object arenaSP : arenaTeamSpawnNames){
				
			    if (i == randomInt){
			    	
	                player.setLocationAndRotation(ContestantConfigUtils.sendContestantToArenaLocation(arenaName, "Team A Spawnpoints", arenaSP).add(0.5, 0.0, 0.5), 
	                		ContestantConfigUtils.sendContestantToArenaLocationRotaton(arenaName, "Team A Spawnpoints", arenaSP));
			    }
			    
			    i = i + 1;
			}
			
			return;
		}
		
    	Set<Object> arenaTeamSpawnNames = ArenaConfigUtils.getArenaTeamSpawnpoints(arenaName, "Team B Spawnpoints");
    	
		int size = arenaTeamSpawnNames.size();
		
		int randomInt = new Random().nextInt(size);
		
		int i = 0;
		
		for(Object arenaSP : arenaTeamSpawnNames){
			
		    if (i == randomInt){
		    	
                player.setLocationAndRotation(ContestantConfigUtils.sendContestantToArenaLocation(arenaName, "Team B Spawnpoints", arenaSP).add(0.5, 0.0, 0.5), 
                		ContestantConfigUtils.sendContestantToArenaLocationRotaton(arenaName, "Team B Spawnpoints", arenaSP));
		    }
		    
		    i = i + 1;
		}
    }

    public static List<Player> convertObjectContestantsToPlayers(String arenaName) {
    	
        Set<Object> objectContestants = ContestantConfigUtils.getArenaContestants(arenaName);
        
        List<Player> newListPlayers = Lists.newArrayList();
        
        for (Object objectContestant : objectContestants) {
        	
            for (Player player : Sponge.getServer().getOnlinePlayers()) {
            	
                if (player.getName().equals(objectContestant.toString())){
                	
                	newListPlayers.add(player);
                }
            }
        }
        return newListPlayers;
    }

    public static Text getRandomTeamB(Text teamA) {
    	
        List<Text> teams = Teams.Teams;
        
        for (@SuppressWarnings("unused") Text team : teams) {
        	
            Text randomTeam = TDMConfigUtils.getRandomTeam();
            
            if (randomTeam != teamA){
            	
            	return randomTeam;
            }
        }
        return null;
    }

    public static Text getRandomTeam() {
    	
        List<Text> teams = Teams.Teams;
        
        int size = teams.size();
        
        int item = new Random().nextInt(size);
        
        int i = 0;
        
        for (Text team : teams) {
        	
            if (i == item) {
            	
                return team;
            }
            i++;
        }
        return null;
    }

	public static void setPointWin(String arenaName, int i) {

		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Max Score").setValue(i);
		
		UnversalConfigs.saveConfig(ArenaConfig);
		
	}
	
	public static int getPointWin(String arenaName) {

		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Max Score").getInt();		
	}
}