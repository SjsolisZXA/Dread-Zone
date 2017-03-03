package ConfigUtils;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import ConfigFiles.ArenaFileConfig;
import ConfigFiles.Configurable;
import ConfigFiles.UnversalConfigs;
import Main.Main;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class PABConfigUtils {
	
	private static Configurable ArenaConfig = ArenaFileConfig.getConfig();

	public static void setPointA(Transform<World> transform, String arenaName) {
		
		Location<World> location = transform.getLocation();
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "PAB","Point A","Spawn Location","X").setValue(location.getBlockX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "PAB","Point A","Spawn Location","Y").setValue(location.getBlockY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "PAB","Point A","Spawn Location","Z").setValue(location.getBlockZ());
		
		Vector3d rotation = transform.getRotation();
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "PAB","Point A","Spawn Location","Rotation","X").setValue((int)rotation.getX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "PAB","Point A","Spawn Location","Rotation","Y").setValue((int)rotation.getY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "PAB","Point A","Spawn Location","Rotation","Z").setValue((int)rotation.getZ());
		
		UnversalConfigs.saveConfig(ArenaConfig);
	}

	public static void declarePB(Location<World> blockLocation, String arenaName) {
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "PAB","Point B","Finish Line","X").setValue(blockLocation.getBlockX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "PAB","Point B","Finish Line","Y").setValue(blockLocation.getBlockY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "PAB","Point B","Finish Line","Z").setValue(blockLocation.getBlockZ());
		
	    blockLocation.add(-1,0,3).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//front glass
	    blockLocation.add(0,0,3).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(1,0,3).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(-1,0,-3).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//back glass
	    blockLocation.add(0,0,-3).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(1,0,-3).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(-3,0,1).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//left glass
	    blockLocation.add(-3,0,0).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(-3,0,-1).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(3,0,1).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//right glass
	    blockLocation.add(3,0,0).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(3,0,-1).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(-2,0,2).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//left top
	    blockLocation.add(-2,0,-2).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//left bottom
	    blockLocation.add(2,0,2).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//right top
	    blockLocation.add(2,0,-2).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());//left bottom
	}

	public static Location<World> getPABBLocation(String arenaName) {

		CommentedConfigurationNode finishLineNode = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "PAB","Point B","Finish Line");
		
		String worldName = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "World").getString();
		
		World world = Sponge.getServer().getWorld(worldName).orElse(null);
		
		int x = finishLineNode.getNode("X").getInt();
		int y = finishLineNode.getNode("Y").getInt();
		int z = finishLineNode.getNode("Z").getInt();

		return new Location<World>(world, x, y, z);
	}

}
