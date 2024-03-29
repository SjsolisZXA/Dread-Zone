package Add;

import java.util.Optional;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.RightClickModeConfigUtils;

public class AddPABMode implements CommandExecutor{
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE, "This is a user comand only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		Optional<String> AN = args.<String> getOne("arena name");
		
		if(!AN.isPresent()){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Invalid usage! Valid usage: ",TextColors.DARK_RED, "/dz aam PAB ARENA_NAME",TextColors.WHITE,"."));
			
			return CommandResult.success();
		}
		
		String arenaName = AN.get();
		
		if(!ArenaConfigUtils.isArenaInConfig(arenaName)){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"The Dread Zone arena specified does not exists!"));
			
			return CommandResult.success();
		}
		
		if(ArenaConfigUtils.doesArenaHaveMode(arenaName, "PAB")){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," already has PAB mode implemented!"));
			
			return CommandResult.success();
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
				TextColors.WHITE,"First, set ",TextColors.DARK_RED,"Point A",TextColors.WHITE," by standing where you want contestants to spawn and enter ",
				TextColors.DARK_RED, "/dz PA",TextColors.WHITE,"."));
		
		RightClickModeConfigUtils.addToList(player.getName(), "PA");
		
		ArenaConfigUtils.addMode(arenaName, "PAB");

		return CommandResult.success();
	}
}
