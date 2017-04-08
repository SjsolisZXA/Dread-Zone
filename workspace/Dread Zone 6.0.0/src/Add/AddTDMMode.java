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
			
			src.sendMessage(Text.of(TextColors.RED, "This is a user comand only!"));
			
			return CommandResult.success();
		}
		
		String arenaName = args.<String> getOne("arena name").get();
		
		Player player = (Player)src;
		
		if(!ArenaConfigUtils.isArenaInConfig(arenaName)){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"The Dread Zone arena specified does not exists! "
							+ "To get a list of Dread Zone arenas, enter ",TextColors.DARK_RED,"/dz al",TextColors.WHITE,"."));
			
			return CommandResult.success();
		}

		Optional<Integer> ONOMPT = args.<Integer> getOne("number of players per team");//numberOfMemebrsPerTeam
		
		if(!ONOMPT.isPresent()){
			
            src.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                    TextColors.WHITE, "Invalid usage! Usage: ",TextColors.DARK_RED,"/dz aam ARENA_NAME",TextColors.WHITE,
                    ". To get a list of Dread Zone arenas, enter ",TextColors.DARK_RED,"/dz al",TextColors.WHITE,"."));
            
            return CommandResult.success();
		}
		
		//the user is not actually going to be in a right click mode, but this will help the user set player spawn points
		RightClickModeConfigUtils.addToList(player.getName(), "TDM");
		
		int nOMPT = args.<Integer> getOne("number of players per team").get();
		
		ArenaConfigUtils.setIntCurrent(arenaName, 0);
		
		ArenaConfigUtils.setIntGoal(arenaName, nOMPT*2);
		
		TDM.SATS(arenaName,player);
		
		TDMConfigUtils.setPointWin(arenaName, 40);
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Stand where you want players from Team A to spawn in the arena and enter ",TextColors.DARK_RED,"/dzasp SPAWN_NAME"));
	
		return CommandResult.success();
	}
}
