package Listeners;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.GameModeData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.scoreboard.displayslot.DisplaySlots;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.title.Title;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import ConfigUtils.TDMConfigUtils;
import Main.Main;
import Utils.CreditsTimer;
import Utils.DZArenaUtils;
import Utils.GUI;
import Utils.ScoreBoardUtils;

public class TDMListeners {
	
	@Listener
	public void onContestantClickHeadgear(ClickInventoryEvent event, @Root Player player){
		
		Location<World> pl = player.getLocation();
		
		if(ArenaConfigUtils.getUserArenaNameFromLocation(pl)!=null){
			
			String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(pl);
			
        	if(ArenaConfigUtils.getArenaStatus(arenaName).equals("TDM")){
        		
        		if(ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())){
        			
        			event.getTransactions().forEach(t ->{
        				
        				if(t.getSlot().contains(ItemTypes.REDSTONE_BLOCK)||
        						t.getSlot().contains(ItemTypes.SLIME)){
        					
        					event.setCancelled(true);
        					
        					return;
        				}
        			});
        		}
        	}
		}
	}
    
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

			                        	event.setCancelled(true);
			                        	
			                        	return;
			                        }
			                		
				                	Player player = ((Player) damageTarget);
				                	
				                	//if the damage target player is not a contestant, cancel it
				                	if(!ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())){
				                		
				                		event.setCancelled(true);
				                		
				                		return;
				                	}

				                	ScoreBoardUtils.addDeathScore(player);
				                	ScoreBoardUtils.addKillScore(killer);
				                	
				                	DZArenaUtils.restoreContestant(player);
			            			
			            			//update score bar
			            			Text killerTeam = killer.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get().getDisplayName();
			            					
			            			List<Player> contestants = TDMConfigUtils.convertObjectContestantsToPlayers(arenaName);
		            				
		            				Map<String,ServerBossBar> b1 = GUI.bTeamBars.get(arenaName);
		            				Map<String,ServerBossBar> b0 = GUI.aTeamBars.get(arenaName);
		            				
		            				int MaxPoints = TDMConfigUtils.getPointWin(arenaName);
	
			            			for(Player contestant: contestants){
			            							            				
			            				ServerBossBar aBar = b0.get(contestant.getName());
			            				ServerBossBar bBar = b1.get(contestant.getName());
			            				
			            				if(aBar.getName().equals(killerTeam)){
			            					
				            				float TAMatchKills = aBar.getPercent()*MaxPoints;
				            				float TANPercent = (1+TAMatchKills)/MaxPoints;
				            				
				            				aBar.setPercent(TANPercent);
			            				}
			            				
			            				if(bBar.getName().equals(killerTeam)){
				            				
				            				float TBMatchKills = bBar.getPercent()*MaxPoints;
				            				float TBNPercent = (1+TBMatchKills)/MaxPoints;
				            				
				            				bBar.setPercent(TBNPercent);
			            				}
			            				
			            				if(aBar.getPercent()==1||bBar.getPercent()==1){
			            					
			            					EconomyService economyService = Sponge.getServiceManager().provide(EconomyService.class).get();
			            					UniqueAccount acc = economyService.getOrCreateAccount(player.getUniqueId()).get();
			            					BigDecimal reward = BigDecimal.valueOf(700);			            					
			            					acc.deposit(economyService.getDefaultCurrency(), reward, Cause.source(Main.getPluginContainer()).build());

			            					Title MT = Title.builder().subtitle(Text.of(killerTeam, TextColors.WHITE," WINS!")).stay(60).build();
			            					GUI.removeTDMGUI(contestant, aBar, bBar, MT);
			            					
			            					GameModeData gm = contestant.getGameModeData().set(Keys.GAME_MODE, GameModes.SPECTATOR);
			            					contestant.offer(gm);
			            					
			            		    		contestant.setLocationAndRotation(ArenaConfigUtils.getCreditsLocation(arenaName), 
			            		    				ArenaConfigUtils.getCreditsLocationTransform(arenaName));
			            				}
			            			}
			            			          			
			            			if(b0.get(contestants.get(0).getName()).getPercent()==1||b1.get(contestants.get(0).getName()).getPercent()==1){
			            				
			            				ArenaConfigUtils.setCreditsMode(arenaName, true);

				            	        Sponge.getScheduler().createTaskBuilder().interval(1, TimeUnit.SECONDS).delay(1, TimeUnit.SECONDS).execute(new 
				            	        		CreditsTimer(arenaName, contestants, 5)).submit(Main.Dreadzone);
				            	        
			            				/**Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ",
		            							TextColors.WHITE,"Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," has re-opened! To join, enter: ",
		            							TextColors.DARK_RED,"/dz join ",arenaName," ARENA_MODE",TextColors.WHITE,"."));*/
			            				
			            				Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ",
		            							TextColors.WHITE,"Contestants of Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," have been rewarded with $700"
		            									+ " and has re-opened! To join, enter: ",TextColors.DARK_RED,"/dz join ",arenaName," ARENA_MODE",TextColors.WHITE,"."));
				            	        
				            	        event.setCancelled(true);
				            	        
				            	        return;

			            			}
			            				
			            			TDMConfigUtils.respawnContestant(arenaName, player);

		                			event.setCancelled(true);
		                			
		                			return;
		                		}
		                	}
		                }
		        	}
		        }
	        }
	        
        	Entity damageTarget = event.getTargetEntity();
        	
	        Location<World> damageLocation = damageTarget.getLocation();
	        
	        //filters all damage events to only within arenas
	        if(ArenaConfigUtils.getUserArenaNameFromLocation(damageLocation)!=null){
	        	
	        	String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(damageLocation);
	        	
	        	//filter arenas only with TDM modes
	        	if(ArenaConfigUtils.getArenaStatus(arenaName).equals("TDM")){
	        		
	        		//filter out non player target entities
	                if (damageTarget instanceof Player) {
	                	
	                	Player player = ((Player) damageTarget);
	                	
	                	ScoreBoardUtils.addDeathScore(player);
	                	
	                	DZArenaUtils.restoreContestant(player);
	                	
	                	TDMConfigUtils.respawnContestant(arenaName, player);
	                	
	                	event.setCancelled(true);
	                	
	                	return;
	                }
	        	}
	        }
    	}
    }
}