package ConfigUtils;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import Main.Main;
import Utils.TeamDeathmatchTimer;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.boss.BossBarOverlays;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class TDMConfigUtils {
	
    static Text Team1 = Text.of(TextColors.DARK_GRAY, "<", TextColors.RED, "Spetsnaz", TextColors.DARK_GRAY, ">");
    
    static Text Team2 = Text.of(TextColors.DARK_RED, "<", TextColors.DARK_AQUA, "Tropas", TextColors.DARK_RED, ">");
    
    static Text Team3 = Text.of(TextColors.GOLD, "<", TextColors.RED, "N.V.A", TextColors.GOLD, ">");
    
    static Text Team4 = Text.of(TextColors.GRAY, "<", TextColors.BLUE, "Black Ops", TextColors.GRAY, ">");
    
    static Text Team5 = Text.of(TextColors.GRAY, "<", TextColors.DARK_GREEN, "S.O.G", TextColors.GRAY, ">");
    
    static Text Team6 = Text.of(TextColors.BLUE, "<", TextColors.WHITE, "Op 40", TextColors.BLUE, ">");
    
    static List<Text> Teams = new ArrayList<Text>(Arrays.asList(Team1, Team2, Team3, Team4, Team5, Team6));
    
    static Text teamAName = TDMConfigUtils.getRandomTeam();
    
    static Text teamBName = TDMConfigUtils.getRandomTeamB(teamAName);
    
    static List<Player> teamAContestants = Lists.newArrayList();
    
    static List<Player> teamBContestants = Lists.newArrayList();

    public static void beingTDM(String arenaName) {
    	
        List<Player> contestants = TDMConfigUtils.convertObjectContestantsToPlayers(arenaName);
        
        Collections.shuffle(contestants);
        
        TDMConfigUtils.defineBothTeams(contestants);
        
        TDMConfigUtils.teleportPlayersToArena(arenaName, teamAContestants, "Team A Spawnpoints");
        
        TDMConfigUtils.teleportPlayersToArena(arenaName, teamBContestants, "Team B Spawnpoints");
        
        Sponge.getScheduler().createTaskBuilder().interval(1, TimeUnit.SECONDS).delay(1, TimeUnit.SECONDS).execute(new 
        		TeamDeathmatchTimer(contestants, teamAName, teamBName, teamAContestants, teamBContestants)).submit(Main.dreadzone);
    }

    private static synchronized void teleportPlayersToArena(Object arenaName, List<Player> teamContestants, String teamName) {
    	
        Set<Object> arenaTeamSpawnNames = ArenaConfigUtils.getArenaTeamSpawnpoints(arenaName, teamName);
        
        for (Object arenaSP : arenaTeamSpawnNames) {
        	
            for (Player player : teamContestants) {
            	
                player.setLocationAndRotation(ContestantConfigUtils.sendContestantToArenaLocation(arenaName, teamName, arenaSP).add(0.5, 0.0, 0.5), 
                		ContestantConfigUtils.sendContestantToArenaLocationRotaton(arenaName, teamName, arenaSP));
            }
        }
    }

    public static void setUpTDMGUI(List<Player> teamContestants, Text teamName) {
    	
        for (Player player : teamContestants) {
        	
        	//sets up team name tag below username display name
            Scoreboard teamTagName = Scoreboard.builder().build();
            
            Objective teamTagNameObjective = Objective.builder().name("Team").displayName(Text.of(teamName)).criterion(Criteria.DUMMY).build();
            
            teamTagName.addObjective(teamTagNameObjective);
            
            teamTagName.updateDisplaySlot(teamTagNameObjective, DisplaySlots.BELOW_NAME);
            
            //sets up kills death scoreboard
            Scoreboard scoreboard = Scoreboard.builder().build();
            
            Objective objective = Objective.builder().name("Stats").displayName(Text.of((String.valueOf(player.getName()) + " Stats"))).criterion(Criteria.DUMMY).build();
            
            scoreboard.addObjective(objective);
            
            scoreboard.updateDisplaySlot(objective, DisplaySlots.SIDEBAR);
            
            objective.getOrCreateScore(Text.of("Kills")).setScore(0);
            
            objective.getOrCreateScore(Text.of("Deaths")).setScore(0);
            
            player.setScoreboard(scoreboard);
            
            for (Player playerOnline : Sponge.getServer().getOnlinePlayers()) {
            	
                if (ContestantConfigUtils.isUserAnArenaContestant(ArenaConfigUtils.getUserArenaNameFromLocation(playerOnline.getLocation()), player.getName())){
                	
                player.getTabList().removeEntry(playerOnline.getUniqueId());
                }
            }
             
          //sets up boss bar team point count
            player.getTabList().getEntry(player.getUniqueId()).get().setDisplayName(Text.of(player.getName()));// come back and change this once we get team color
            
            Text text1 = Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "]");
            
            Text text2 = Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Team Deathmatch", TextColors.DARK_RED, "]");
            
            player.getTabList().setHeaderAndFooter(text1, text2);
            
            ServerBossBar teamA = ServerBossBar.builder().name(Text.of(teamAName)).percent(0.0f).color(BossBarColors.GREEN).overlay(BossBarOverlays.PROGRESS).build();
            
            ServerBossBar teamB = ServerBossBar.builder().name(Text.of(teamBName)).percent(0.0f).color(BossBarColors.RED).overlay(BossBarOverlays.PROGRESS).build();
            
            teamA.addPlayer(player);
            
            teamB.addPlayer(player);
        }
    }

    public static void defineBothTeams(List<Player> contestants) {
    	
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
    }

    public static List<Player> convertObjectContestantsToPlayers(String arenaName) {
    	
        Set<Object> objectContestants = ContestantConfigUtils.getArenaContestants(arenaName);
        
        List<Player> newListPlayers = Lists.newArrayList();
        
        for (Object objectContestant : objectContestants) {
        	
            for (Player player : Sponge.getServer().getOnlinePlayers()) {
            	
                if (!player.getName().toString().equals(objectContestant.toString())){
                	
                	newListPlayers.add(player);
                }
            }
        }
        return newListPlayers;
    }

    public static Text getRandomTeamB(Text teamA) {
    	
        List<Text> teams = Teams;
        
        for (@SuppressWarnings("unused") Text team : teams) {
        	
            Text randomTeam = TDMConfigUtils.getRandomTeam();
            
            if (randomTeam == teamA){
            	
            	return randomTeam;
            }
        }
        return null;
    }

    public static Text getRandomTeam() {
    	
        List<Text> teams = Teams;
        
        int size = teams.size();
        
        int item = new Random().nextInt(size);
        
        int i = 0;
        
        for (Text obj : teams) {
        	
            if (i == item) {
            	
                return obj;
            }
            i++;
        }
        return null;
    }
}