package Add;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ClassConfigUtils;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class AddClassItem implements CommandExecutor{
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		if(!(src instanceof Player)){
			
			src.sendMessage(Text.of(TextColors.RED, "This is a user command only!"));
			
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		String arenaName = args.<String> getOne("arena name").get();
		
		String className = args.<String> getOne("class name").get();
		
		Optional<ItemStack> itemStackOptional = player.getItemInHand();
		
		if(itemStackOptional.isPresent()){
		
			if(ArenaConfigUtils.isArenaInConfig(arenaName)){
				
				if(ClassConfigUtils.doesClassExist(arenaName, className)){
			
					if(ClassConfigUtils.getNumOfNoneEmptyClassItems(arenaName, className)<ClassConfigUtils.getNumOfClassItems(arenaName, className)){
						
						ItemStack itemStack = (ItemStack)itemStackOptional.get();
						
				        int quantity = itemStackOptional.get().getQuantity();
	
				        itemStack.setQuantity(quantity);
						
						try {
							
							ClassConfigUtils.addItemToClass(arenaName, className, itemStack);
							
						} catch (ObjectMappingException e) {
							
							e.printStackTrace();
						}
						
						player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
								TextColors.WHITE,"Item ",TextColors.DARK_RED, itemStack, 
								TextColors.WHITE," successfuly added to class ",TextColors.DARK_RED,className,TextColors.WHITE,"!"));
						
						return CommandResult.success();
					}
					
					try {
						
						player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
								TextColors.WHITE,"Class ",TextColors.DARK_RED,className,TextColors.WHITE," already has 4 items. "
										+ "To remove an item from this class, hold one of these items: ",TextColors.DARK_RED,
										ClassConfigUtils.getClassItems(arenaName, className),TextColors.WHITE," and enter ",
										TextColors.DARK_RED,"/rdzci ARENA_NAME CLASS_NAME"));
						
					} catch (ObjectMappingException e) {
						
						e.printStackTrace();
					}
					
					return CommandResult.success();
				}
				
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
						TextColors.WHITE,"Class ",TextColors.DARK_RED,className,TextColors.WHITE," does not exists. To view a list of an arena's classes, enter ",
						TextColors.DARK_RED,"/dzcl ARENA_NAME"));
				
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
