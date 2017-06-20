package Add;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.type.HandTypes;
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
			
			src.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE, "This is a user command only!"));
		}
		
		Player player = (Player)src;
		Optional<String> CN = args.<String> getOne("class name");
		Optional<String> AN = args.<String> getOne("arena name");
				
		if(!AN.isPresent()||!CN.isPresent()){
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Invalid usage! Valid usage: ",TextColors.DARK_RED, "/dz aci CLASS_NAME ARENA_NAME",TextColors.WHITE,"."));
			
			return CommandResult.success();
		}
		
		String className = CN.get();
		String arenaName = AN.get();
				
		Optional<ItemStack> itemStackOptional = player.getItemInHand(HandTypes.MAIN_HAND);
		
		if(itemStackOptional.isPresent()){
		
			if(ArenaConfigUtils.isArenaInConfig(arenaName)){
				
				if(ClassConfigUtils.doesClassExist(arenaName, className)){
					
					if(ClassConfigUtils.getNumOfClassItems(arenaName, className)<36){
						
						ItemStack itemStack = itemStackOptional.get();
						
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
					
					player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
							TextColors.WHITE,"Class ",TextColors.DARK_RED,className,TextColors.WHITE," has too many items! "
									+ "To remove an item from this class, hold an existing class item and it's exact quantity and enter ",
									TextColors.DARK_RED,"/dz rci ARENA_NAME CLASS_NAME"));
				
					return CommandResult.success();
				}
				
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
						TextColors.WHITE,"Class ",TextColors.DARK_RED,className,TextColors.WHITE," does not exists. To view a list of an arena's classes, enter ",
						TextColors.DARK_RED,"/dz cl ARENA_NAME"));
				
				return CommandResult.success();
			}
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
					TextColors.WHITE,"Dread Zone arena ",TextColors.DARK_RED,arenaName,TextColors.WHITE," does not exists! "
							+ "To view a list of avaiable Dread Zone arenas, enter ",TextColors.DARK_RED,"/dz al",TextColors.WHITE,"!"));
			
			return CommandResult.success();
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
				className,TextColors.WHITE," You must be holding something in your hand to executethis command!"));
		
		return CommandResult.success();
		
	}
}
