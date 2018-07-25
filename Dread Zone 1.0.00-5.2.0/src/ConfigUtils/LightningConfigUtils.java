package ConfigUtils;

import java.util.Set;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigFiles.UnversalConfigs;
import ConfigFiles.Configurable;
import ConfigFiles.LightningFileConfig;

public class LightningConfigUtils {
	
	private static Configurable lightningConfig = LightningFileConfig.getConfig();
	
	public static void setTarget(Location<World> blockLocation, String worldName, String targetName)
	{
		UnversalConfigs.getConfig(lightningConfig).getNode("lightningRod", targetName, "world").setValue(worldName);
		UnversalConfigs.getConfig(lightningConfig).getNode("lightningRod", targetName, "X").setValue(blockLocation.getBlockX());
		UnversalConfigs.getConfig(lightningConfig).getNode("lightningRod", targetName, "Y").setValue(blockLocation.getBlockY());
		UnversalConfigs.getConfig(lightningConfig).getNode("lightningRod", targetName, "Z").setValue(blockLocation.getBlockZ());

		UnversalConfigs.saveConfig(lightningConfig);
	}
	
	public static Set<Object> getTargets()
	{
		return UnversalConfigs.getConfig(lightningConfig).getNode("lightningRod").getChildrenMap().keySet();
	}
	
	public static String getLightningConfigName(String targetName)
	{
		Set<Object> targets = getTargets();
		for (Object target : targets)
		{
			if (target.toString().equalsIgnoreCase(targetName))
				return target.toString();
		}
		return null;
	}
	
	public static boolean isTargetInConfig(String targetName)
	{
		return getLightningConfigName(targetName) != null;
	}
	
	public static void deleteTarget(String targetName)
	{
		UnversalConfigs.removeChild(lightningConfig, new Object[] { "lightningRod"}, getLightningConfigName(targetName));
	}
}