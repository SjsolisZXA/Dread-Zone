package ConfigUtils;

import java.util.Set;

import ConfigFiles.UnversalConfigs;
import ConfigFiles.Configurable;
import ConfigFiles.RightClickModeFileConfig;

public class RightClickModeConfigUtils {
	
	private static Configurable RCMConfig = RightClickModeFileConfig.getConfig();
	
	//add user and mode
	public static void addToList(String Username, String mode)
	{
		if(isUserInConfig(Username)){
			deleteUsernameInList(Username);
		}
		UnversalConfigs.getConfig(RCMConfig).getNode("Users", Username).setValue(mode);
		UnversalConfigs.saveConfig(RCMConfig);
	}
	
	//remove a specific user in database
	public static void deleteUsernameInList(Object Username)
	{
		UnversalConfigs.removeChild(RCMConfig, new Object[] {"Users"}, getUserConfigName(Username));
	}
	
	//get all users in database
	public static Set<Object> getUsersInDatabase()
	{
		return UnversalConfigs.getConfig(RCMConfig).getNode("Users").getChildrenMap().keySet();
	}

	//get a specific user's mode in the database
	public static String getUserMode(String Username){
		
		return UnversalConfigs.getConfig(RCMConfig).getNode("Users", Username).getString();
	}
	
	//is the user in the database
	public static boolean isUserInConfig(String Username)
	{
		return getUserConfigName(Username) != null;
	}
	
	//look through the database to find a specific user
	public static String getUserConfigName(Object player)
	{
		Set<Object> Users = getUsersInDatabase();
		
		for(Object User: Users){
			
			if(User.equals(player)){
				
				return User.toString();
			}
		}
		return null;
	}
	
	//remove all of the users in the database
	public static void resetUserDatabase(){
		
		Set<Object> Users = getUsersInDatabase();

		for(Object User: Users){
					
			deleteUsernameInList(User);
		    UnversalConfigs.saveConfig(RCMConfig);		
		}
	}
}

