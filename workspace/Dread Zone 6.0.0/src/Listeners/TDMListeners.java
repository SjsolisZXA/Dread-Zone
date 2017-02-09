package Listeners;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.text.Text;

public class TDMListeners {
	
    @Listener
    public void whenOponentIsKilled(DestructEntityEvent.Death event, @First Player player) {        
        
		int score = player.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get().getScore(Text.of("Kills")).get().getScore();
		
		player.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get().getScore(Text.of("Kills")).get().setScore(score+1);
		
    }
}
	
