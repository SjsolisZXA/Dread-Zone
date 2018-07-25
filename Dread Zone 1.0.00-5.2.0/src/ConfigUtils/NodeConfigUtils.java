package ConfigUtils;

import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigFiles.UnversalConfigs;
import ConfigFiles.Configurable;
import ConfigFiles.NodeFileConfig;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class NodeConfigUtils {
	
	private static Configurable nodeConfig = NodeFileConfig.getConfig();
	
	public static void setNode(Location<World> blockLocation, String worldName, String nodeName)
	{		
		UnversalConfigs.getConfig(nodeConfig).getNode("Nodes", nodeName, "World").setValue(worldName);
		UnversalConfigs.getConfig(nodeConfig).getNode("Nodes", nodeName, "X").setValue(blockLocation.getBlockX());
		UnversalConfigs.getConfig(nodeConfig).getNode("Nodes", nodeName, "Y").setValue(blockLocation.getBlockY());
		UnversalConfigs.getConfig(nodeConfig).getNode("Nodes", nodeName, "Z").setValue(blockLocation.getBlockZ());
		UnversalConfigs.getConfig(nodeConfig).getNode("Nodes", nodeName, "Team").setValue(null);

		UnversalConfigs.saveConfig(nodeConfig);
	}
	
	public static Set<Object> getNodes()
	{
		return UnversalConfigs.getConfig(nodeConfig).getNode("Nodes").getChildrenMap().keySet();
	}
	
	public static Set<Object> getTeam(Object nodeName)
	{
		return UnversalConfigs.getConfig(nodeConfig).getNode("Nodes",nodeName,"Team").getChildrenMap().keySet();
	}
	
	public static Set<Object> getNodeChildren(Object nodeName){
		return UnversalConfigs.getConfig(nodeConfig).getNode("Nodes",nodeName).getChildrenMap().keySet();
	}
	
	public static boolean isNodeInConfig(String nodeName)
	{
		return getNodeConfigName(nodeName) != null;
	}
	
	public static void deleteNode(String nodeName)
	{
		UnversalConfigs.removeChild(nodeConfig, new Object[] {"Nodes"}, getNodeConfigName(nodeName));
	}
	public static Location<World> getNode(String nodeName)
	{
		CommentedConfigurationNode targetNode = UnversalConfigs.getConfig(nodeConfig).getNode("Nodes", getNodeConfigName(nodeName));
		String worldName = targetNode.getNode("World").getString();
		World world = Sponge.getServer().getWorld(worldName).orElse(null);
		double x = targetNode.getNode("X").getDouble();
		double y = targetNode.getNode("Y").getDouble();
		double z = targetNode.getNode("Z").getDouble();

		return new Location<World>(world, x, y, z);
	}
	
	
	public static void resetNodeTeam(){
		
		Set<Object> nodes = NodeConfigUtils.getNodes();

		for(Object node: nodes){
								    
		    UnversalConfigs.getConfig(nodeConfig).getNode("Nodes", node, "Team").setValue(null);
		    UnversalConfigs.saveConfig(nodeConfig);		
		}
	}
	
	public static String getNodeConfigName(String nodeName)
	{
		Set<Object> nodes = getNodes();
		
		for (Object node : nodes)
		{	
			
			if(node.toString().equalsIgnoreCase(nodeName)){
				return node.toString();
			}
		}
		return null;
	}
}

