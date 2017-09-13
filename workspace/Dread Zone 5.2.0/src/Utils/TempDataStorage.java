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
import com.google.common.collect.Lists;

public class TempDataStorage {
	
	public static Map<String, List<ItemStack>> ContestantInventories = new HashMap<String, List<ItemStack>>();
	
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
        }
        
        if(player.getChestplate().isPresent()){
        	
        	eInv.add(1,player.getChestplate().get());
        }
        
        if(player.getLeggings().isPresent()){
        	
        	eInv.add(2,player.getLeggings().get());
        }
        
        if(player.getBoots().isPresent()){
        	
        	eInv.add(3,player.getBoots().get());
        }
        
        if(player.getItemInHand(HandTypes.OFF_HAND).isPresent()){
        	
        	eInv.add(4,player.getItemInHand(HandTypes.OFF_HAND).get());
        }
        
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
		
		if(!eInv.get(0).equals(null)){
			
			player.setHelmet(eInv.get(0));
		}
		
		if(!eInv.get(1).equals(null)){
			
			player.setChestplate(eInv.get(1));
		}
		
		if(!eInv.get(2).equals(null)){
			
			player.setLeggings(eInv.get(2));
		}
		
		if(!eInv.get(3).equals(null)){
			
			player.setBoots(eInv.get(3));
		}
		
		if(!eInv.get(4).equals(null)){
			
			player.setItemInHand(HandTypes.OFF_HAND, eInv.get(4));
		}
	}
}
