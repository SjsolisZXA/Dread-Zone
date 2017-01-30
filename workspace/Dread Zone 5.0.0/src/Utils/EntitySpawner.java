package Utils;

import java.util.UUID;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class EntitySpawner {
	
    public static void spawnEntity(Location<World> spawnLocation, EntityType x) {
    	
        Entity entity = spawnLocation.getExtent().createEntity(x, spawnLocation.getPosition());
        
        spawnLocation.getExtent().spawnEntity(entity,Cause.source(EntitySpawnCause.builder().entity(entity).type(SpawnTypes.PLUGIN).build()).build());
    }
    

	public static void spawnNPC(Location<World> location, EntityType x, UUID UUID, String displayName) {
		
		final Entity entity = location.getExtent().createEntity(x, location.getPosition());
		
		entity.offer(Keys.DISPLAY_NAME, Text.of(displayName));
		
		entity.offer(Keys.SKIN_UNIQUE_ID, UUID);
		
		entity.offer(Keys.AI_ENABLED, true);
		
		location.getExtent().spawnEntity(entity, Cause.source(EntitySpawnCause.builder().entity(entity).type(SpawnTypes.PLUGIN).build()).build());
	}
}
