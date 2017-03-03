package Listeners;

import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ChangeInventoryEvent;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ClassConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import ConfigUtils.LobbyConfigUtils;
import Utils.TeamDeathmatchTimer;

public class GeneralArenaListeners {
	
	static String AN;
	
	//keeps Dread Zone contestants inside their arenas and corresponding lobbies
	@Listener
	public void onPlayerMove(MoveEntityEvent event, @First Player player){
		
	    if(ContestantConfigUtils.isUserAnArenaContestant(AN, player.getName())
	    		&&!ArenaConfigUtils.isUserinArena(player.getLocation(), AN)
	    				&&!LobbyConfigUtils.isUserinLobby(player.getLocation(),  AN)){
	    	
	    	if(ArenaConfigUtils.isUserinArena(player.getLocation().add(1,0,0),AN)
	    			||LobbyConfigUtils.isUserinLobby(player.getLocation().add(1,0,0), AN)){
	    		
	    		player.setLocation(player.getLocation().add(1,0,0));
	    		return;
	    	}
	    	
	    	if(ArenaConfigUtils.isUserinArena(player.getLocation().add(-1,0,0),AN)
	    			||LobbyConfigUtils.isUserinLobby(player.getLocation().add(-1,0,0), AN)){
	    		
	    		player.setLocation(player.getLocation().add(-1,0,0));
	    		return;
	    	}
	    	
	    	if(ArenaConfigUtils.isUserinArena(player.getLocation().add(0,0,1),AN)
	    			||LobbyConfigUtils.isUserinLobby(player.getLocation().add(0,0,1), AN)){
	    		
	    		player.setLocation(player.getLocation().add(0,0,1));
	    		return;
	    	}
	    	
	    	if(ArenaConfigUtils.isUserinArena(player.getLocation().add(0,0,-1),AN)
	    			||LobbyConfigUtils.isUserinLobby(player.getLocation().add(0,0,-1), AN)){
	    		
	    		player.setLocation(player.getLocation().add(0,0,-1));
	    		return;
	    	}
	    	
	    	if(ArenaConfigUtils.isUserinArena(player.getLocation().add(0,-1,0),AN)
	    			||LobbyConfigUtils.isUserinLobby(player.getLocation().add(0,-1,0), AN)){
	    		
	    		player.setLocation(player.getLocation().add(0,-1,0));
	    		return;
	    	}
	    	
	    	if(ArenaConfigUtils.isUserinArena(player.getLocation().add(0,1,0),AN)
	    			||LobbyConfigUtils.isUserinLobby(player.getLocation().add(0,1,0), AN)){
	    		
	    		player.setLocation(player.getLocation().add(0,1,0));
	    		return;
	    	}
	    	
	    	ContestantConfigUtils.removeContestant(AN, player.getName());
	    	
	    	player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Thanks for playing!"));
		}
	    return;
	}

	public static void AN(String arenaName) {

		AN = arenaName;		
	}
	
	//prevents contestants from executing any commands
    @Listener
    public void playerCommand(SendCommandEvent commandEvent, @First Player player) {//WORKS
    	
    	String command = commandEvent.getCommand();
    	
    	String arguments = commandEvent.getArguments();
    
    	if(ContestantConfigUtils.isUserAnArenaContestant(AN, player.getName())&&
    			!command.equals("dz")&& !(arguments.equals("ready")|| arguments.equals("reload") || arguments.equals("leave")) && !command.equals("test")){
    			
    		
    		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Commands are not allowed in Dread Zone other than ", TextColors.DARK_RED, "/dz leave", 
					TextColors.WHITE, " and ", TextColors.DARK_RED, "/dz ready"));
    		
    		commandEvent.setCancelled(true);
    	}
    }
    
    //prevents contestants from moving their inventories in lobbies
    @Listener
    public void contestantClickInventoryItem(ClickInventoryEvent event, @First Player player){//WORKS
    	
    	if(ContestantConfigUtils.isUserAnArenaContestant(AN, player.getName())&&
    			LobbyConfigUtils.isUserinLobby(player.getLocation(), AN)){
    		
    		event.setCancelled(true);
    	}
    }
    
    //prevents contestants from picking up items in lobbies and prevents non-contestants from picking up contestant's items
    @Listener
    public void pickUpItem(ChangeInventoryEvent.Pickup event, @First Player player){//WORKS
    	
    	if(ContestantConfigUtils.isUserAnArenaContestant(AN, player.getName())&&
    			LobbyConfigUtils.isUserinLobby(player.getLocation(), AN)){
    		
    		event.setCancelled(true);
    		
    		return;
    	}
    	
    	if(!ContestantConfigUtils.isUserAnArenaContestant(AN, player.getName())&&
    			ArenaConfigUtils.isUserinArena(player.getLocation(), AN)){
    		
    		event.setCancelled(true);
    		
    		return;
    	}
    }
    
    //prevents the Dread Zone NPCs and contestants from receiving damage in lobbies
    @Listener
    public void contestantAndNPCReceiveDamage(DamageEntityEvent event, @First EntityDamageSource source){//WORKS
    	
    	Location<World> sourceLocation = source.getSource().getLocation();
    	
    	if(ArenaConfigUtils.getUserArenaNameFromLocation(sourceLocation)!=null){
    	
	    	String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(sourceLocation);
	    	
	    	Optional<Text> userN = event.getTargetEntity().get(Keys.DISPLAY_NAME);
	    	
	    	if(userN.isPresent()){
	    		
	    		String userName = userN.get().toPlain();
	    		
		    	if(LobbyConfigUtils.isUserinLobby(sourceLocation, arenaName)&&
		    			(ContestantConfigUtils.isUserAnArenaContestant(arenaName, userName)||
		    			ClassConfigUtils.doesClassExist(arenaName, userName))){
		    		
		    		event.setCancelled(true);
		    	}
	    	}
    	}
    }

    //prevents contestants from moving during the TDM countdown
    @Listener
    public void onPlayerMoveInArena(MoveEntityEvent event, @First Player player) {//WORKS
    	
    	if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation())!=null){
    		
    		String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
    	
	        if (ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())) {
	        	
	            event.setCancelled(TeamDeathmatchTimer.isFreezingPlayers());
	        }
    	}
    }    
    
    //prevents anyone from destroying blocks inside the Dread Zone arena and lobby
    @Listener
    public void onDestroyBlock(ChangeBlockEvent.Break event) {//WORKS
    	
        event.getTransactions().stream().forEach((trans) -> {
            	
            Location<World> eventLocation = trans.getOriginal().getLocation().get();
                
        	if(ArenaConfigUtils.getUserArenaNameFromLocation(eventLocation) != null){
        	
    	        if (LobbyConfigUtils.isUserinLobby(eventLocation, AN)|| 
    	        		ArenaConfigUtils.isUserinArena(eventLocation, AN)) {
    	        	
    	            boolean playerCause = event.getCause().first(Player.class).isPresent();
    	            
    	            if (playerCause) {
    	            	
    	                Player player = event.getCause().first(Player.class).get();
    	                
			              player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
			                      TextColors.WHITE, "You can't break a Dreaz Zone arena or it's lobby!"));
    	            }
    	        	    	           
    	        	event.setCancelled(true);
    	        }
        	}
        });
    }
    
    //prevents contestants from dropping items in lobbies and prevents non-contestants from dropping items in arenas
    @Listener
    public void contestantDropItem(DropItemEvent.Dispense event, @First EntitySpawnCause spawncause){//TEST
    	
    	Optional<Text> userN = spawncause.getEntity().get(Keys.DISPLAY_NAME);
    	
    	if(userN.isPresent()){
    		
    		String userName = userN.get().toPlain();
    		
    		if(ArenaConfigUtils.getUserArenaNameFromLocation(spawncause.getEntity().getLocation())!=null){
    		
	    		if(ContestantConfigUtils.isUserAnArenaContestant(AN, userName)&&
	    				LobbyConfigUtils.isUserinLobby(spawncause.getEntity().getLocation(), AN)){
	    			
	    			event.setCancelled(true);
	    			
	    			return;
	    		}
    		}
    		
    		Player player = (Player)spawncause.getEntity();
    		
	       	if(!ContestantConfigUtils.isUserAnArenaContestant(AN, userName)&&
	       			ArenaConfigUtils.isUserinArena(spawncause.getEntity().getLocation(), AN)){
	              
                player.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                      TextColors.WHITE, "Non-contestants can't drop items in Dread Zone arenas!"));
	    		
	    		event.setCancelled(true);
	    		
	    		return;
	       	}
    	}
    }

    

    
    
    //Abstract listeners
    /**
    //prevents contestants from consuming potions in lobbies
    @Listener
    public void onPlayerConsumption(UseItemStackEvent.Start event, @Root Player player) {//needs cause event location and if the cause if by a contestnat//FIX
    		
    	event.setCancelled(true);

    }
	
    //prevents players from throwing potions in Lobbies
    @Listener
    public void onPotionThrow(LaunchProjectileEvent event,@Root Player player) {//needs cause event location and if the cause if by a contestnat//FIX
    
        event.setCancelled(true);
    }*/
}
