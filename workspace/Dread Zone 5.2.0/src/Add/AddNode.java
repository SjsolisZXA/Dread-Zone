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

import ConfigUtils.RightClickModeConfigUtils;
import Listeners.RightClickMode;

public class AddNode implements CommandExecutor {
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException{
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE, "This is a user command only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		Optional<String> NN = args.<String> getOne("node name");
		
		if(!NN.isPresent()){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Invalid usage! Valid usage: ",TextColors.DARK_RED, "/dz an NODE_NAME",TextColors.WHITE,"."));
			
			return CommandResult.success();
		}
		
		String nodeName = NN.get();
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE, "Right click where you want to create a node."));	
		
		RightClickModeConfigUtils.addToList(player.getName(),"N");
		
		RightClickMode.SN(nodeName);
	    
		return CommandResult.success();
	}
}
