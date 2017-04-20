package Utils;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ScoreBoardUtils {
	
	public static void addDeathScore(Player player){

    	Score VSCB = player.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get().getScore(Text.of("Deaths")).get();
        int deathScore = VSCB.getScore();
        
        Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_GREEN,"Victim Death Score: ",deathScore+1));
        
        VSCB.setScore(deathScore + 1);
	}

	public static void addKillScore(Player player){
		
        Score KSCB = player.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get().getScore(Text.of("Kills")).get();
        int killScore = KSCB.getScore();
        
        Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GOLD,"Victim Death Score: ",killScore+1));
        
        KSCB.setScore(killScore + 1);
	}
}
