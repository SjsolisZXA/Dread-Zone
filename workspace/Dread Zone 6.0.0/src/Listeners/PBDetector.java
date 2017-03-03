package Listeners;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import ConfigUtils.PABConfigUtils;

public class PBDetector {
	
	@Listener
	public void onPlayerNearPB(MoveEntityEvent e,@Root Player player){
		
		if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation())!=null){
			
			String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
			
			if(ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())){
				
				Location<World> location = player.getLocation();
	
				Location<World> PB = PABConfigUtils.getPABBLocation(arenaName).add(0,1,0);
			    		
				if(location.equals(PB)){
					
					player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
							TextColors.WHITE, "Challange Completed!"));
					
				}
			}
		}
	}
}
