package ConfigUtils;

import java.util.List;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
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
		
		setupScoreboards(TDMConfigUtils.convertObjectContestantsToPlayers(arenaName));
	}

	private static void setupScoreboards(List<Player> contestants) {

        Scoreboard scoreboard = Scoreboard.builder().build();
        
        for(Player player:contestants){
        
	        Objective objective = Objective.builder().name("Stats").displayName(Text.of(TextColors.DARK_RED,TextStyles.UNDERLINE,(String.valueOf(player.getName()) + " Kills"))).criterion(Criteria.DUMMY).build();
	        
	        scoreboard.addObjective(objective);
	        
	        scoreboard.updateDisplaySlot(objective, DisplaySlots.SIDEBAR);
	        
	        objective.getOrCreateScore(Text.of("Total Kills")).setScore(0);
	        
	        objective.getOrCreateScore(Text.of("Deaths")).setScore(0);
	        
	        player.setScoreboard(scoreboard);
	        
	        Text text1 = Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "]");
	        
	        Text text2 = Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Challange", TextColors.DARK_RED, "]");
	        
	        player.getTabList().setHeaderAndFooter(text1, text2);
        }
	}

	private static void teletportContestantsToArena(String arenaName) {
		
		List<Player> contestants = TDMConfigUtils.convertObjectContestantsToPlayers(arenaName);
		
		for(Player contestant: contestants){
			
			contestant.setLocationAndRotation(getPABALocation(arenaName), getPABARotation(arenaName));
		}
	}
}
