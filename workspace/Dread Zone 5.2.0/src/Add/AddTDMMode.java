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
import ConfigUtils.TDMConfigUtils;
import Modes.TDM;

public class AddTDMMode implements CommandExecutor{
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE, "This is a user comand only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		Optional<String> AN = args.<String> getOne("arena name");
		Optional<Integer> ONOMPT = args.<Integer> getOne("number of players per team");
		
		if(!ONOMPT.isPresent()||!AN.isPresent()){
			
            src.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                    TextColors.WHITE, "Invalid usage! Usage: ",TextColors.DARK_RED,"/dz aam TDM ARENA_NAME NUM_OF_PLAYERS_PER_TEAM",TextColors.WHITE,
                    ". To get a list of Dread Zone arenas, enter ",TextColors.DARK_RED,"/dz al",TextColors.WHITE,"."));
            
            return CommandResult.success();
		}
		
		String arenaName = AN.get();
		
		if(!ArenaConfigUtils.isArenaInConfig(arenaName)){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"The Dread Zone arena specified does not exists! "
							+ "To get a list of Dread Zone arenas, enter ",TextColors.DARK_RED,"/dz al",TextColors.WHITE,"."));
			
			return CommandResult.success();
		}
		
		if(ArenaConfigUtils.doesArenaHaveMode(arenaName, "TDM")){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," already has TDM mode implemented!"));
			
			return CommandResult.success();
		}
		
		RightClickModeConfigUtils.addToList(player.getName(), "TDM");
		
		int nOMPT = ONOMPT.get();
		ArenaConfigUtils.setIntCurrent(arenaName, 0);
		ArenaConfigUtils.setIntGoal(arenaName, nOMPT*2);
		TDM.SATS(arenaName,player);
		TDMConfigUtils.setPointWin(arenaName, 40);
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Stand where you want players from ",TextColors.DARK_RED,"Team A",TextColors.WHITE," "
						+ "to spawn in the arena and enter: ",TextColors.DARK_RED,"/dz aasp SPAWN_NAME",TextColors.WHITE,"."));
	
		return CommandResult.success();
	}
}
