package Utils;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;

public abstract class AsyncCommandExecutorBase extends CommandExecutorBase {

	public abstract void executeAsync(CommandSource src, CommandContext args);

    @Override
    public final CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Sponge.getScheduler().createAsyncExecutor(Main.Main.getDreadZone()).execute(() -> executeAsync(src, args));
        return CommandResult.success();
    }
}
