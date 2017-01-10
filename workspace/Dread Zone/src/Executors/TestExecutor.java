package Executors;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
//import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.LightningConfigUtils;

public class TestExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		//Player player =(Player)src;	
		
		Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED,LightningConfigUtils.getTargets()));
		
		Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED,LightningConfigUtils.getTargets().equals(null)));
		
		Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED,LightningConfigUtils.getTargets().isEmpty()));
		
		return CommandResult.success();
	}
}


