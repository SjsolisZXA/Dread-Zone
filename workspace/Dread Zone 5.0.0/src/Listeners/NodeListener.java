package Listeners;

import java.util.Set;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.DyeColors;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigFiles.Configurable;
import ConfigFiles.NodeFileConfig;
import ConfigFiles.UnversalConfigs;
import ConfigUtils.NodeConfigUtils;

public class NodeListener {
	
    @Listener
    public void whenPlayerStepsOnNode(ChangeBlockEvent.Modify event,@First Player player) {
    	
    	Configurable nodeConfig = NodeFileConfig.getConfig();
    	
    	//Inventory team checker (Team Checker)
	    ItemStack RG = ItemStack.of(ItemTypes.STAINED_GLASS, 1);
	    RG.offer(Keys.DYE_COLOR, DyeColors.RED);
	    
	    ItemStack BG = ItemStack.of(ItemTypes.STAINED_GLASS, 1);
	    BG.offer(Keys.DYE_COLOR, DyeColors.BLUE);
	    
	    //Node team colors
	    
	    //Blue
	    BlockState stainedGlassB = BlockState.builder().blockType(BlockTypes.STAINED_GLASS).build();
	    stainedGlassB = stainedGlassB.with(Keys.DYE_COLOR, DyeColors.BLUE).get();
	    
	    //Red
	    BlockState stainedGlassR = BlockState.builder().blockType(BlockTypes.STAINED_GLASS).build();
	    stainedGlassR = stainedGlassR.with(Keys.DYE_COLOR, DyeColors.RED).get();
	    
		//Takes apart the event
    	for (Transaction<BlockSnapshot> transaction : event.getTransactions()) {
			BlockSnapshot block = transaction.getFinal();
			BlockType type = block.getState().getType();
			
			Set<Object> nodes = NodeConfigUtils.getNodes();

			//Goes through all of the registered nodes in the nodes configuraton file
			for(Object node: nodes){
			
				if(block.getLocation().get().equals(NodeConfigUtils.getNode(node.toString()))&&
						(type.equals(BlockTypes.STONE_PRESSURE_PLATE))&&
						(block.getExtendedState().get(Keys.POWERED).isPresent())&&
						(block.getExtendedState().get(Keys.POWERED).get())){
				    
					Location <World> plocation = block.getLocation().get();
					
					//Player team checker
				    if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
				    	
				    	ItemStack itemInHand = player.getItemInHand(HandTypes.MAIN_HAND).get();
				    	
				    	if(itemInHand.equalTo(RG)){
				    		
				    		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
				    			"Node captured by the Red Team."));
				    		
						    plocation.add(-1,-1,3).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//front glass
						    plocation.add(0,-1,3).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    plocation.add(1,-1,3).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    
						    plocation.add(-1,-1,-3).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//back glass
						    plocation.add(0,-1,-3).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    plocation.add(1,-1,-3).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    
						    plocation.add(-3,-1,1).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left glass
						    plocation.add(-3,-1,0).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    plocation.add(-3,-1,-1).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    
						    plocation.add(3,-1,1).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//right glass
						    plocation.add(3,-1,0).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    plocation.add(3,-1,-1).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    
						    plocation.add(-2,-1,2).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left top
						    plocation.add(-2,-1,-2).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left bottom
						    plocation.add(2,-1,2).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//right top
						    plocation.add(2,-1,-2).setBlock(stainedGlassR,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left bottom
						    
						    UnversalConfigs.getConfig(nodeConfig).getNode("Nodes", node, "Team").setValue("Red");
						    UnversalConfigs.saveConfig(nodeConfig);		
						    
						    return;
				    	}
				    	if(itemInHand.equalTo(BG)){
				    		
				    		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					    			TextColors.DARK_BLUE,"Node captured by the Blue Team."));
					    
						    plocation.add(-1,-1,3).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//front glass
						    plocation.add(0,-1,3).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    plocation.add(1,-1,3).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    
						    plocation.add(-1,-1,-3).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//back glass
						    plocation.add(0,-1,-3).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    plocation.add(1,-1,-3).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    
						    plocation.add(-3,-1,1).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left glass
						    plocation.add(-3,-1,0).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    plocation.add(-3,-1,-1).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    
						    plocation.add(3,-1,1).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//right glass
						    plocation.add(3,-1,0).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    plocation.add(3,-1,-1).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));
						    
						    plocation.add(-2,-1,2).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left top
						    plocation.add(-2,-1,-2).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left bottom
						    plocation.add(2,-1,2).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//right top
						    plocation.add(2,-1,-2).setBlock(stainedGlassB,Cause.of(NamedCause.owner(Main.Main.dreadzone)));//left bottom
						    
						    UnversalConfigs.getConfig(nodeConfig).getNode("Nodes", node, "Team").setValue("Blue");
						    UnversalConfigs.saveConfig(nodeConfig);	
						    
						    return;
				    	}
				    }
			    	/**player.sendMessage(Text.of(TextColors.WHITE, "You are not in any team."));**/
				    return;
				}
			}
		}
	}
}
	
