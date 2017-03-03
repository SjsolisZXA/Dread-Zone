package Test;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class TestExecutor1 implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		//PABConfigUtils.getPABBLocation("Stygia").add(0,1,0).setBlockType(BlockTypes.STAINED_GLASS,Cause.source(Main.getPluginContainer()).build());

		Optional<String> opTA = args.<String>getOne("optionalTestArguments");
		
		if(!opTA.isPresent()){
			
			src.sendMessage(Text.of("Not enough ARGUMENTS BITCH!"));
			
			return CommandResult.success();
		}
		
		src.sendMessage(Text.of("The thrid argument was provided!"));
		
		return CommandResult.success();
	}

}
