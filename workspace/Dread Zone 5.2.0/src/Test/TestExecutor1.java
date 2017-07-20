package Test;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class TestExecutor1 implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		Player player = (Player)src;
		
		player.sendMessage(Text.of(Main.Main.getPluginContainer().getId()+".dz"));
		player.sendMessage(Text.of(player.hasPermission(Main.Main.getPluginContainer().getId()+".dz")));
		
		return CommandResult.success();
	}

}
