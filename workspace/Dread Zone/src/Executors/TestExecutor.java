package Executors;

import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

public class TestExecutor implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) {
		
		//Player player =(Player)src;	
		
		//player.sendMessage(Text.of(TextColors.GOLD,player.getItemInHand().get().getItem().getBlock().get().getTrait("variant").get().getValueClass().getTypeName()));
		
		//player.sendMessage(Text.of(TextColors.DARK_GREEN,player.getItemInHand().get().getContainers()));
		
		/**Optional<BlockType> optBlock = Sponge.getRegistry().getType(BlockType.class, player.getItemInHand().get().getItem().getId());
		if (optBlock.isPresent()) {
			
			player.sendMessage(Text.of(TextColors.AQUA,optBlock));
			
		    BlockState defaultState = optBlock.get().getDefaultState();
		    
		    player.sendMessage(Text.of(TextColors.DARK_AQUA,defaultState));
		    
		    Optional<BlockTrait<?>> optTrait = defaultState.getTrait("variant");
		    
		    player.sendMessage(Text.of(TextColors.DARK_RED,optTrait));
		    
		    if (optTrait.isPresent()) {
		        Optional<BlockState> optState = defaultState.withTrait(optTrait.get(), "granite");
		        if (optState.isPresent()) {
		            BlockState blockState = optState.get();
		            
		            player.sendMessage(Text.of(TextColors.LIGHT_PURPLE,blockState));
		        }
		    }
		}**/
		
		
		
		//player.sendMessage(Text.of(TextColors.LIGHT_PURPLE,player.getItemInHand()
		
		//player.sendMessage(Text.of(TextColors.LIGHT_PURPLE,player.getItemInHand().get().getContentVersion()));
		
		//player.sendMessage(Text.of(TextColors.AQUA,player.getItemInHand().get().getValues()));
		
		//player.sendMessage(Text.of(TextColors.DARK_BLUE,player.getItemInHand().get().getKeys()));
		
		return CommandResult.success();
	}
}


