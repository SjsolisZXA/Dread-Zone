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
import ConfigUtils.LobbyConfigUtils;

public class SetLobbySpawn implements CommandExecutor{
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.RED, "This is a user comand only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		Optional<String> AN = args.<String> getOne("arena name");
		
		if(!AN.isPresent()){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Invalid usage! Valid usage: ",TextColors.DARK_RED, "/dz slsp ARENA_NAME",TextColors.WHITE,"."));
			
			return CommandResult.success();
		}
		
		String arenaName = AN.get();
		
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
						+ "You must add at least one of the following Dread Zone arena modes."));
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"To add a Team Deathmatch mode, enter: ",TextColors.DARK_RED,"/dz aam TDM ",arenaName," "
						+ "NUMBER_OF_PLAYERS_PER_TEAM",TextColors.WHITE,"."));
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"To add a Point A Point B mode, enter: ",TextColors.DARK_RED,"/dz aam PAB ",arenaName,TextColors.WHITE,"."));

		return CommandResult.success();
	}
}
