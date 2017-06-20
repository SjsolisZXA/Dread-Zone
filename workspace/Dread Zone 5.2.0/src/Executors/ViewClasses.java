package Executors;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;

public class ViewClasses implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE, "This is a user command only!"));
			
			return CommandResult.success();
		}
			
		Optional<String> AN = args.<String> getOne("arena name");
		
		Player player = (Player)src;
		
		if(!AN.isPresent()){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Invalid usage! Valid usage: ",TextColors.DARK_RED,"/dz cl ARENA_NAME.",
					TextColors.WHITE,"To view a list of arenas, enter: ",TextColors.DARK_RED,"/dz al",TextColors.WHITE,"."));
				
			return CommandResult.success();
		}
		
		String arenaName = AN.get();
			
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
				TextColors.WHITE,"Dread Zone classes available for arena ",TextColors.DARK_RED,arenaName,
				TextColors.WHITE,": ",TextColors.DARK_RED, ArenaConfigUtils.getArenaGrandchildren(arenaName,"ArenaClasses")));
			
		return CommandResult.success();
	}

}
