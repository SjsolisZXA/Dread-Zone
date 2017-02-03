package AutomatedExecutors;

import java.util.Random;
import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigFiles.UnversalConfigs;
import ConfigFiles.Configurable;
import ConfigFiles.LightningFileConfig;
import ConfigUtils.LightningConfigUtils;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class LightningExecutor {
	
	private static Configurable lightningConfig = LightningFileConfig.getConfig();
	
	public static Object getRandom(){
		
		Set<Object> targets = LightningConfigUtils.getTargets();
		
		int size = targets.size();
		int item = new Random().nextInt(size);
		int i = 0;
		for(Object obj : targets){
			
		    if (i == item)
		    	return obj;
		    i = i + 1;
		}
		return null;
	}
	
	public static Location<World> getTarget(String targetName)
	{
		CommentedConfigurationNode targetNode = UnversalConfigs.getConfig(lightningConfig).getNode("lightningRod", LightningConfigUtils.getLightningConfigName(targetName));
		String worldName = targetNode.getNode("world").getString();
		World world = Sponge.getServer().getWorld(worldName).orElse(null);
		double x = targetNode.getNode("X").getDouble();
		double y = targetNode.getNode("Y").getDouble();
		double z = targetNode.getNode("Z").getDouble();

		return new Location<>(world, x, y, z);
	}
}
