package Executors;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ArenaConfigUtils.LobbyConfigUtils;
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
		
		if (LobbyConfigUtils.isLobbyInConfig(arenaName,arenaName+"Lobby"))
		{
			LobbyConfigUtils.deleteLobby(arenaName);
			
			RightClickModeConfigUtils.addToList(player.getName(),"CAL");
			
			RightClickMode.SAAN(arenaName);
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"Right click on a block to be a floor corner of ",arenaName,"'s lobby."));
			
			return CommandResult.success();
		}
		
		src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Error, DZ Lobby ", TextColors.DARK_RED, arenaName, TextColors.WHITE, " not found!"));
		
		return CommandResult.success();
	}
}
