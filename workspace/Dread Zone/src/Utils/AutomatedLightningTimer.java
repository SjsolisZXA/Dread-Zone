package Utils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import AutomatedExecutors.LightningExecutor;
import Main.Main;

@SuppressWarnings("unused")
public class AutomatedLightningTimer implements Consumer<Task> {

    @Override
    public void accept(Task task) {
    	
		Object target = LightningExecutor.getRandom();
		
		String TTS = target.toString();
		
		//Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED,target));
		
		Utils.EntitySpawner.spawnEntity(LightningExecutor.getTarget(TTS), EntityTypes.LIGHTNING);
		
		Sponge.getScheduler().createTaskBuilder().execute(new AutomatedLightningTimer()).delay(ThreadLocalRandom.current().nextInt(15), TimeUnit.SECONDS).submit(Main.dreadzone);
    }
}
