package Add;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.RightClickModeConfigUtils;
import Listeners.RightClickMode;

public class AddMobCrate implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.RED, "Console already decides where mobs spawn."));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation())!=null){
			
			String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
			
			if(ArenaConfigUtils.doesArenaHaveMode(arenaName,"PAB")){
				
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
						TextColors.WHITE,"Right click on where you want a mob create to drop."));
		
				RightClickModeConfigUtils.addToList(player.getName(),"MC");
				
				String groupName = args.<String> getOne("group name").get();
				String targetName = args.<String> getOne("mobcrate name").get();
				
				RightClickMode.SMCL(groupName, targetName);
			
				return CommandResult.success();
			}
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"This Dread Zone arena has not implemented a PAB mode!"));
			
			return CommandResult.success();
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"You must be inside a Dread Zone arena to execute this command!"));
		
		return CommandResult.success();
	}	
}
