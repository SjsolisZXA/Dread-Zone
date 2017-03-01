package Executors;

import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ConfigUtils.ArenaConfigUtils;
import ConfigUtils.ClassConfigUtils;

public class TestExecutor0 implements CommandExecutor {
	
    public CommandResult execute(CommandSource src, CommandContext args) {
    	
        //Player player = (Player)src;     
    	
    	Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.BLUE,"Test child 0"));
    	 
    	//NodeBlockTracker.restoreArenaTerrain();
    	
        /**Scoreboard teamTagName = Scoreboard.builder().build();
        
        Objective teamTagNameObjective = Objective.builder().name("Team").displayName(Text.of(TDMConfigUtils.getRandomTeam())).criterion(Criteria.DUMMY).build();
        
        teamTagName.addObjective(teamTagNameObjective);
        
        teamTagName.updateDisplaySlot(teamTagNameObjective, DisplaySlots.BELOW_NAME);
        
        player.setScoreboard(teamTagName);*/

        
        //crate test scoreboard
        /**Scoreboard scoreboard = Scoreboard.builder().build();
        
        Objective objective = Objective.builder().name("Stats").displayName(Text.of((String.valueOf(player.getName()) + " Stats"))).criterion(Criteria.DUMMY).build();
        
        scoreboard.addObjective(objective);
        
        scoreboard.updateDisplaySlot(objective, DisplaySlots.SIDEBAR);
        
        objective.getOrCreateScore(Text.of("Kills")).setScore(99999);
        
        objective.getOrCreateScore(Text.of("Deaths")).setScore(0);
        
        player.setScoreboard(scoreboard);
        
        //interact with scoreboard
        int score = (player.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get()).getScore(Text.of("Kills")).get().getScore();
        
        player.sendMessage(Text.of(TextColors.GOLD, score));
        
        player.getScoreboard().getObjective(DisplaySlots.SIDEBAR).get().getScore(Text.of("Kills")).get().setScore(score + 1);
        
        player.sendMessage(Text.of(TextColors.GREEN, score));*/
        
        return CommandResult.success();
    }

    public void getClosestEntity(Player player) {
    	
        Entity closest = null;
        
        double closestDistance = 0.0;
        
        for (Entity entity : (player.getLocation().getExtent()).getEntities()) {
        	
            if (entity == player){
            	
            	continue;
            }
            	
            double distance = entity.getLocation().getPosition().distance(player.getLocation().getPosition());
            
            if (closest != null && distance >= closestDistance){
            	
	            closest = entity;
	            closestDistance = distance;
            }
        }
        
        Optional<Text> NPC = closest.get(Keys.DISPLAY_NAME);
        
        if (NPC.isPresent()) {
        	
            Text classN = (Text)NPC.get();
            
            String className = classN.toPlain();
            
            if (ClassConfigUtils.doesClassExist(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation()), className)) {
            	
                closest.offer(Keys.SKIN_UNIQUE_ID, player.getUniqueId());
            }
        }
    }

    public void getClosestPlayer(Player player) {
    	
        Entity closest = null;
        
        double closestDistance = 0.0;
        
        for (Entity entity : (player.getLocation().getExtent()).getEntities()) {
        	
            double distance = entity.getLocation().getPosition().distance(player.getLocation().getPosition());
            
            if (closest != null && distance >= closestDistance){
            	
	            closest = entity;
	            closestDistance = distance;
            }
        }
        Optional<Text> NPC = closest.get(Keys.DISPLAY_NAME);
        
        if (NPC.isPresent()) {
        	
            Text classN = (Text)NPC.get();
            
            String className = classN.toPlain();
            
            if (ClassConfigUtils.doesClassExist(ArenaConfigUtils.getUserArenaNameFromLocation(player.getLocation()), className)) {
            	
                closest.offer(Keys.SKIN_UNIQUE_ID, player.getUniqueId());
            }
        }
    }
}


