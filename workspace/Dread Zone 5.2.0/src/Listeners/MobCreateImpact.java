package Listeners;

import java.util.Collection;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.FallingBlock;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import com.flowpowered.math.vector.Vector3d;

import Utils.EntityHandler;

public class MobCreateImpact {
	
    @Listener
    public void onMobCrateImpact(ChangeBlockEvent.Place event,@Root FallingBlock fallingblock) {
    	
    	for (Transaction<BlockSnapshot> transaction : event.getTransactions()) {
    		
			BlockSnapshot block = transaction.getFinal();
			
			BlockType type = block.getState().getType();
			
			if (type.equals(BlockTypes.CRAFTING_TABLE)){
				
				Vector3d SLL = block.getPosition().toDouble();
				Location<World> LL = block.getLocation().get();
				
				Collection<Entity> NBE = block.getLocation().get().getExtent().getEntities();
				
				for(Entity e: NBE){
					
					if(e instanceof Player){
						
						Player player = (Player)e;
						
						player.playSound(SoundTypes.ENTITY_GENERIC_EXPLODE, SLL, 2);
						player.playSound(SoundTypes.ENTITY_FIREWORK_BLAST, SLL, 2);
						player.playSound(SoundTypes.ENTITY_FIREWORK_BLAST_FAR, SLL, 2);
						
						player.spawnParticles(ParticleEffect.builder().type(ParticleTypes.EXPLOSION).build(), SLL);
						player.spawnParticles(ParticleEffect.builder().type(ParticleTypes.HUGE_EXPLOSION).build(), SLL);
						player.spawnParticles(ParticleEffect.builder().type(ParticleTypes.LARGE_EXPLOSION).build(), SLL);
					}
				}

				EntityHandler.spawnEntity(LL, EntityTypes.SKELETON);
				EntityHandler.spawnEntity(LL, EntityTypes.BLAZE);
				EntityHandler.spawnEntity(LL, EntityTypes.ENDERMITE);
				EntityHandler.spawnEntity(LL, EntityTypes.PIG_ZOMBIE);
				EntityHandler.spawnEntity(LL, EntityTypes.SILVERFISH);
				EntityHandler.spawnEntity(LL, EntityTypes.SPIDER);
				
				event.setCancelled(true);
				return;
			}
    	}	
    }
}
