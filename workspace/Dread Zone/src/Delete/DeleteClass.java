package Delete;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;

public class DeleteClass implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
		String arenaName = args.<String> getOne("arena name").get();
		
		String className = args.<String> getOne("class name").get();
		
		if (ArenaConfigUtils.getArenaGrandchildInConfig(arenaName, "ArenaClasses", className)!=null){
			
			ArenaConfigUtils.removeArenaGrandchild(arenaName, "ArenaClasses", className);
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"Success, ", TextColors.DARK_RED, className, TextColors.WHITE, " class removed!"));
			
			return CommandResult.success();
		}
		
		src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Error, ", TextColors.DARK_RED, className, TextColors.WHITE, " class does not exist!"));
		
		return CommandResult.success();
	}	
}
