package Utils;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import Main.Main;

public class JumpPadUtils {
	
	public static void buildJumpPad(Location<World> location){
		
		location.setBlockType(BlockTypes.LIGHT_WEIGHTED_PRESSURE_PLATE,Cause.source(Main.getPluginContainer()).build());
		
		location.add(0,-1,0).setBlockType(BlockTypes.REDSTONE_LAMP,Cause.source(Main.getPluginContainer()).build());
		
		location.add(-1,-1,0).setBlockType(BlockTypes.COAL_BLOCK,Cause.source(Main.getPluginContainer()).build());
		location.add(0,-1,-1).setBlockType(BlockTypes.COAL_BLOCK,Cause.source(Main.getPluginContainer()).build());
		
		location.add(1,-1,0).setBlockType(BlockTypes.REDSTONE_BLOCK,Cause.source(Main.getPluginContainer()).build());
		location.add(0,-1,1).setBlockType(BlockTypes.REDSTONE_BLOCK,Cause.source(Main.getPluginContainer()).build());
	}
}
