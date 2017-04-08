package Listeners;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
import org.spongepowered.api.effect.potion.PotionEffect;
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
	                		
		                    int score = player.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get().getScore(Text.of("Deaths")).get().getScore();
		
		                    player.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get().getScore(Text.of("Deaths")).get().setScore(score + 1);
		                    
		                    //restore health level
	            			HealthData maxHealth = player.getHealthData().set(Keys.HEALTH, player.get(Keys.MAX_HEALTH).get());
	            			player.offer(maxHealth);
	            			
	            			//restore food level
	            			FoodData maxFood = player.getFoodData().set(Keys.FOOD_LEVEL, 20);
	            			player.offer(maxFood);
	            			
	            			//restore clean slate potion effects
	            			Optional<List<PotionEffect>> potions = player.get(Keys.POTION_EFFECTS);
	            			
	            			if(potions.isPresent()){
	            				
	            				List<PotionEffect> potionEffects = potions.get();
	            				potionEffects.clear(); 
	            				player.offer(Keys.POTION_EFFECTS, potionEffects);
	            			}
	            			
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