package Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import ConfigFiles.ArenaFileConfig;
import ConfigFiles.LightningFileConfig;
import ConfigFiles.MobCrateFileConfig;
import ConfigFiles.NodeFileConfig;
import ConfigFiles.RightClickModeFileConfig;

public class LoadConfig {
	
	public static void loadConfig(Path configDir) {
			
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

}
