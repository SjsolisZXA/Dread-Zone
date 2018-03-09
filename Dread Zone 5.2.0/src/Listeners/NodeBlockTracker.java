package Listeners;

import java.util.HashSet;
import java.util.Set;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.world.BlockChangeFlag;

public class NodeBlockTracker {
	
	private final static Set<Transaction<BlockSnapshot>> snapshots = new HashSet<>();
	
    @Listener
    public void onBlockAlteration(ChangeBlockEvent.Place event) {

		snapshots.addAll(event.getTransactions());
		
		//Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GOLD,snapshots));
    
    }
    
    public static void restoreArenaTerrain() {
        
        for (Transaction<BlockSnapshot> transaction : snapshots) {
        	
            transaction.getOriginal().restore(true, BlockChangeFlag.ALL); 
        }
        
        snapshots.clear();
    }
}
