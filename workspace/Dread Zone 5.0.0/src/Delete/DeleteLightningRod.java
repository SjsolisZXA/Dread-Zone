package Delete;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.LightningConfigUtils;

public class DeleteLightningRod implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		if(!(src instanceof Player)){
			src.sendMessage(Text.of(TextColors.RED, "Console already decides where lightning should not hit."));
			
			return CommandResult.success();
		}
		
		String targetName = args.<String> getOne("target name").get();
		
		if (LightningConfigUtils.isTargetInConfig(targetName))
		{
			LightningConfigUtils.deleteTarget(targetName);
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"Success, ", TextColors.DARK_RED, targetName, TextColors.WHITE, " DZ Rod removed!"));
		}
		return CommandResult.success();
	}	
}
