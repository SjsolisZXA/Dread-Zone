package ConfigFiles;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public final class UnversalConfigs
{

	public static CommentedConfigurationNode getConfig(Configurable config)
	{
		return config.get();
	}

	public static void saveConfig(Configurable config)
	{
		config.save();
	}

	public static void setValue(Configurable config, Object[] nodePath, Object value)
	{
		config.get().getNode(nodePath).setValue(value);
		config.save();
	}

	public static void removeChild(Configurable config, Object[] nodePath, Object child)
	{
		config.get().getNode(nodePath).removeChild(child);
		config.save();
		
	}

	public static void removeChildren(Configurable config, Object[] nodePath)
	{
		for (Object child : config.get().getNode(nodePath).getChildrenMap().keySet())
		{
			config.get().getNode(nodePath).removeChild(child);
		}

		config.save();
	}
}
