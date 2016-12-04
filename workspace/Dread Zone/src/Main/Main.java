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

import ConfigFiles.LightningFileConfig;
import ConfigFiles.MobCrateFileConfig;
import ConfigFiles.NodeFileConfig;
import ConfigUtils.MobCreateConfigUtils;
import Delete.DeleteLightningRod;
import Delete.DeleteMobCrate;
import Delete.DeleteNode;
import Executors.JoinExecuter;
import Executors.TestExecutor;
import Listeners.FreezePlayer;
import Listeners.MobCreatePlayerDetector;
import Listeners.MobCreateImpact;
import Listeners.NodeListener;
import Reset.ResetMobCreate;
import Reset.ResetNodes;
import Setters.SetNode;
import Setters.SetLightningRodLocation;
import Setters.SetMobCrateLocation;
import Utils.AutomatedLightningTimer;
import Utils.TeamDeathmatchTimerTask;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;


@Plugin(id = "DreadZone", name = "Dread Zone", version = "0.0.0")

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
	}
	
	//Checks for configuration file
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
		CommandSpec joinCmd = CommandSpec.builder()
				.description(Text.of("Join a Dread Zone Arena."))
				.arguments(GenericArguments.onlyOne(
				GenericArguments.string(Text.of("arena"))))
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
		
		game.getCommandManager().register(this, testCmd, "test");
		game.getCommandManager().register(this, nodeCmd, "dznode");
		game.getCommandManager().register(this, lightiningRodCmd, "dzlrod");
		game.getCommandManager().register(this, deleteLightiningRodCmd, "rdzlrod");
		game.getCommandManager().register(this, deleteNodeCmd, "rdznode");
		game.getCommandManager().register(this, resetArenaMobCreatesCmd, "rdzamcs");
		game.getCommandManager().register(this, resetNodesCmd, "rdzns");
		game.getCommandManager().register(this, deleteMobCreateCmd, "rdzms");
		game.getCommandManager().register(this, mobSetterCmd, "dzms");
		game.getCommandManager().register(this, joinCmd, "dzjoin");
		
		game.getEventManager().registerListeners(this, new NodeListener());
		game.getEventManager().registerListeners(this, new FreezePlayer());
		game.getEventManager().registerListeners(this, new MobCreateImpact());
		game.getEventManager().registerListeners(this, new MobCreatePlayerDetector());
		
        game.getCommandManager().register(this,CommandSpec.builder().executor((source, commandContext) -> {
        	game.getScheduler().createTaskBuilder().interval(1, TimeUnit.SECONDS).delay(1, TimeUnit.SECONDS)
        	.execute(new TeamDeathmatchTimerTask(source, commandContext)).submit(this);
        	return CommandResult.success();
        }).build(), "timer");
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
