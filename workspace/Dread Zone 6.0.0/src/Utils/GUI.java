package Utils;

import java.util.List;

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
import org.spongepowered.api.text.format.TextStyles;

public class GUI {

    public static void setUpTDMGUI(List<Player> teamContestants, Text teamName, Text teamName1, Text teamName2) {

        for (Player player : teamContestants) {
        	
        	//player.sendMessage(Text.of(TextColors.AQUA,player.getName()," ",teamName));
        	
        	//player.sendMessage(Text.of(TextColors.RED,teamContestants));
        	
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
            /**for (Player playerOnline : Sponge.getServer().getOnlinePlayers()) {
            	
            	if(ArenaConfigUtils.getUserArenaNameFromLocation(playerOnline.getLocation())!=null){
            		
            		String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(playerOnline.getLocation());
            	
	                if (ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())){
	                	
	                	player.getTabList().removeEntry(playerOnline.getUniqueId());
	                }
            	}
            }**/
            
            //player.getTabList().getEntry(player.getUniqueId()).get().setDisplayName(Text.of(player.getName()));// come back and change this once we get team color
            
            Text text1 = Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "]");
            
            Text text2 = Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Team Deathmatch", TextColors.DARK_RED, "]");
            
            player.getTabList().setHeaderAndFooter(text1, text2);
            
            //sets up boss bar team point count
            ServerBossBar teamA = ServerBossBar.builder().name(Text.of(teamName1)).percent(0.0f).color(BossBarColors.GREEN).overlay(BossBarOverlays.PROGRESS).build();
            
            ServerBossBar teamB = ServerBossBar.builder().name(Text.of(teamName2)).percent(0.0f).color(BossBarColors.RED).overlay(BossBarOverlays.PROGRESS).build();
            
            teamA.addPlayer(player);
            
            teamB.addPlayer(player);
        }
    }
    
	public static void setupPABScoreboards(List<Player> contestants) {

        Scoreboard scoreboard = Scoreboard.builder().build();
        
        for(Player player:contestants){
        
	        Objective objective = Objective.builder().name("Stats").displayName(Text.of(TextColors.DARK_RED,TextStyles.UNDERLINE,(String.valueOf(player.getName()) + " Kills"))).criterion(Criteria.DUMMY).build();
	        
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
}
