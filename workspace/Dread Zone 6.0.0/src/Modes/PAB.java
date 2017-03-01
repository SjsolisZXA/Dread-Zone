package Modes;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.RightClickModeConfigUtils;

public class PAB implements CommandExecutor{
	
	static String arenaN;
	static Player player;
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
		if(RightClickModeConfigUtils.isUserInConfig(player.getName())&&RightClickModeConfigUtils.getUserMode(player.getName()).equals("TDM")){
				
			if(ArenaConfigUtils.isUserinArena(player.getLocation(), arenaN)){
			
				if((int)ArenaConfigUtils.getArenaDataInt(arenaN, "SASP_C")<(int)ArenaConfigUtils.getArenaDataInt(arenaN, "SASP_G")){
					
					String spawnName = args.<String> getOne("spawn name").get();
					
					if(ArenaConfigUtils.getNumOfArenaTeamSpawnpoints(arenaN, "Team A Spawnpoints")<(int)ArenaConfigUtils.getArenaDataInt(arenaN, "SASP_G")/2){
						
						ArenaConfigUtils.addArenaSpawn(arenaN, "Team A Spawnpoints",spawnName ,player.getTransform());
						
						ArenaConfigUtils.setIntCurrent(arenaN, (int)ArenaConfigUtils.getArenaDataInt(arenaN, "SASP_C")+1);
						
						player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
								TextColors.WHITE,"Spawnpont ",TextColors.DARK_RED,spawnName,TextColors.WHITE," for Team A set!"));
						
						if((int)ArenaConfigUtils.getArenaDataInt(arenaN, "SASP_C")==(int)ArenaConfigUtils.getArenaDataInt(arenaN, "SASP_G")/2){
							
							player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
									TextColors.WHITE, "Player spawnpoints for set for Team A! Now stand were you want the spawnpoints for "
											+ "Team B to be at in the arena and enter the same command."));
						}
						
						return CommandResult.success();
					}
					
					if(ArenaConfigUtils.getNumOfArenaTeamSpawnpoints(arenaN, "Team B Spawnpoints")<(int)ArenaConfigUtils.getArenaDataInt(arenaN, "SASP_G")/2){
						
						ArenaConfigUtils.addArenaSpawn(arenaN, "Team B Spawnpoints",spawnName ,player.getTransform());
						
						player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
								TextColors.WHITE,"Spawnpont ",TextColors.DARK_RED,spawnName,TextColors.WHITE," for Team B set!"));
					}
					
					ArenaConfigUtils.setIntCurrent(arenaN, (int)ArenaConfigUtils.getArenaDataInt(arenaN, "SASP_C")+1);
					
					if((int)ArenaConfigUtils.getArenaDataInt(arenaN, "SASP_C")==(int)ArenaConfigUtils.getArenaDataInt(arenaN, "SASP_G")){
						
						RightClickModeConfigUtils.deleteUsernameInList(player.getName());
						
						ArenaConfigUtils.removeArenaChild(arenaN, "SASP_C");
						
						ArenaConfigUtils.removeArenaChild(arenaN, "SASP_G");
						
						RightClickModeConfigUtils.addToList(player.getName(),"AAC");
						
						player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
								TextColors.WHITE,"Arena spawnponts set! You'll now need to set ",TextColors.DARK_RED,arenaN+"'s",
								TextColors.WHITE," 5 Classes players can choose from. To do so, enter ",TextColors.DARK_RED,"/adzc ",arenaN," CLASS_NAME NUMBER_OF_CLASS_ITEMS"));
						
						return CommandResult.success();
					}
					
					return CommandResult.success();
				}
			
				return CommandResult.success();
			}
	
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE, "You need to be inside the arena to set a spawnpoint!"));
		
			return CommandResult.success();
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE, "You are not in a Dread Zone arena mode creation state!"));
		
		return CommandResult.success();
	}

	public static void passedArguments(String arenaName, Player p) {
		
		arenaN = arenaName;
		player = p;
	}
}
