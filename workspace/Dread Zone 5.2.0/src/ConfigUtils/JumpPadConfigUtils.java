package ConfigUtils;

import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigFiles.Configurable;
import ConfigFiles.JumpPadFileConfig;
import ConfigFiles.UnversalConfigs;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class JumpPadConfigUtils {
	
	private static Configurable JumpPadConfig = JumpPadFileConfig.getConfig();
	
	public static void registerJumpPad(Location<World> blockLocation, String worldName, String padName)
	{		
		UnversalConfigs.getConfig(JumpPadConfig).getNode("Jump Pads", padName, "World").setValue(worldName);
		UnversalConfigs.getConfig(JumpPadConfig).getNode("Jump Pads", padName, "X").setValue(blockLocation.getBlockX());
		UnversalConfigs.getConfig(JumpPadConfig).getNode("Jump Pads", padName, "Y").setValue(blockLocation.getBlockY());
		UnversalConfigs.getConfig(JumpPadConfig).getNode("Jump Pads", padName, "Z").setValue(blockLocation.getBlockZ());

		UnversalConfigs.saveConfig(JumpPadConfig);
	}
	
	public static Set<Object> getJumpPads(){
		
		return UnversalConfigs.getConfig(JumpPadConfig).getNode("Jump Pads").getChildrenMap().keySet();
	}
	
	public static Location<World> getJumpPadLocation(String padName){
		
		CommentedConfigurationNode targetNode = UnversalConfigs.getConfig(JumpPadConfig).getNode("Jump Pads", padName);
		String worldName = targetNode.getNode("World").getString();
		World world = Sponge.getServer().getWorld(worldName).orElse(null);
		double x = targetNode.getNode("X").getInt();
		double y = targetNode.getNode("Y").getInt();
		double z = targetNode.getNode("Z").getInt();

		return new Location<World>(world, x, y, z);
	}
}
