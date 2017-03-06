package Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import com.google.inject.Inject;

import Add.AddPABMode;
import Add.AddTDMMode;
import Add.AddClass;
import Add.AddClassItem;
import Add.AddClassNPC;
import Add.AddLightningRod;
import Add.AddMobCrate;
import Add.AddNode;
import ConfigUtils.LightningConfigUtils;
import ConfigUtils.MobCreateConfigUtils;
import ConfigUtils.NodeConfigUtils;
import Delete.DeleteClass;
import Delete.DeleteClassItem;
import Delete.DeleteLightningRod;
import Delete.DeleteMobCrate;
import Delete.DeleteNode;
import Executors.ViewArenas;
import Executors.ViewClasses;
import Executors.ViewModes;
import Executors.ReloadExecutor;
import Executors.JoinArena;
import Executors.LeaveArena;
import Executors.MoveArenaLobby;
import Executors.Ready;
import Listeners.MobCreatePlayerDetector;
import Listeners.NodeBlockTracker;
import Listeners.DZNPCListener;
import Listeners.GeneralArenaListeners;
import Listeners.MobCreateImpact;
import Listeners.NodeListener;
import Listeners.PABListeners;
import Listeners.PBDetector;
import Listeners.RightClickMode;
import Modes.PAB;
import Modes.TDM;
import Reset.ResetMobCreate;
import Reset.ResetNodes;
import Utils.AutomatedLightningTimer;
import Utils.LoadConfig;
import Setters.SetArena;
import Setters.SetLobbySpawn;
import Test.TestExecutor0;
import Test.TestExecutor1;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Plugin(id = "dreadzone", name = "Dread Zone", version = "0.0.1")

public class Main {
	
	public static Main Dreadzone;
	
	@Inject
	Game game;
	
    @Inject
    private PluginContainer PC;
	
	@Inject
	private Logger logger;
	
	@Inject
	@ConfigDir(sharedRoot = false)
	public Path configDir;
	
	//Checks for existence of the configuration files
	@Listener
	public void onPreInitialization(GamePreInitializationEvent event){
		
		Dreadzone = this;
		
		LoadConfig.loadConfig(configDir);
		
		ReloadExecutor.configurationDirectory(configDir);
	}

	@Inject
	@DefaultConfig(sharedRoot = false)
	public File configuration;
	
	@Inject
	@DefaultConfig(sharedRoot = false)
	public ConfigurationLoader<CommentedConfigurationNode> configurationLoader;
	
	public static CommentedConfigurationNode configurationNode;

	//Checks for the main configuration
	@Listener
	public void onInit (GameInitializationEvent event){
		
		logger.info("Dread Zone loading...");
		
		try{
			if(!configuration.exists()){
				configuration.createNewFile();
				configurationNode = configurationLoader.load();
				configurationNode.setValue("This is a test");
				configurationNode.getNode("test").setComment("Dread Zone Configuration.");
				configurationNode.getNode("arenas", "bool").setValue(false);
				configurationNode.getNode("rods", "string").setValue("Hello");
				configurationLoader.save(configurationNode);
			}
			configurationNode = configurationLoader.load();
			
		}catch(IOException exception){
			
			logger.warning("Error loading default configuration!"); 
		}
		
		if(!MobCreateConfigUtils.getTargets().isEmpty()){
			
			MobCreateConfigUtils.resetMobCreateBoolean();
		}
		
		//Commands
	    CommandSpec reloadCmd = CommandSpec.builder()
	    	      .description(Text.of("Reload Dread Zone configuration."))
	    	      .executor(new ReloadExecutor())
	    	      .build();	
	    
		CommandSpec addArenaSpawnsCmd = CommandSpec.builder()
				.description(Text.of("Set arena team spawnpoints."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("spawn name"))))
				.executor(new TDM())
				.build();
		
		CommandSpec arenaListCmd = CommandSpec.builder()
				.description(Text.of("Gets a list of all the Dread Zone arenas."))
				.executor(new ViewArenas())
				.build();
		
		CommandSpec classListCmd = CommandSpec.builder()
				.description(Text.of("Gets a list of all the classes for a specified arena."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))))
				.executor(new ViewClasses())
				.build();
		
		CommandSpec addClassItemCmd = CommandSpec.builder()
				.description(Text.of("Add an item in hand to an arena class."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))),
						GenericArguments.string(Text.of("class name")))
				.executor(new AddClassItem())
				.build();
		
		CommandSpec removeClassItemCmd = CommandSpec.builder()
				.description(Text.of("Remove a class item based on the exact item stack in hand."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))),
						GenericArguments.string(Text.of("class name")))
				.executor(new DeleteClassItem())
				.build();	
		
		CommandSpec addClassNPCCmd = CommandSpec.builder()
				.description(Text.of("Add a Dread Zone NPC Class spawnpoint."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("class name"))))
				.executor(new AddClassNPC())
				.build();	
		
		CommandSpec leaveArenaCmd = CommandSpec.builder()
				.description(Text.of("Leave an arena."))
				.executor(new LeaveArena())
				.build();
		
		CommandSpec addLightningRodCmd = CommandSpec.builder()
				.description(Text.of("Add a lightning rod target."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("target name"))))
				.executor(new AddLightningRod())
				.build();
		
		CommandSpec removeLightningRodCmd = CommandSpec.builder()
				.description(Text.of("Remove a lightning rod target."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("target name"))))
				.executor(new DeleteLightningRod())
				.build();
		
		CommandSpec setDZLSACmd = CommandSpec.builder()
				.description(Text.of("Set a Dread Zone's lobby spawn point."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))))
				.executor(new SetLobbySpawn())
				.build();
		
		CommandSpec moveLobbyCmd = CommandSpec.builder()
				.description(Text.of("Re-create a specified Dread Zone arena lobby."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))))
				.executor(new MoveArenaLobby())
				.build();
		
		CommandSpec removeNodeCmd = CommandSpec.builder()
				.description(Text.of("Remove a capture node."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("node name"))))
				.executor(new DeleteNode())
				.build();
		
		CommandSpec removeMobCreateCmd = CommandSpec.builder()
				.description(Text.of("Remove a mob create."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("group name"))),
						GenericArguments.string(Text.of("target name")))
				.executor(new DeleteMobCrate())
				.build();
		
		
		
		CommandSpec TDMMode = CommandSpec.builder()
				.description(Text.of("Add TDM mode for an arena."))
				.arguments(GenericArguments.integer(Text.of("number of players per team")))
				.executor(new AddTDMMode())
				.build();
		
		CommandSpec PABMode = CommandSpec.builder()
				.description(Text.of("Add PAB mode for an arena."))
				.executor(new AddPABMode())
				.build();
		
		CommandSpec addArenaModeCmd = CommandSpec.builder()
				.description(Text.of("Add an arena Mode."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))))
				.child(TDMMode, "TDM")
				.child(PABMode, "PAB")
				.build();
		
		
		CommandSpec PABCmd = CommandSpec.builder()
				.description(Text.of("Set PAB point A for an arena."))
				.executor(new PAB())
				.build();
		
		CommandSpec addMobCrateCmd = CommandSpec.builder()
				.description(Text.of("Add a mob crate dropper."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("group name"))),
						GenericArguments.string(Text.of("mobcrate name")))
				.executor(new AddMobCrate())
				.build();
		
		CommandSpec addClassCmd = CommandSpec.builder()
				.description(Text.of("Add an arena class."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))),
						GenericArguments.string(Text.of("class name")),
						GenericArguments.integer(Text.of("number of class items")))
				.executor(new AddClass())
				.build();
		
		CommandSpec removeClassCmd = CommandSpec.builder()
				.description(Text.of("Remove a class from an arena."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))),
						GenericArguments.string(Text.of("class name")))
				.executor(new DeleteClass())
				.build();
		
		CommandSpec addNodeCmd = CommandSpec.builder()
				.description(Text.of("Add a capture node."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("node name"))))
				.executor(new AddNode())
				.build();
		
		CommandSpec createArenaCmd = CommandSpec.builder()
				.description(Text.of("Create a Dread Zone arena."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))))
				.executor(new SetArena())
				.build();
		
		CommandSpec arenaModes = CommandSpec.builder()
				.description(Text.of("Get a list of all of the avaliable modes for a specified arena."))
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))))
				.executor(new ViewModes())
				.build();

		CommandSpec joinCmd = CommandSpec.builder()
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))),
						GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("mode")))))
				.executor(new JoinArena())
				.build();	
		
		
		
		
		CommandSpec resetMobCreatesCmd = CommandSpec.builder()
				.description(Text.of("Reset all mob crates."))
				.executor(new ResetMobCreate())
				.build();	
		
		CommandSpec resetNodesCmd = CommandSpec.builder()
				.description(Text.of("Reset all capture nodes."))
				.executor(new ResetNodes())
				.build();	
		
	    CommandSpec readyCmd = CommandSpec.builder()
	    	      .description(Text.of("Ready for combat!"))
	    	      .executor(new Ready())
	    	      .build();
		    
	    
		CommandSpec dreadZoneCmd = CommandSpec.builder()
				.description(Text.of("Dread Zone."))
				.child(readyCmd, "ready")
				.child(leaveArenaCmd, "leave")
				.child(reloadCmd, "reload")
				.child(joinCmd, "join")
				.child(addLightningRodCmd, "addLightningRod","alr")
				.child(addClassNPCCmd, "addClassNPC","acnpc")
				.child(addClassCmd, "addClass","ac")
				.child(addClassItemCmd, "addClassItem","aci")
				.child(addArenaModeCmd, "addArenaMode","aam")
				.child(addNodeCmd, "addNode","an")
				.child(addMobCrateCmd, "addMobCrate","amc")
				.child(addArenaSpawnsCmd, "addArenaSpawns","aas")
				.child(createArenaCmd, "createArena","ca")
				.child(removeClassItemCmd, "removeClassItem","rci")
				.child(removeClassCmd, "removeClass","rc")
				.child(removeLightningRodCmd, "removeLightningRod","rlr")
				.child(removeNodeCmd, "removeNode","rn")
				.child(removeMobCreateCmd,"removeMobCreate","rmc")
				.child(resetMobCreatesCmd, "resetMobCrates","rmcs")
				.child(resetNodesCmd, "resetNodes","rnds")
				.child(arenaListCmd, "arenaList","al")
				.child(classListCmd, "classList","cl")
				.child(setDZLSACmd, "setLobbySpawn","slp")
				.child(moveLobbyCmd, "moveLobby","ml")
				.child(PABCmd, "PointA","PA")
				.child(arenaModes, "arenaModes", "am")
				.executor(new DZCMD())
				.build();
		
		CommandSpec testChildCmd0 = CommandSpec.builder()
				.description(Text.of("Test child method."))
				.executor(new TestExecutor0())
				.build();
		CommandSpec testChildCmd1 = CommandSpec.builder()
				.description(Text.of("Test child method."))
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("optionalTestArguments")))))
				.executor(new TestExecutor1())
				.build();
		CommandSpec testCmd = CommandSpec.builder()
				.description(Text.of("Test method."))
				.child(testChildCmd0,"child0")
				.child(testChildCmd1, "child1")
				.build();
	    
	    game.getCommandManager().register(this, testCmd, "test");
	    game.getCommandManager().register(this, dreadZoneCmd,"dz");

		
		//game.getEventManager().registerListeners(this, new TDMListeners());
		game.getEventManager().registerListeners(this, new DZNPCListener());
		game.getEventManager().registerListeners(this, new NodeListener());
		game.getEventManager().registerListeners(this, new GeneralArenaListeners());
		game.getEventManager().registerListeners(this, new MobCreateImpact());
		game.getEventManager().registerListeners(this, new MobCreatePlayerDetector());
		game.getEventManager().registerListeners(this, new RightClickMode());
		game.getEventManager().registerListeners(this, new NodeBlockTracker());		
		game.getEventManager().registerListeners(this, new PBDetector());	
		game.getEventManager().registerListeners(this, new PABListeners());	
	}
	
	@Listener
	public void postInit (GamePostInitializationEvent event){
		
		if(!LightningConfigUtils.getTargets().isEmpty()){
			
			Sponge.getScheduler().createTaskBuilder().execute(new AutomatedLightningTimer()).submit(this);
		}
	}
	
	@Listener 
	public void postStartedServer(GameStartedServerEvent event){
		
		if(!NodeConfigUtils.getNodes().isEmpty()){
			
			ResetNodes.resetTeamNodes();
		}
	}
	
	@Listener
	public void onServerStopping(GameStoppingServerEvent event){
		
		if(!NodeConfigUtils.getNodes().isEmpty()){
			
			ResetNodes.resetTeamNodes();
		}
	}
	
	@Listener
	public void onServerStop (GameStoppedServerEvent event){
		
		//MobCreateConfigUtils.resetMobCreateBoolean();
		
		logger.info("Dread Zone Plugin Stopped!");
	}
	
	public Path getConfigDir()
	{
		return configDir;
	}
	
    public static PluginContainer getPluginContainer() {
    	
        return Dreadzone.PC;
    }
	
	public static Main getDreadZone(){
		
		return Dreadzone;
	}
}
