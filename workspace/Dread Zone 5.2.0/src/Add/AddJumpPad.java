package Add;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
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

public class AddJumpPad implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE, "This is a user command only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		Optional<String> PN = args.<String> getOne("pad name");
		
		if(!PN.isPresent()){
			
	        player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
	                TextColors.WHITE, "Invalid usage! Valid usage: ",TextColors.DARK_RED,"/dz ajp PAD_NAME",TextColors.WHITE,"."));

			return CommandResult.success();
		}
		
		if(!RightClickModeConfigUtils.isUserInConfig(player.getName())){
		
			if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation())!=null){
				
				String padName = PN.get();
	        		
	    		RightClickModeConfigUtils.addToList(player.getName(), "AJP");
	    		RightClickMode.PN(padName);
	        		
	    		player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ",
	    				TextColors.WHITE,"Right click on a block to set a jump pad location."));
		
	        	return CommandResult.success();
	    		
			}
			player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ",
					TextColors.WHITE,"You must be inside a Dread Zone arena to execute this command!"));
	
			return CommandResult.success();
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                TextColors.WHITE,"It seems like you were doing something else before trying to add a jump pad. "
                		+ "Please finish that up and try again."));

		return CommandResult.success();
	}
}
