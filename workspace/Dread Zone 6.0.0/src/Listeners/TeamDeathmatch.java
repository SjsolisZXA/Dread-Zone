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

public class TeamDeathmatch {
	int teamGKills = 0;
	int teamBKills= 0;
	
    @Listener
    public void whenKillsAnotherPlayer(DestructEntityEvent.Death event,@First Player player) {
    	
        //player.sendMessage(Text.of(TextColors.DARK_GRAY, "<",TextColors.RED,"Spetsnaz",TextColors.DARK_GRAY, ">"));  //DONE
        player.sendMessage(Text.of(TextColors.DARK_RED, "<",TextColors.DARK_AQUA,"Tropas",TextColors.DARK_RED, ">"));
        player.sendMessage(Text.of(TextColors.GOLD, "<",TextColors.RED,"N.V.A",TextColors.GOLD, ">"));
        
        player.sendMessage(Text.of(TextColors.GRAY, "<",TextColors.BLUE,"Black Ops",TextColors.GRAY, ">"));
        player.sendMessage(Text.of(TextColors.GRAY, "<",TextColors.DARK_GREEN,"S.O.G",TextColors.GRAY, ">"));
        player.sendMessage(Text.of(TextColors.BLUE, "<",TextColors.WHITE,"Op 40",TextColors.BLUE, ">"));
        
        
        Scoreboard scoreboard = Scoreboard.builder().build();
        Objective objective = Objective.builder().name("Team")
        		.displayName(Text.of(TextColors.DARK_GRAY, "<",TextColors.RED,"Spetsnaz",TextColors.DARK_GRAY, ">"))
        		.criterion(Criteria.DUMMY).build();

        scoreboard.addObjective(objective);
        scoreboard.updateDisplaySlot(objective, DisplaySlots.BELOW_NAME);

        for (Player player0: Sponge.getServer().getOnlinePlayers()) {
            objective.getOrCreateScore(player0.getTeamRepresentation()).setScore(123);
        }

        for (Player player0: Sponge.getServer().getOnlinePlayers()) {
            player0.setScoreboard(scoreboard);
        }
    }
    
    
}
	
