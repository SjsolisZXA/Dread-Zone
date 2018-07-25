package Delete;

import java.util.Optional;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import ConfigUtils.NodeConfigUtils;

public class DeleteNode implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
		Optional<String> NN = args.<String> getOne("node name");
		
		if(!NN.isPresent()){
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Invalid usage! Valid usage: ",TextColors.DARK_RED, "/dz rn NODE_NAME",TextColors.WHITE,"."));
			
			return CommandResult.success();
		}
		
		String nodeName = NN.get();
		
		if (NodeConfigUtils.isNodeInConfig(nodeName)){
			
			//Location<World> blockLocation = NodeConfigUtils.getNode(nodeName).add(0,1,0);
			
			//NodeUtils.undoNodeBuild(blockLocation);
			
			NodeConfigUtils.deleteNode(nodeName);
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"Success, ", TextColors.DARK_RED, nodeName, TextColors.WHITE, " DZ Node removed!"));
			
			return CommandResult.success();
		}
		
		src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Error, ", TextColors.DARK_RED, nodeName, TextColors.WHITE, " DZ Node does not exist!"));
		
		return CommandResult.success();
	}	
}
