package Executors;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import ConfigUtils.MobCreateConfigUtils;
import Utils.MobCrateSpawner;

public class TestExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		Player player =(Player)src;		
		
		Set<Object> targets = MobCreateConfigUtils.getTargets();

		Vector3i Plocation = (player.getLocation().getPosition().toInt());
		
		Location<World> location = new Location<>(player.getWorld(), Plocation.toDouble());
		
		for(Object object: targets){
			
			Set<Object> children = MobCreateConfigUtils.getTargetChildren(object);
			
			for(Object child: children){
				
				String group = object.toString();
				
				String target = child.toString();
			
				if(location.equals(MobCreateConfigUtils.getTarget(group,target))){
					
					for(Object groupChildren: children){
						
						String GC = groupChildren.toString();
					
					    MobCrateSpawner.spawnMobDropper(MobCreateConfigUtils.getTarget(group,GC),ThreadLocalRandom.current().nextInt(7));
					}
					return CommandResult.success();
				}
			}
		}
		player.sendMessage(Text.of(TextColors.DARK_RED,"You're not in range of a DZ Mb Crate spawning location!"));
		return CommandResult.success();
	}
}


