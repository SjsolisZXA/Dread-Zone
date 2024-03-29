package Reset;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.MobCreateConfigUtils;

public class ResetMobCreate implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		MobCreateConfigUtils.resetMobCreateBoolean();
		
		src.sendMessage(Text.of(TextColors.GREEN,"DZ Arena Mob Create spawn points reset!"));
		
		return CommandResult.success();
	}

}
