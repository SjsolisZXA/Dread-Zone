package Listeners;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
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
import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import ConfigUtils.LobbyConfigUtils;

public class ArenaListeners {
	
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
    public void playerCommand(SendCommandEvent commandEvent, @First Player player) {
    	
    	String command = commandEvent.getCommand();
    
    	if(ContestantConfigUtils.isUserAnArenaContestant(AN, player.getName())&&(!command.equals("ldzarena")&&!command.equals("test"))){
    		
    		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Commands are not allowed in Dread Zone! To leave this arena, enter ",TextColors.DARK_RED,"/ldzarena"));
    		
    		commandEvent.setCancelled(true);
    	}
    }
    
    //prevents users from moving any inventories
    @Listener
    public void playerClickInventoryItem(ClickInventoryEvent event, @First Player player){
    	
    	if(LobbyConfigUtils.isUserinLobby(player.getLocation(), AN)){
    		
    		event.setCancelled(true);
    	}
    }
    
    //prevents users from dropping items in the arena
    @Listener
    public void playerDropItem(DropItemEvent.Dispense event, @First EntitySpawnCause spawncause){

       	if(LobbyConfigUtils.isUserinLobby(spawncause.getEntity().getLocation(), AN)){
    		
    		event.setCancelled(true);
    	}
    }
    
    //prevents users from picking up items from the floor
    @Listener
    public void playerReceiveItem(ChangeInventoryEvent.Pickup event, @First Player player){
    	
    	if(ContestantConfigUtils.isUserAnArenaContestant(AN, player.getName())){
    		
    		event.setCancelled(true);
    	}
    }
    
    //prevents users from obtaining items in creative by middle clicking their mouse button
    @Listener
    public void playerMiddleMouseClick(ClickInventoryEvent.Middle event, @First Player player){
    	
    	if(ContestantConfigUtils.isUserAnArenaContestant(AN, player.getName())){
    		
    		event.setCancelled(true);
    	}
    }
    
    //prevents users from damaging other players
    @Listener
    public void playerReceiveDamage(DamageEntityEvent event, @First EntityDamageSource source){

    	if(LobbyConfigUtils.isUserinLobby(source.getSource().getLocation(), ArenaConfigUtils.getUserArenaNameFromLocation(source.getSource().getLocation()))){
    		
    		event.setCancelled(true);
    	}
    }
}
