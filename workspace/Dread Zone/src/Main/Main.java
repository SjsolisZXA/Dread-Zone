package Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
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
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;

import ConfigFiles.ArenaFileConfig;
import ConfigFiles.LightningFileConfig;
import ConfigFiles.MobCrateFileConfig;
import ConfigFiles.NodeFileConfig;
import ConfigFiles.RightClickModeFileConfig;
import ConfigUtils.LightningConfigUtils;
import ConfigUtils.MobCreateConfigUtils;
import Delete.DeleteLightningRod;
import Delete.DeleteMobCrate;
import Delete.DeleteNode;
import Executors.ArenaListExecutor;
import Executors.JoinExecuter;
import Executors.LeaveArenaExecutor;
import Executors.MoveLobbyExecutor;
import Executors.TestExecutor;
import Listeners.MobCreatePlayerDetector;
import Listeners.MobCreateImpact;
import Listeners.NodeListener;
import Listeners.PlayerBarrier;
import Listeners.RightClickMode;
import Reset.ResetMobCreate;
import Reset.ResetNodes;
import Setters.SetNode;
import Setters.SetArena;
import Setters.SetArenaMode;
import Setters.SetLightningRodLocation;
import Setters.SetLobbySpawn;
import Setters.SetMobCrateLocation;
import Utils.AutomatedLightningTimer;
import Utils.TeamDeathmatchTimerTask;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;


@Plugin(id = "dreadzone", name = "Dread Zone", version = "0.0.1")

public class Main {
	public static Main dreadzone;
	
	@Inject
	Game game;
	
	@Inject
	private Logger logger;
	
	@Inject
	@ConfigDir(sharedRoot = false)
	public Path configDir;
	
	public static Main getDreadZone()
	{
		return dreadzone;
	}
	
	//Checks for existence of the configuration files
	@Listener
	public void onPreInitialization(GamePreInitializationEvent event){
		dreadzone = this;
		
		if (!Files.exists(configDir)){
			
			if (Files.exists(configDir)){
				
				try {
					Files.move(configDir, configDir);
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
			else{
				
				try{
					
					Files.createDirectories(configDir);
				}
				catch (IOException e){
					
					e.printStackTrace();
				}
			}
		}
		if (!Files.exists(configDir.resolve("data")))
		{
			try{
				
				Files.createDirectories(configDir.resolve("data"));
			}
			catch (IOException e){
				
				e.printStackTrace();
			}
		}
		LightningFileConfig.getConfig().setup();
		MobCrateFileConfig.getConfig().setup();
		NodeFileConfig.getConfig().setup();
		ArenaFileConfig.getConfig().setup();
		
		if (!Files.exists(configDir.resolve("Databases")))
		{
			try{
				
				Files.createDirectories(configDir.resolve("Databases"));
			}
			catch (IOException e){
				
				e.printStackTrace();
			}
		}
		RightClickModeFileConfig.getConfig().setup();
	}
	
	//Checks for the main configuration
	@Inject
	@DefaultConfig(sharedRoot = false)
	public File configuration = null;
	
	@Inject
	@DefaultConfig(sharedRoot = false)
	public ConfigurationLoader<CommentedConfigurationNode> configurationLoader = null;
	
	public CommentedConfigurationNode configurationNode = null;

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
		
		MobCreateConfigUtils.resetMobCreateBoolean();
		
		//Commands
		CommandSpec testCmd = CommandSpec.builder()
				.description(Text.of("Test method."))
				.executor(new TestExecutor())
				.build();
		CommandSpec dzArenasCmd = CommandSpec.builder()
				.description(Text.of("Gets all of the Dread Zone arenas"))
				.executor(new ArenaListExecutor())
				.build();
		CommandSpec ldzarenaCmd = CommandSpec.builder()
				.description(Text.of("Allows a dread Zone contestant to leave the current arena they are in"))
				.executor(new LeaveArenaExecutor())
				.build();
		CommandSpec lightiningRodCmd = CommandSpec.builder()
				.description(Text.of("Set lighting target."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("target name"))))
				.executor(new SetLightningRodLocation())
				.build();
		CommandSpec deleteLightiningRodCmd = CommandSpec.builder()
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
				.executor(new MoveLobbyExecutor())
				.build();
		CommandSpec deleteNodeCmd = CommandSpec.builder()
				.description(Text.of("Delete DZ Node."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("node name"))))
				.executor(new DeleteNode())
				.build();
		CommandSpec deleteMobCreateCmd = CommandSpec.builder()
				.description(Text.of("Delete Mob Create spawner location."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("group name"))),
						GenericArguments.string(Text.of("target name")))
				.executor(new DeleteMobCrate())
				.build();
		CommandSpec setArenaModeCmd = CommandSpec.builder()
				.description(Text.of("Create a Mode for a specified arena."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))),
						GenericArguments.string(Text.of("mode")))
				.executor(new SetArenaMode())
				.build();
		CommandSpec mobSetterCmd = CommandSpec.builder()
				.description(Text.of("Set mob dropper spawner."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("group name"))),
						GenericArguments.string(Text.of("target name")))
				.executor(new SetMobCrateLocation())
				.build();
		CommandSpec nodeCmd = CommandSpec.builder()
				.description(Text.of("Create a capture node."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("node name"))))
				.executor(new SetNode())
				.build();
		CommandSpec cArenaCmd = CommandSpec.builder()
				.description(Text.of("Create a DZ arena."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))))
				.executor(new SetArena())
				.build();
		CommandSpec joinCmd = CommandSpec.builder()
				.description(Text.of("Join a Dread Zone Arena."))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("arena name"))),
						GenericArguments.string(Text.of("mode")))
				.executor(new JoinExecuter())
				.build();	
		CommandSpec resetArenaMobCreatesCmd = CommandSpec.builder()
				.description(Text.of("Reset Dread Zone Arena Mob Crates."))
				.executor(new ResetMobCreate())
				.build();	
		CommandSpec resetNodesCmd = CommandSpec.builder()
				.description(Text.of("Reset Dread Zone Arena Nodes."))
				.executor(new ResetNodes())
				.build();	
		game.getCommandManager().register(this, setDZLSACmd, "cdzlsa");
		game.getCommandManager().register(this, setArenaModeCmd, "cdzam");
		game.getCommandManager().register(this, dzArenasCmd, "dzarenas");
		game.getCommandManager().register(this, ldzarenaCmd, "dzlarena");
		game.getCommandManager().register(this, testCmd, "test");
		game.getCommandManager().register(this, nodeCmd, "dznode");
		game.getCommandManager().register(this, cArenaCmd, "dzcarena");
		game.getCommandManager().register(this, lightiningRodCmd, "dzlrod");
		game.getCommandManager().register(this, deleteLightiningRodCmd, "rdzlrod");
		game.getCommandManager().register(this, deleteNodeCmd, "rdznode");
		game.getCommandManager().register(this, resetArenaMobCreatesCmd, "rdzamcs");
		game.getCommandManager().register(this, resetNodesCmd, "rdzns");
		game.getCommandManager().register(this, deleteMobCreateCmd, "rdzms");
		game.getCommandManager().register(this, mobSetterCmd, "dzms");
		game.getCommandManager().register(this, joinCmd, "dzjoin");
		game.getCommandManager().register(this, moveLobbyCmd, "dzmlobby");
		
		game.getEventManager().registerListeners(this, new NodeListener());
		game.getEventManager().registerListeners(this, new PlayerBarrier());
		game.getEventManager().registerListeners(this, new MobCreateImpact());
		game.getEventManager().registerListeners(this, new MobCreatePlayerDetector());
		game.getEventManager().registerListeners(this, new RightClickMode());
		
		if(!LightningConfigUtils.getTargets().equals(null)){
		
		game.getCommandManager().register(this,CommandSpec.builder().executor((source, commandContext) -> {
        		
            	game.getScheduler().createTaskBuilder().interval(1, TimeUnit.SECONDS).delay(1, TimeUnit.SECONDS)
            	.execute(new TeamDeathmatchTimerTask(source, commandContext)).submit(this);
        	
	        	return CommandResult.success();
	        }).build(), "timer");
		}
	}
	
	@Listener
	public void postInit (GamePostInitializationEvent event){

		Sponge.getScheduler().createTaskBuilder().execute(new AutomatedLightningTimer()).submit(this);
	}
	
	@Listener 
	public void fgf(GameStartedServerEvent event){
		ResetNodes.resetTeamNodes();
	}
	
	@Listener
	public void onServerStopping(GameStoppingServerEvent event){
		
		ResetNodes.resetTeamNodes();
	}
	
	@Listener
	public void onServerStop (GameStoppedServerEvent event){
		
		MobCreateConfigUtils.resetMobCreateBoolean();
		
		logger.info("Dread Zone Plugin Stopped!");
	}
	
	public Path getConfigDir()
	{
		return configDir;
	}
}
