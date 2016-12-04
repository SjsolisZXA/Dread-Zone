package Utils;

import org.spongepowered.api.command.spec.CommandExecutor;
import javax.annotation.Nonnull;

public abstract class CommandExecutorBase implements CommandExecutor {

    protected String[] getAssociatedModules() {
        return new String[0];
    }

    @Nonnull
    public String getPrimaryAlias() {
        return getAliases()[0];
    }

    @Nonnull public abstract String[] getAliases();

    @Override
    public int hashCode() {
        return getClass().getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(getClass());
    }
}
