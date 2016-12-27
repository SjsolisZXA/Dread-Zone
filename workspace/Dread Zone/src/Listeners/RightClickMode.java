package Listeners;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigUtils.LightningConfigUtils;
import ConfigUtils.LobbyConfigUtils;
import ConfigUtils.MobCreateConfigUtils;
import ConfigUtils.NodeConfigUtils;
import ConfigUtils.RightClickModeConfigUtils;
import Utils.NodeUtils;

public class RightClickMode {
	
	static String SLRLTN;
	static String SMCLGN;
	static String SMCLTN;
	static String NN;
	static String SLLN;
	
	public static void SLRLTN(String targetName) {
		
		SLRLTN = targetName;
	}
	
	public static void SMCL(String groupName, String targetName) {

		SMCLGN = groupName;
		SMCLTN = targetName;
	}
	
	public static void SN(String nodeName) {

		NN = nodeName;
	}
	
	public static void SLLN(String lobbyName) {
		
		SLLN = lobbyName;
	}
	
	@Listener
	public void onPlayerInteractBlock(InteractBlockEvent.Secondary event, @First Player player){
		
		if(RightClickModeConfigUtils.isUserInConfig(player.getName())){
			
			Location<World> blockLocation = event.getTargetBlock().getLocation().get().add(0,1,0);
			
			//check for Lightning Rod Mode
			if (RightClickModeConfigUtils.getUserMode(player.getName().toString()).equals("LM")){
				
				//check to see if rod exists, if not create it in the configuration file 
				if (LightningConfigUtils.isTargetInConfig(SLRLTN))
				{
					LightningConfigUtils.deleteTarget(SLRLTN);
				}
				
				LightningConfigUtils.setTarget(blockLocation, player.getWorld().getName(), SLRLTN);
				
				RightClickModeConfigUtils.deleteUsernameInList(player.getName());
				
				//Confirmation message
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
						TextColors.WHITE, "Success, ", TextColors.DARK_RED, SLRLTN, TextColors.WHITE," DZ Rod created!"));
				
				return;
			}
			
			//check for Mob Crate Mode
			if(RightClickModeConfigUtils.getUserMode(player.getName().toString()).equals("MC")){
				
				//check to see if mob crate exists, if not create it in the configuration file 
				if (MobCreateConfigUtils.isTargetInConfig(SMCLGN,SMCLTN))
				{
					MobCreateConfigUtils.deleteTarget(SMCLGN,SMCLTN);
				}
				
				MobCreateConfigUtils.setTarget(blockLocation, player.getWorld().getName(), SMCLGN, SMCLTN);
				
				RightClickModeConfigUtils.deleteUsernameInList(player.getName());
				
				//Confirmation message
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
						TextColors.WHITE,"Success, DZ Mob Create ",TextColors.DARK_RED,SMCLTN,TextColors.WHITE, " added to group ", TextColors.DARK_RED, SMCLGN,TextColors.WHITE," !"));
				
				return;
			}
			
			//check for Node Mode
			if(RightClickModeConfigUtils.getUserMode(player.getName().toString()).equals("N")){
				
				//check to see if node exists, if not create it in the configuration file 
				if (NodeConfigUtils.isNodeInConfig(NN))
				{
					NodeConfigUtils.deleteNode(NN);
				}
				
				//configuration part of the node build
				NodeConfigUtils.setNode(blockLocation, player.getWorld().getName(), NN);
				
				//save base that will be replaced by the node
				//NodeUtils.saveNodeBase(blockLocation);
				
				//building of the physical node
				NodeUtils.buildNode(blockLocation);
				
			    RightClickModeConfigUtils.deleteUsernameInList(player.getName());
			    
				//Confirmation message
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
						TextColors.WHITE,"Success, DZ Node ",TextColors.DARK_RED, NN ,TextColors.WHITE, " created !"));
			    
			    return;
			}
			
			//check for Lobby Create Mode
			if(RightClickModeConfigUtils.getUserMode(player.getName().toString()).equals("CL")){
				
				if(!(LobbyConfigUtils.isLobbyInConfig(SLLN))){
					
					LobbyConfigUtils.setLobbyp1(blockLocation, player.getWorld().getName(), SLLN);
					
					//Confirmation message 
					player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
							TextColors.WHITE,"Select the oposite corner of that block to be the ceiling of the lobby."));
					
					return;
				}				
				
				LobbyConfigUtils.setLobbyp2(blockLocation.add(0,-1,0), SLLN);
				
				RightClickModeConfigUtils.deleteUsernameInList(player.getName());
				
				//Confirmation message
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
						TextColors.WHITE,"Success, Lobby ",TextColors.DARK_RED, SLLN,TextColors.WHITE, " created !"));
				
				return;
			}
			
			return;
		}
		return;
	}
}
