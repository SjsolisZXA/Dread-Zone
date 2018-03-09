package Utils;

import java.util.List;
import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.FoodData;
import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.player.Player;

public class DZArenaUtils {
	
	public static void setMaxHealth(Player player){
		
		HealthData maxHealth = player.getHealthData().set(Keys.HEALTH, player.get(Keys.MAX_HEALTH).get());
		player.offer(maxHealth);
	}
	
	public static void setMaxFood(Player player){
		
		FoodData maxFood = player.getFoodData().set(Keys.FOOD_LEVEL, 20);
		player.offer(maxFood);
	}
	
	public static void restoreContestant(Player player){
		
        //restore health level
    	setMaxHealth(player);
		
		//restore food level
    	setMaxFood(player);

		//restore clean slate potion effects
		Optional<List<PotionEffect>> potions = player.get(Keys.POTION_EFFECTS);
		
		if(potions.isPresent()){
			
			List<PotionEffect> potionEffects = potions.get();
			potionEffects.clear(); 
			player.offer(Keys.POTION_EFFECTS, potionEffects);
		}
	}
}
