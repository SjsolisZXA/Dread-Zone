package Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import ConfigFiles.ArenaFileConfig;
import ConfigFiles.JumpPadFileConfig;
import ConfigFiles.LightningFileConfig;
import ConfigFiles.MobCrateFileConfig;
import ConfigFiles.NodeFileConfig;
import ConfigFiles.RightClickModeFileConfig;

public class LoadConfig {
	
	public static void loadConfig(Path configDir) {
			
		if (!Files.exists(configDir)){//creates the "dreadzone" folder
			try {
				Files.createDirectories(configDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (!Files.exists(configDir.resolve("data")))//creates the "data" subfolder
		{
			try{
				Files.createDirectories(configDir.resolve("data"));
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
		
		if (!Files.exists(configDir.resolve("Databases")))//creates the "database" subfolder
		{
			try{
				
				Files.createDirectories(configDir.resolve("Databases"));
			}
			catch (IOException e){
				
				e.printStackTrace();
			}
		}
		
		LightningFileConfig.getConfig().setup();
		MobCrateFileConfig.getConfig().setup();
		NodeFileConfig.getConfig().setup();
		ArenaFileConfig.getConfig().setup();
		JumpPadFileConfig.getConfig().setup();
		RightClickModeFileConfig.getConfig().setup();
	}

}
