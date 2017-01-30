package Reset;

import java.util.Set;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigUtils.NodeConfigUtils;

public class ResetNodes implements CommandExecutor{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		resetTeamNodes();
		
		src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"DZ Nodes reset!"));
		
		return CommandResult.success();
	}
	
	public static void resetTeamNodes(){
		
		NodeConfigUtils.resetNodeTeam();
		
		BlockState stainedGlass = BlockState.builder().blockType(BlockTypes.STAINED_GLASS).build();
		
		Set<Object> nodes = NodeConfigUtils.getNodes();

		for(Object node: nodes){
		
			Location <World> location = NodeConfigUtils.getNode(node.toString());
	    
		    location.add(-1,-1,3).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//front glass
		    location.add(0,-1,3).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
		    location.add(1,-1,3).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
		    
		    location.add(-1,-1,-3).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//back glass
		    location.add(0,-1,-3).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
		    location.add(1,-1,-3).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
		    
		    location.add(-3,-1,1).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left glass
		    location.add(-3,-1,0).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
		    location.add(-3,-1,-1).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
		    
		    location.add(3,-1,1).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//right glass
		    location.add(3,-1,0).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
		    location.add(3,-1,-1).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
		    
		    location.add(-2,-1,2).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left top
		    location.add(-2,-1,-2).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left bottom
		    location.add(2,-1,2).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//right top
		    location.add(2,-1,-2).setBlock(stainedGlass,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left bottom
		}
	}
}
