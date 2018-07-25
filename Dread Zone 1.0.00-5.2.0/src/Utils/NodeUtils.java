package Utils;

import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import Main.Main;

public class NodeUtils {
	
	public static void buildNode(Location<World> blockLocation){
		
		//pressure plate build
	    blockLocation.setBlockType(BlockTypes.STONE_PRESSURE_PLATE,Cause.source(Main.getPluginContainer()).build());
	    
	    //quarts build
	    Location<World> quartzForLoopLocation = blockLocation.add(-2,-1,-2);
	    
	    for(int i=0; i<=4; i++){
	    	
	    	for(int j=0; j<=4; j++){
	    		
	    		quartzForLoopLocation.add(i,0,j).setBlockType(BlockTypes.QUARTZ_BLOCK,Cause.source(Main.getPluginContainer()).build());
	    	}
	    }
	    
	    //iron build
	    Location<World> ironForLoopLocation = blockLocation.add(-4,-5,-4);
	    
	    for(int i=0; i<=8; i++){
	    	
	    	for(int j=0; j<=8; j++){
	    		
	    		ironForLoopLocation.add(i,0,j).setBlockType(BlockTypes.IRON_BLOCK,Cause.source(Main.getPluginContainer()).build());
	    	}
	    }
	    
	    blockLocation.add(-1,-1,3).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//front glass
	    blockLocation.add(0,-1,3).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(1,-1,3).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(-1,-1,-3).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//back glass
	    blockLocation.add(0,-1,-3).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(1,-1,-3).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(-3,-1,1).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//left glass
	    blockLocation.add(-3,-1,0).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(-3,-1,-1).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(3,-1,1).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//right glass
	    blockLocation.add(3,-1,0).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(3,-1,-1).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(-2,-1,2).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//left top
	    blockLocation.add(-2,-1,-2).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//left bottom
	    blockLocation.add(2,-1,2).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//right top
	    blockLocation.add(2,-1,-2).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//left bottom
	    
	    
	    blockLocation.add(-1,-2,3).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//front air
	    blockLocation.add(0,-2,3).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(1,-2,3).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(-1,-3,3).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//front 2nd air
	    blockLocation.add(0,-3,3).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(1,-3,3).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(-1,-2,-3).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//back air
	    blockLocation.add(0,-2,-3).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(1,-2,-3).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(-1,-3,-3).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//back 2nd air
	    blockLocation.add(0,-3,-3).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(1,-3,-3).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(-3,-2,1).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//left air
	    blockLocation.add(-3,-2,0).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(-3,-2,-1).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(-3,-3,1).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//left 2nd air
	    blockLocation.add(-3,-3,0).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(-3,-3,-1).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(3,-2,1).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//right air
	    blockLocation.add(3,-2,0).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(3,-2,-1).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(3,-3,1).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//right 2nd air
	    blockLocation.add(3,-3,0).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(3,-3,-1).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(-2,-2,2).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//left top
	    blockLocation.add(-2,-2,-2).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//left bottom
	    blockLocation.add(2,-2,2).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//right top
	    blockLocation.add(2,-2,-2).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//left bottom

	    blockLocation.add(-2,-3,2).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//left 2nd top
	    blockLocation.add(-2,-3,-2).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//left 2nd bottom
	    blockLocation.add(2,-3,2).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//right 2nd top
	    blockLocation.add(2,-3,-2).setBlockType(BlockTypes.AIR,Cause.source(Main.getPluginContainer()).build());//left 2nd bottom
	    
	    
	    blockLocation.add(-1,-4,3).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());//front beacon
	    blockLocation.add(0,-4,3).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(1,-4,3).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(-1,-4,-3).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());//back beacon
	    blockLocation.add(0,-4,-3).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(1,-4,-3).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(-3,-4,1).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());//left beacon
	    blockLocation.add(-3,-4,0).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(-3,-4,-1).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(3,-4,1).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());//right beacon
	    blockLocation.add(3,-4,0).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(3,-4,-1).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(-2,-4,2).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());//left top
	    blockLocation.add(-2,-4,-2).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());//left bottom
	    blockLocation.add(2,-4,2).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());//right top
	    blockLocation.add(2,-4,-2).setBlockType(BlockTypes.BEACON,Cause.source(Main.getPluginContainer()).build());//left bottom
	    
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
