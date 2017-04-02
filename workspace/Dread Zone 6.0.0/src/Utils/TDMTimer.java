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
	
    static int seconds = 10;
    
    static List<Player> lPlayers = Lists.newArrayList();
    
    Text TAN;
    
    Text TBN;
    
    List<Player> TAC;
    
    List<Player> TBC;
    
    static Boolean TOF = false;
    
    Title subT = Title.builder().subtitle(Text.of("Match Begins in:")).stay(230).build();

    public TDMTimer(List<Player> players, Text teamAName, Text teamBName, List<Player> teamAContestants, List<Player> teamBContestants) {
    	
    	TAN = teamAName;
        
        TBN = teamBName;
        
        TAC = teamAContestants;
        
        TBC = teamBContestants;
        
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
        	
            player.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.YELLOW, seconds));
        }
        
        seconds--;
        
        if (seconds < 1) {
        	
            GUI.setUpTDMGUI(TAC, TAN, TAN, TBN);
            
            GUI.setUpTDMGUI(TBC, TBN, TAN, TBN);
            
            TOF = false;
            
            task.cancel();
        }
    }
}