package ConfigUtils;

import java.util.Set;

import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigFiles.UnversalConfigs;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ConfigFiles.ArenaFileConfig;
import ConfigFiles.Configurable;

public class ArenaConfigUtils {
	
	private static Configurable ArenaConfig = ArenaFileConfig.getConfig();
	
	//corner 1 of the arena
	public static void setArenap1(Location<World> blockLocation, String worldName, String arenaName){
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "World").setValue(worldName);
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Min X").setValue(blockLocation.getBlockX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Min Y").setValue(blockLocation.getBlockY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Min Z").setValue(blockLocation.getBlockZ());
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Status").setValue(null);
		
		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	//corner 2 of the arena
	public static void setArenap2(Location<World> blockLocation, String arenaName){
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Max X").setValue(blockLocation.getBlockX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Max Y").setValue(blockLocation.getBlockY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Max Z").setValue(blockLocation.getBlockZ());
		
		compareArenaCoordinates(arenaName, "Min X","Max X");
		compareArenaCoordinates(arenaName, "Min Y","Max Y");
		compareArenaCoordinates(arenaName, "Min Z","Max Z");

		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	public static void addArenaSpawn(String arenaName, Object team,Object spawnPointName,Transform<World> location){

		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Spawnpoints", team, spawnPointName, "X").setValue(location.getLocation().getBlockX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Spawnpoints", team, spawnPointName, "Y").setValue(location.getLocation().getBlockY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Spawnpoints", team, spawnPointName, "Z").setValue(location.getLocation().getBlockZ());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Spawnpoints", team, spawnPointName, "Transform", "X").setValue(((int)location.getRotation().getX()));
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Spawnpoints", team, spawnPointName, "Transform", "Y").setValue(((int)location.getRotation().getY()));
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Spawnpoints", team, spawnPointName, "Transform", "Z").setValue(((int)location.getRotation().getZ()));

		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	public static Set<Object> getArenaTeamSpawnpoints(Object arenaName, Object team)
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Spawnpoints", team).getChildrenMap().keySet();
	}
	
	public static Set<Object> getArenaSpawnpointTeams(Object arenaName)
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Spawnpoints").getChildrenMap().keySet();
	}
	
	public static boolean doTeamSpawnpointsExists(Object arenaName){
		
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Spawnpoints") != null;
	}
	
	public static int getNumOfArenaTeamSpawnpoints(Object arenaName, Object team){
		
		Set<Object> teamSpawnpoints = getArenaTeamSpawnpoints(arenaName,team);
		
		int NOATS = 0;
		
		for(@SuppressWarnings("unused") Object tSP:teamSpawnpoints){
			
			NOATS++;
		}
		return NOATS;
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
	
	//get all of the arenas in the configuration
	public static Set<Object> getArenas()
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena").getChildrenMap().keySet();
	}
	
	public static void setIntGoal(Object arenaName, Integer i){
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "SASP_G").setValue(i);
		
		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	public static void setIntCurrent(Object arenaName, Integer i){
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "SASP_C").setValue(i);
		
		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
	//get all of the arena's children in the configuration
	public static Set<Object> getArenaChildren(Object arenaName)
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena",arenaName).getChildrenMap().keySet();
	}
	
	public static Set<Object> getArenaGrandchildren(Object arenaName, Object parent)
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena",arenaName,parent).getChildrenMap().keySet();
	}
	
	//get a child of an arena
	public static Object getArenaData(Object arenaName, Object target)
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, target).getValue();
	}
	
	public static Object getArenaDataInt(Object arenaName, String target)
	{
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, target).getValue();
	}
	
	//check to see if an arena already exists
	public static boolean isArenaInConfig(String arenaName)
	{
		return getArenaInConfig(arenaName) != null;
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
	
	public static void removeArenaChild(String arenaName,Object child){
		
		UnversalConfigs.removeChild(ArenaConfig, new Object[] {"Arena",arenaName}, getArenaChildInConfig(arenaName,child));
	}
	
	public static void removeArenaGrandchild(String arenaName, Object parent, Object child){
		
		UnversalConfigs.removeChild(ArenaConfig, new Object[] {"Arena", arenaName, parent}, getArenaGrandchildInConfig(arenaName, parent, child));
	}
	
	public static Object getArenaChildInConfig(Object arenaName, Object targetChild)
	{
		Set<Object> arenas = getArenas();
		
		for (Object arena : arenas)
		{	
			Set<Object> children = getArenaChildren(arena);
			
			for(Object child : children){

				if(child.equals(targetChild)){
					
					return child;
			    }
			}
			
		}
		return null;
	}
	
	public static Object getArenaGrandchildInConfig(Object arenaName, Object parent, Object targetChild)
	{
		
		Set<Object> children = getArenaGrandchildren(arenaName,parent);
		
		for(Object child: children){

			if(child.equals(targetChild)){
				
				return child;
		    }
		}
		
		return null;
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

	public static boolean isArenaOpen(String arenaName) {
		
		if(getArenaData(arenaName,"Status").equals("Open")){
			
			return true;
		}
		return false;
	}
	
	//gathers a specific arena's data to check to see if a player is within bounds
	public static boolean isUserinArena(Location<World> location, String arenaName)
	{
		CommentedConfigurationNode targetArena = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName);
		
		String worldName = targetArena.getNode("World").getString();
		
		int minX = targetArena.getNode("Min X").getInt();
		int minY = targetArena.getNode("Min Y").getInt();
		int minZ = targetArena.getNode("Min Z").getInt();
		int maxX = targetArena.getNode("Max X").getInt();
		int maxY = targetArena.getNode("Max Y").getInt();
		int maxZ = targetArena.getNode("Max Z").getInt();
		
		if (location.getExtent().getName().equalsIgnoreCase(worldName)&& location.getBlockX() >= minX && location.getBlockX() <= maxX
				&& location.getBlockY() >= minY && location.getBlockY() <= maxY&& location.getBlockZ() >= minZ && location.getBlockZ() <= maxZ){
			
			return true;
		}
		return false;
	}
}

