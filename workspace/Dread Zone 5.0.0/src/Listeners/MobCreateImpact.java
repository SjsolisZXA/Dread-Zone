package Listeners;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.FallingBlock;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;

import Utils.EntitySpawner;

@SuppressWarnings("unused")
public class MobCreateImpact {
    @Listener
    public void onEndPortalImpact(ChangeBlockEvent.Place event,@First Player player,@Root FallingBlock fallingblock) {
    	for (Transaction<BlockSnapshot> transaction : event.getTransactions()) {
			BlockSnapshot block = transaction.getFinal();
			BlockType type = block.getState().getType();
			if (type.equals(BlockTypes.CRAFTING_TABLE)){
				
				Vector3i landLocation = block.getPosition();
				Location<World> LL = new Location<>(player.getWorld(),landLocation.toDouble());
				Vector3d SLL = new Vector3d(landLocation.toDouble()); 
		
				player.playSound(SoundTypes.ENTITY_GENERIC_EXPLODE, SLL, 2);
				player.playSound(SoundTypes.ENTITY_FIREWORK_BLAST, SLL, 2);
				player.playSound(SoundTypes.ENTITY_FIREWORK_BLAST_FAR, SLL, 2);
				
				player.spawnParticles(ParticleEffect.builder().type(ParticleTypes.EXPLOSION).build(), SLL);
				player.spawnParticles(ParticleEffect.builder().type(ParticleTypes.HUGE_EXPLOSION).build(), SLL);
				player.spawnParticles(ParticleEffect.builder().type(ParticleTypes.LARGE_EXPLOSION).build(), SLL);
				
				EntitySpawner.spawnEntity(LL, EntityTypes.SKELETON);
				EntitySpawner.spawnEntity(LL, EntityTypes.BLAZE);
				EntitySpawner.spawnEntity(LL, EntityTypes.ENDERMITE);
				EntitySpawner.spawnEntity(LL, EntityTypes.PIG_ZOMBIE);
				EntitySpawner.spawnEntity(LL, EntityTypes.SILVERFISH);
				EntitySpawner.spawnEntity(LL, EntityTypes.SPIDER);
				
				/**player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
						TextColors.WHITE,"Mob create spawned!"));**/
				
				event.setCancelled(true);
				return;
			}
    	}	
    }
}
