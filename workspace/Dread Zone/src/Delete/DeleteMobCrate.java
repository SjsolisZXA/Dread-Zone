package Delete;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.MobCreateConfigUtils;

public class DeleteMobCrate implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		if(!(src instanceof Player)){
			src.sendMessage(Text.of(TextColors.RED, "Console already decides where mobs should not spawn at."));
			
			return CommandResult.success();
		}
		
		String groupName = args.<String> getOne("group name").get();
		String targetName = args.<String> getOne("target name").get();
		
		if (MobCreateConfigUtils.isTargetInConfig(groupName,targetName))
		{
			MobCreateConfigUtils.deleteTarget(groupName,targetName);
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"Success, DZ Mob Create ", TextColors.DARK_RED, targetName, TextColors.WHITE, " in group ",TextColors.DARK_RED,groupName,TextColors.WHITE," removed!"));
		}
		
		return CommandResult.success();
	}
}
