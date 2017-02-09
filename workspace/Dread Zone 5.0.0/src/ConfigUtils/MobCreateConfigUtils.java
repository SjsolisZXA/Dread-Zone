package ConfigUtils;

import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigFiles.UnversalConfigs;
import ConfigFiles.Configurable;
import ConfigFiles.MobCrateFileConfig;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class MobCreateConfigUtils {
	private static Configurable mobCreateConfig = MobCrateFileConfig.getConfig();
	
	public static void setTarget(Location<World> blockLocation, String worldName, String groupName, String targetName)
	{
		UnversalConfigs.getConfig(mobCreateConfig).getNode("mobCreate", groupName, targetName, "World").setValue(worldName);
		UnversalConfigs.getConfig(mobCreateConfig).getNode("mobCreate", groupName, targetName, "X").setValue(blockLocation.getBlockX());
		UnversalConfigs.getConfig(mobCreateConfig).getNode("mobCreate", groupName, targetName, "Y").setValue(blockLocation.getBlockY());
		UnversalConfigs.getConfig(mobCreateConfig).getNode("mobCreate", groupName, targetName, "Z").setValue(blockLocation.getBlockZ());
		UnversalConfigs.getConfig(mobCreateConfig).getNode("mobCreate", groupName, targetName, "Status").setValue(true);

		UnversalConfigs.saveConfig(mobCreateConfig);
	}
	
	public static Set<Object> getTargets()
	{
		return UnversalConfigs.getConfig(mobCreateConfig).getNode("mobCreate").getChildrenMap().keySet();
	}
	
	public static Set<Object> getStatus(Object groupName,Object targetName)
	{
		return UnversalConfigs.getConfig(mobCreateConfig).getNode("mobCreate",groupName,targetName,"Status").getChildrenMap().keySet();
	}
	
	public static Set<Object> getTargetChildren(Object groupName){
		return UnversalConfigs.getConfig(mobCreateConfig).getNode("mobCreate",groupName).getChildrenMap().keySet();
	}
	
	public static boolean isTargetInConfig(String groupName, String targetName)
	{
		return getMobCrateConfigName(groupName,targetName) != null;
	}
	
	public static void deleteTarget(String groupName, String targetName)
	{
		UnversalConfigs.removeChild(mobCreateConfig, new Object[] {"mobCreate",groupName}, getMobCrateConfigName(groupName, targetName));
	}
	
	public static Location<World> getTarget(String groupName, String targetName)
	{
		CommentedConfigurationNode targetNode = UnversalConfigs.getConfig(mobCreateConfig).getNode("mobCreate", groupName, getMobCrateConfigName(groupName, targetName));
		String worldName = targetNode.getNode("World").getString();
		World world = Sponge.getServer().getWorld(worldName).orElse(null);
		double x = targetNode.getNode("X").getDouble();
		double y = targetNode.getNode("Y").getDouble();
		double z = targetNode.getNode("Z").getDouble();

		return new Location<World>(world, x, y, z);
	}
	
	
	public static void resetMobCreateBoolean(){
		
		Set<Object> targets = MobCreateConfigUtils.getTargets();

		for(Object parent: targets){
			
			Set<Object> children = MobCreateConfigUtils.getTargetChildren(parent);
			
			for(Object child: children){
								    
			    UnversalConfigs.getConfig(mobCreateConfig).getNode("mobCreate", parent,child, "Status").setValue(true);
			    UnversalConfigs.saveConfig(mobCreateConfig);		
			}
		}
	}
	
	public static String getMobCrateConfigName(String groupName, String targetName)
	{
		Set<Object> targets = getTargets();
		
		for (Object group : targets)
		{	
			Set<Object> targetChildren = getTargetChildren(group);
			
			for (Object child: targetChildren){
			
				if(group.toString().equalsIgnoreCase(groupName)&&child.toString().equalsIgnoreCase(targetName)){
					return child.toString();
			    }
			}
		}
		return null;
	}
}

