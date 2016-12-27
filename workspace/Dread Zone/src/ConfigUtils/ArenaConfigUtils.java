package ConfigUtils;

import java.util.Set;

import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigFiles.UnversalConfigs;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ConfigFiles.ArenaFileConfig;
import ConfigFiles.Configurable;

public class ArenaConfigUtils {
	
	private static Configurable ArenaConfig = ArenaFileConfig.getConfig();
	
	//corner 1 of the lobby
	public static void setLobbyp1(Location<World> blockLocation, String worldName, String arenaName)
	{
		String lobbyName = arenaName+"Lobby";
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, "World").setValue(worldName);
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, "Min X").setValue(blockLocation.getBlockX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, "Min Y").setValue(blockLocation.getBlockY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, "Min Z").setValue(blockLocation.getBlockZ());
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Status").setValue(null);
		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	//corner 2 of the lobby
	public static void setLobbyp2(Location<World> blockLocation, String arenaName)
	{
		String lobbyName = arenaName+"Lobby";
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, "Max X").setValue(blockLocation.getBlockX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, "Max Y").setValue(blockLocation.getBlockY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, "Max Z").setValue(blockLocation.getBlockZ());
		
		compareCoordinates(arenaName, lobbyName,"Min X","Max X");
		compareCoordinates(arenaName, lobbyName,"Min Y","Max Y");
		compareCoordinates(arenaName, lobbyName,"Min Z","Max Z");

		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	//correct the Max and Min coordinate values of point 1 and 2 of the area specified
	public static void compareCoordinates(Object arenaName, Object lobbyName, Object min, Object max){
		
		int temp;
		
		if(((int)getLobbyData(arenaName, lobbyName, min))>((int)getLobbyData(arenaName, lobbyName, max))){
			
			temp = (int)getLobbyData(arenaName, lobbyName, min);
			
			UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, min).setValue(getLobbyData(arenaName, lobbyName, max));
			UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, max).setValue(temp);
			
			UnversalConfigs.saveConfig(ArenaConfig);
		}
	}
	
	//get all of the arenas in the configuration
	public static Set<Object> getArenas()
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena").getChildrenMap().keySet();
	}
	
	//get a child of a lobby
	public static Object getLobbyData(Object arenaName, Object lobbyName, Object target)
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName, target).getValue();
	}
	
	//check to see if an arena already exists
	public static boolean isArenaInConfig(String arenaName)
	{
		return getArenaInConfig(arenaName) != null;
	}	
	
	//delete an arena
	public static void deleteLobby(String arenaName)
	{
		UnversalConfigs.removeChild(ArenaConfig, new Object[] {"Arena"}, getArenaInConfig(arenaName));
	}
	
	//reset all of the arena status'
	public static void resetAllArenasStatus(){
		
		Set<Object> arenas = ArenaConfigUtils.getArenas();

		for(Object arena: arenas){
								    
		    UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arena, "Status").setValue("Open");
		    UnversalConfigs.saveConfig(ArenaConfig);		
		}
	}	
	
	//check to see if a lobby exists
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
	
	//gathers a specific lobby's data to check to see if a player is within bounds
	public static boolean isUserinLobby(Location<World> location, String arenaName, String lobbyName)
	{
		CommentedConfigurationNode targetLobby = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, lobbyName);
		
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

