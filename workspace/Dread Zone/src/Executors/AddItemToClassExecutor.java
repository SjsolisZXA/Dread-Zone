package Executors;

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

public class AddItemToClassExecutor implements CommandExecutor{
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.RED, "This is a user command only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		String arenaName = args.<String> getOne("arena name").get();
		
		String className = args.<String> getOne("class name").get();
		
		if(!player.getItemInHand().get().getItem().getId().isEmpty()){
		
			if(ArenaConfigUtils.isArenaInConfig(arenaName)){
			
				if(ClassConfigUtils.getNumOfClasses(arenaName)<5){//Check to see if a class already has more than 5 items
					
					ClassConfigUtils.addItemToClass(arenaName, className, player.getItemInHand().get().getItem().getId()+
							":"+player.getItemInHand().get().getQuantity());
					
					player.getItemInHand().get().getQuantity();
					
					player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
							TextColors.WHITE,"ItemStack ",TextColors.DARK_RED, player.getItemInHand().get().getItem().getId(), 
							TextColors.WHITE," successfuly added to class ",TextColors.DARK_RED,className,TextColors.WHITE,"!"));
					
					return CommandResult.success();
				}
				
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
						className,TextColors.WHITE," already has 5 items. To remove an item from this class, enter ",TextColors.DARK_RED,"/rdzci ARENA_NAME CLASS_NAME"));
				
				return CommandResult.success();
			}
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," does not exists! "
							+ "To view a list of avaiable Dread Zone arenas, enter ",TextColors.DARK_RED,"/dzarenas",TextColors.WHITE,"!"));
			
			return CommandResult.success();
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
				className,TextColors.WHITE," You must be holding something in your hand to executethis command!"));
		
		return CommandResult.success();
		
	}

}
