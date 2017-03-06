package Executors;

import java.util.Set;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class LeaveArena implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.RED, "This is a user command only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		Set<Object> arenas = ArenaConfigUtils.getArenas();
		
		for(Object arenaName : arenas){
			
			Set<Object> contestants = ContestantConfigUtils.getArenaContestants(arenaName);
			
			for(Object contestant: contestants){
				
				if((contestant).equals(player.getName())){		
					
					player.getInventory().clear();
					
					//restore player location
					player.setLocationAndRotation(ContestantConfigUtils.returnContestant(arenaName.toString(), player.getName().toString()), 
							ContestantConfigUtils.returnContestantTransform(arenaName.toString(), player.getName().toString()));
					
					//restore original food level
					player.offer(Keys.FOOD_LEVEL, ContestantConfigUtils.fetchOriginalFoodLevel(arenaName.toString(), player.getName()));
					
					//restore game mode
					try {
						 
						player.offer(player.getGameModeData().set(Keys.GAME_MODE,ContestantConfigUtils.fetchOriginalGamemode(arenaName.toString(), player.getName().toString())));
						
					} catch (ObjectMappingException e1) {
						
						e1.printStackTrace();
					}
					
					//restore potion effects
					try {
						
						player.offer(Keys.POTION_EFFECTS,ContestantConfigUtils.fetchOriginalPotionEffects(arenaName.toString(), player.getName()));
						
					} catch (ObjectMappingException e) {
						
						e.printStackTrace();
					}
					
					ContestantConfigUtils.removeContestant(arenaName.toString(),player.getName());
					
					if(ContestantConfigUtils.getArenaContestants(arenaName).isEmpty()){
						
						ArenaConfigUtils.setArenaStatus(arenaName, "Open");
					}
					
			    	player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
							TextColors.WHITE,"Thanks for playing!"));
					
					return CommandResult.success();
				}
			}
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
				TextColors.WHITE,"You're not in any Dread Zone arena! To join a Dread Zone arena, enter ",TextColors.DARK_RED,"/dz al"));
		
		return CommandResult.success();
	}
}