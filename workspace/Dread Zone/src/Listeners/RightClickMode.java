package Listeners;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.LightningConfigUtils;
import ConfigUtils.MobCreateConfigUtils;
import ConfigUtils.NodeConfigUtils;
import ConfigUtils.RightClickModeConfigUtils;
import Utils.NodeUtils;

public class RightClickMode {
	
	static String SLRLTN;
	static String SMCLGN;
	static String SMCLTN;
	static String NN;
	static String SAAN;
	
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
	
	public static void SAAN(String arenaName) {
		
		SAAN = arenaName;
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
						TextColors.WHITE, "Success, ", TextColors.DARK_RED, SLRLTN, TextColors.WHITE," Dread Zone Rod created!"));
				
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
						TextColors.WHITE,"Success, DZ Mob Create ",TextColors.DARK_RED,SMCLTN,TextColors.WHITE, " added to group ", 
						TextColors.DARK_RED, SMCLGN,TextColors.WHITE," !"));
				
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
						TextColors.WHITE,"Success, DZ Node ",TextColors.DARK_RED, NN ,TextColors.WHITE, " created!"));
			    
			    return;
			}
			
			//check for Arena Create Mode
			if(RightClickModeConfigUtils.getUserMode(player.getName().toString()).equals("CA")){
				
				if(!(ArenaConfigUtils.isArenaInConfig(SAAN))){
					
					ArenaConfigUtils.setArenap1(blockLocation, player.getWorld().getName(), SAAN);
					
					//Confirmation message 
					player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
							TextColors.WHITE,"Select the oposite corner of that block to be the ceiling of the arena."));
					
					return;
				}				
				
				ArenaConfigUtils.setArenap2(blockLocation.add(0,-1,0), SAAN);
				
				RightClickModeConfigUtils.deleteUsernameInList(player.getName());
				
				RightClickModeConfigUtils.addToList(player.getName(),"CAL");
				
				//Confirmation message
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
						
						TextColors.WHITE,"Success, Arena ",TextColors.DARK_RED, SAAN,TextColors.WHITE, " created ! "
								+ "Now right click on a block to be a floor corner of ",TextColors.DARK_RED,SAAN,"'s",TextColors.WHITE," lobby."));
				
				return;
			}
			
			//check for Lobby Create Mode
			if(RightClickModeConfigUtils.getUserMode(player.getName().toString()).equals("CAL")){
				
				if(!(ArenaConfigUtils.isLobbyInConfig(SAAN, SAAN+"Lobby"))){
					
					ArenaConfigUtils.setLobbyp1(blockLocation, player.getWorld().getName(), SAAN);
					
					//Confirmation message 
					player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
							TextColors.WHITE,"Select the oposite corner of that block to be the ceiling of ",TextColors.DARK_RED,SAAN,"'s",TextColors.WHITE," lobby."));
					
					return;
				}				
				
				ArenaConfigUtils.setLobbyp2(blockLocation.add(0,-1,0), SAAN);
				
				ArenaConfigUtils.setArenaStatus(SAAN, "Open");
				
				RightClickModeConfigUtils.deleteUsernameInList(player.getName());
				
				//Confirmation message
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
						TextColors.WHITE,"Success, ",TextColors.DARK_RED, SAAN,"'s ",TextColors.WHITE, "lobby created! To set ",
						TextColors.DARK_RED, SAAN,"'s ",TextColors.WHITE,"lobby spawn area, stand where you want players to spawn and enter ",TextColors.DARK_RED,"/cdzlsa ",SAAN));
				
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
						TextColors.WHITE,"Although the arena and lobby have been created, you must create idividual modes for your arenas. "
								+ "To do so, enter ",TextColors.DARK_RED,"/cdzam ",SAAN));
				
				return;
			}
			
			return;
		}
		return;
	}
}
