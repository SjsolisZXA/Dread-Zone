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
import Add.AddJumpPad;
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
import Executors.EditArena;
import Executors.JoinArena;
import Executors.LeaveArena;
import Executors.MoveArenaLobby;
import Executors.Ready;
import Listeners.MobCreatePlayerDetector;
import Listeners.NodeBlockTracker;
import Listeners.DZNPCListeners;
import Listeners.GeneralArenaListeners;
import Listeners.JumpPadListener;
import Listeners.MobCreateImpact;
import Listeners.NodeListener;
import Listeners.PABListeners;
import Listeners.PBDetector;
import Listeners.RightClickMode;
import Listeners.TDMListeners;
import Modes.PAB;
import Modes.TDM;
import Reset.ResetMobCreate;
import Reset.ResetNodes;
import Utils.AutomatedLightningTimer;
import Utils.LoadConfig;
import Setters.SetArena;
import Setters.SetCreditsLocation;
import Setters.SetLobbySpawn;
import Test.TestExecutor0;
import Test.TestExecutor1;
import Test.TestExecutor2;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Plugin(id = "dreadzone", 
	name = "DreadZone", 
	description = "This plugin adds minigames!", 
	url = "http://salvadorzxa.com", 
	version = "0.1.2",
	authors = "SalvadorZXA")

public class Main {
	
	public static Main Dreadzone;
	
	@Inject
	private Logger logger;
	
	public Logger getLogger()
	{
		return logger;
	}
	
	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir;

	@Inject
	Game game;
	
    @Inject
    private PluginContainer PC;
	
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
		
		getLogger().info("Dread Zone loading...");
		
		try{
			if(!configuration.exists()){
				configuration.createNewFile();
				configurationNode = configurationLoader.load();
				configurationNode.getNode("#").setComment("Dread Zone Configuration. Do not alter the configuration unless you know what you are going.");
				configurationNode.getNode("Arenas", "Status").setValue(true).setComment("Set the status of the the Dread Zone arenas.");
				configurationNode.getNode("Lightning Rods", "Status").setValue(true).setComment("Set the status of the Dread Zone Lightning Rods.");
				configurationLoader.save(configurationNode);
			}
			configurationNode = configurationLoader.load();
			
		}catch(IOException exception){
			
			getLogger().warning("Error loading default configuration!"); 
		}
		
		if(!MobCreateConfigUtils.getTargets().isEmpty()){
			MobCreateConfigUtils.resetMobCreateBoolean();
		}
		
		//Commands
	    CommandSpec reloadCmd = CommandSpec.builder()
	    	      .description(Text.of("Reload Dread Zone configuration."))
	    	      .permission(getPluginContainer().getId()+".reload")
	    	      .executor(new ReloadExecutor())
	    	      .build();	
	    
		CommandSpec addArenaSpawnPointCmd = CommandSpec.builder()
				.description(Text.of("Set arena team spawnpoints."))
				.permission(getPluginContainer().getId()+".aasp")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("spawn name")))))
				.executor(new TDM())
				.build();
		
		CommandSpec arenaListCmd = CommandSpec.builder()
				.description(Text.of("Gets a list of all the Dread Zone arenas."))
				.permission(getPluginContainer().getId()+".al")
				.executor(new ViewArenas())
				.build();
		
		CommandSpec classListCmd = CommandSpec.builder()
				.description(Text.of("Gets a list of all the classes for a specified arena."))
				.permission(getPluginContainer().getId()+".cl")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))))
				.executor(new ViewClasses())
				.build();
		
		CommandSpec addClassItemCmd = CommandSpec.builder()
				.description(Text.of("Add an item in hand to an arena class."))
				.permission(getPluginContainer().getId()+".aci")
				.arguments(GenericArguments.optional(GenericArguments.string(Text.of("class name"))),
						GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))))
				.executor(new AddClassItem())
				.build();
		
		CommandSpec removeClassItemCmd = CommandSpec.builder()
				.description(Text.of("Remove a class item based on the exact item stack in hand."))
				.permission(getPluginContainer().getId()+".rci")
				.arguments(GenericArguments.optional(GenericArguments.string(Text.of("class name"))),
						GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))))
				.executor(new DeleteClassItem())
				.build();	
		
		CommandSpec addClassNPCCmd = CommandSpec.builder()
				.description(Text.of("Add a Dread Zone NPC Class spawnpoint."))
				.permission(getPluginContainer().getId()+".acnpc")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("class name")))))
				.executor(new AddClassNPC())
				.build();	
		
		CommandSpec leaveArenaCmd = CommandSpec.builder()
				.description(Text.of("Leave an arena."))
				.permission(getPluginContainer().getId()+".leave")
				.executor(new LeaveArena())
				.build();
		
		CommandSpec addLightningRodCmd = CommandSpec.builder()
				.description(Text.of("Add a lightning rod target."))
				.permission(getPluginContainer().getId()+".alr")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("target name")))))
				.executor(new AddLightningRod())
				.build();
		
		CommandSpec addJumpPadCmd = CommandSpec.builder()
				.description(Text.of("Add a jump pad."))
				.permission(getPluginContainer().getId()+".ajp")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("pad name")))))
				.executor(new AddJumpPad())
				.build();
		
		CommandSpec removeLightningRodCmd = CommandSpec.builder()
				.description(Text.of("Remove a lightning rod target."))
				.permission(getPluginContainer().getId()+".rlr")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("target name")))))
				.executor(new DeleteLightningRod())
				.build();
		
		CommandSpec setDZLSPmd = CommandSpec.builder()
				.description(Text.of("Set a Dread Zone's lobby spawn point."))
				.permission(getPluginContainer().getId()+".slsp")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))))
				.executor(new SetLobbySpawn())
				.build();
		
		CommandSpec moveLobbyCmd = CommandSpec.builder()
				.description(Text.of("Re-create a specified Dread Zone arena lobby."))
				.permission(getPluginContainer().getId()+".ml")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))))
				.executor(new MoveArenaLobby())
				.build();
		
		@SuppressWarnings("unused")
		CommandSpec removeNodeCmd = CommandSpec.builder()
				.description(Text.of("Remove a capture node."))
				.permission(getPluginContainer().getId()+".rn")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("node name")))))
				.executor(new DeleteNode())
				.build();
		
		CommandSpec removeMobCreateCmd = CommandSpec.builder()
				.description(Text.of("Remove a mob create."))
				.permission(getPluginContainer().getId()+".rmc")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("group name")))),
						GenericArguments.optional(GenericArguments.string(Text.of("target name"))))
				.executor(new DeleteMobCrate())
				.build();
		
		
		
		CommandSpec TDMMode = CommandSpec.builder()
				.description(Text.of("Add TDM mode for an arena."))
				.permission(getPluginContainer().getId()+".tdm")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))),
						GenericArguments.optional(GenericArguments.integer(Text.of("number of players per team"))))
				.executor(new AddTDMMode())
				.build();
		
		CommandSpec PABMode = CommandSpec.builder()
				.description(Text.of("Add PAB mode for an arena."))
				.permission(getPluginContainer().getId()+".pab")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))))
				.executor(new AddPABMode())
				.build();
		
		CommandSpec addArenaModeCmd = CommandSpec.builder()
				.description(Text.of("Add an arena Mode."))
				.permission(getPluginContainer().getId()+".aam")
				.child(TDMMode, "TDM")
				.child(PABMode, "PAB")
				.build();
		
		
		CommandSpec PABCmd = CommandSpec.builder()
				.description(Text.of("Set PAB point A for an arena."))
				.permission(getPluginContainer().getId()+".pa")
				.executor(new PAB())
				.build();
		
		CommandSpec addMobCrateCmd = CommandSpec.builder()
				.description(Text.of("Add a mob crate dropper."))
				.permission(getPluginContainer().getId()+".amc")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("group name")))),
						GenericArguments.optional(GenericArguments.string(Text.of("mobcrate name"))))
				.executor(new AddMobCrate())
				.build();
		
		CommandSpec addClassCmd = CommandSpec.builder()
				.description(Text.of("Add an arena class."))
				.permission(getPluginContainer().getId()+".ac")
				.arguments(GenericArguments.optional(GenericArguments.string(Text.of("class name"))),
						GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))))
				.executor(new AddClass())
				.build();
		
		CommandSpec removeClassCmd = CommandSpec.builder()
				.description(Text.of("Remove a class from an arena."))
				.permission(getPluginContainer().getId()+".rc")
				.arguments(GenericArguments.optional(GenericArguments.string(Text.of("class name"))),
						GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))))
				.executor(new DeleteClass())
				.build();
		
		@SuppressWarnings("unused")
		CommandSpec addNodeCmd = CommandSpec.builder()
				.description(Text.of("Add a capture node."))
				.permission(getPluginContainer().getId()+".an")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("node name")))))
				.executor(new AddNode())
				.build();
		
		CommandSpec createArenaCmd = CommandSpec.builder()
				.description(Text.of("Create a Dread Zone arena."))
				.permission(getPluginContainer().getId()+".ca")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))))
				.executor(new SetArena())
				.build();
		
		CommandSpec arenaModes = CommandSpec.builder()
				.description(Text.of("Get a list of all of the avaliable modes for a specified arena."))
				.permission(getPluginContainer().getId()+".am")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))))
				.executor(new ViewModes())
				.build();

		CommandSpec joinCmd = CommandSpec.builder()
				.description(Text.of("Join an arena."))
				.permission(getPluginContainer().getId()+".join")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))),
						GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("mode")))))
				.executor(new JoinArena())
				.build();	
		CommandSpec editArena = CommandSpec.builder()
				.description(Text.of("Edit an arena."))
				.permission(getPluginContainer().getId()+".ea")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))))
				.executor(new EditArena())
				.build();	
		CommandSpec setCreditsLocation = CommandSpec.builder()
				.description(Text.of("Set the credits location of an arena."))
				.permission(getPluginContainer().getId()+".scl")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name")))))
				.executor(new SetCreditsLocation())
				.build();
		
		
		
		
		CommandSpec resetMobCreatesCmd = CommandSpec.builder()
				.description(Text.of("Reset all mob crates."))
				.permission(getPluginContainer().getId()+".rmcs")
				.executor(new ResetMobCreate())
				.build();	
		
		@SuppressWarnings("unused")
		CommandSpec resetNodesCmd = CommandSpec.builder()
				.description(Text.of("Reset all capture nodes."))
				.permission(getPluginContainer().getId()+".rnds")
				.executor(new ResetNodes())
				.build();	
		
	    CommandSpec readyCmd = CommandSpec.builder()
	    	      .description(Text.of("Ready for combat!"))
	    	      .permission(getPluginContainer().getId()+".ready")
	    	      .executor(new Ready())
	    	      .build();
		    
	    
		CommandSpec dreadZoneCmd = CommandSpec.builder()
				.description(Text.of("Dread Zone."))
				.permission(getPluginContainer().getId()+".dz")
				.child(readyCmd, "ready")//
				.child(leaveArenaCmd, "leave")//
				.child(reloadCmd, "reload")//
				.child(joinCmd, "join")//
				.child(addLightningRodCmd, "addLightningRod","alr")//
				.child(addClassNPCCmd, "addClassNPC","acnpc")//
				.child(addClassCmd, "addClass","ac")//
				.child(addJumpPadCmd, "addJumpPad","ajp")//
				.child(addClassItemCmd, "addClassItem","aci")//
				.child(addArenaModeCmd, "addArenaMode","aam")//
				//.child(addNodeCmd, "addNode","an")//
				.child(addMobCrateCmd, "addMobCrate","amc")//
				.child(addArenaSpawnPointCmd, "addArenaSpawnPoint","aasp")//
				.child(createArenaCmd, "createArena","ca")//
				.child(removeClassItemCmd, "removeClassItem","rci")//
				.child(removeClassCmd, "removeClass","rc")//
				.child(removeLightningRodCmd, "removeLightningRod","rlr")//
				//.child(removeNodeCmd, "removeNode","rn")//
				.child(removeMobCreateCmd,"removeMobCreate","rmc")//
				.child(resetMobCreatesCmd, "resetMobCrates","rmcs")//
				//.child(resetNodesCmd, "resetNodes","rnds")//
				.child(arenaListCmd, "arenaList","al")//
				.child(classListCmd, "classList","cl")//
				.child(setDZLSPmd, "setLobbySpawnPoint","slsp")//
				.child(moveLobbyCmd, "moveLobby","ml")//
				.child(PABCmd, "PointA","PA")//
				.child(arenaModes, "arenaModes", "am")//
				.child(editArena, "editArena", "ea")//
				.child(setCreditsLocation, "setCreditsLocation", "scl")//
				.executor(new DZCMD())
				.build();
		
		CommandSpec testChildCmd2 = CommandSpec.builder()
				.description(Text.of("Test child method 2."))
				.permission(getPluginContainer().getId()+".child2")
				.executor(new TestExecutor2())
				.build();
		CommandSpec testChildCmd1 = CommandSpec.builder()
				.description(Text.of("Test child method 1."))
				.permission(getPluginContainer().getId()+".child1")
				.executor(new TestExecutor1())
				.build();
		CommandSpec testChildCmd0 = CommandSpec.builder()
				.description(Text.of("Test child method 0."))
				.permission(getPluginContainer().getId()+".child0")
				.arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.string(Text.of("optionalTestArguments")))))
				.executor(new TestExecutor0())
				.build();
		
		
		CommandSpec testCmd = CommandSpec.builder()
				.description(Text.of("Test method."))
				.permission(getPluginContainer().getId()+".test")
				.child(testChildCmd0,"child0")
				.child(testChildCmd1, "child1")
				.child(testChildCmd2, "child2")
				.build();
	    
	    game.getCommandManager().register(this, testCmd, "test");
	    game.getCommandManager().register(this, dreadZoneCmd,"dz");

		
		game.getEventManager().registerListeners(this, new TDMListeners());
		game.getEventManager().registerListeners(this, new DZNPCListeners());
		game.getEventManager().registerListeners(this, new NodeListener());
		game.getEventManager().registerListeners(this, new GeneralArenaListeners());
		game.getEventManager().registerListeners(this, new MobCreateImpact());
		game.getEventManager().registerListeners(this, new MobCreatePlayerDetector());
		game.getEventManager().registerListeners(this, new RightClickMode());
		game.getEventManager().registerListeners(this, new NodeBlockTracker());		
		game.getEventManager().registerListeners(this, new PBDetector());	
		game.getEventManager().registerListeners(this, new PABListeners());	
		game.getEventManager().registerListeners(this, new JumpPadListener());
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
		
		getLogger().info("Dread Zone Plugin Stopped!");
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
