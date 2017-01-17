package ConfigUtils;

import java.util.Set;

import ConfigFiles.ArenaFileConfig;
import ConfigFiles.Configurable;
import ConfigFiles.UnversalConfigs;
import ninja.leaping.configurate.ConfigurationNode;

public class ClassConfigUtils {

	private static Configurable classConfig = ArenaFileConfig.getConfig();
	
	public static void addItemToClass(String arenaName, String className, String item)
	{
		ConfigurationNode itemsNodeTarget = UnversalConfigs.getConfig(classConfig).getNode((Object[]) ("Arena." + arenaName + ".ArenaClasses." + className + ".items").split("\\."));
		
		String items = itemsNodeTarget.getString();
		
		String newItem = (item + ",");
		
		UnversalConfigs.setValue(classConfig, itemsNodeTarget.getPath(), (items + newItem));
	}

	public static void addClass(String arenaName, String className)
	{
		UnversalConfigs.setValue(classConfig, new Object[] { "Arena", arenaName ,"ArenaClasses",className, "items" }, (""));
	}
	
	public static int getNumOfClasses(Object arenaName){
		
		Set<Object> arenaClasses = getArenaClasses(arenaName);
		
		int NOAC = 0;
		
		for(@SuppressWarnings("unused") Object AC:arenaClasses){
			
			NOAC++;
		}
		return NOAC;
	}
	
	public static Set<Object> getArenaClasses(Object arenaName)
	{
		return UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName, "ArenaClasses").getChildrenMap().keySet();
	}
	
	public static boolean doesClassExist(Object arenaName,Object className){
		
		Set<Object> arenaClasses = getArenaClasses(arenaName);
		
		for(Object arenaClass: arenaClasses){
			
			if(arenaClass.equals(className)){
				
				return true;
			}
		}
		return false;
	}
}
