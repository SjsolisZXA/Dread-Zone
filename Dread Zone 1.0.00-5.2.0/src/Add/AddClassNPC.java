package Add;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ClassConfigUtils;
import ConfigUtils.LobbyConfigUtils;
import ConfigUtils.RightClickModeConfigUtils;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class AddClassNPC implements CommandExecutor{
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE, "This is a user command only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		Optional<String> CN = args.<String> getOne("class name");

		if(!CN.isPresent()){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Invalid usage! Valid usage: ",TextColors.DARK_RED, "/dz ac CLASS_NAME",TextColors.WHITE,"."));
			
			return CommandResult.success();
		}
		
		if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation())!=null){
		
			String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
			
			if (LobbyConfigUtils.isUserinLobby(player.getLocation(), arenaName)){
			
				String className = CN.get();
				
				if(ClassConfigUtils.doesClassExist(arenaName, className)){
					
					try {
						
						ClassConfigUtils.addClassNPC(player.getTransform(),arenaName, className);
						
					} catch (ObjectMappingException e) {
	
						e.printStackTrace();
					}
					
					player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
							TextColors.WHITE,"Dread Zone NPC location for ",TextColors.DARK_RED,className,TextColors.WHITE," class successfuly added!"));
					
					if(RightClickModeConfigUtils.isUserInConfig(player.getName())&&RightClickModeConfigUtils.getUserMode(player.getName()).equals("AAC")){
						
						try {
							if(ClassConfigUtils.getNumOfSetNPCClasses(arenaName)==5){
								
								player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
										TextColors.WHITE,"NPC spawn locations set, but you'll need to add items to each class. To add an "
														+ "item to a Class, hold an item and it's exact amount that you would like to add, and enter ",
												TextColors.DARK_RED,"/dz aci CLASS_NAME ",arenaName,TextColors.WHITE,"."));
								
								RightClickModeConfigUtils.deleteUsernameInList(player.getName());
								
								ArenaConfigUtils.addMode(arenaName,"TDM");
							}
							
						} catch (ObjectMappingException e) {
							
							e.printStackTrace();
						}
					}
					
					return CommandResult.success();
				}
				
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
						TextColors.WHITE,"Class ",TextColors.DARK_RED,className,TextColors.WHITE," does not exist! To view a list of ",
						TextColors.DARK_RED,arenaName+"'s",TextColors.WHITE," classes enter ",TextColors.DARK_RED,"/dzcl ",arenaName));
				
				return CommandResult.success();
			}
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
				TextColors.WHITE,"You must be inside a Dread Zone arena lobby to execute this command!"));
		
		return CommandResult.success();
		
	}

}
