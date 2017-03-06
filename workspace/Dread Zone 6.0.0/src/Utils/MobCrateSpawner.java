package Utils;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.FallingBlock;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import com.flowpowered.math.vector.Vector3i;

public class MobCrateSpawner {

	public static void spawnMobDropper(Location<World> spawnLocation, int height){
		
		Vector3i location = (spawnLocation.getPosition().toInt());
		
		Extent extent = spawnLocation.getExtent();
		
		Entity entity = extent.createEntity(EntityTypes.FALLING_BLOCK, location.toDouble().add(0.5, 5+height, 0.5));
	    
	    FallingBlock block = (FallingBlock) entity;
            
        BlockState blockState = BlockState.builder().blockType(BlockTypes.CRAFTING_TABLE).build();
        
        block.offer(Keys.CAN_PLACE_AS_BLOCK, false);
        block.offer(Keys.FALL_TIME, 120);     
        block.offer(Keys.FALLING_BLOCK_CAN_HURT_ENTITIES, true);        
        block.offer(Keys.FALLING_BLOCK_STATE, blockState);
        
        extent.spawnEntity(block, Cause.source(EntitySpawnCause.builder().entity(block).type(SpawnTypes.PLUGIN).build()).build());	    
	}
}