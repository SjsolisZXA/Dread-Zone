package Listeners;

import java.math.BigDecimal;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;
import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ContestantConfigUtils;
import ConfigUtils.PABConfigUtils;
import Main.Main;

public class PBDetector {
	
	@Listener
	public void onPlayerNearPB(MoveEntityEvent e,@Root Player player){
		
		if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation())!=null){
			
			String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
			
			if(ContestantConfigUtils.isUserAnArenaContestant(arenaName, player.getName())){
				
				Location<World> playerLocation = player.getLocation();
				
				Vector3i Plocation = (playerLocation.getPosition().toInt());
				
				Location<World> location = new Location<>(player.getWorld(), Plocation.toDouble());
	
				Location<World> PB = PABConfigUtils.getPABBLocation(arenaName).add(0,1,0);
			    		
				if(location.equals(PB.add(-1,0,3))||
				    location.equals(PB.add(0,0,3))||
				    location.equals(PB.add(1,0,3))||
				    
				    location.equals(PB.add(-1,0,-3))||
				    location.equals(PB.add(0,0,-3))||
				    location.equals(PB.add(1,0,-3))||
				    
				    location.equals(PB.add(-3,0,1))||
				    location.equals(PB.add(-3,0,0))||
				    location.equals(PB.add(-3,0,-1))||
				    
				    location.equals(PB.add(3,0,1))||
				    location.equals(PB.add(3,0,0))||
				    location.equals(PB.add(3,0,-1))||
				    
				    location.equals(PB.add(-2,0,2))||
				    location.equals(PB.add(-2,0,-2))||
				    location.equals(PB.add(2,0,2))||
				    location.equals(PB.add(2,0,-2))){
					
					EconomyService economyService = Sponge.getServiceManager().provide(EconomyService.class).get();
					UniqueAccount acc = economyService.getOrCreateAccount(player.getUniqueId()).get();
					BigDecimal reward = BigDecimal.valueOf(1000);
					acc.deposit(economyService.getDefaultCurrency(), reward, Cause.source(Main.getPluginContainer()).build());
					
					/**player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
							TextColors.WHITE, "Challange Completed! You've been rewarded with",TextColors.DARK_RED," $",reward,TextColors.WHITE,"."));*/
					
    				Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ",
							TextColors.WHITE,"Contestant ",TextColors.DARK_RED,player.getName(),TextColors.WHITE," of Dread Zone arena ",TextColors.DARK_RED,
							arenaName,TextColors.WHITE," has been rewarded with ",TextColors.DARK_RED,"$",reward,TextColors.WHITE," and has re-opened! "
									+ "To join, enter: ",TextColors.DARK_RED,"/dz join ",arenaName," ARENA_MODE",TextColors.WHITE,"."));
					
					ContestantConfigUtils.removeContestant(arenaName, player);
					
					return;
				}
				return;
			}
			return;
		}
		return;
	}
}
