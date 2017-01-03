package Executors;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;

public class TestExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		Player player =(Player)src;	
		
		player.sendMessage(Text.of(TextColors.AQUA,ArenaConfigUtils.getLobbySpawnLocation("NukeTown"), TextColors.DARK_RED,ArenaConfigUtils.getLobbySpawnLocationRotation("NukeTown")));
		
		return CommandResult.success();
	}
}


