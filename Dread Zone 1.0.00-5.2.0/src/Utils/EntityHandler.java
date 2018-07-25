package Utils;

import java.util.Optional;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.LobbyConfigUtils;

public class EntityHandler {
	
    public static void spawnEntity(Location<World> spawnLocation, EntityType x) {
    	
        Entity entity = spawnLocation.getExtent().createEntity(x, spawnLocation.getPosition());
        
        spawnLocation.getExtent().spawnEntity(entity,Cause.source(EntitySpawnCause.builder().entity(entity).type(SpawnTypes.PLUGIN).build()).build());
    }
    
	public static void spawnNPC(Location<World> location, Direction direction,EntityType x, UUID UUID, String displayName) {
		
		final Entity entity = location.getExtent().createEntity(x, location.getPosition().toDouble().add(0.5,0,0.5));
		
		entity.offer(Keys.DISPLAY_NAME, Text.of(displayName));
		
		entity.offer(Keys.DIRECTION, direction);
			
		entity.offer(Keys.SKIN_UNIQUE_ID, UUID);

		location.getExtent().spawnEntity(entity, Cause.source(EntitySpawnCause.builder().entity(entity).type(SpawnTypes.PLUGIN).build()).build());
	}
	
	public static void clearAllMobsInArena(String worldName, String arenaName){
		
		for(Entity entity : Sponge.getServer().getWorld(worldName).get().getEntities()){
			
			Optional<Text> DN = entity.get(Keys.DISPLAY_NAME);
			
			if(!DN.isPresent()){
				
				if(ArenaConfigUtils.isUserinArena(entity.getLocation(), arenaName)
						||LobbyConfigUtils.isUserinLobby(entity.getLocation(), arenaName)){
					
					entity.remove();
				}
			}
		}
	}
}
