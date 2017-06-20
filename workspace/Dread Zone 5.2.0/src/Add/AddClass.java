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
import ConfigUtils.RightClickModeConfigUtils;

public class AddClass implements CommandExecutor{
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE, "This is a user command only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		Optional<String> CN = args.<String> getOne("class name");
		Optional<String> AN = args.<String> getOne("arena name");
		
		if(!AN.isPresent()||!CN.isPresent()){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Invalid usage! Valid usage: ",TextColors.DARK_RED, "/dz ac CLASS_NAME ARENA_NAME",TextColors.WHITE,"."));
			
			return CommandResult.success();
		}
		
		String arenaName = AN.get();
		
		if(ArenaConfigUtils.isArenaInConfig(arenaName)){
		
			if(ClassConfigUtils.getNumOfClasses(arenaName)<5){
				
				String className = CN.get();
				
				ClassConfigUtils.addClass(arenaName, className);
				
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
						TextColors.WHITE,"Class ",TextColors.DARK_RED,className,TextColors.WHITE," successfuly added to arena ",
						TextColors.DARK_RED,arenaName,TextColors.WHITE,"!"));
				
				if(RightClickModeConfigUtils.isUserInConfig(player.getName())&&RightClickModeConfigUtils.getUserMode(player.getName()).equals("AAC")){
					
					if(ClassConfigUtils.getNumOfClasses(arenaName)<5){
					
						for(int i = 0; i<5; i++){
							
							if(ClassConfigUtils.getNumOfClasses(arenaName)==i&&i<4){
								
								player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
										TextColors.WHITE,(5-i)+" more classes!"));
								
							}
							
							if(ClassConfigUtils.getNumOfClasses(arenaName)==4&&i==4){
								player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
										TextColors.WHITE,"1 more class!"));
							}
						}
					}
					
					if(ClassConfigUtils.getNumOfClasses(arenaName)==5){
						
						player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
								TextColors.WHITE,"Arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," now has 5 Classes, but you'll need to set the "
										+ "spawnpoints of the Dread Zone Class NPCs. To add a DZC NPC, stand where you would like the NPC to appear in ",
										TextColors.DARK_RED,arenaName+"'s",TextColors.WHITE," lobby, face the same direction as you want the NPC to face and enter: ",
										TextColors.DARK_RED,"/dz acnpc CLASS_NAME"));
					}
				}
				
				return CommandResult.success();
			}
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					arenaName,TextColors.WHITE," already has 5 classes. To remove a class, enter ",TextColors.DARK_RED,"/dz rc ARENA_NAME CLASS_NAME",
					TextColors.WHITE,". To view the available classes for an arena, enter ",TextColors.DARK_RED,"/dz cl ARENA_NAME"));
			
			return CommandResult.success();
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
				TextColors.WHITE,"Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," does not exists! "
						+ "To view a list of avaiable Dread Zone arenas, enter ",TextColors.DARK_RED,"/dz al",TextColors.WHITE,"!"));
		
		return CommandResult.success();
		
	}

}
