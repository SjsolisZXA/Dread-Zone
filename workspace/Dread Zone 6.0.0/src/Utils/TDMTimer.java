package Utils;

import java.util.List;
import java.util.function.Consumer;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Title;

import com.google.common.collect.Lists;

public class TDMTimer implements Consumer<Task> {
	
    int S;
    
    List<Player> lPlayers = Lists.newArrayList();
    
    Text TEAM_A_NAME;
    Text TEAM_B_NAME;
    
    List<Player> TEAM_A_CONTESTANTS;
    List<Player> TEAM_B_CONTESTANTS;
    
    static Boolean TOF = false;
    
    String ARENA_NAME;
    
    Title subT = Title.builder().subtitle(Text.of("Match Begins in:")).stay(200).build();

    public TDMTimer(String arenaName, List<Player> players, Text teamAName, Text teamBName, List<Player> teamAContestants, List<Player> teamBContestants, int seconds) {
    	
    	this.TEAM_A_NAME = teamAName;
        this.TEAM_B_NAME = teamBName;
        
        this.TEAM_A_CONTESTANTS = teamAContestants;
        this.TEAM_B_CONTESTANTS = teamBContestants;
        
        this.ARENA_NAME = arenaName;
        
        this.S = seconds;
        
        for (Player p : players) {
        	
            p.sendTitle(subT);
            
            lPlayers.add(p);
        }
    }

    public static boolean isFreezingPlayers() {
    	
        return TOF;
    }

    @Override
    public void accept(Task task) {
    	
    	TOF = true;
        
        for (Player player : lPlayers) {
        	
            player.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.YELLOW, S));
        }
        
        S--;
        
        if (S < 0) {
        	
            TOF = false;
            
            task.cancel();
        	        	
            GUI.setUpTDMGUI(ARENA_NAME, TEAM_A_CONTESTANTS, TEAM_B_CONTESTANTS, TEAM_A_NAME, TEAM_A_NAME, TEAM_B_NAME);
            GUI.setUpTDMGUI(ARENA_NAME, TEAM_B_CONTESTANTS, TEAM_A_CONTESTANTS, TEAM_B_NAME, TEAM_A_NAME, TEAM_B_NAME);

        }
    }
}