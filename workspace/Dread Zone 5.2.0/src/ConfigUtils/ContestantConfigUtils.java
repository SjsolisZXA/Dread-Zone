package ConfigUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;

import ConfigFiles.ArenaFileConfig;
import ConfigFiles.Configurable;
import ConfigFiles.UnversalConfigs;
import Utils.GUI;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ContestantConfigUtils {

	private static Configurable ArenaConfig = ArenaFileConfig.getConfig();
	
	//adds a contestant to the arena roster
	public static void addContestant(String arenaName, String userName, Transform<World> location, String worldName, GameMode playerGamemode, Integer foodLevel) throws ObjectMappingException {

		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"X").setValue(location.getLocation().getBlockX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"Y").setValue(location.getLocation().getBlockY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"Z").setValue(location.getLocation().getBlockZ());
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"World").setValue(worldName);
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"Transform","X").setValue((int)location.getRotation().getX());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"Transform","Y").setValue((int)location.getRotation().getY());
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"Transform","Z").setValue((int)location.getRotation().getZ());
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"Gamemode").setValue(TypeToken.of(GameMode.class), playerGamemode);
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"Foodlevel").setValue(foodLevel);
		
		UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants", userName, "Ready").setValue(false);
		
		UnversalConfigs.saveConfig(ArenaConfig);
	}
	
    public static void isready(String arenaName, String userName) {
    	
        UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants", userName, "Ready").setValue(true);
        
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
	public static void removeContestantFromList(String arenaName, String player) {
				
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
	public static boolean isUserAnArenaContestant(String arenaName, String userName){
		
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

	public static GameMode fetchOriginalGamemode(String arenaName, String userName) throws ObjectMappingException {

		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"Gamemode").getValue(TypeToken.of(GameMode.class));
	}
	
	public static Integer fetchOriginalFoodLevel(String arenaName, String userName){
		
		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants",userName,"Foodlevel").getInt();
	}

	public static void saveOriginalPotionEffects(List<PotionEffect> potionEffects, String arenaName, String userName) throws ObjectMappingException {
	
		int i = 1;
		
		for(PotionEffect potionEffect: potionEffects){
			
			UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants", userName, "Potion Effects","Effect "+i).setValue(TypeToken.of(PotionEffect.class),potionEffect);
			
			UnversalConfigs.saveConfig(ArenaConfig);
			
			i++;
		}
	}
	
	public static List<PotionEffect> fetchOriginalPotionEffects(String arenaName, String userName) throws ObjectMappingException{
		
		List<PotionEffect> potionEffects = Lists.newArrayList();
		
		CommentedConfigurationNode node = UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants", userName, "Potion Effects");
		
		int NOPE = getNumOfPotionEffects(arenaName,userName);
		
		for(int i = 1; i<= NOPE;i++){
			
			PotionEffect potionEffect = node.getNode("Effect "+i).getValue(TypeToken.of(PotionEffect.class));
			
			potionEffects.add(potionEffect);

		}
		
		return potionEffects;
	}

	private static int getNumOfPotionEffects(String arenaName, String userName) {

		return UnversalConfigs.getConfig(ArenaConfig).getNode("Arena", arenaName, "Contestants", userName, "Potion Effects").getChildrenMap().keySet().size();
	}
	
	public static void removeContestant(String arenaName, Player player) {
		
		player.getInventory().clear();
		
		//restore player location
		player.setLocationAndRotation(returnContestant(arenaName.toString(), player.getName().toString()), 
				returnContestantTransform(arenaName.toString(), player.getName().toString()));
		
		//restore original food level
		player.offer(Keys.FOOD_LEVEL, fetchOriginalFoodLevel(arenaName.toString(), player.getName()));
		
		//restore game mode
		try {
			 
			player.offer(player.getGameModeData().set(Keys.GAME_MODE,fetchOriginalGamemode(arenaName.toString(), player.getName().toString())));
			
		} catch (ObjectMappingException e1) {
			
			e1.printStackTrace();
		}
		
		
		//restore potion effects
		/**try {
			
			player.offer(Keys.POTION_EFFECTS,ContestantConfigUtils.fetchOriginalPotionEffects(arenaName.toString(), player.getName()));
			
		} catch (ObjectMappingException e) {
			
			e.printStackTrace();
		}**/
		
		removeContestantFromList(arenaName.toString(),player.getName());
		
		//remove GUI
		if(ArenaConfigUtils.getMatchStatus(arenaName).equals(true)){
		
			//remove PAB Scoreboard
			if(ArenaConfigUtils.getArenaStatus(arenaName)=="PAB"){
				
				GUI.removePABGUI(player);
			}
			
			//remove TDM Scoreboard and Boss bar
			if(ArenaConfigUtils.getArenaStatus(arenaName)=="TDM"){
				
				player.getScoreboard().removeObjective(player.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get());
				player.getScoreboard().removeObjective(player.getScoreboard().getObjective(DisplaySlots.BELOW_NAME).get());
				
				if(GUI.aTeamBars.get(arenaName)!=null&&GUI.bTeamBars.get(arenaName)!=null){
					
					Map<String,ServerBossBar> b1 = GUI.bTeamBars.get(arenaName);
					Map<String,ServerBossBar> b0 = GUI.aTeamBars.get(arenaName);
					
					b0.get(player.getName()).removePlayer(player);
					b1.get(player.getName()).removePlayer(player);
				}
			}
		}
		
		//clean up NPCs if no one is left in the arena
		if(ContestantConfigUtils.getArenaContestants(arenaName).isEmpty()){
			
			ArenaConfigUtils.setArenaStatus(arenaName, "Open");
			
			ArenaConfigUtils.setMatchStatus(arenaName, false);

			Collection<Entity> DZNPCs = player.getWorld().getEntities();

			Set<Object> NPCClasses = ClassConfigUtils.getArenaClasses(arenaName);
			
			for(Entity entity:DZNPCs){
				
				Optional<Text> NPC = entity.get(Keys.DISPLAY_NAME);
				
				if(NPC.isPresent()){
					
					Text classN = NPC.get();
					
					String className = classN.toPlain();
					
					for(Object CN: NPCClasses){
						
						if(CN.toString().equals(className.toString())
								&&LobbyConfigUtils.isUserinLobby(entity.getLocation(), arenaName.toString())){
							
							entity.remove();
						}
					}
				}
			}
		}
	}
}
