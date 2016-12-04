package Listeners;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DisplaceEntityEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import ConfigFiles.UnversalConfigs;
import ConfigFiles.Configurable;
import ConfigFiles.MobCrateFileConfig;
import ConfigUtils.MobCreateConfigUtils;
import Utils.MobCrateSpawner;

public class MobCreatePlayerDetector {
	private static Configurable mobCreateConfig = MobCrateFileConfig.getConfig();
	
	@Listener
	public void onPlayerNearXYZ(DisplaceEntityEvent.TargetPlayer e,@First Player player){
		
		Set<Object> targets = MobCreateConfigUtils.getTargets();

		Vector3i Plocation = (player.getLocation().getPosition().toInt());
		
		Location<World> location = new Location<>(player.getWorld(), Plocation.toDouble());
		
		for(Object object: targets){
			
			Set<Object> children = MobCreateConfigUtils.getTargetChildren(object);
			
			for(Object child: children){
				
				String group = object.toString();
				
				String target = child.toString();
				
				Location<World> CL = MobCreateConfigUtils.getTarget(group,target);
			    		
				if(location.equals(CL.add(-8,0,0))||
					location.equals(CL.add(-8,0,-1))||
					location.equals(CL.add(-8,0,-2))||
					location.equals(CL.add(-7,0,-3))||
					location.equals(CL.add(-7,0,-4))||
					location.equals(CL.add(-6,0,-5))||
					location.equals(CL.add(-5,0,-6))||
					location.equals(CL.add(-4,0,-7))||
					location.equals(CL.add(-3,0,-7))||
					location.equals(CL.add(-2,0,-8))||
					location.equals(CL.add(-1,0,-8))||
					location.equals(CL.add(0,0,-8))||
					
					location.equals(CL.add(8,0,0))||
					location.equals(CL.add(8,0,-1))||
					location.equals(CL.add(8,0,-2))||
					location.equals(CL.add(7,0,-3))||
					location.equals(CL.add(7,0,-4))||
					location.equals(CL.add(6,0,-5))||
					location.equals(CL.add(5,0,-6))||
					location.equals(CL.add(4,0,-7))||
					location.equals(CL.add(3,0,-7))||
					location.equals(CL.add(2,0,-8))||
					location.equals(CL.add(1,0,-8))||
					
					location.equals(CL.add(8,0,0))||
					location.equals(CL.add(8,0,1))||
					location.equals(CL.add(8,0,2))||
					location.equals(CL.add(7,0,3))||
					location.equals(CL.add(7,0,4))||
					location.equals(CL.add(6,0,5))||
					location.equals(CL.add(5,0,6))||
					location.equals(CL.add(4,0,7))||
					location.equals(CL.add(3,0,7))||
					location.equals(CL.add(2,0,8))||
					location.equals(CL.add(1,0,8))||
					
					location.equals(CL.add(-8,0,1))||
					location.equals(CL.add(-8,0,2))||
					location.equals(CL.add(-7,0,3))||
					location.equals(CL.add(-7,0,4))||
					location.equals(CL.add(-6,0,5))||
					location.equals(CL.add(-5,0,6))||
					location.equals(CL.add(-4,0,7))||
					location.equals(CL.add(-3,0,7))||
					location.equals(CL.add(-2,0,8))||
					location.equals(CL.add(-1,0,8))||
					location.equals(CL.add(0,0,8))){
					
					for(Object groupChildren: children){
						
						String GC = groupChildren.toString();
						
						if(UnversalConfigs.getConfig(mobCreateConfig).getNode("mobCreate",object,groupChildren,"Status").getBoolean()==true){
							
						    UnversalConfigs.getConfig(mobCreateConfig).getNode("mobCreate", object,groupChildren, "Status").setValue(false);
						    UnversalConfigs.saveConfig(mobCreateConfig);
					
						    MobCrateSpawner.spawnMobDropper(MobCreateConfigUtils.getTarget(group,GC),ThreadLocalRandom.current().nextInt(6));  
						}
					}
					return;
				}
			}
		}
	}
}
