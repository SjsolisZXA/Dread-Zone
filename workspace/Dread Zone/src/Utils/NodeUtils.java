package Utils;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class NodeUtils {
	
	public static void buildNode(Location<World> blockLocation){
		
		//pressure plate build
	    BlockState pressureP = BlockState.builder().blockType(BlockTypes.STONE_PRESSURE_PLATE).build();
	    blockLocation.setBlock(pressureP);
	    
	    //quarts build
	    BlockState quartz = BlockState.builder().blockType(BlockTypes.QUARTZ_BLOCK).build();
	    Location<World> quartzForLoopLocation = blockLocation.add(-2,-1,-2);
	    for(int i=0; i<=4; i++){
	    	for(int j=0; j<=4; j++){
	    		quartzForLoopLocation.add(i,0,j).setBlock(quartz);
	    	}
	    }
	    
	    //iron build
	    Location<World> ironForLoopLocation = blockLocation.add(-4,-5,-4);
	    BlockState ironBlock = BlockState.builder().blockType(BlockTypes.IRON_BLOCK).build();
	    for(int i=0; i<=8; i++){
	    	for(int j=0; j<=8; j++){
	    		ironForLoopLocation.add(i,0,j).setBlock(ironBlock);
	    	}
	    }
	   
	    BlockState stainedGlassW = BlockState.builder().blockType(BlockTypes.STAINED_GLASS).build();
	    
	    blockLocation.add(-1,-1,3).setBlock(stainedGlassW);//front glass
	    blockLocation.add(0,-1,3).setBlock(stainedGlassW);
	    blockLocation.add(1,-1,3).setBlock(stainedGlassW);
	    
	    blockLocation.add(-1,-1,-3).setBlock(stainedGlassW);//back glass
	    blockLocation.add(0,-1,-3).setBlock(stainedGlassW);
	    blockLocation.add(1,-1,-3).setBlock(stainedGlassW);
	    
	    blockLocation.add(-3,-1,1).setBlock(stainedGlassW);//left glass
	    blockLocation.add(-3,-1,0).setBlock(stainedGlassW);
	    blockLocation.add(-3,-1,-1).setBlock(stainedGlassW);
	    
	    blockLocation.add(3,-1,1).setBlock(stainedGlassW);//right glass
	    blockLocation.add(3,-1,0).setBlock(stainedGlassW);
	    blockLocation.add(3,-1,-1).setBlock(stainedGlassW);
	    
	    blockLocation.add(-2,-1,2).setBlock(stainedGlassW);//left top
	    blockLocation.add(-2,-1,-2).setBlock(stainedGlassW);//left bottom
	    blockLocation.add(2,-1,2).setBlock(stainedGlassW);//right top
	    blockLocation.add(2,-1,-2).setBlock(stainedGlassW);//left bottom
	    
	    BlockState air = BlockState.builder().blockType(BlockTypes.AIR).build();
	    
	    blockLocation.add(-1,-2,3).setBlock(air);//front air
	    blockLocation.add(0,-2,3).setBlock(air);
	    blockLocation.add(1,-2,3).setBlock(air);
	    blockLocation.add(-1,-3,3).setBlock(air);//front 2nd air
	    blockLocation.add(0,-3,3).setBlock(air);
	    blockLocation.add(1,-3,3).setBlock(air);
	    
	    blockLocation.add(-1,-2,-3).setBlock(air);//back air
	    blockLocation.add(0,-2,-3).setBlock(air);
	    blockLocation.add(1,-2,-3).setBlock(air);
	    blockLocation.add(-1,-3,-3).setBlock(air);//back 2nd air
	    blockLocation.add(0,-3,-3).setBlock(air);
	    blockLocation.add(1,-3,-3).setBlock(air);
	    
	    blockLocation.add(-3,-2,1).setBlock(air);//left air
	    blockLocation.add(-3,-2,0).setBlock(air);
	    blockLocation.add(-3,-2,-1).setBlock(air);
	    blockLocation.add(-3,-3,1).setBlock(air);//left 2nd air
	    blockLocation.add(-3,-3,0).setBlock(air);
	    blockLocation.add(-3,-3,-1).setBlock(air);
	    
	    blockLocation.add(3,-2,1).setBlock(air);//right air
	    blockLocation.add(3,-2,0).setBlock(air);
	    blockLocation.add(3,-2,-1).setBlock(air);
	    blockLocation.add(3,-3,1).setBlock(air);//right 2nd air
	    blockLocation.add(3,-3,0).setBlock(air);
	    blockLocation.add(3,-3,-1).setBlock(air);
	    
	    blockLocation.add(-2,-2,2).setBlock(air);//left top
	    blockLocation.add(-2,-2,-2).setBlock(air);//left bottom
	    blockLocation.add(2,-2,2).setBlock(air);//right top
	    blockLocation.add(2,-2,-2).setBlock(air);//left bottom

	    blockLocation.add(-2,-3,2).setBlock(air);//left 2nd top
	    blockLocation.add(-2,-3,-2).setBlock(air);//left 2nd bottom
	    blockLocation.add(2,-3,2).setBlock(air);//right 2nd top
	    blockLocation.add(2,-3,-2).setBlock(air);//left 2nd bottom
	    
	    BlockState beacon = BlockState.builder().blockType(BlockTypes.BEACON).build();   
	    
	    blockLocation.add(-1,-4,3).setBlock(beacon);//front beacon
	    blockLocation.add(0,-4,3).setBlock(beacon);
	    blockLocation.add(1,-4,3).setBlock(beacon);
	    
	    blockLocation.add(-1,-4,-3).setBlock(beacon);//back beacon
	    blockLocation.add(0,-4,-3).setBlock(beacon);
	    blockLocation.add(1,-4,-3).setBlock(beacon);
	    
	    blockLocation.add(-3,-4,1).setBlock(beacon);//left beacon
	    blockLocation.add(-3,-4,0).setBlock(beacon);
	    blockLocation.add(-3,-4,-1).setBlock(beacon);
	    
	    blockLocation.add(3,-4,1).setBlock(beacon);//right beacon
	    blockLocation.add(3,-4,0).setBlock(beacon);
	    blockLocation.add(3,-4,-1).setBlock(beacon);
	    
	    blockLocation.add(-2,-4,2).setBlock(beacon);//left top
	    blockLocation.add(-2,-4,-2).setBlock(beacon);//left bottom
	    blockLocation.add(2,-4,2).setBlock(beacon);//right top
	    blockLocation.add(2,-4,-2).setBlock(beacon);//left bottom
	    
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
