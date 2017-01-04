package ConfigUtils;

import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import ConfigFiles.UnversalConfigs;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ConfigFiles.ArenaFileConfig;
import ConfigFiles.Configurable;

public class ArenaConfigUtils {
	
	private static Configurable ArenaConfig = ArenaFileConfig.getConfig();
	
	//corner 1 of the arena
	public static void setArenap1(Location<World> blockLocation, String worldName, String arenaName)
	{
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "World").setValue(worldName);
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Min X").setValue(blockLocation.getBlockX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Min Y").setValue(blockLocation.getBlockY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Min Z").setValue(blockLocation.getBlockZ());
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Status").setValue(null);
		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	//corner 2 of the arena
	public static void setArenap2(Location<World> blockLocation, String arenaName)
	{
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Max X").setValue(blockLocation.getBlockX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Max Y").setValue(blockLocation.getBlockY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Max Z").setValue(blockLocation.getBlockZ());
		
		compareArenaCoordinates(arenaName, "Min X","Max X");
		compareArenaCoordinates(arenaName, "Min Y","Max Y");
		compareArenaCoordinates(arenaName, "Min Z","Max Z");

		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	//correct the Max and Min coordinate values of point 1 and 2 of the area specified
	public static void compareArenaCoordinates(Object arenaName, Object min, Object max){
		
		int temp;
		
		if(((int)getArenaData(arenaName, min))>((int)getArenaData(arenaName, max))){
			
			temp = (int)getArenaData(arenaName, min);
			
			UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, min).setValue(getArenaData(arenaName, max));
			UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, max).setValue(temp);
			
			UnversalConfigs.saveConfig(ArenaConfig);
		}
	}
	
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

		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, spawnLocationName, "X").setValue((int)location.getLocation().getX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, spawnLocationName, "Y").setValue((int)location.getLocation().getY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, spawnLocationName, "Z").setValue((int)location.getLocation().getZ());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, spawnLocationName, "Transform", "X").setValue((int)location.getRotation().getX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, spawnLocationName, "Transform", "Y").setValue((int)location.getRotation().getY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, spawnLocationName, "Transform", "Z").setValue((int)location.getRotation().getZ());

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
	
	//get all of the arenas in the configuration
	public static Set<Object> getArenas()
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena").getChildrenMap().keySet();
	}
	
	//get all of the arena's children in the configuration
	public static Set<Object> getArenaChildren(Object arenaName)
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena",arenaName).getChildrenMap().keySet();
	}
	
	//get a child of a lobby
	public static Object getLobbyData(Object arenaName, Object lobbyName, Object target)
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, target).getValue();
	}
	
	//get a child of an arena
	public static Object getArenaData(Object arenaName, Object target)
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, target).getValue();
	}
	
	//get all of an arena's contestants
	public static Set<Object> getArenaContestants(Object arenaName)
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants").getChildrenMap().keySet();
	}
	
	//check to see if an arena already exists
	public static boolean isArenaInConfig(String arenaName)
	{
		return getArenaInConfig(arenaName) != null;
	}	
	
	//check to see if a lobby within a specified arena already exists
	public static boolean isLobbyInConfig(String arenaName, String lobbyName)
	{
		return getLobbyInConfig(arenaName,lobbyName) != null;
	}
	
	public static void setArenaStatus(Object arenaName, Object status){
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Status").setValue(status);
		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	//delete an arena
	public static void deleteArena(String arenaName)
	{
		UnversalConfigs.removeChild(ArenaConfig, new Object[] {"Arena"}, getArenaInConfig(arenaName));
	}
	
	//delete a lobby
	public static void deleteLobby(String arenaName)
	{
		UnversalConfigs.removeChild(ArenaConfig, new Object[] {"Arena", arenaName}, getLobbyInConfig(arenaName,arenaName+"Lobby"));
	}
	
	//reset all of the arena status'
	public static void resetAllArenasStatus(){
		
		Set<Object> arenas = ArenaConfigUtils.getArenas();

		for(Object arena: arenas){
								    
		    UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arena, "Status").setValue("Open");
		    UnversalConfigs.saveConfig(ArenaConfig);		
		}
	}	
	
	//check to see if an arena exists
	public static String getArenaInConfig(String arenaName)
	{
		Set<Object> arenas = getArenas();
		
		for (Object arena : arenas)
		{	

			if(arena.toString().equals(arenaName)){
				return arena.toString();
		    }
		}
		return null;
	}	
	
	//check to see if a lobby exists within a specified arena
	public static String getLobbyInConfig(String arenaName, String lobbyName)
	{
		Set<Object> arenas = getArenas();
		
		for (Object arena : arenas)
		{	
			
			Set<Object> arenaChildren = getArenaChildren(arena);
			
			for(Object child: arenaChildren)
			
				if(arena.toString().equals(arenaName)&&child.toString().equals(lobbyName)){
					
					return child.toString();
			    }
		}
		return null;
	}
	
	public static void addContestant(String arenaName, String user) {
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",user).setValue(user);
		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	public static void removeContestant(Object arenaName, String player) {
				
		UnversalConfigs.removeChild(ArenaConfig, new Object[] {"Arena",arenaName, "Contestants"}, getContestant(arenaName,player));
		
		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	public static Set<Object> getContestants(Object arenaName) {
		
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants").getChildrenMap().keySet();
	}
	
	public static Object getContestant(Object arenaName, Object user) {
		
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants", user).getValue();
	}
	
	public static boolean isUserAnArenaContestant(Object arenaName, Object user){
		
		Set<Object> contestants = getContestants(arenaName);
		
		for(Object contestant: contestants){
			
			if(contestant.equals(user)){
				
				return true;
			}
		}
		return false;
	}

	public static boolean isArenaOpen(String arenaName) {
		
		if(getArenaData(arenaName,"Status").equals("Open")){
			
			return true;
		}
		return false;
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
	
	//gathers a specific arena's data to check to see if a player is within bounds
	public static boolean isUserinArena(Location<World> location, String arenaName)
	{
		CommentedConfigurationNode targetLobby = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName);
		
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
}

