package Executors;

import java.util.Optional;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.RightClickModeConfigUtils;

public class EditArena implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args){

		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.RED, "This is a user comand only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		Optional<String> AN = args.<String> getOne("arena name");
		
		if(!AN.isPresent()){
			
            player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                    TextColors.WHITE, "Invalid usage! Usage: ",TextColors.DARK_RED,"/dz ea ARENA_NAME",TextColors.WHITE,
                    ". To get a list of Dread Zone arenas, enter ",TextColors.DARK_RED,"/dz al",TextColors.WHITE,"."));
			
			return CommandResult.success();
		}
		
		String arenaName = args.<String> getOne("arena name").get();
		
		if(RightClickModeConfigUtils.isUserInConfig(player.getName())){
			
			if(RightClickModeConfigUtils.getUserMode(player.getName()).equals("EA - "+arenaName)){
				
				player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
		                TextColors.WHITE,"Edit mode for Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," disabled!"));
				
				RightClickModeConfigUtils.deleteUsernameInList(player.getName());
				
				return CommandResult.success();
			}
			
			player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                    TextColors.WHITE,"It seems like you were doing something else before trying to edit an arena. Please finish that up and try again."));
			
			return CommandResult.success();
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                TextColors.WHITE," Edit mode for Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," enabled!"));

		RightClickModeConfigUtils.addToList(player.getName(), "EA - "+arenaName);

		return CommandResult.success();
	}

}
