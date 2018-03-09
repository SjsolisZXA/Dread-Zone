package Utils;

import org.spongepowered.api.item.inventory.Inventory;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class InventoryFetcher implements TypeSerializer<Inventory>{

	public static void saveInventory(){
		
		//Main.Main.configurationLoader.getDefaultOptions().getSerializers().registerType(TypeToken.of(this));
	}

	@Override
	public Inventory deserialize(com.google.common.reflect.TypeToken<?> type, ConfigurationNode value)
			throws ObjectMappingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void serialize(com.google.common.reflect.TypeToken<?> type, Inventory obj, ConfigurationNode value)
			throws ObjectMappingException {
		// TODO Auto-generated method stub
		
	}
}
