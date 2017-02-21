package Listeners;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.text.Text;

public class TDMListeners {
	
	//add kill count and add death count
    @Listener
    public void onEntityDeath(DestructEntityEvent.Death event) {
    	
        Optional<EntityDamageSource> optDamageSource = event.getCause().first(EntityDamageSource.class);
        
        if (optDamageSource.isPresent()) {
        	
            EntityDamageSource damageSource = optDamageSource.get();
            
            Entity killer = damageSource.getSource();
            
            if (killer instanceof Player) {
            	
            	Player player = ((Player) killer);
            	
            	String killerUserName = player.getName();
            	
            	Text killerTeamName = player.getScoreboard().getObjective(DisplaySlots.BELOW_NAME).get().getDisplayName();
            	
            	Entity opDamageTarget = event.getTargetEntity();
            	
            	if(opDamageTarget instanceof Player){
            		
    				Optional<Text> userN = event.getTargetEntity().get(Keys.DISPLAY_NAME);
    				
    				if(userN.isPresent()){
    					
    					Text userNa = userN.get();
    					
    					String targetUserName = userNa.toPlain();
    					
    	            	Text targetTeamName = player.getScoreboard().getObjective(DisplaySlots.BELOW_NAME).get().getDisplayName();
    	            	
    	            	Sponge.getServer().getBroadcastChannel().send(Text.of("Player ",killerUserName," from team ",killerTeamName,
    	            			" assassinated ",targetUserName," from team ",targetTeamName," in cold blood!"));
    				}
            	}
            }
        }
    }
}