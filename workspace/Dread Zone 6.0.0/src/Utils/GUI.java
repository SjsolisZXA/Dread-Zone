package Utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.boss.BossBarOverlays;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.tab.TabList;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.text.title.Title;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import ConfigUtils.ContestantConfigUtils;

public class GUI {
	
	public static Map<String,Map<String,ServerBossBar>> aTeamBars = Maps.newHashMap();
	static Map<String,ServerBossBar> aTBInnerMap = Maps.newHashMap();

	public static Map<String,Map<String,ServerBossBar>> bTeamBars = Maps.newHashMap();
	static Map<String,ServerBossBar> bTBInnerMap = Maps.newHashMap();
	
    public static void setUpTDMGUI(String arenaName, List<Player> teamContestants, List<Player> otherContestants, Text teamName, Text teamName1, Text teamName2) {

    	List<String> TeamContestants = Lists.newArrayList();
    	
    	for(Player pl: teamContestants){
    		
    		TeamContestants.add(pl.getName());
    	}
    		
        for (Player player : teamContestants) {

        	player.sendMessage(Text.of(teamName, TextColors.DARK_GRAY," Team: ",TextColors.DARK_RED,TeamContestants));
        	
        	//sets up a scoreboard
            Scoreboard scoreboard = Scoreboard.builder().build();
            
            //sets up team name tag below username display name
            Objective teamTagNameObjective = Objective.builder().name(teamName.toPlain()).displayName(Text.of(teamName)).criterion(Criteria.DUMMY).build();
            
            scoreboard.addObjective(teamTagNameObjective);
            scoreboard.updateDisplaySlot(teamTagNameObjective, DisplaySlots.BELOW_NAME);
            
            //sets up kills death scoreboard
            Objective objective = Objective.builder().name("Stats").displayName(Text.of((String.valueOf(player.getName()) + " Stats"))).criterion(Criteria.DUMMY).build();
            
            scoreboard.addObjective(objective);
            scoreboard.updateDisplaySlot(objective, DisplaySlots.SIDEBAR);
            objective.getOrCreateScore(Text.of("Kills")).setScore(0);
            objective.getOrCreateScore(Text.of("Deaths")).setScore(0);
            player.setScoreboard(scoreboard);
            
            //sets up colored tab list team names
            Collection<Player> SP = Sponge.getServer().getOnlinePlayers();
            TabList PTL = player.getTabList();

            for(Player p: SP){
            	
            	if(!ContestantConfigUtils.isUserAnArenaContestant(arenaName, p.getName())){
            		
            		PTL.removeEntry(p.getUniqueId());
            	}
            }
            
            for(Player TCP: teamContestants){
            	
                Text playerName = Text.of(" "+TCP.getName());
                Text TLN = Text.join(new Text[] { teamName, playerName });
            	PTL.getEntry(TCP.getUniqueId()).get().setDisplayName(TLN);
            }
            
            for(Player OTCP: otherContestants){
            	
                Text playerName = Text.of(" "+OTCP.getName());
            	
            	if(!teamName.equals(teamName1)){
            		
                    Text OTLN1 = Text.join(new Text[] { teamName1, playerName });
            		PTL.getEntry(OTCP.getUniqueId()).get().setDisplayName(OTLN1);
            	}
            	if(!teamName.equals(teamName2)){
            		
            		Text OTLN2 = Text.join(new Text[] { teamName2, playerName });
            		PTL.getEntry(OTCP.getUniqueId()).get().setDisplayName(OTLN2);
            	}
            }
            
            Text text1 = Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "]");
            Text text2 = Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Team Deathmatch", TextColors.DARK_RED, "]");
            player.getTabList().setHeaderAndFooter(text1, text2);
            
            //sets up boss bar team point count
            ServerBossBar teamA = ServerBossBar.builder().name(Text.of(teamName1)).percent(0.0f).color(BossBarColors.GREEN).overlay(BossBarOverlays.PROGRESS).build();
            ServerBossBar teamB = ServerBossBar.builder().name(Text.of(teamName2)).percent(0.0f).color(BossBarColors.RED).overlay(BossBarOverlays.PROGRESS).build();
            
            teamA.addPlayer(player);
            aTBInnerMap.put(player.getName(), teamA);
            aTeamBars.put(arenaName,aTBInnerMap);
            
            teamB.addPlayer(player);
            bTBInnerMap.put(player.getName(), teamB);
            bTeamBars.put(arenaName,bTBInnerMap);
        }
    }
    
	public static void setupPABScoreboards(List<Player> contestants) {

        Scoreboard scoreboard = Scoreboard.builder().build();
        
        for(Player player:contestants){
        
	        Objective objective = Objective.builder().name("Stats").displayName(Text.of(TextColors.DARK_RED,
	        		TextStyles.UNDERLINE,(String.valueOf(player.getName()) + " Kills"))).criterion(Criteria.DUMMY).build();	        
	        scoreboard.addObjective(objective);	        
	        scoreboard.updateDisplaySlot(objective, DisplaySlots.SIDEBAR);	        
	        objective.getOrCreateScore(Text.of("Total Kills")).setScore(0);	        
	        objective.getOrCreateScore(Text.of("Deaths")).setScore(0);	        
	        player.setScoreboard(scoreboard);
	        
	        Text text1 = Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "]");	        
	        Text text2 = Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Challange", TextColors.DARK_RED, "]");
	        
	        player.getTabList().setHeaderAndFooter(text1, text2);
        }
	}
	
	public static void removeGUI(Player contestant, ServerBossBar aBar, ServerBossBar bBar, Title MT){
		
		aBar.removePlayer(contestant);
		bBar.removePlayer(contestant);
		
		contestant.getScoreboard().removeObjective(contestant.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get());
		contestant.getScoreboard().removeObjective(contestant.getScoreboard().getObjective(DisplaySlots.BELOW_NAME).get());
		
		contestant.sendTitle(MT);
	}
}
