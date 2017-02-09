package Utils;

import java.util.function.Consumer;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Title;

import Executors.TestExecutor;

public class TeamDeathmatchTimerTask implements Consumer<Task> {
    private int seconds = 10;
    private Player player;
    private static Boolean TOF = false;
    public static TestExecutor TE;
    Title subT = Title.builder().subtitle(Text.of("Match Begins in:")).stay(230).build();
    
	public static boolean isFreezingPlayers() {
		return TOF;
	}
		
    public TeamDeathmatchTimerTask(CommandSource source, CommandContext commandContext) {
    	player = (Player)source;
    	player.sendTitle(subT);
    	TOF = true;
	}
    
	@Override
    public void accept(Task task) {

		player.sendMessage(ChatTypes.ACTION_BAR, Text.of(TextColors.YELLOW,seconds));
		
		seconds--;
  
        if(seconds < 1) {
        	TOF = false;
            task.cancel();
        }
    }
}