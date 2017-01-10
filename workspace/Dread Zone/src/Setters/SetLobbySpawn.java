package Setters;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.LobbyConfigUtils;

public class SetLobbySpawn implements CommandExecutor{
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.RED, "This is a user comand only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		String arenaName = args.<String> getOne("arena name").get();
		
		if(!ArenaConfigUtils.isArenaInConfig(arenaName)){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"The Dread Zone arena specified does not exists!"));
			
			return CommandResult.success();
		}
		
		if(!LobbyConfigUtils.isUserinLobby(player.getLocation(), arenaName)){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"You must be in the specified arena's lobby to execute this commmand!"));
			
			return CommandResult.success();
		}
		
		LobbyConfigUtils.setLobbySpawn(player.getTransform(), arenaName, arenaName+"Lobby");
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Sucess, ",TextColors.DARK_RED,arenaName+"Lobby",TextColors.WHITE," spawn point set! "
						+ "Although the arena and lobby have been created, you must create idividual modes for your arenas. "
						+ "To do so, enter ",TextColors.DARK_RED,"/cdzam ",arenaName));

		return CommandResult.success();
	}
}
