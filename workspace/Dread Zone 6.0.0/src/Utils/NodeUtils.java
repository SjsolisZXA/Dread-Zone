package Utils;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class NodeUtils {
	
	public static void buildNode(Location<World> blockLocation){
		
		//pressure plate build
	    BlockState pressureP = BlockState.builder().blockType(BlockTypes.STONE_PRESSURE_PLATE).build();
	    blockLocation.setBlock(pressureP,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    
	    //quarts build
	    BlockState quartz = BlockState.builder().blockType(BlockTypes.QUARTZ_BLOCK).build();
	    Location<World> quartzForLoopLocation = blockLocation.add(-2,-1,-2);
	    for(int i=0; i<=4; i++){
	    	for(int j=0; j<=4; j++){
	    		quartzForLoopLocation.add(i,0,j).setBlock(quartz,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    	}
	    }
	    
	    //iron build
	    Location<World> ironForLoopLocation = blockLocation.add(-4,-5,-4);
	    BlockState ironBlock = BlockState.builder().blockType(BlockTypes.IRON_BLOCK).build();
	    for(int i=0; i<=8; i++){
	    	for(int j=0; j<=8; j++){
	    		ironForLoopLocation.add(i,0,j).setBlock(ironBlock,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    	}
	    }
	   
	    BlockState stainedGlassW = BlockState.builder().blockType(BlockTypes.STAINED_GLASS).build();
	    
	    blockLocation.add(-1,-1,3).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//front glass
	    blockLocation.add(0,-1,3).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(1,-1,3).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    
	    blockLocation.add(-1,-1,-3).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//back glass
	    blockLocation.add(0,-1,-3).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(1,-1,-3).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    
	    blockLocation.add(-3,-1,1).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left glass
	    blockLocation.add(-3,-1,0).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(-3,-1,-1).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    
	    blockLocation.add(3,-1,1).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//right glass
	    blockLocation.add(3,-1,0).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(3,-1,-1).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    
	    blockLocation.add(-2,-1,2).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left top
	    blockLocation.add(-2,-1,-2).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left bottom
	    blockLocation.add(2,-1,2).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//right top
	    blockLocation.add(2,-1,-2).setBlock(stainedGlassW,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left bottom
	    
	    BlockState air = BlockState.builder().blockType(BlockTypes.AIR).build();
	    
	    blockLocation.add(-1,-2,3).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//front air
	    blockLocation.add(0,-2,3).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(1,-2,3).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(-1,-3,3).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//front 2nd air
	    blockLocation.add(0,-3,3).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(1,-3,3).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    
	    blockLocation.add(-1,-2,-3).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//back air
	    blockLocation.add(0,-2,-3).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(1,-2,-3).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(-1,-3,-3).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//back 2nd air
	    blockLocation.add(0,-3,-3).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(1,-3,-3).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    
	    blockLocation.add(-3,-2,1).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left air
	    blockLocation.add(-3,-2,0).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(-3,-2,-1).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(-3,-3,1).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left 2nd air
	    blockLocation.add(-3,-3,0).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(-3,-3,-1).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    
	    blockLocation.add(3,-2,1).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//right air
	    blockLocation.add(3,-2,0).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(3,-2,-1).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(3,-3,1).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//right 2nd air
	    blockLocation.add(3,-3,0).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(3,-3,-1).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    
	    blockLocation.add(-2,-2,2).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left top
	    blockLocation.add(-2,-2,-2).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left bottom
	    blockLocation.add(2,-2,2).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//right top
	    blockLocation.add(2,-2,-2).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left bottom

	    blockLocation.add(-2,-3,2).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left 2nd top
	    blockLocation.add(-2,-3,-2).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left 2nd bottom
	    blockLocation.add(2,-3,2).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//right 2nd top
	    blockLocation.add(2,-3,-2).setBlock(air,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left 2nd bottom
	    
	    BlockState beacon = BlockState.builder().blockType(BlockTypes.BEACON).build();   
	    
	    blockLocation.add(-1,-4,3).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//front beacon
	    blockLocation.add(0,-4,3).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(1,-4,3).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    
	    blockLocation.add(-1,-4,-3).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//back beacon
	    blockLocation.add(0,-4,-3).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(1,-4,-3).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    
	    blockLocation.add(-3,-4,1).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left beacon
	    blockLocation.add(-3,-4,0).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(-3,-4,-1).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    
	    blockLocation.add(3,-4,1).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//right beacon
	    blockLocation.add(3,-4,0).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    blockLocation.add(3,-4,-1).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
	    
	    blockLocation.add(-2,-4,2).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left top
	    blockLocation.add(-2,-4,-2).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left bottom
	    blockLocation.add(2,-4,2).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//right top
	    blockLocation.add(2,-4,-2).setBlock(beacon,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left bottom
	    
	    return;
	}
	
	/**public static void saveNodeBase(Location<World> blockLocation){
		
	    Location<World> sFL = blockLocation.add(-4,-5,-4);
	    
	    for(int i=0; i<=8; i++){
	    	for(int j=0; j<=8; j++){
	    		for(int m=0; m<=6; m++){
	    			
	    			sFL.getBlockType().getId();
	    			//save(sfL) in node config utils
	    		}
	    	}
	    }
		
		return;
	}
	
	public static void undoNodeBuild(Location<World> blockLocation){
		
	    Location<World> uFL = blockLocation.add(-4,-5,-4);
	    
	    for(int i=0; i<=8; i++){
	    	for(int j=0; j<=8; j++){
	    		for(int m=0; m<=6; m++){
	    			
	    			uFL.getBlockType().getId();
	    		}
	    	}
	    }
		
		return;
	}*/
}
