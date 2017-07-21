package Utils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.scheduler.Task;
import AutomatedExecutors.LightningExecutor;
import Main.Main;

public class AutomatedLightningTimer implements Consumer<Task> {

    @Override
    public void accept(Task task) {
    	
		Object target = LightningExecutor.getRandom();
		
		String TTS = target.toString();
		
		Utils.EntityHandler.spawnEntity(LightningExecutor.getTarget(TTS), EntityTypes.LIGHTNING);
		
		Sponge.getScheduler().createTaskBuilder().execute(new AutomatedLightningTimer()).delay(ThreadLocalRandom.current().nextInt(15), TimeUnit.SECONDS).submit(Main.Dreadzone);
    }
}
