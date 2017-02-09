package Executors;

import java.util.Optional;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ClassConfigUtils;

public class TestExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) {
		
		@SuppressWarnings("unused")
		Player player =(Player)src;	
		
		return CommandResult.success();
	}
	
	public void getClosestEntity(final Player player) {
		
	    Entity closest = null;
	    
	    double closestDistance = 0;

	    for (Entity entity : player.getLocation().getExtent().getEntities()) {
	    	
	        if (entity == player) {
	            continue;
	        }

	        double distance = entity.getLocation().getPosition().distance(player.getLocation().getPosition());
	        
	        if (closest == null || distance < closestDistance) {
	        	
	            closest = entity;
	            
	            closestDistance = distance;
	        }
	    }
	    
	    Optional<Text> NPC = closest.get(Keys.DISPLAY_NAME);

	    if(NPC.isPresent()){
	    	
	    	Text classN = NPC.get();
	    	
	    	String className = classN.toPlain();
	    	
	    	if(ClassConfigUtils.doesClassExist(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation()), className)){
	    		
	    		closest.offer(Keys.SKIN_UNIQUE_ID, player.getUniqueId());
	    	}
	    }
	}
	
	
	public void getClosestPlayer(final Player player) {
		
	    Entity closest = null;
	    
	    double closestDistance = 0;

	    for (Entity entity : player.getLocation().getExtent().getEntities()) {

	        double distance = entity.getLocation().getPosition().distance(player.getLocation().getPosition());
	        
	        if (closest == null || distance < closestDistance) {
	        	
	            closest = entity;
	            
	            closestDistance = distance;
	        }
	    }
	    
	    Optional<Text> NPC = closest.get(Keys.DISPLAY_NAME);

	    if(NPC.isPresent()){
	    	
	    	Text classN = NPC.get();
	    	
	    	String className = classN.toPlain();
	    	
	    	if(ClassConfigUtils.doesClassExist(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation()), className)){
	    		
	    		closest.offer(Keys.SKIN_UNIQUE_ID, player.getUniqueId());
	    	}
	    }
	}
}