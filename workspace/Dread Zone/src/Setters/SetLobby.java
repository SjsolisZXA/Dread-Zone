package Setters;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.LobbyConfigUtils;
import ConfigUtils.RightClickModeConfigUtils;
import Listeners.RightClickMode;

public class SetLobby implements CommandExecutor{
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		if(!(src instanceof Player)){
			src.sendMessage(Text.of(TextColors.RED, "This is a user comand only!"));
			return CommandResult.success();
		}
		Player player = (Player)src;
				
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Right click on a block to be a floor corner of the lobby."));
		
		RightClickModeConfigUtils.addToList(player.getName(),"CL");
		
		String lobbyName = args.<String> getOne("lobby name").get();
		
		if(LobbyConfigUtils.isLobbyInConfig(lobbyName)){
			
			LobbyConfigUtils.deleteLobby(lobbyName);
		}
		
		RightClickMode.SLLN(lobbyName);

		return CommandResult.success();
	}
}
