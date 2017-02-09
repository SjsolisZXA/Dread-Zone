package Setters;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.RightClickModeConfigUtils;
import Listeners.RightClickMode;


public class SetLightningRodLocation implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.RED, "Console already decides where lightning hits."));
			
			return CommandResult.success();
			
		}
		
		Player player = (Player)src;
				
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Right click on where you want lightning to hit."));
		
		RightClickModeConfigUtils.addToList(player.getName(),"LM");
		
		String targetName = args.<String> getOne("target name").get();
		
		RightClickMode.SLRLTN(targetName);

		return CommandResult.success();
	}
}
