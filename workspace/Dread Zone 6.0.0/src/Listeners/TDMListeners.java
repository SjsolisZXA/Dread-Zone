package Listeners;

import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import ConfigUtils.TDMConfigUtils;

public class TDMListeners {
    
    //update's a contestant's scoreboard when the contestant kills or is killed
    @Listener
    public void contestantReceiveDamage(DamageEntityEvent event){
    	
    	if(event.willCauseDeath()){
    	
	    	Optional<EntityDamageSource> optDamageSource = event.getCause().first(EntityDamageSource.class);
	    	
	        if (optDamageSource.isPresent()) {
	        
	        	Entity damageTarget = event.getTargetEntity();
	        	
		        Location<World> damageLocation = damageTarget.getLocation();
		        
		        //filters all damage events to only within arenas
		        if(ArenaConfigUtils.getUserArenaNameFromLocation(damageLocation)!=null){
		        	
		        	String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(damageLocation);
		        	
		        	//filter arenas only with TDM modes
		        	if(ArenaConfigUtils.getArenaStatus(arenaName).equals("TDM")){
		        		
		        		EntityDamageSource damageSource = optDamageSource.get();
		        		
		        		//filter out non player target entities
		                if (damageTarget instanceof Player) {
		                	
		                	Entity k = damageSource.getSource();
		                	
		                	//filter out non player damage sources
		                	if(k instanceof Player){
		                	
			                    if (optDamageSource.isPresent()) {
			                        
			                        Player killer = ((Player) k);
			                        
			                        //if the damage source player is not a contestant, cancel it
			                        if(!ContestantConfigUtils.isUserAnArenaContestant(arenaName, killer.getName())){
			                        	
			                        	//Sponge.getServer().getBroadcastChannel().send(Text.of("Attacker is not a contestant"));
			                        	
			                        	event.setCancelled(true);
			                        	
			                        	return;
			                        }
			                		
				                	Player player = ((Player) damageTarget);
				                	
				                	//if the damage target player is not a contestant, cancel it
				                	if(!ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())){
				                		
				                		//Sponge.getServer().getBroadcastChannel().send(Text.of("Target is not a contestant"));
				                		
				                		event.setCancelled(true);
				                		
				                		return;
				                	}
				                	
				                    int deathScore = player.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get().getScore(Text.of("Deaths")).get().getScore();
				
				                    player.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get().getScore(Text.of("Deaths")).get().setScore(deathScore + 1);
				                    
				                    int killScore = killer.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get().getScore(Text.of("Kills")).get().getScore();
				    				
				                    killer.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get().getScore(Text.of("Kills")).get().setScore(killScore + 1);
				                                
			            			HealthData maxHealth = player.getHealthData().set(Keys.HEALTH, player.get(Keys.MAX_HEALTH).get());
			            			
			            			player.offer(maxHealth);
			            			
			            			
			            			
			            			/*Objective killerTeam = killer.getScoreboard().getObjective(DisplaySlots.BELOW_NAME).get();
			            			
			            			Objective victomTeam = player.getScoreboard().getObjective(DisplaySlots.BELOW_NAME).get();
			            			
			            			
			            			
			            			
			                        ServerBossBar teamA = ServerBossBar.builder().name(Text.of(killerTeam)).percent(0.0f).color(BossBarColors.GREEN).overlay(BossBarOverlays.PROGRESS).build();
			                        
			                        ServerBossBar teamB = ServerBossBar.builder().name(Text.of(victomTeam)).percent(0.0f).color(BossBarColors.RED).overlay(BossBarOverlays.PROGRESS).build();
			                        
			                        teamA.addPlayer(player);
			                        
			                        teamB.addPlayer(player);
			            			
			            			
			            			Set<Object> contestants = ContestantConfigUtils.getArenaContestants(arenaName);
			            			
			            			for(Object contestant:contestants){
			            				
			            				for(Player p: Sponge.getServer().getOnlinePlayers()){
			            					
			            					if(contestant.toString().equals(p.getName())){
			            						
			            						//ServerBossBar teamBar = teamBar.get(p.getUniqueId());
			            					}
			            				}
			            			}*/
			            			
			            			TDMConfigUtils.respawnContestant(arenaName, player);
		                			
		                			event.setCancelled(true);
		                		}
		                	}
		                }
		        	}
		        }
	        }
    	}
    }
}