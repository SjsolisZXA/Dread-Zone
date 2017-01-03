package Listeners;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;

public class PlayerBarrier {
	
	static String AN;
	
	@Listener
	public void onPlayerMove(DisplaceEntityEvent.TargetPlayer event, @First Player player){
		
	    if(ArenaConfigUtils.isUserAnArenaContestant(AN, player.getName())
	    		&&!ArenaConfigUtils.isUserinArena(player.getLocation(), AN)
	    				&&!ArenaConfigUtils.isUserinLobby(player.getLocation(),  AN)){
	    	
	    	if(ArenaConfigUtils.isUserinArena(player.getLocation().add(1,0,0),AN)
	    			||ArenaConfigUtils.isUserinLobby(player.getLocation().add(1,0,0), AN)){
	    		
	    		player.setLocation(player.getLocation().add(1,0,0));
	    		return;
	    	}
	    	
	    	if(ArenaConfigUtils.isUserinArena(player.getLocation().add(-1,0,0),AN)
	    			||ArenaConfigUtils.isUserinLobby(player.getLocation().add(-1,0,0), AN)){
	    		
	    		player.setLocation(player.getLocation().add(-1,0,0));
	    		return;
	    	}
	    	
	    	if(ArenaConfigUtils.isUserinArena(player.getLocation().add(0,0,1),AN)
	    			||ArenaConfigUtils.isUserinLobby(player.getLocation().add(0,0,1), AN)){
	    		
	    		player.setLocation(player.getLocation().add(0,0,1));
	    		return;
	    	}
	    	
	    	if(ArenaConfigUtils.isUserinArena(player.getLocation().add(0,0,-1),AN)
	    			||ArenaConfigUtils.isUserinLobby(player.getLocation().add(0,0,-1), AN)){
	    		
	    		player.setLocation(player.getLocation().add(0,0,-1));
	    		return;
	    	}
	    	
	    	if(ArenaConfigUtils.isUserinArena(player.getLocation().add(0,-1,0),AN)
	    			||ArenaConfigUtils.isUserinLobby(player.getLocation().add(0,-1,0), AN)){
	    		
	    		player.setLocation(player.getLocation().add(0,-1,0));
	    		return;
	    	}
	    	
	    	if(ArenaConfigUtils.isUserinArena(player.getLocation().add(0,1,0),AN)
	    			||ArenaConfigUtils.isUserinLobby(player.getLocation().add(0,1,0), AN)){
	    		
	    		player.setLocation(player.getLocation().add(0,1,0));
	    		return;
	    	}
	    	
	    	ArenaConfigUtils.removeContestant(AN, player.getName());
	    	
	    	player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Thanks for playing!"));
		}
	    return;
	}

	public static void LN(String lobbyName) {

		AN = lobbyName;		
	}
	
    @Listener
    public void playerCommand(SendCommandEvent commandEvent, @First Player player) {
    	
    	String command = commandEvent.getCommand();
    
    	if(ArenaConfigUtils.isUserAnArenaContestant(AN, player.getName())&&!command.equals("dzlarena")){
    		
    		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Commands are not allowed in Dread Zone!"));
    		
    		commandEvent.setCancelled(true);
    	}
    	
    }
}
