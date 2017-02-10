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

public class ContestantConfigUtils {

	private static Configurable ArenaConfig = ArenaFileConfig.getConfig();
	
	//adds a contestant to the arena roster
	public static void addContestant(String arenaName, String userName, Transform<World> location, String worldName) {

		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"X").setValue(location.getLocation().getBlockX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"Y").setValue(location.getLocation().getBlockY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"Z").setValue(location.getLocation().getBlockZ());
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"World").setValue(worldName);
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"Transform","X").setValue((int)location.getRotation().getX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"Transform","Y").setValue((int)location.getRotation().getY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"Transform","Z").setValue((int)location.getRotation().getZ());
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants", userName, "Ready").setValue(false);
		
		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
    public static void isready(String arenaName, String userName) {
    	
        UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants", userName, "Ready").setValue((Object)true);
        
        UnversalConfigs.saveConfig(ArenaConfig);
    }
    
    public static int getNumOfReadyContestants(String arenaName) {
        int r = 0;
        
        Set<Object> contestants = ContestantConfigUtils.getArenaContestants(arenaName);
        
        for (Object userName : contestants) {
        	
            if (UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants", userName, "Ready").getValue().equals(true)){
            	
            	r++;
            }
        }
        return r;
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
	
    public static int getNumOfArenaContestants(String arenaName) {
    	
        Set<Object> contestants = ContestantConfigUtils.getArenaContestants(arenaName);
        
        int c = 0;
        
        for (@SuppressWarnings("unused") Object contestant : contestants) {
        	
            ++c;
        }
        return c;
    }
	
	//checks to see if a specified user is a listed contestant in the arena roster
	public static boolean isUserAnArenaContestant(Object arenaName, Object userName){
		
		Set<Object> contestants = getArenaContestants(arenaName);
		
		for(Object contestant: contestants){
			
			if(contestant.equals(userName)){
				
				return true;
			}
		}
		return false;
	}
	
    public static Location<World> sendContestantToArenaLocation(Object arenaName, Object teamName, Object spawnName) {
    	
        CommentedConfigurationNode targetNode = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Spawnpoints", teamName, spawnName);
        
        String targetPlayerWorldName = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "World").getString();
        
        World world = Sponge.getServer().getWorld(targetPlayerWorldName).orElse(null);
        
        int x = targetNode.getNode("X").getInt();
        
        int y = targetNode.getNode("Y").getInt();
        
        int z = targetNode.getNode("Z").getInt();
        
        return new Location<World>(world, x, y, z);
    }
	
    public static Vector3d sendContestantToArenaLocationRotaton(Object arenaName, Object teamName, Object spawnName) {
    	
        CommentedConfigurationNode targetNode = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Spawnpoints", teamName, spawnName, "Transform");
        
        int tx = targetNode.getNode("X").getInt();
        
        int ty = targetNode.getNode("Y").getInt();
        
        int tz = targetNode.getNode("Z").getInt();
        
        return new Vector3d(tx, ty, tz);
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
	
    /**public static void savePlayerInventory(String arenaName, Player player) throws ObjectMappingException {
        
    	List<ItemStack> inventory = Lists.newArrayList();
        
    	player.getInventory().slots().forEach(slot -> {
    		
            if (slot.peek().isPresent()) {
            	
                ItemStack stack = (ItemStack)slot.poll().get();
                
                inventory.add(stack);
            }
        });
    	
        UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants", player.getName(), "Inventory").setValue(TypeToken.of(Lists.class), inventory);
        UnversalConfigs.saveConfig(ArenaConfig);
    }*/
}
