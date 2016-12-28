package Delete;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.LobbyConfigUtils;

public class DeleteLobby implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.RED, "This is a user command only!"));
			
			return CommandResult.success();
		}
		
		String lobbyName = args.<String> getOne("lobby name").get();
		
		if (LobbyConfigUtils.isLobbyInConfig(lobbyName))
		{
			LobbyConfigUtils.deleteLobby(lobbyName);
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"Success, DZ Lobby ", TextColors.DARK_RED, lobbyName, TextColors.WHITE, " removed!"));
			
			return CommandResult.success();
		}
		
		src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Error, DZ Lobby ", TextColors.DARK_RED, lobbyName, TextColors.WHITE, " not found!"));
		
		return CommandResult.success();
	}
}
