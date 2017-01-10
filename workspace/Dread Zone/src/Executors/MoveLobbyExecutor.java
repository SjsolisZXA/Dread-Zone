package Executors;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.LobbyConfigUtils;
import ConfigUtils.RightClickModeConfigUtils;
import Listeners.RightClickMode;

public class MoveLobbyExecutor implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.RED, "This is a user command only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		String arenaName = args.<String> getOne("arena name").get();
		
		if(ArenaConfigUtils.isArenaInConfig(arenaName)){
		
			if (LobbyConfigUtils.isLobbyInConfig(arenaName,arenaName+"Lobby"))
			{
				LobbyConfigUtils.deleteLobby(arenaName);
			}
				
			RightClickModeConfigUtils.addToList(player.getName(),"CAL");
			
			RightClickMode.SAAN(arenaName);
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"Right click on a block to be a floor corner of ",TextColors.DARK_RED,arenaName,"'s",TextColors.WHITE," lobby."));
			
			return CommandResult.success();

		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Dread Zone arena ", TextColors.DARK_RED, arenaName, TextColors.WHITE, " does not exist!"));
		
		return CommandResult.success();
	}
}
