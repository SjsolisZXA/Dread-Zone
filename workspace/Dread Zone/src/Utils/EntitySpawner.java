package Utils;

import java.util.Optional;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

public class EntitySpawner {
    public static void spawnEntity(Location<World> spawnLocation,EntityType x) {
        Extent extent = spawnLocation.getExtent();
        Optional<Entity> optional = extent.createEntity(x, spawnLocation.getPosition());
        if (optional.isPresent()) {
            Entity mob = optional.get();
            extent.spawnEntity(mob,Cause.source(EntitySpawnCause.builder().entity(mob).type(SpawnTypes.PLUGIN).build()).build());
        }
    }

}
