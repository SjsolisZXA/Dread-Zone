package Delete;

import java.util.Optional;

import org.spongepowered.api.Sponge;
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

public class DeleteClassItem implements CommandExecutor {
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args){
		
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
			
				if (ArenaConfigUtils.getArenaGrandchildInConfig(arenaName, "ArenaClasses", className)!=null){
					
					ItemStack itemStack = (ItemStack)itemStackOptional.get();
					
					itemStack.setQuantity(itemStackOptional.get().getQuantity());
					
					try {

						if(ClassConfigUtils.doesClassItemExist(arenaName, className, itemStack)!=null){//FIX
							
							Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GOLD,"IF DCIE"));
							
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
