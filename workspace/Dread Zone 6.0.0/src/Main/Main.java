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

import Add.AddArenaMode;
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
import Executors.ReloadExecutor;
import Executors.JoinArena;
import Executors.LeaveArena;
import Executors.MoveArenaLobby;
import Executors.Ready;
import Executors.TestExecutor0;
import Executors.TestExecutor1;
import Listeners.MobCreatePlayerDetector;
import Listeners.NodeBlockTracker;
import Listeners.DZNPCListener;
import Listeners.GeneralArenaListeners;
import Listeners.MobCreateImpact;
import Listeners.NodeListener;
import Listeners.RightClickMode;
import Listeners.TDMListeners;
import Modes.TDM;
import Reset.ResetMobCreate;
import Reset.ResetNodes;
import Utils.AutomatedLightningTimer;
import Utils.LoadConfig;
import Setters.SetArena;
import Setters.SetLobbySpawn;
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
				.description(Text.of("Set specified arena spawn points"))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("spawn name"))))
				.executor(new TDM())
				.build();
		
		CommandSpec arenaListCmd = CommandSpec.builder()
				.description(Text.of("Gets all of the Dread Zone arenas"))
				.executor(new ViewArenas())
				.build();
		
		CommandSpec classListCmd = CommandSpec.builder()
				.description(Text.of("Gets all of the classes for a specified arena"))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))))
				.executor(new ViewClasses())
				.build();
		
		CommandSpec addClassItemCmd = CommandSpec.builder()
				.description(Text.of("Add an item in hand to a specified arena class"))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))),
						GenericArguments.string(Text.of("class name")))
				.executor(new AddClassItem())
				.build();
		
		CommandSpec removeClassItemCmd = CommandSpec.builder()
				.description(Text.of("Add an item in hand to a specified arena class"))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))),
						GenericArguments.string(Text.of("class name")))
				.executor(new DeleteClassItem())
				.build();	
		
		CommandSpec addClassNPCCmd = CommandSpec.builder()
				.description(Text.of("Set a spawnpoint for a Dread Zone NPC Class"))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("class name"))))
				.executor(new AddClassNPC())
				.build();	
		
		CommandSpec leaveArenaCmd = CommandSpec.builder()
				.description(Text.of("Allows a dread Zone contestant to leave the current arena they are in"))
				.executor(new LeaveArena())
				.build();
		
		CommandSpec addLightiningRodCmd = CommandSpec.builder()
				.description(Text.of("Set lighting target."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("target name"))))
				.executor(new AddLightningRod())
				.build();
		
		CommandSpec removeLightiningRodCmd = CommandSpec.builder()
				.description(Text.of("Delete lighting target."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("target name"))))
				.executor(new DeleteLightningRod())
				.build();
		
		CommandSpec setDZLSACmd = CommandSpec.builder()
				.description(Text.of("Set a Dread Zone's lobby spawn area."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))))
				.executor(new SetLobbySpawn())
				.build();
		
		CommandSpec moveLobbyCmd = CommandSpec.builder()
				.description(Text.of("Re-create a specified DZ arena lobby."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))))
				.executor(new MoveArenaLobby())
				.build();
		
		CommandSpec removeNodeCmd = CommandSpec.builder()
				.description(Text.of("Delete DZ Node."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("node name"))))
				.executor(new DeleteNode())
				.build();
		
		CommandSpec removeMobCreateCmd = CommandSpec.builder()
				.description(Text.of("Delete Mob Create spawner location."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("group name"))),
						GenericArguments.string(Text.of("target name")))
				.executor(new DeleteMobCrate())
				.build();
		
		CommandSpec addArenaModeCmd = CommandSpec.builder()
				.description(Text.of("Create a Mode for a specified arena."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))),
						GenericArguments.string(Text.of("mode")),GenericArguments.integer(Text.of("number of players per team")))
				.executor(new AddArenaMode())
				.build();
		
		CommandSpec addMobCrateCmd = CommandSpec.builder()
				.description(Text.of("Set mob dropper spawner."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("group name"))),
						GenericArguments.string(Text.of("target name")))
				.executor(new AddMobCrate())
				.build();
		
		CommandSpec addClassCmd = CommandSpec.builder()
				.description(Text.of("Add an arena class to a specified Dread Zone arena."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))),
						GenericArguments.string(Text.of("class name")),
						GenericArguments.integer(Text.of("number of class items")))
				.executor(new AddClass())
				.build();
		
		CommandSpec removeClassCmd = CommandSpec.builder()
				.description(Text.of("Delete an arena class in a specified Dread Zone arena."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))),
						GenericArguments.string(Text.of("class name")))
				.executor(new DeleteClass())
				.build();
		
		CommandSpec addNodeCmd = CommandSpec.builder()
				.description(Text.of("Create a capture node."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("node name"))))
				.executor(new AddNode())
				.build();
		
		CommandSpec createArenaCmd = CommandSpec.builder()
				.description(Text.of("Create a DZ arena."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))))
				.executor(new SetArena())
				.build();
		
		CommandSpec joinCmd = CommandSpec.builder()
				.description(Text.of("Join a Dread Zone Arena."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))),
						GenericArguments.string(Text.of("mode")))
				.executor(new JoinArena())
				.build();	
		
		CommandSpec resetMobCreatesCmd = CommandSpec.builder()
				.description(Text.of("Reset Dread Zone Arena Mob Crates."))
				.executor(new ResetMobCreate())
				.build();	
		
		CommandSpec resetNodesCmd = CommandSpec.builder()
				.description(Text.of("Reset Dread Zone Arena Nodes."))
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
				.child(addLightiningRodCmd, "addLightningRod","alr")
				.child(addClassNPCCmd, "addClassNPC","acnpc")
				.child(addClassCmd, "addClass","ac")
				.child(addClassItemCmd, "addClass","aci")
				.child(addArenaModeCmd, "addArenaMode","aam")
				.child(addNodeCmd, "addNode","an")
				.child(addMobCrateCmd, "addMobCrate","amc")
				.child(addArenaSpawnsCmd, "addArenaSpawns","aas")
				.child(createArenaCmd, "createArena","ca")
				.child(removeClassItemCmd, "removeClassItem","rci")
				.child(removeClassCmd, "removeClass","rc")
				.child(removeLightiningRodCmd, "removeLightiningRod","rlr")
				.child(removeNodeCmd, "removeNode","rn")
				.child(removeMobCreateCmd, "rmc")
				.child(resetMobCreatesCmd, "resetMobCrates","rmc")
				.child(resetNodesCmd, "resetNodes","rnds")
				.child(arenaListCmd, "arenaList","al")
				.child(classListCmd, "classList","cl")
				.child(setDZLSACmd, "setLobbySpawn","slp")
				.child(moveLobbyCmd, "moveLobby","ml")
				.build();
		
		CommandSpec testChildCmd0 = CommandSpec.builder()
				.description(Text.of("Test method."))
				.executor(new TestExecutor0())
				.build();
		CommandSpec testChildCmd1 = CommandSpec.builder()
				.description(Text.of("Test child method."))
				.executor(new TestExecutor1())
				.build();
		CommandSpec testCmd = CommandSpec.builder()
				.description(Text.of("Test method."))
				.child(testChildCmd0,"child0").child(testChildCmd1, "child1")
				.build();
	    
	    game.getCommandManager().register(this, testCmd, "test");
	    game.getCommandManager().register(this, dreadZoneCmd,"dz");

		
		game.getEventManager().registerListeners(this, new TDMListeners());
		game.getEventManager().registerListeners(this, new DZNPCListener());
		game.getEventManager().registerListeners(this, new NodeListener());
		game.getEventManager().registerListeners(this, new GeneralArenaListeners());
		game.getEventManager().registerListeners(this, new MobCreateImpact());
		game.getEventManager().registerListeners(this, new MobCreatePlayerDetector());
		game.getEventManager().registerListeners(this, new RightClickMode());
		game.getEventManager().registerListeners(this, new NodeBlockTracker());		
		
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
