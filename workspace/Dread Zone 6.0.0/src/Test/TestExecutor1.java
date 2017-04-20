package Test;

import java.util.List;
import java.util.Map;

import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.TDMConfigUtils;
import Utils.GUI;

public class TestExecutor1 implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		Player player = (Player)src;
		
		List<Player> contestants = TDMConfigUtils.convertObjectContestantsToPlayers("bud");
		
		Map<String,ServerBossBar> b1 = GUI.bTeamBars.get("bud");
		Map<String,ServerBossBar> b0 = GUI.aTeamBars.get("bud");
		
		player.sendMessage(Text.of(TextColors.AQUA,"Number of Contestants in BossBar 0: ",b0.size()));
		player.sendMessage(Text.of(TextColors.YELLOW,"Number of Contestants in BossBar 1: ",b1.size()));
						            				
		ServerBossBar aBar = b0.get(contestants.get(0).getName());
		ServerBossBar bBar = b1.get(contestants.get(0).getName());
		
		return CommandResult.success();
	}

}
