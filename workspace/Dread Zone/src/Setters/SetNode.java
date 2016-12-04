package Setters;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigUtils.NodeConfigUtils;

public class SetNode implements CommandExecutor {
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException{
		if(!(src instanceof Player)){
			src.sendMessage(Text.of(TextColors.RED, "This is a user command only."));
			return CommandResult.success();
		}
		Player player = (Player)src;
		
		/**player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE, "Stand where you want to create a node."));**/	
		
		String nodeName = args.<String> getOne("node name").get();
		
		if (NodeConfigUtils.isNodeInConfig(nodeName))
		{
			NodeConfigUtils.deleteNode(nodeName);
		}
		NodeConfigUtils.setNode(player.getTransform(), player.getWorld().getName(), nodeName);
		
		//Confirmation message
		src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				TextColors.WHITE,"Success, DZ Node ",TextColors.DARK_RED,nodeName,TextColors.WHITE, " created !"));
		
		//pressure plate build
	    BlockState pressureP = BlockState.builder().blockType(BlockTypes.STONE_PRESSURE_PLATE).build();
	    Location<World> location = player.getLocation();
	    location.setBlock(pressureP);
	    
	    //quarts build
	    BlockState quartz = BlockState.builder().blockType(BlockTypes.QUARTZ_BLOCK).build();
	    Location<World> quartzForLoopLocation = player.getLocation().add(-2,-1,-2);
	    for(int i=0; i<=4; i++){
	    	for(int j=0; j<=4; j++){
	    		quartzForLoopLocation.add(i,0,j).setBlock(quartz);
	    	}
	    }
	    
	    //iron build
	    Location<World> ironForLoopLocation = player.getLocation().add(-4,-5,-4);
	    BlockState ironBlock = BlockState.builder().blockType(BlockTypes.IRON_BLOCK).build();
	    for(int i=0; i<=8; i++){
	    	for(int j=0; j<=8; j++){
	    		ironForLoopLocation.add(i,0,j).setBlock(ironBlock);
	    	}
	    }
	   
	    BlockState stainedGlassW = BlockState.builder().blockType(BlockTypes.STAINED_GLASS).build();
	    
	    location.add(-1,-1,3).setBlock(stainedGlassW);//front glass
	    location.add(0,-1,3).setBlock(stainedGlassW);
	    location.add(1,-1,3).setBlock(stainedGlassW);
	    
	    location.add(-1,-1,-3).setBlock(stainedGlassW);//back glass
	    location.add(0,-1,-3).setBlock(stainedGlassW);
	    location.add(1,-1,-3).setBlock(stainedGlassW);
	    
	    location.add(-3,-1,1).setBlock(stainedGlassW);//left glass
	    location.add(-3,-1,0).setBlock(stainedGlassW);
	    location.add(-3,-1,-1).setBlock(stainedGlassW);
	    
	    location.add(3,-1,1).setBlock(stainedGlassW);//right glass
	    location.add(3,-1,0).setBlock(stainedGlassW);
	    location.add(3,-1,-1).setBlock(stainedGlassW);
	    
	    location.add(-2,-1,2).setBlock(stainedGlassW);//left top
	    location.add(-2,-1,-2).setBlock(stainedGlassW);//left bottom
	    location.add(2,-1,2).setBlock(stainedGlassW);//right top
	    location.add(2,-1,-2).setBlock(stainedGlassW);//left bottom
	    
	    BlockState air = BlockState.builder().blockType(BlockTypes.AIR).build();
	    
	    location.add(-1,-2,3).setBlock(air);//front air
	    location.add(0,-2,3).setBlock(air);
	    location.add(1,-2,3).setBlock(air);
	    location.add(-1,-3,3).setBlock(air);//front 2nd air
	    location.add(0,-3,3).setBlock(air);
	    location.add(1,-3,3).setBlock(air);
	    
	    location.add(-1,-2,-3).setBlock(air);//back air
	    location.add(0,-2,-3).setBlock(air);
	    location.add(1,-2,-3).setBlock(air);
	    location.add(-1,-3,-3).setBlock(air);//back 2nd air
	    location.add(0,-3,-3).setBlock(air);
	    location.add(1,-3,-3).setBlock(air);
	    
	    location.add(-3,-2,1).setBlock(air);//left air
	    location.add(-3,-2,0).setBlock(air);
	    location.add(-3,-2,-1).setBlock(air);
	    location.add(-3,-3,1).setBlock(air);//left 2nd air
	    location.add(-3,-3,0).setBlock(air);
	    location.add(-3,-3,-1).setBlock(air);
	    
	    location.add(3,-2,1).setBlock(air);//right air
	    location.add(3,-2,0).setBlock(air);
	    location.add(3,-2,-1).setBlock(air);
	    location.add(3,-3,1).setBlock(air);//right 2nd air
	    location.add(3,-3,0).setBlock(air);
	    location.add(3,-3,-1).setBlock(air);
	    
	    location.add(-2,-2,2).setBlock(air);//left top
	    location.add(-2,-2,-2).setBlock(air);//left bottom
	    location.add(2,-2,2).setBlock(air);//right top
	    location.add(2,-2,-2).setBlock(air);//left bottom

	    location.add(-2,-3,2).setBlock(air);//left 2nd top
	    location.add(-2,-3,-2).setBlock(air);//left 2nd bottom
	    location.add(2,-3,2).setBlock(air);//right 2nd top
	    location.add(2,-3,-2).setBlock(air);//left 2nd bottom
	    
	    BlockState beacon = BlockState.builder().blockType(BlockTypes.BEACON).build();   
	    
	    location.add(-1,-4,3).setBlock(beacon);//front beacon
	    location.add(0,-4,3).setBlock(beacon);
	    location.add(1,-4,3).setBlock(beacon);
	    
	    location.add(-1,-4,-3).setBlock(beacon);//back beacon
	    location.add(0,-4,-3).setBlock(beacon);
	    location.add(1,-4,-3).setBlock(beacon);
	    
	    location.add(-3,-4,1).setBlock(beacon);//left beacon
	    location.add(-3,-4,0).setBlock(beacon);
	    location.add(-3,-4,-1).setBlock(beacon);
	    
	    location.add(3,-4,1).setBlock(beacon);//right beacon
	    location.add(3,-4,0).setBlock(beacon);
	    location.add(3,-4,-1).setBlock(beacon);
	    
	    location.add(-2,-4,2).setBlock(beacon);//left top
	    location.add(-2,-4,-2).setBlock(beacon);//left bottom
	    location.add(2,-4,2).setBlock(beacon);//right top
	    location.add(2,-4,-2).setBlock(beacon);//left bottom
	    
		return CommandResult.success();
	}
}
