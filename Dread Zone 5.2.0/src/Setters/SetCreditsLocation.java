package Setters;

import java.util.Optional;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;

public class SetCreditsLocation implements CommandExecutor{
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
	
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE, "This is a user command only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		Optional<String> AN = args.<String> getOne("arena name");
		
		if(!AN.isPresent()){
			
            player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                    TextColors.WHITE, "Invalid usage! Usage: ",TextColors.DARK_RED,"/dz scl ARENA_NAME",TextColors.WHITE,
                    ". To get a list of Dread Zone arenas, enter ",TextColors.DARK_RED,"/dz al",TextColors.WHITE,"."));
			
			return CommandResult.success();
		}
		
		String arenaName = AN.get();
		
		if(!ArenaConfigUtils.isArenaInConfig(arenaName)){
			
            player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                    TextColors.WHITE, "The arena you have specified does not exist! To get a list of Dread Zone arenas, enter ",
                    TextColors.DARK_RED,"/dz al",TextColors.WHITE,"."));
			
			return CommandResult.success();
		}
		
		if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation()).equals(null)){
			
            player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                    TextColors.WHITE, "You're not inside a Dread Zone arena!"));
			
			return CommandResult.success();
		}
		
		if(!ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation()).equals(arenaName)){
			
            player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                    TextColors.WHITE, "You must be inside Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," to execute this command!"));
			
			return CommandResult.success();
		}
		
		ArenaConfigUtils.setCreditsLocation(arenaName, player.getTransform());
		
        player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                TextColors.WHITE, "Credits location for Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," set!"));
		
        return CommandResult.success();
	}

}
