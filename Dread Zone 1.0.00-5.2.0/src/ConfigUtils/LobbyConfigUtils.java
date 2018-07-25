package ConfigUtils;

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

public class LobbyConfigUtils {
	
	private static Configurable ArenaConfig = ArenaFileConfig.getConfig();

	//corner 1 of the lobby
	public static void setLobbyp1(Location<World> blockLocation, String worldName, String arenaName)
	{
		String lobbyName = arenaName+"Lobby";
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, "World").setValue(worldName);
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, "Min X").setValue(blockLocation.getBlockX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, "Min Y").setValue(blockLocation.getBlockY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, "Min Z").setValue(blockLocation.getBlockZ());
		
		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	//corner 2 of the lobby
	public static void setLobbyp2(Location<World> blockLocation, String arenaName)
	{
		String lobbyName = arenaName+"Lobby";
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, "Max X").setValue(blockLocation.getBlockX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, "Max Y").setValue(blockLocation.getBlockY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, "Max Z").setValue(blockLocation.getBlockZ());
		
		compareLobbyCoordinates(arenaName, lobbyName,"Min X","Max X");
		compareLobbyCoordinates(arenaName, lobbyName,"Min Y","Max Y");
		compareLobbyCoordinates(arenaName, lobbyName,"Min Z","Max Z");

		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	//correct the Max and Min coordinate values of point 1 and 2 of the lobby specified
	public static void compareLobbyCoordinates(Object arenaName, Object lobbyName, Object min, Object max){
		
		int temp;
		
		if(((int)getLobbyData(arenaName, lobbyName, min))>((int)getLobbyData(arenaName, lobbyName, max))){
			
			temp = (int)getLobbyData(arenaName, lobbyName, min);
			
			UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, min).setValue(getLobbyData(arenaName, lobbyName, max));
			UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, max).setValue(temp);
			
			UnversalConfigs.saveConfig(ArenaConfig);
		}
	}
	
	public static void setLobbySpawn(Transform<World> location, String arenaName, String lobbyName)
	{
		String spawnLocationName = lobbyName+"SpawnLocation";

		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, spawnLocationName, "X").setValue(location.getLocation().getBlockX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, spawnLocationName, "Y").setValue(location.getLocation().getBlockY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, spawnLocationName, "Z").setValue(location.getLocation().getBlockZ());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, spawnLocationName, "Transform", "X").setValue(((int)location.getRotation().getX()));
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, spawnLocationName, "Transform", "Y").setValue(((int)location.getRotation().getY()));
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, spawnLocationName, "Transform", "Z").setValue(((int)location.getRotation().getZ()));

		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	public static Location<World> getLobbySpawnLocation(String arenaName)
	{
		CommentedConfigurationNode targetLobby = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, arenaName+"Lobby",arenaName+"Lobby"+"SpawnLocation");
		
		String worldName = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, arenaName+"Lobby","World").getString();
		
		World world = Sponge.getServer().getWorld(worldName).orElse(null);
		
		int x = targetLobby.getNode("X").getInt();
		int y = targetLobby.getNode("Y").getInt();
		int z = targetLobby.getNode("Z").getInt();

		return new Location<World>(world, x, y, z);
	}
	
	public static Vector3d getLobbySpawnLocationRotation(String arenaName)
	{
		CommentedConfigurationNode targetLobby = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, arenaName+"Lobby",arenaName+"Lobby"+"SpawnLocation");
		
		int tx = targetLobby.getNode("Transform", "X").getInt();
		int ty = targetLobby.getNode("Transform", "Y").getInt();
		int tz = targetLobby.getNode("Transform", "Z").getInt();

		return new Vector3d(tx, ty, tz);
	}
	
	//get a child of a lobby
	public static Object getLobbyData(Object arenaName, Object lobbyName, Object target)
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, target).getValue();
	}
	
	//check to see if a lobby within a specified arena already exists
	public static boolean isLobbyInConfig(String arenaName, String lobbyName)
	{
		return getLobbyInConfig(arenaName,lobbyName) != null;
	}
	
	//check to see if a lobby exists within a specified arena
	public static String getLobbyInConfig(String arenaName, String lobbyName)
	{
		Set<Object> arenas = ArenaConfigUtils.getArenas();
		
		for (Object arena : arenas)
		{	
			
			Set<Object> arenaChildren = ArenaConfigUtils.getArenaChildren(arena);
			
			for(Object child: arenaChildren)
			
				if(arena.toString().equals(arenaName)&&child.toString().equals(lobbyName)){
					
					return child.toString();
			    }
		}
		return null;
	}
	
	//gathers a specific lobby's data to check to see if a player is within bounds
	public static boolean isUserinLobby(Location<World> location, String arenaName)
	{
		CommentedConfigurationNode targetLobby = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, arenaName+"Lobby");
		
		String worldName = targetLobby.getNode("World").getString();
		
		int minX = targetLobby.getNode("Min X").getInt();
		int minY = targetLobby.getNode("Min Y").getInt();
		int minZ = targetLobby.getNode("Min Z").getInt();
		int maxX = targetLobby.getNode("Max X").getInt();
		int maxY = targetLobby.getNode("Max Y").getInt();
		int maxZ = targetLobby.getNode("Max Z").getInt();
		
		if (location.getExtent().getName().equalsIgnoreCase(worldName)&& location.getBlockX() >= minX && location.getBlockX() <= maxX
				&& location.getBlockY() >= minY && location.getBlockY() <= maxY&& location.getBlockZ() >= minZ && location.getBlockZ() <= maxZ){
			
			return true;
		}
		return false;
	}
	
	//delete a lobby
	public static void deleteLobby(String arenaName)
	{
		UnversalConfigs.removeChild(ArenaConfig, new Object[] {"Arena", arenaName}, getLobbyInConfig(arenaName,arenaName+"Lobby"));
	}

	public static boolean doesLobbySpawnExist(Object arenaName) {
		
		if(getLobbySpawnLocation(arenaName.toString())!=null){
			
			return true;
		}
		
		return false;
	}
}
