package Listeners;

import java.util.Optional;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.scoreboard.Score;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import ConfigUtils.PABConfigUtils;
import Utils.DZArenaUtils;
import Utils.ScoreBoardUtils;

public class PABListeners {
	
	//update's a player's scoreboard when contestant assassinates a monster
    @Listener
    public void onEntityDeath(DestructEntityEvent.Death event) {
    	
        Optional<EntityDamageSource> optDamageSource = event.getCause().first(EntityDamageSource.class);
        
        if (optDamageSource.isPresent()) {
        	
            EntityDamageSource damageSource = optDamageSource.get();
        	
        	Entity opDamageTarget = event.getTargetEntity();
            
            Entity killer = damageSource.getSource();
            
            Location<World> deathLocation = opDamageTarget.getLocation();
            
            if(ArenaConfigUtils.getUserArenaNameFromLocation(deathLocation)!=null){
            	
            	String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(deathLocation);
            	
            	if(ArenaConfigUtils.getArenaStatus(arenaName).equals("PAB")){
            		
                    if (killer instanceof Player) {
                    	
                    	if(opDamageTarget instanceof Monster){
                    		
                        	Player player = ((Player) killer);
                    		
                    		EntityType monster = opDamageTarget.getType();
                    		
                    		Objective playerScoreboard = player.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get();
                    		
                    		Score totalKillsScore = playerScoreboard.getScore(Text.of("Total Kills")).get();

                            int totalScore =  totalKillsScore.getScore();

                            totalKillsScore.setScore(totalScore + 1);
                            
                            if(playerScoreboard.getScore(Text.of(monster)).isPresent()){
                            	
                            	Score monsterKillScore = playerScoreboard.getScore(Text.of(monster)).get();

	                            int monsterScore =  monsterKillScore.getScore();
	
	                            monsterKillScore.setScore(monsterScore + 1);
	                            
	                            return;
                            }
                            
                            playerScoreboard.getOrCreateScore(Text.of(monster)).setScore(1);

                            return;
                    	}
                    }
            	}
            }
        }
    }
    
    //update's a contestant's scoreboard when killed
    @Listener
    public void contestantReceiveDamage(DamageEntityEvent event){
        	
    	Entity damageTarget = event.getTargetEntity();
        
        Location<World> damageLocation = damageTarget.getLocation();
        
        if(event.willCauseDeath()){

	        if(ArenaConfigUtils.getUserArenaNameFromLocation(damageLocation)!=null){
	        	
	        	String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(damageLocation);
	        	
	        	if(ArenaConfigUtils.getArenaStatus(arenaName).equals("PAB")){
	        		
	                if (damageTarget instanceof Player) {
	                		
	                	Player player = ((Player) damageTarget);
	                	
	                	if(ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())){
	                		
	                		ScoreBoardUtils.addDeathScore(player);
		                    
		                    DZArenaUtils.restoreContestant(player);
	            			
	            			player.setLocationAndRotation(PABConfigUtils.getPABALocation(arenaName), PABConfigUtils.getPABARotation(arenaName));
	            			
	            			event.setCancelled(true);
	            			
	            			return;
	            		}
	                	
	                }
	        	}
	        }
        }
    }
}