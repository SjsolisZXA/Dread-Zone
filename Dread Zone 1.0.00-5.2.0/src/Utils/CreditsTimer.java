package Utils;

import java.util.List;
import java.util.function.Consumer;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ContestantConfigUtils;

public class CreditsTimer implements Consumer<Task> {
	
    int SECONDS;
    List<Player> CONTESTANTS;
    String ARENA_NAME;

    public CreditsTimer(String arenaName, List<Player> contestants, int seconds) {
    	
        this.CONTESTANTS = contestants;
        this.ARENA_NAME = arenaName;
        this.SECONDS = seconds;

    }

    @Override
    public void accept(Task task) {
       
        SECONDS--;
        
        if (SECONDS < 1) {
        	
            for (Player contestant : CONTESTANTS) {
            	
            	contestant.getTabList().setHeaderAndFooter(null, null);
                ArenaConfigUtils.setCreditsMode(ARENA_NAME, false);
                ContestantConfigUtils.removeContestant(ARENA_NAME, contestant);
            }

            task.cancel();
        }
    }
}