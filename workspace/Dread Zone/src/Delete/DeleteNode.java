package Delete;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.NodeConfigUtils;
import Utils.AsyncCommandExecutorBase;

public class DeleteNode extends AsyncCommandExecutorBase {
	
	@Override
	public void executeAsync(CommandSource src, CommandContext args){
		
		String nodeName = args.<String> getOne("node name").get();
		
		if (NodeConfigUtils.isNodeInConfig(nodeName))
		{
			NodeConfigUtils.deleteNode(nodeName);
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"Success, ", TextColors.DARK_RED, nodeName, TextColors.WHITE, " DZ Node removed!"));
		}
	}
	@Nonnull
	@Override
	public String[] getAliases()
	{
		return new String[] { "deletehome", "delhome" };
	}	
}
