package Main;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

public class DZCMD implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

		if(!(src instanceof Player)){
			
            src.sendMessage(Text.of(TextColors.DARK_RED, "[", TextColors.DARK_GRAY, "Dread Zone", TextColors.DARK_RED, "] ", 
                    TextColors.WHITE,"This is a user command only!"));
            
			return CommandResult.success();
		}
		
		Player player = (Player)src;
		
		PaginationList.Builder builder = PaginationList.builder();
		
		List<Text> contents = new ArrayList<>();

		if(player.hasPermission(Main.getPluginContainer().getId()+".reload")){
			
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz reload")).append(Text.of("/dz reload",TextColors.WHITE," Reload the Dread Zone configuration.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".ready")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz ready")).append(Text.of("/dz ready",TextColors.WHITE," Ready for combat!")).build());	
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".leave")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz leave")).append(Text.of("/dz leave",TextColors.WHITE," Leave an arena.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".join")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz join")).append(Text.of("/dz join",TextColors.WHITE," Join a Dread Zone Arena.")).build());
		}	
		if(player.hasPermission(Main.getPluginContainer().getId()+".alr")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz alr"))
					.append(Text.of("/dz alr",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz addLightningRod",TextColors.WHITE," Add a lightning rod target.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".acnpc")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz acnpc"))
					.append(Text.of("/dz acnpc",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz addClassNPC",TextColors.WHITE," Add a Dread Zone NPC Class spawnpoint.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".ac")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz ac"))
					.append(Text.of("/dz ac",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz addClass",TextColors.WHITE," Add an arena class.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".ajp")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz ajp"))
					.append(Text.of("/dz ajp",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz addJumpPad",TextColors.WHITE," Add a jump pad.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".aci")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz aci"))
					.append(Text.of("/dz aci",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz addClassItem",TextColors.WHITE," Add an item in hand to an arena class.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".aam")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz aam"))
					.append(Text.of("/dz aam",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz addArenaMode",TextColors.WHITE," Add an arena Mode.")).build());
		}
		/**if(player.hasPermission(Main.getPluginContainer().getId()+".an")){	
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz an"))
					.append(Text.of("/dz an",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz addNode",TextColors.WHITE," Add a capture node.")).build());
		}*/
		if(player.hasPermission(Main.getPluginContainer().getId()+".amc")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz amc"))
					.append(Text.of("/dz amc",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz addMobCrate",TextColors.WHITE," Add a mob crate dropper.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".aasp")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz aasp"))
					.append(Text.of("/dz aas",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz addArenaSpawnPoint",TextColors.WHITE," Set arena team spawnpoints.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".ca")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz ca"))
					.append(Text.of("/dz ca",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz createArena",TextColors.WHITE," Create a Dread Zone arena.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".rci")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz rci"))
					.append(Text.of("/dz rci",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz removeClassItem", TextColors.WHITE," Remove a class item based on the exact item stack in hand.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".rc")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz rc"))
					.append(Text.of("/dz rc",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz removeClass",TextColors.WHITE," Remove a class from an arena.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".rlr")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz rlr"))
					.append(Text.of("/dz rlr",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz removeLightiningRod",TextColors.WHITE," Remove a lightning rod target.")).build());
		}
		/**if(player.hasPermission(Main.getPluginContainer().getId()+".rn")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz rn"))
					.append(Text.of("/dz rn",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz removeNode",TextColors.WHITE," Remove a capture node.")).build());
		}*/
		if(player.hasPermission(Main.getPluginContainer().getId()+".rmc")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz rmc"))
					.append(Text.of("/dz rmc",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz removeMobCreate", TextColors.WHITE," Remove a mob create.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".rmcs")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz rmcs"))
					.append(Text.of("/dz rmcs",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz resetMobCrates",TextColors.WHITE," Reset all mob crates.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".rnds")){
			/**contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz rnds"))
					.append(Text.of("/dz rnds",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz resetNodes",TextColors.WHITE," Reset all capture nodes.")).build());*/
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".al")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz al"))
					.append(Text.of("/dz al",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz arenaList",TextColors.WHITE," Gets a list of all the Dread Zone arenas")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".cl")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz cl"))
					.append(Text.of("/dz cl",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz classList",TextColors.WHITE," Gets a list of all the classes for a specified arena.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".slsp")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz slsp"))
					.append(Text.of("/dz slsp",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz setLobbySpawnPoint",TextColors.WHITE," Set a Dread Zone's lobby spawn point.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".ml")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz ml"))
					.append(Text.of("/dz ml",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz moveLobby",TextColors.WHITE," Re-create a specified Dread Zone arena lobby.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".alr")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz alr"))
					.append(Text.of("/dz PA",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz PointA",TextColors.WHITE," Set PAB point A for an arena.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".am")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz am"))
					.append(Text.of("/dz am",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz arenaModes",TextColors.WHITE," Get a list of all of the avaliable modes for a specified arena.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".ea")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz ea"))
					.append(Text.of("/dz ea",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz editArena",TextColors.WHITE," Edit an arena.")).build());
		}
		if(player.hasPermission(Main.getPluginContainer().getId()+".scl")){
			contents.add(Text.builder().color(TextColors.DARK_RED).onClick(TextActions.runCommand("/dz scl"))
					.append(Text.of("/dz scl",TextColors.WHITE," or ",TextColors.DARK_RED, "/dz setCreditsLocation",TextColors.WHITE," Set the credits location of an arena.")).build());
		}
		builder.title(Text.builder().color(TextColors.DARK_GRAY).append(Text.of(TextColors.DARK_RED,"[",TextColors.DARK_GRAY, "Dread Zone Commands",TextColors.DARK_RED,"]")).build())
		.contents(contents)
		.linesPerPage(20)
		.padding(Text.of("="))
	    .sendTo(player);
		
		return CommandResult.success();
	}

}
