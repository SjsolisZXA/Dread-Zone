package Listeners;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class TDMListeners {
	int teamGKills = 0;
	int teamBKills= 0;
	
    @Listener
    public void whenKillsAnotherPlayer(DestructEntityEvent.Death event,@First Player player) {
          
        Scoreboard scoreboard = Scoreboard.builder().build();
        Objective objective = Objective.builder().name("Team")
        		.displayName(Text.of(TextColors.DARK_GRAY, "<",TextColors.RED,"Spetsnaz",TextColors.DARK_GRAY, ">"))
        		.criterion(Criteria.DUMMY).build();

        scoreboard.addObjective(objective);
        scoreboard.updateDisplaySlot(objective, DisplaySlots.BELOW_NAME);

        for (Player player0: Sponge.getServer().getOnlinePlayers()) {
            objective.getOrCreateScore(player0.getTeamRepresentation()).setScore(123);
        }
    }
    
    
}
	
