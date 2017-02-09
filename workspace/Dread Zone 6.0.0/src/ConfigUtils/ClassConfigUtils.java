package ConfigUtils;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.Direction;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;

import ConfigFiles.ArenaFileConfig;
import ConfigFiles.Configurable;
import ConfigFiles.UnversalConfigs;
import Utils.EntitySpawner;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ClassConfigUtils {

	private static Configurable classConfig = ArenaFileConfig.getConfig();
	
	
	public static void addItemToClass(Object arenaName, Object className, ItemStack itemStack) throws ObjectMappingException
	{
		int numOfItems = getNumOfClassItems(arenaName, className);

		for(int i=1; i<=numOfItems; i++){
			
			ConfigurationNode targetNode = UnversalConfigs.getConfig(classConfig).getNode("Arena" , arenaName , "ArenaClasses" , className , "items", "item "+i);
			
			if(targetNode.getValue().equals("empty")){

				UnversalConfigs.getConfig(classConfig).getNode("Arena" , arenaName , "ArenaClasses" , className , "items", "item "+i).setValue(TypeToken.of(ItemStack.class),itemStack);
				
				UnversalConfigs.saveConfig(classConfig);
				
				return;
			}
		}
	}

	public static List<ItemStack> getClassItems(Object arenaName, Object className) throws ObjectMappingException{
		
		ConfigurationNode targetClass = UnversalConfigs.getConfig(classConfig).getNode("Arena" , arenaName , "ArenaClasses" , className , "items");
		
		List<ItemStack> itemList = Lists.newArrayList();
		
		int numOfItems = getNumOfClassItems(arenaName, className);
		
		for(int i = 1; i<=numOfItems; i++){
			
			String itemTarget = targetClass.getNode("item "+i).getValue().toString();
			
			if(!itemTarget.equals("empty")){
				
				ItemStack item = targetClass.getNode("item "+i).getValue(TypeToken.of(ItemStack.class));
				
				itemList.add(item);
			}
		}
		
		return itemList;
	}
	
	public static void offerClass(Player player, List<ItemStack> list){
		
		player.getInventory().clear();
		
		for(ItemStack item: list){
			
			/*if(player.canEquip(EquipmentTypes.ANY, item)){
			
				if(player.canEquip(EquipmentTypes.HEADWEAR, item)){
					
					player.setHelmet(item);
				}
				
				if(player.canEquip(EquipmentTypes.CHESTPLATE, item)){
					
					player.setChestplate(item);
				}
				
				if(player.canEquip(EquipmentTypes.LEGGINGS, item)){
					
					player.setLeggings(item);
				}
				
				if(player.canEquip(EquipmentTypes.BOOTS, item)){
					
					player.setBoots(item);
				}
			}else{*/
				
				player.getInventory().offer(item);
			//}			
		}
	}
	
	public static void addClass(String arenaName, String className, int numOfItems)
	{	
		for(int i = 1; i<=numOfItems; i++){
			
			UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName ,"ArenaClasses",className, "items", "item "+i).setValue("empty");
			
			UnversalConfigs.saveConfig(classConfig);
		}
	}
	
	public static int getNumOfClasses(Object arenaName){
		
		Set<Object> arenaClasses = getArenaClasses(arenaName);
		
		int NOAC = 0;
		
		for(@SuppressWarnings("unused") Object AC:arenaClasses){
			
			NOAC++;
		}
		return NOAC;
	}
	
	public static int getNumOfClassItems(Object arenaName, Object className){
		
		Set<Object> arenaClassItems = getArenaClassItems(arenaName,className);
		
		int NOACI = 0;
		
		for(@SuppressWarnings("unused") Object ACI: arenaClassItems){
		
			NOACI++;
		}
		return NOACI;
	}
	
	public static int getNumOfNoneEmptyClassItems(Object arenaName, Object className){
		
		Set<Object> arenaClassItems = getArenaClassItems(arenaName,className);
		
		int NONECI = 0;
		
		for(Object ACI: arenaClassItems){
			
			if(!getArenaClassItemData(arenaName,className,ACI).equals("empty")){
				
				NONECI++;
			}
		}
		return NONECI;
	}
	
	public static Object getClassItem(Object arenaName, Object className, Object itemNumber){
		
		Set<Object> arenaClassItems = getArenaClassItems(arenaName,className);
		
		for(Object cItem : arenaClassItems){
			
			if( cItem.equals(itemNumber)){
				
				return cItem;
			}
		}
		return null;
	}
	
	public static void removeClassItem(String arenaName, Object className, ItemStack itemStack) throws ObjectMappingException{
		
		ConfigurationNode targetClass = UnversalConfigs.getConfig(classConfig).getNode("Arena" , arenaName , "ArenaClasses" , className , "items");
		
		int numOfItems = getNumOfClassItems(arenaName, className);
		
		for(int i = 1; i<=numOfItems; i++){
			
			String itemTarget = targetClass.getNode("item "+i).getValue().toString();
			
			if(!itemTarget.equals("empty")){
				
				ItemStack item = targetClass.getNode("item "+i).getValue(TypeToken.of(ItemStack.class));
				
				if(item.equalTo(itemStack)||item.toString().equals(itemStack.toString())){
					
					UnversalConfigs.removeChild(classConfig, new Object[] {"Arena", arenaName, "ArenaClasses", className, "items"}, getClassItem(arenaName,className,"item "+i));
					
					UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName ,"ArenaClasses",className, "items", "item "+i).setValue("empty");
					
					UnversalConfigs.saveConfig(classConfig);
				}
			}
		}
	}
	
	public static Set<Object> getArenaClasses(Object arenaName)
	{
		return UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName, "ArenaClasses").getChildrenMap().keySet();
	}
	
	public static Set<Object> getArenaClassItems(Object arenaName, Object className)
	{
		return UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName, "ArenaClasses",className,"items").getChildrenMap().keySet();
	}
	
	public static Object getArenaClassItemData(Object arenaName, Object className, Object itemNumber)
	{
		return UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName, "ArenaClasses", className,"items", itemNumber).getValue();
	}
	
	public static Object doesClassItemExist(Object arenaName, Object className, ItemStack itemStack) throws ObjectMappingException{
		
		List<ItemStack> items = getClassItems(arenaName, className);
		
		for(ItemStack item: items){			

			Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GREEN,item.equalTo(itemStack)));
			
			Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE,item.toString().equals(itemStack.toString())));
			
			if(item.equalTo(itemStack)||item.toString().equals(itemStack.toString())){
				
				return true;
			}
		}
		
		return null;
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
	
	public static void addClassNPC(Transform<World> transform, String arenaName, String className) throws ObjectMappingException {
		
		int lx =  transform.getLocation().getBlockX();
		int ly =  transform.getLocation().getBlockY();
		int lz =  transform.getLocation().getBlockZ();

		UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName, "ArenaClasses", className, "NPC", "X").setValue(lx);
		UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName, "ArenaClasses", className, "NPC", "Y").setValue(ly);
		UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName, "ArenaClasses", className, "NPC", "Z").setValue(lz);
		
		int rx = (int)transform.getRotation().getX();
		int ry = (int)transform.getRotation().getY();
		int rz = (int)transform.getRotation().getZ();
		
		Vector3d V3d = new Vector3d(rx, ry, rz);
		
		UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName, "ArenaClasses", className, "NPC", "Direction").setValue(TypeToken.of(Direction.class),Direction.getClosest(V3d));
																																
		UnversalConfigs.saveConfig(classConfig);
	}
	
	public static Location<World> getClassNPCLocation(String arenaName, String className) {

		int x = (int) UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName, "ArenaClasses", className, "NPC", "X").getValue();
		int y = (int) UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName, "ArenaClasses", className, "NPC", "Y").getValue();
		int z = (int) UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName, "ArenaClasses", className, "NPC", "Z").getValue();
		
		String worldName = UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName, arenaName+"Lobby","World").getString();
		
		World world = Sponge.getServer().getWorld(worldName).orElse(null);
		
		return new Location<World>(world,x, y, z);
	}
	
	public static Direction getClassNPCDirection(String arenaName, String className) throws ObjectMappingException {
		
		Direction direction = (Direction) UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName, "ArenaClasses", className, "NPC", "Direction").getValue(TypeToken.of(Direction.class));
		
		return direction;
	}

	public static int getNumOfSetNPCClasses(String arenaName) throws ObjectMappingException {
		
		int numOfSetNPCs = 0;
		
		Set<Object> arenaClasses = getArenaClasses(arenaName);
		
		for(Object arenaClass: arenaClasses){
			
			if(UnversalConfigs.getConfig(classConfig).getNode("Arena", arenaName, "ArenaClasses", arenaClass, "NPC", "Direction").getValue(TypeToken.of(Direction.class))!=null){
				
				numOfSetNPCs++;
			}
		}
		
		return numOfSetNPCs;
	}

	public static void spawnArenaNPCs(UUID uuid, String arenaName) throws ObjectMappingException {
		
		Set<Object> classes = getArenaClasses(arenaName);
		
		for(Object aClass: classes){
			
			EntitySpawner.spawnNPC(getClassNPCLocation(arenaName, aClass.toString()), getClassNPCDirection(arenaName, aClass.toString()), EntityTypes.HUMAN, uuid, aClass.toString(), getClassItems(arenaName,aClass));
		}
	}
}
