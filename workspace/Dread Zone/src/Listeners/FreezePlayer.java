package Listeners;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;

import Utils.TeamDeathmatchTimerTask;

public class FreezePlayer {
	@Listener
	public void onPlayerMove(DisplaceEntityEvent.TargetPlayer e){
	    if(TeamDeathmatchTimerTask.isFreezingPlayers()){
	        e.setCancelled(true);
		}
	}
}
