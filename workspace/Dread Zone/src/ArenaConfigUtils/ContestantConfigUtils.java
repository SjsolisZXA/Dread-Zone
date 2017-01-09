package ArenaConfigUtils;

import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import ConfigFiles.ArenaFileConfig;
import ConfigFiles.Configurable;
import ConfigFiles.UnversalConfigs;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class ContestantConfigUtils {

	private static Configurable ArenaConfig = ArenaFileConfig.getConfig();
	
	//adds a contestant to the arena roster
	public static void addContestant(String arenaName, String user, Transform<World> location, String worldName) {

		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",user,"X").setValue(location.getLocation().getBlockX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",user,"Y").setValue(location.getLocation().getBlockY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",user,"Z").setValue(location.getLocation().getBlockZ());
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",user,"World").setValue(worldName);
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",user,"Transform","X").setValue((int)location.getRotation().getX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",user,"Transform","Y").setValue((int)location.getRotation().getY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",user,"Transform","Z").setValue((int)location.getRotation().getZ());
		
		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	//removes a specific user from the arena roster
	public static void removeContestant(Object arenaName, String player) {
				
		UnversalConfigs.removeChild(ArenaConfig, new Object[] {"Arena",arenaName,"Contestants"}, getContestant(arenaName,player));
		
		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	//gets all of the specified arena's current contestans on the arena roster
	public static Set<Object> getArenaContestants(Object arenaName)
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants").getChildrenMap().keySet();
	}
	
	//gets a specific contestant from a specified arena
	public static Object getContestant(Object arenaName, Object user) {		
		
		Set<Object> contestants = getArenaContestants(arenaName);
		
		for(Object contestant: contestants){
			
			if(contestant.equals(user)){
				
				return contestant.toString();
			}
		}
		return null;
	
	}
	
	//checks to see if a specified user is a listed contestant in the arena roster
	public static boolean isUserAnArenaContestant(Object arenaName, Object user){
		
		Set<Object> contestants = getArenaContestants(arenaName);
		
		for(Object contestant: contestants){
			
			if(contestant.equals(user)){
				
				return true;
			}
		}
		return false;
	}
		
	public static Location<World> returnContestant(String arenaName, String player)
	{
		CommentedConfigurationNode targePlayer = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName,"Contestants",player);
		
		String targetPlayerWorldName = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName,"Contestants",player,"World").getString();
		
		World world = Sponge.getServer().getWorld(targetPlayerWorldName).orElse(null);
		
		int x = targePlayer.getNode("X").getInt();
		int y = targePlayer.getNode("Y").getInt();
		int z = targePlayer.getNode("Z").getInt();

		return new Location<World>(world, x, y, z);
	}
	
	public static Vector3d returnContestantTransform(String arenaName, String player)
	{
		CommentedConfigurationNode targePlayerTransform = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName,"Contestants",player,"Transform");
		
		int tx = targePlayerTransform.getNode("X").getInt();
		int ty = targePlayerTransform.getNode("Y").getInt();
		int tz = targePlayerTransform.getNode("Z").getInt();

		return new Vector3d(tx, ty, tz);
	}
}
