package Delete;

import java.util.Optional;

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

public class DeleteClassItem implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
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
					TextColors.WHITE,"Invalid usage! Valid usage: ",TextColors.DARK_RED, "/dz rci CLASS_NAME ARENA_NAME",TextColors.WHITE,"."));
			
			return CommandResult.success();
		}
		
		String className = CN.get();
		String arenaName = AN.get();
		
		Optional<ItemStack> itemStackOptional = player.getItemInHand(HandTypes.MAIN_HAND);
		
		if(itemStackOptional.isPresent()){
		
			if(ArenaConfigUtils.isArenaInConfig(arenaName)){
			
				if (ArenaConfigUtils.getArenaGrandchildInConfig(arenaName, "ArenaClasses", className)!=null){
					
					ItemStack itemStack = (ItemStack)itemStackOptional.get();
					
					itemStack.setQuantity(itemStackOptional.get().getQuantity());
					
					try {

						if(ClassConfigUtils.doesClassItemExist(arenaName, className, itemStack)!=null){//FIX
							
							ClassConfigUtils.removeClassItem(arenaName, className, itemStack);
						
							player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
									TextColors.WHITE,"Success, item ",TextColors.DARK_RED,itemStack, TextColors.WHITE," removed from class ",
									TextColors.DARK_RED, className, TextColors.WHITE, "!"));
							
							return CommandResult.success();
						}
						
						player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
								TextColors.WHITE,"Either the item you are holding does not exist or you are not holding the item and "
										+ "the exact amount that is specified in class ",TextColors.DARK_RED,className));
						
						return CommandResult.success();
						
					} catch (ObjectMappingException e) {
						
						e.printStackTrace();
					}
				}
				
				player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
						TextColors.WHITE,"Class ", TextColors.DARK_RED, className, TextColors.WHITE, " class does not exist!"));
				
				return CommandResult.success();
			}
			
			player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ", 
					TextColors.WHITE,"Arena ", TextColors.DARK_RED, arenaName, TextColors.WHITE, " does not exist!"));
			
			return CommandResult.success();
		}
		
		player.sendMessage(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone",TextColors.DARK_RED,"] ",
				className,TextColors.WHITE," You must be holding something in your hand to executethis command!"));
		
		return CommandResult.success();
	}	
}
