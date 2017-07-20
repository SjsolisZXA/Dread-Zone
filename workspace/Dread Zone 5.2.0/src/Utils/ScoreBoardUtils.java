package Utils;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;

public class ScoreBoardUtils {
	
	public static void addDeathScore(Player player){

    	Score VSCB = player.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get().getScore(Text.of("Deaths")).get();
        int deathScore = VSCB.getScore();
        
        VSCB.setScore(deathScore + 1);
	}

	public static void addKillScore(Player player){
		
        Score KSCB = player.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get().getScore(Text.of("Kills")).get();
        int killScore = KSCB.getScore();
        
        KSCB.setScore(killScore + 1);
	}
	
	public static void addMonsterKillScore(Player player, EntityType monster){
		
		Objective playerScoreboard = player.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get();
		Score totalKillsScore = playerScoreboard.getScore(Text.of("Total Kills")).get();
        int totalScore =  totalKillsScore.getScore();

        totalKillsScore.setScore(totalScore + 1);
        
        if(playerScoreboard.getScore(Text.of(monster)).isPresent()){
        	
        	Score monsterKillScore = playerScoreboard.getScore(Text.of(monster)).get();
            int monsterScore =  monsterKillScore.getScore();

            monsterKillScore.setScore(monsterScore + 1);
            return;
        }
        
        playerScoreboard.getOrCreateScore(Text.of(monster)).setScore(1);
	}
}
