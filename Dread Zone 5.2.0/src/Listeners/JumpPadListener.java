package Listeners;

import java.util.Set;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.Root;
import ConfigUtils.JumpPadConfigUtils;

public class JumpPadListener {
	
    @Listener
    public void blockChange(ChangeBlockEvent.Modify event, @Root Player player){
    	
        BlockSnapshot bs = event.getTransactions().get(0).getFinal();
        
        BlockType bt = bs.getState().getType();
        
        if(bt == BlockTypes.LIGHT_WEIGHTED_PRESSURE_PLATE){
        	
        	Set<Object> jumpPads = JumpPadConfigUtils.getJumpPads();
        	
        	for(Object jumpPad: jumpPads){
        		
            	if(bs.getLocation().get().equals(JumpPadConfigUtils.getJumpPadLocation(jumpPad.toString()))){
            		
            		player.setVelocity(player.getVelocity().mul(9d,0,5d).add(0,2.4,0));
            		
            		return;
            	}
        	}
        }
    }
}
