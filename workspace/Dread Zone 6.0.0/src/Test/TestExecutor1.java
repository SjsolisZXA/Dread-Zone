package Test;

import java.util.List;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.player.Player;

public class TestExecutor1 implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		Player player = (Player)src;
		
		List<PotionEffect> effects = player.get(Keys.POTION_EFFECTS).get();
		
		effects.clear(); 
		
		player.offer(Keys.POTION_EFFECTS, effects);
		
		return CommandResult.success();
	}

}
