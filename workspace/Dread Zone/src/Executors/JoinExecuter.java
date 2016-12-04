package Executors;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class JoinExecuter implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException{
		if(!(src instanceof Player)){
			src.sendMessage(Text.of(TextColors.RED, "Console can't join combat."));
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"]",
				TextColors.WHITE," Choose your class ",TextColors.DARK_RED, player.getName(),"!"));
		
		Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
				TextColors.DARK_RED, player.getName(),TextColors.WHITE," joined Dread Zone Arena",TextColors.DARK_RED," ARENA NAME!"));
		
		Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
				TextColors.WHITE, "To join, enter",TextColors.DARK_RED," /dzjoin ARENA NAME!"));
		return CommandResult.success();
		/**
		 * 
		 * A method in which the player's inventory is saved, cleared and, and given back, once they leave the arena.
		 * 
		 * new Arena();???
		 * 
		 */
	}
}
