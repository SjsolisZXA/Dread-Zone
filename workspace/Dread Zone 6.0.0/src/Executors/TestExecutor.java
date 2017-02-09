package Executors;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import ConfigUtils.ClassConfigUtils;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class TestExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) {
		
		Player player =(Player)src;	
		
		try {
			
			ClassConfigUtils.spawnArenaNPCs(player.getUniqueId(), "bud");
			
		} catch (ObjectMappingException e) {

			e.printStackTrace();
		}
		
		return CommandResult.success();
	}
}


