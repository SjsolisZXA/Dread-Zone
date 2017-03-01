package Add;

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
			
			src.sendMessage(Text.of(TextColors.RED, "This is a user command only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		if(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation())!=null){
		
			String arenaName = ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation());
			
			if (LobbyConfigUtils.isUserinLobby(player.getLocation(), arenaName)){
			
				String className = args.<String> getOne("class name").get();
				
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
										TextColors.WHITE,"Arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," has 5 Classes and its NPCs, but "
												+ "you'll need to add items to ",TextColors.DARK_RED,arenaName+"'s",TextColors.WHITE," classes. To add an "
														+ "item to a Class, hold the exact amount of an item that you would like to add, and enter ",
												TextColors.DARK_RED,"/adzci ",arenaName," CLASS_NAME",TextColors.WHITE,"."));
								
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
