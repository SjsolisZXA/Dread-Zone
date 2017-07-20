package Listeners;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.entity.projectile.LaunchProjectileEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.item.inventory.ChangeInventoryEvent;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.item.inventory.UseItemStackEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ClassConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import ConfigUtils.LobbyConfigUtils;
import ConfigUtils.PABConfigUtils;
import ConfigUtils.RightClickModeConfigUtils;
import ConfigUtils.TDMConfigUtils;
import Utils.DZArenaUtils;
import Utils.ScoreBoardUtils;
import Utils.TDMTimer;

public class GeneralArenaListeners {
	/**
	//keeps Dread Zone contestants inside their arenas and corresponding lobbies
	@Listener
	public void onPlayerMove(MoveEntityEvent event, @Root Player player){
		
		if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation()) != null){
			
			String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
			
		    if(ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())){
		    	
		    	if(!ArenaConfigUtils.isUserinArena(player.getLocation().add(1,0,0),arenaName)
		    			||!LobbyConfigUtils.isUserinLobby(player.getLocation().add(1,0,0), arenaName)){
		    		
		    		player.setLocation(player.getLocation().add(1,0,0));
		    		return;
		    	}
		    	
		    	if(!ArenaConfigUtils.isUserinArena(player.getLocation().add(-1,0,0),arenaName)
		    			||!LobbyConfigUtils.isUserinLobby(player.getLocation().add(-1,0,0), arenaName)){
		    		
		    		player.setLocation(player.getLocation().add(-1,0,0));
		    		return;
		    	}
		    	
		    	if(!ArenaConfigUtils.isUserinArena(player.getLocation().add(0,0,1),arenaName)
		    			||!LobbyConfigUtils.isUserinLobby(player.getLocation().add(0,0,1), arenaName)){
		    		
		    		player.setLocation(player.getLocation().add(0,0,1));
		    		return;
		    	}
		    	
		    	if(!ArenaConfigUtils.isUserinArena(player.getLocation().add(0,0,-1),arenaName)
		    			||!LobbyConfigUtils.isUserinLobby(player.getLocation().add(0,0,-1), arenaName)){
		    		
		    		player.setLocation(player.getLocation().add(0,0,-1));
		    		return;
		    	}
		    	
		    	if(!ArenaConfigUtils.isUserinArena(player.getLocation().add(0,-1,0),arenaName)
		    			||!LobbyConfigUtils.isUserinLobby(player.getLocation().add(0,-1,0), arenaName)){
		    		
		    		player.setLocation(player.getLocation().add(0,-1,0));
		    		return;
		    	}
		    	
		    	if(!ArenaConfigUtils.isUserinArena(player.getLocation().add(0,1,0),arenaName)
		    			||!LobbyConfigUtils.isUserinLobby(player.getLocation().add(0,1,0), arenaName)){
		    		
		    		player.setLocation(player.getLocation().add(0,1,0));
		    		return;
		    	}
		    }
		}
		
	    Set<Object> arenas = ArenaConfigUtils.getArenas();
	    
	    for(Object arena: arenas){
	    	
	    	if(ContestantConfigUtils.isUserAnArenaContestant(arena.toString(), player.getName())&&
	    			ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation()).equals(null)){
	    		
		    	ContestantConfigUtils.removeContestant(arena.toString(), player);
		    	
		    	player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
						TextColors.WHITE,"Thanks for playing!"));
		    	
		    	return;
	    	}
	    }
	}*/
	
	//prevents contestants from executing any commands
    @Listener
    public void playerCommand(SendCommandEvent commandEvent, @Root Player player) {
    	
		if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation()) != null){
			
			String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
    	
	    	String command = commandEvent.getCommand();
	    	
	    	String arguments = commandEvent.getArguments();
	    
	    	if(ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())&&
	    			!command.equals("dz")&& !(arguments.equals("ready")|| arguments.equals("leave"))){
	    			
	    		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
						TextColors.WHITE,"Commands are not allowed in Dread Zone other than ", TextColors.DARK_RED, "/dz leave", 
						TextColors.WHITE, " and ", TextColors.DARK_RED, "/dz ready"));
	    		
	    		commandEvent.setCancelled(true);
	    	}
		}
    }
    
    //prevents contestants from moving their inventories in lobbies
    @Listener
    public void contestantClickInventoryItem(ClickInventoryEvent event, @Root Player player){
    	
		if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation()) != null){
			
			String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
    	
	    	if(ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())&&
	    			LobbyConfigUtils.isUserinLobby(player.getLocation(), arenaName)){
	    		
	    		event.setCancelled(true);
	    	}
		}
    }
    
    //prevents contestants from picking up items in lobbies and prevents non-contestants from picking up contestant's items
    @Listener
    public void pickUpItem(ChangeInventoryEvent.Pickup event, @Root Player player){
    	
		if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation()) != null){
			
			String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
    	
	    	if(ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())&&
	    			LobbyConfigUtils.isUserinLobby(player.getLocation(), arenaName)){//test after arena redo
	    		
	    		event.setCancelled(true);
	    		
	    		return;
	    	}
	    	
	    	if(!ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())&&
	    			ArenaConfigUtils.isUserinArena(player.getLocation(), arenaName)){//works
	    		
	    		event.setCancelled(true);
	    		
	    		return;
	    	}
	    }
    }
    
    //prevents the Dread Zone NPCs and contestants from receiving damage in lobbies
    @Listener
    public void contestantAndNPCReceiveDamage(DamageEntityEvent event, @First EntityDamageSource source){//WORKS
    	
    	Location<World> damageTartgetLocation = event.getTargetEntity().getLocation();
    	
    	if(ArenaConfigUtils.getUserArenaNameFromLocation(damageTartgetLocation)!=null){
    	
	    	String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(damageTartgetLocation);
	    	
	    	Optional<Text> userN = event.getTargetEntity().get(Keys.DISPLAY_NAME);
	    	
	    	if(userN.isPresent()){
	    		
	    		String userName = userN.get().toPlain();
	    		
		    	if(LobbyConfigUtils.isUserinLobby(damageTartgetLocation, arenaName)&&
		    			(ContestantConfigUtils.isUserAnArenaContestant(arenaName, userName)||
		    			ClassConfigUtils.doesClassExist(arenaName, userName))){
		    		
		    		event.setCancelled(true);
		    	}
	    	}
    	}
    }

    //prevents contestants from moving during the TDM countdown
    @Listener
    public void onPlayerMoveInArena(MoveEntityEvent event, @Root Player player) {
    	
    	if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation())!=null){
    		
    		String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
    	
	        if (ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())) {
	        	
	            event.setCancelled(TDMTimer.isFreezingPlayers());
	        }
    	}
    }    
    
    //prevents anyone from destroying blocks inside the Dread Zone arena and lobby
    @Listener
    public void onBlockDestroy(ChangeBlockEvent.Break event, @Root Entity entity) {
    	
    	if(entity instanceof Player){
    		
    		Player player = (Player)entity;
    	
	        event.getTransactions().stream().forEach((trans) -> {
	            	
	            Location<World> eventLocation = trans.getOriginal().getLocation().get();
	                
	        	if(ArenaConfigUtils.getUserArenaNameFromLocation(eventLocation) != null){
	        		
	        		String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(eventLocation);
	
	    	        if (LobbyConfigUtils.isUserinLobby(eventLocation, arenaName)|| 
	    	        		ArenaConfigUtils.isUserinArena(eventLocation, arenaName)) {
	    	        	    	
	    	        	if(!RightClickModeConfigUtils.isUserInConfig(player.getName())){

	    	        		event.setCancelled(true);
	    	        		return;
	    	        	}
	    	        	if(RightClickModeConfigUtils.isUserInConfig(player.getName())&&
	    	        		!RightClickModeConfigUtils.getUserMode(player.getName()).equals("EA - "+arenaName)){
	        	
	    	        		event.setCancelled(true);
	    	        		return;
	        			}
	    	        }
	        	}
	        });
    	}
    	
    	if((entity instanceof Monster)){
    		
    		event.setCancelled(true);
    	}
    }
    
    //prevents anyone from placing blocks inside the Dread Zone arena and lobby
    @Listener
    public void onBlockPlace(ChangeBlockEvent.Place event, @Root Entity entity) {
    	
    	if(entity instanceof Player){
    		
    		Player player = (Player)entity;
    	
	        event.getTransactions().stream().forEach((trans) -> {
	            	
	            Location<World> eventLocation = trans.getOriginal().getLocation().get();
	                
	        	if(ArenaConfigUtils.getUserArenaNameFromLocation(eventLocation) != null){
	        		
	        		String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(eventLocation);
	
	    	        if (LobbyConfigUtils.isUserinLobby(eventLocation, arenaName)|| 
	    	        		ArenaConfigUtils.isUserinArena(eventLocation, arenaName)) {
	    	        	    	
	    	        	if(!RightClickModeConfigUtils.isUserInConfig(player.getName())){

	    	        		event.setCancelled(true);
	    	        		return;
	    	        	}
	    	        	if(RightClickModeConfigUtils.isUserInConfig(player.getName())&&
	    	        		!RightClickModeConfigUtils.getUserMode(player.getName()).equals("EA - "+arenaName)){
	        	
	    	        		event.setCancelled(true);
	    	        		return;
	        			}
	    	        }
	        	}
	        });
    	}
    	
    	if((entity instanceof Monster)){
    		
    		event.setCancelled(true);
    	}
    }
    
    //prevents contestants from dropping items in lobbies and prevents non-contestants from dropping items in arenas
    @Listener
    public void contestantDropItem(DropItemEvent.Dispense event, @First EntitySpawnCause spawncause){
    	
    	Location<World> spawnCauseLocation = spawncause.getEntity().getLocation();
    	
		if(ArenaConfigUtils.getUserArenaNameFromLocation(spawnCauseLocation) != null){
			
			String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(spawnCauseLocation);
    	
	    	Optional<Text> userN = spawncause.getEntity().get(Keys.DISPLAY_NAME);
	    	
	    	if(userN.isPresent()){
	    		
	    		String userName = userN.get().toPlain();
	    		
	    		if(ContestantConfigUtils.isUserAnArenaContestant(arenaName, userName)&&
	    				LobbyConfigUtils.isUserinLobby(spawnCauseLocation, arenaName)){
	    			
	    			event.setCancelled(true);
	    			
	    			return;
	    		}
	    		
	    		Player player = (Player)spawncause.getEntity();
	    		
		       	if(!ContestantConfigUtils.isUserAnArenaContestant(arenaName, userName)&&
		       			ArenaConfigUtils.isUserinArena(spawnCauseLocation, arenaName)){
		              
	                player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
	                      TextColors.WHITE, "Non-contestants can't drop items in Dread Zone arenas!"));
		    		
		    		event.setCancelled(true);
		    		
		    		return;
		       	}
	    	}
		}
    }  
    
    //removes a contestant from an arena on disconnect
    @Listener
    public void onContestantLogOut(ClientConnectionEvent.Disconnect event){
    	
    	Player player = (Player)event.getTargetEntity();
    	
    	if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation())!=null){
    		
    		String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
    		
    		if(ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())){
    			
    			ContestantConfigUtils.removeContestant(arenaName, player);
    		}
    	}
    }
    
    //meant to prevent contestant's tab list from getting bigger when players join the server
    @Listener
    public void onuserLogIn(ClientConnectionEvent.Join event){
    	
    	Set<Object> arenas = ArenaConfigUtils.getArenas();
    	
    	Player JP = (Player)event.getTargetEntity();
    	
    	for(Object arena: arenas){
    		
    		List<Player> contestants = TDMConfigUtils.convertObjectContestantsToPlayers(arena.toString());
    		
    		for(Player player: contestants){
    			
    			player.getTabList().removeEntry(JP.getUniqueId());
    		}
    	}
    }
    
    //prevents any contestant from moving while credits are being shown
    @Listener
    public void onPlayerMoveDuringCredits(MoveEntityEvent event, @Root Player player) {
    	
    	if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation())!=null){
    		
    		String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
    		
    		if(ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())){
    			
    			if(ArenaConfigUtils.getCreditMode(arenaName, player.getName())!=null){
    				
        			if(ArenaConfigUtils.getCreditMode(arenaName, player.getName()).equals(true)){
        				
        				event.setCancelled(true);
        			}
    			}
    		}
    	}
    }
    
	//update's a player's scoreboard when they fall out of the map
    @Listener
    public void onEntityDeath(DamageEntityEvent event) {
    	
        if(event.willCauseDeath()){//only when an entity dies
    	
	        Optional<EntityDamageSource> optDamageSource = event.getCause().first(EntityDamageSource.class);
	        
	        if (!optDamageSource.isPresent()) {//checks for supernatural deaths
	    	
		    	Entity entity = event.getTargetEntity();
		    	
		    	Optional<Text> userN = entity.get(Keys.DISPLAY_NAME);
		    	
		    	if(userN.isPresent()){//checks for only players
		    		
		    		Player player = ((Player) entity);
		            
		            for(Object arena:ArenaConfigUtils.getArenas()){
			            	
		            	if(ContestantConfigUtils.isUserAnArenaContestant(arena.toString(), player.getName())){
		            	
			            	if(ArenaConfigUtils.getMatchStatus(arena.toString()).equals(true)){//filters the death to contestants
				
		                		ScoreBoardUtils.addDeathScore(player);
		                		
		                		Sponge.getServer().getBroadcastChannel().send(Text.of("A contestatnd died of supernatural causes."));
		                		
			                    DZArenaUtils.restoreContestant(player);
			                    
			                    if(ArenaConfigUtils.getArenaStatus(arena.toString()).equals("PAB")){
			                    	
			                    	player.setLocationAndRotation(PABConfigUtils.getPABALocation(arena.toString()), PABConfigUtils.getPABARotation(arena.toString()));
			                    	
			                    	Sponge.getServer().getBroadcastChannel().send(Text.of("Player was in PAB mode."));
			                    }
		            			
			                    if(ArenaConfigUtils.getArenaStatus(arena.toString()).equals("TDM")){
			                    	
			                    	TDMConfigUtils.respawnContestant(arena.toString(), player);
			                    }
		            			
		            			event.setCancelled(true);
		            			
		            			break;
			            	}
		            	}
		            }
		    	}
	        }
        }
    }
    
    /**@Listener
    public static void onPlayerChangeDoor(InteractBlockEvent.Secondary.MainHand event, @First Player player){//prevents any door from being opened in an arena or loby unles in EA mode

        BlockSnapshot targetBlock = event.getTargetBlock();
        
    	Sponge.getServer().getBroadcastChannel().send(Text.of("Something was right clicked"));
        	
        Location<World> eventLocation = targetBlock.getLocation().get();
            
    	if(ArenaConfigUtils.getUserArenaNameFromLocation(eventLocation) != null){
    		
    		Sponge.getServer().getBroadcastChannel().send(Text.of("event happened in an arena"));
    		
    		if(targetBlock.getState().getType()==BlockTypes.DARK_OAK_DOOR){
    			
    			Sponge.getServer().getBroadcastChannel().send(Text.of("a dark oak door was right clicked"));
    		
	    		String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(eventLocation);
	    		
	        	if(RightClickModeConfigUtils.isUserInConfig(player.getName())&&
	            		RightClickModeConfigUtils.getUserMode(player.getName()).equals("EA - "+arenaName)){

	            	return;
	    		}
	        	Sponge.getServer().getBroadcastChannel().send(Text.of("User is not in config and is not in EA mode"));
	        	event.setCancelled(true);
    		}
    	}
    }*/
    
    
    //Abstract listeners
    
    //prevents contestants from consuming potions in lobbies
    @Listener
    public void onPlayerConsumption(UseItemStackEvent.Start event, @Root Player player) {//needs cause event location and if the cause if by a contestnat//FIX
    		
    	event.setCancelled(true);

    }
	
    //prevents players from throwing potions in Lobbies
    @Listener
    public void onPotionThrow(LaunchProjectileEvent event, @Root Player player) {//needs cause event location and if the cause if by a contestnat//FIX
    
        event.setCancelled(true);
    }
}
