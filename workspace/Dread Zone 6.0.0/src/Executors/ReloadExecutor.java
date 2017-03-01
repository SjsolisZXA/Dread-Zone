package Executors;

import java.nio.file.Path;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import Utils.LoadConfig;

public class ReloadExecutor implements CommandExecutor {
	
	static Path configurationDirectory;

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    	
        LoadConfig.loadConfig(configurationDirectory);
        
        src.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", TextColors.WHITE, "Dread Zone configuration reloaded!"));
        
        return CommandResult.success();
    }

	public static void configurationDirectory(Path configDir) {
		
		configurationDirectory = configDir;
	}
}
