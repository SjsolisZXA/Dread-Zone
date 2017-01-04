package Executors;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import ConfigUtils.ArenaConfigUtils;

public class TestExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		//Player player =(Player)src;	
		
		ArenaConfigUtils.getLobbySpawnLocation("NukeTown");
		
		//player.sendMessage(Text.of(TextColors.DARK_RED,ArenaConfigUtils.getLobbySpawnLocationRotation("NukeTown")));
		
		return CommandResult.success();
	}
}


