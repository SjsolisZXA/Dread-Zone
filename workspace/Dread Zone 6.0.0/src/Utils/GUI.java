package Utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.boss.BossBarColor;
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

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import ConfigUtils.TDMConfigUtils;

public class GUI {
	
	public static Map<String,Map<String,ServerBossBar>> aTeamBars = Maps.newHashMap();
	static Map<String,ServerBossBar> aTBInnerMap = Maps.newHashMap();

	public static Map<String,Map<String,ServerBossBar>> bTeamBars = Maps.newHashMap();
	static Map<String,ServerBossBar> bTBInnerMap = Maps.newHashMap();
	
    static Text text1 = Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "]");
    static Text text2 = Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Team Deathmatch", TextColors.DARK_RED, "]");
	
    public static void setUpAllTDMGUI(String arenaName, List<Player> teamContestants, List<Player> otherContestants, Text teamName, Text teamName1, Text teamName2) {

        //sets up boss bar team point count
        ServerBossBar teamA = ServerBossBar.builder()
        		.name(Text.of(teamName1))
        		.percent(0.0f)
        		.color(BossBarColors.GREEN)
        		.overlay(BossBarOverlays.PROGRESS)
        		.build();
        
        ServerBossBar teamB = ServerBossBar.builder()
        		.name(Text.of(teamName2))
        		.percent(0.0f)
        		.color(BossBarColors.RED)
        		.overlay(BossBarOverlays.PROGRESS)
        		.build();
        
        ArenaConfigUtils.setMatchStatus(arenaName, true);
	
        for (Player player : teamContestants) {  	  
 
            universalTDMGUISetup(arenaName, teamA, teamB, player, teamContestants, otherContestants, teamName, teamName1, teamName2);
        }
    }
    
    public static void setUpIndividualTDMGUI(String arenaName, Player player){
    	
    	List<Player> contestants = TDMConfigUtils.convertObjectContestantsToPlayers(arenaName);
    	
    	contestants.remove(player);
    	
    	int numOfTAC=0;
    	int numOfTBC=0;
    	
		Map<String,ServerBossBar> b1 = bTeamBars.get(arenaName);
		Map<String,ServerBossBar> b0 = aTeamBars.get(arenaName);
						            				
		ServerBossBar aBar = b0.get(contestants.get(0).getName());//original Team A bar
		ServerBossBar bBar = b1.get(contestants.get(0).getName());//original Team B bar
		
		Text teamAName = aBar.getName();
		Text teamBName = bBar.getName();
		
		BossBarColor TABC = aBar.getColor();
		BossBarColor TBBC = bBar.getColor();
		
		float TAP = aBar.getPercent();
		float TBP = bBar.getPercent();
		
        ServerBossBar teamA = ServerBossBar.builder()
        		.name(Text.of(teamAName))
        		.percent(TAP)
        		.color(TABC)
        		.overlay(BossBarOverlays.PROGRESS)
        		.build();
        
        ServerBossBar teamB = ServerBossBar.builder()
        		.name(Text.of(teamBName))
        		.percent(TBP)
        		.color(TBBC)
        		.overlay(BossBarOverlays.PROGRESS)
        		.build();
		
		List<Player> teamAContestants = Lists.newArrayList();
		List<Player> teamBContestants = Lists.newArrayList();
    	
    	for(Player contestant: contestants){
    		
    		Text teamName = contestant.getScoreboard().getObjective(DisplaySlots.BELOW_NAME).get().getDisplayName();
    		
    		if(teamAName.equals(teamName)){
    			
    			numOfTAC++;
    			teamAContestants.add(contestant);
    		}
    		if(teamBName.equals(teamName)){
    			
    			numOfTBC++;
    			teamBContestants.add(contestant);
    		}
    	}
    	
    	if(numOfTAC<numOfTBC){
    		
    		teamAContestants.add(player);
    		universalTDMGUISetup(arenaName, teamA, teamB, player, teamAContestants, teamBContestants, teamAName, teamAName, teamBName);
    		
    		return;
    	}
    	
    	teamBContestants.add(player);
    	universalTDMGUISetup(arenaName, teamA, teamB, player, teamBContestants, teamAContestants, teamBName, teamAName, teamBName);
    	
    	return;
    }
    
    public static void universalTDMGUISetup(String arenaName, ServerBossBar aBar, ServerBossBar bBar, Player player, 
    		List<Player> teamContestants, List<Player> otherContestants, Text teamName, Text teamName1, Text teamName2){
    	
    	List<String> TeamContestants = Lists.newArrayList();
    	
    	for(Player pl: teamContestants){
    		
    		TeamContestants.add(pl.getName());
    	}
    	
    	player.sendMessage(Text.of(teamName, TextColors.DARK_GRAY," Team: ",TextColors.DARK_RED,TeamContestants));
    	
    	//setup boss bar
    	aBar.addPlayer(player);    	
        aTBInnerMap.put(player.getName(), aBar);
        aTeamBars.put(arenaName,aTBInnerMap);
        
    	bBar.addPlayer(player);
        bTBInnerMap.put(player.getName(), bBar);
        bTeamBars.put(arenaName,bTBInnerMap);
    	
        //sets up colored tab list team names
        player.getTabList().setHeaderAndFooter(text1, text2);
       
        Collection<Player> onlinePlayers = Sponge.getServer().getOnlinePlayers();
        TabList contestantTabList = player.getTabList();

        for(Player onlinePlayer: onlinePlayers){
        	
        	if(!ContestantConfigUtils.isUserAnArenaContestant(arenaName, onlinePlayer.getName())){
        		
        		contestantTabList.removeEntry(onlinePlayer.getUniqueId());
        	}
        }
        
        for(Player teamContestant: teamContestants){
        	
            Text playerName = Text.of(" "+teamContestant.getName());
            Text TLN = Text.join(new Text[] { teamName, playerName });
            contestantTabList.getEntry(teamContestant.getUniqueId()).get().setDisplayName(TLN);
        }
        
        for(Player OTCP: otherContestants){
        	
            Text playerName = Text.of(" "+OTCP.getName());
        	
        	if(!teamName.equals(teamName1)){
        		
                Text OTLN1 = Text.join(new Text[] { teamName1, playerName });
                contestantTabList.getEntry(OTCP.getUniqueId()).get().setDisplayName(OTLN1);
        	}
        	if(!teamName.equals(teamName2)){
        		
        		Text OTLN2 = Text.join(new Text[] { teamName2, playerName });
        		contestantTabList.getEntry(OTCP.getUniqueId()).get().setDisplayName(OTLN2);
        	}
        }
        
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
