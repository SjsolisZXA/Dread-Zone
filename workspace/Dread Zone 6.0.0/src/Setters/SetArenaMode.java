package Setters;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.RightClickModeConfigUtils;
import Modes.TDM;

public class SetArenaMode implements CommandExecutor{
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.RED, "This is a user comand only!"));
			
			return CommandResult.success();
		}
		
		String arenaName = args.<String> getOne("arena name").get();
		
		Player player = (Player)src;
		
		if(!ArenaConfigUtils.isArenaInConfig(arenaName)){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"The Dread Zone arena specified does not exists!"));
			
			return CommandResult.success();
		}
		
		String mode = args.<String> getOne("mode").get();
				
		if(mode.toUpperCase().equals("TDM")){

			//the user is not actually going to be in a right click mode, but this will help the user set player spawn points
			RightClickModeConfigUtils.addToList(player.getName(), mode.toUpperCase());

			int nOMPT = args.<Integer> getOne("number of players per team").get();//numberOfMemebrsPerTeam
			
			ArenaConfigUtils.setIntCurrent(arenaName, 0);
			
			ArenaConfigUtils.setIntGoal(arenaName, nOMPT*2);
			
			TDM.SATS(arenaName);
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"Stand where you want players from Team A to spawn in the arena and enter ",TextColors.DARK_RED,"/dzasp SPAWN_NAME"));
	
			return CommandResult.success();
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"The mode specifed is not avaialable!"));
		
		return CommandResult.success();
	}
}