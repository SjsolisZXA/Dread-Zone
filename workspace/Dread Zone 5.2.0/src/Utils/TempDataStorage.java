package Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.type.GridInventory;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Lists;

public class TempDataStorage {
	
	static Map<String, List<ItemStack>> ContestantInventories = new HashMap<String, List<ItemStack>>();
	static Map<String, Location<World>> ContestantOriginalLocations = new HashMap<String, Location<World>>();
	static Map<String, Vector3d> ContestantOriginalRotations = new HashMap<String, Vector3d>();
	
	public static void addContestantReturnPoint(String playerName, Location<World> location, Vector3d rotation){
		
		ContestantOriginalLocations.put(playerName, location);
		ContestantOriginalRotations.put(playerName, rotation);
	}

	public static void returnContestant(Player player) {
		
		player.setLocationAndRotation(ContestantOriginalLocations.get(player.getName()),
				ContestantOriginalRotations.get(player.getName()));
	}
	
	public static void saveInv(Player player){
		
        Inventory inv = player.getInventory();
        
        Hotbar hotbar = inv.query(Hotbar.class);
        GridInventory gridInv = inv.query(GridInventory.class);
        
        List<ItemStack> pHBInv = Lists.newArrayList();
		List<ItemStack> pGInv = Lists.newArrayList();
		List<ItemStack> eInv = Lists.newArrayList();
		
        hotbar.slots().forEach(slot -> {
            if (slot.peek().isPresent()) {
                ItemStack stack = slot.poll().get();
                pHBInv.add(stack);
            }
        });
        
        ContestantInventories.put(player.getName()+"H", pHBInv);
        
        gridInv.slots().forEach(slot -> {
            if (slot.peek().isPresent()) {
                ItemStack stack = slot.poll().get();
                pGInv.add(stack);
            }
        });
        
        ContestantInventories.put(player.getName()+"G", pGInv);
        
        if(player.getHelmet().isPresent()){
        	
        	eInv.add(0,player.getHelmet().get());
        }else {eInv.add(0, null);}
        
        if(player.getChestplate().isPresent()){
        	
        	eInv.add(1,player.getChestplate().get());
        }else {eInv.add(1, null);}
        
        if(player.getLeggings().isPresent()){
        	
        	eInv.add(2,player.getLeggings().get());
        }else {eInv.add(2, null);}
        
        if(player.getBoots().isPresent()){
        	
        	eInv.add(3,player.getBoots().get());
        }else {eInv.add(3, null);}
        
        if(player.getItemInHand(HandTypes.OFF_HAND).isPresent()){
        	
        	eInv.add(4,player.getItemInHand(HandTypes.OFF_HAND).get());
        }else {eInv.add(4, null);}
        
        ContestantInventories.put(player.getName()+"E", eInv);
	}

	public static void restoreInv(Player player){

		List<ItemStack> pHBInv = ContestantInventories.get(player.getName()+"H");
		List<ItemStack> pGInv = ContestantInventories.get(player.getName()+"G");
		List<ItemStack> eInv = ContestantInventories.get(player.getName()+"E");
		
		Inventory inv = player.getInventory();
		
        Hotbar hotbar = inv.query(Hotbar.class);
        GridInventory gridInv = inv.query(GridInventory.class);
		
		int curr = 0;
		int a = 0;
		int b = 0;
		int c = 0;
		
		for(ItemStack itemStack: pHBInv){
			
			hotbar.set(new SlotIndex(curr), itemStack);
			curr++;
		}
		
		curr = 0;
		for(ItemStack itemStack: pGInv){

			if(curr<9){		
		        gridInv.set(a,0, itemStack);
				a++;
			}
			if(curr>8 && curr<18){
				gridInv.set(b,1, itemStack);
				b++;
			}
			if(curr>17 && curr<27){
				gridInv.set(c,2, itemStack);
				c++;
			}
			curr++;
		}
		
		if(eInv.get(0)!=null){
			
			player.setHelmet(eInv.get(0));
		}
		
		if(eInv.get(1)!=null){
			
			player.setChestplate(eInv.get(1));
		}
		
		if(eInv.get(2)!=null){
			
			player.setLeggings(eInv.get(2));
		}
		
		if(eInv.get(3)!=null){
			
			player.setBoots(eInv.get(3));
		}
		
		if(eInv.get(4)!=null){
			
			player.setItemInHand(HandTypes.OFF_HAND, eInv.get(4));
		}
	}
}
