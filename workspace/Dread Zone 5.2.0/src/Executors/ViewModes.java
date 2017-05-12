package Executors;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;

public class ViewModes implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		Optional<String> AN = args.<String> getOne("arena name");
		
		if(!AN.isPresent()){
			
            src.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                    TextColors.WHITE, "Invalid usage! Usage: ",TextColors.DARK_RED,"/dz am ARENA_NAME",TextColors.WHITE,
                    ". To get a list of Dread Zone arenas, enter ",TextColors.DARK_RED,"/dz al",TextColors.WHITE,"."));
            
            return CommandResult.success();
		}
		
		String arenaName = args.<String> getOne("arena name").get();
		
		if(ArenaConfigUtils.getNumOfArenaModes(arenaName)==1){
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Mode available for ",TextColors.DARK_RED,arenaName,TextColors.WHITE,": ",
					TextColors.DARK_RED,ArenaConfigUtils.getArenaModes(arenaName)));
			
			return CommandResult.success();
		}

		src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
				TextColors.WHITE,"Modes available for ",TextColors.DARK_RED,arenaName,TextColors.WHITE,": ",
				TextColors.DARK_RED,ArenaConfigUtils.getArenaModes(arenaName)));
		
		return CommandResult.success();
	}

}
