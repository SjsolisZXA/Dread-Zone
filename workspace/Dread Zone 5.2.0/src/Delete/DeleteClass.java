package Delete;

import java.util.Optional;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;

public class DeleteClass implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE, "This is a user command only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		Optional<String> CN = args.<String> getOne("class name");
		Optional<String> AN = args.<String> getOne("arena name");
		
		if(!AN.isPresent()||!CN.isPresent()){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Invalid usage! Valid usage: ",TextColors.DARK_RED, "/dz rc CLASS_NAME ARENA_NAME",TextColors.WHITE,"."));
			
			return CommandResult.success();
		}
		
		String className = CN.get();
		String arenaName = AN.get();
		
		if(ArenaConfigUtils.isArenaInConfig(arenaName)){
		
			if (ArenaConfigUtils.getArenaGrandchildInConfig(arenaName, "ArenaClasses", className)!=null){
				
				ArenaConfigUtils.removeArenaGrandchild(arenaName, "ArenaClasses", className);
				
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
						TextColors.WHITE,"Success, ", TextColors.DARK_RED, className, TextColors.WHITE, " class removed!"));
				
				return CommandResult.success();
			}
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"Error, ", TextColors.DARK_RED, className, TextColors.WHITE, " class does not exist!"));
			
			return CommandResult.success();
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Arena ", TextColors.DARK_RED, arenaName, TextColors.WHITE, " does not exist!"));
		
		return CommandResult.success();
	}	
}
