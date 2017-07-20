package ConfigUtils;

import java.util.List;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import ConfigFiles.ArenaFileConfig;
import ConfigFiles.Configurable;
import ConfigFiles.UnversalConfigs;
import Main.Main;
import Utils.GUI;
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
		
		UnversalConfigs.saveConfig(ArenaConfig);
		
	    BlockState stainedGlassB = BlockState.builder().blockType(BlockTypes.STAINED_GLASS).build();
	    stainedGlassB = stainedGlassB.with(Keys.DYE_COLOR, DyeColors.LIGHT_BLUE).get();
		
	    blockLocation.add(-1,0,3).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());//front glass
	    blockLocation.add(0,0,3).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(1,0,3).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(-1,0,-3).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());//back glass
	    blockLocation.add(0,0,-3).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(1,0,-3).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(-3,0,1).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());//left glass
	    blockLocation.add(-3,0,0).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(-3,0,-1).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(3,0,1).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());//right glass
	    blockLocation.add(3,0,0).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());
	    blockLocation.add(3,0,-1).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());
	    
	    blockLocation.add(-2,0,2).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());//left top
	    blockLocation.add(-2,0,-2).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());//left bottom
	    blockLocation.add(2,0,2).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());//right top
	    blockLocation.add(2,0,-2).setBlock(stainedGlassB,Cause.source(Main.getPluginContainer()).build());//left bottom
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
	
	public static Location<World> getPABALocation(String arenaName) {

		CommentedConfigurationNode pointANode = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "PAB", "Point A", "Spawn Location");
		
		String worldName = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "World").getString();
		
		World world = Sponge.getServer().getWorld(worldName).orElse(null);
		
		int x = pointANode.getNode("X").getInt();
		int y = pointANode.getNode("Y").getInt();
		int z = pointANode.getNode("Z").getInt();

		return new Location<World>(world, x, y, z);
	}
	
	public static Vector3d getPABARotation(String arenaName) {

		CommentedConfigurationNode pointARotationNode = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "PAB", "Point A", "Spawn Location","Rotation");
		
		int x = pointARotationNode.getNode("X").getInt();
		int y = pointARotationNode.getNode("Y").getInt();
		int z = pointARotationNode.getNode("Z").getInt();

		return new Vector3d(x, y, z);
	}

	public static void beginPAB(String arenaName) {
		
		teletportContestantsToArena(arenaName);
		
		GUI.setupPABScoreboards(TDMConfigUtils.convertObjectContestantsToPlayers(arenaName),arenaName);
	}

	private static void teletportContestantsToArena(String arenaName) {
		
		List<Player> contestants = TDMConfigUtils.convertObjectContestantsToPlayers(arenaName);
		
		for(Player contestant: contestants){
			
			contestant.setLocationAndRotation(getPABALocation(arenaName), getPABARotation(arenaName));
		}
	}
}
