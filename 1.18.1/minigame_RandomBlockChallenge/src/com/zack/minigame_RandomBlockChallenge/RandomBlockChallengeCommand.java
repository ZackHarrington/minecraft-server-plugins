package com.zack.minigame_RandomBlockChallenge;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RandomBlockChallengeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if (args.length == 2) {
				try {
					int numBlocks = Integer.parseInt(args[1]);
					if (numBlocks > 18) {
						player.sendMessage(ChatColor.RED + "Alright, thats a bit too many blocks don't ya think, try <18");
						return false;
					}
					
					if (Manager.isPlaying(player))
						player.teleport(new Location(Bukkit.getWorld("world"), 0, 60, 0));
					
					switch (args[0]) {
					case "small":
						Manager.createNewChallenge(player, 's', numBlocks);
						break;
					case "medium":
						Manager.createNewChallenge(player, 'm', numBlocks);
						break;
					case "large":
						Manager.createNewChallenge(player, 'l', numBlocks);
						break;
					default:
						player.sendMessage(ChatColor.RED + "you did not specify a small, medium, or large challenge");
						messageOptions(player, true);
						return false;
					}
				} catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "That is not a number!");
					messageOptions(player, true);
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("newBlocks")) {
				if (Manager.isPlaying(player))
					Manager.newBlocks(player);
				else 
					player.sendMessage(ChatColor.RED + "You are not in a challenge");
			} else if (args.length == 1 && args[0].equalsIgnoreCase("pause")) {
				if (Manager.isPlaying(player))
					Manager.pauseChallenge(player);
				else 
					player.sendMessage(ChatColor.RED + "You are not in a challenge");
			} else if (args.length == 1 && args[0].equalsIgnoreCase("resume")) {
				if (Manager.isPlaying(player))
					Manager.resumeChallenge(player);
				else 
					player.sendMessage(ChatColor.RED + "You are not in a challenge");
			} else if (args.length == 1 && args[0].equalsIgnoreCase("reset")) {
				if (Manager.isPlaying(player))
					Manager.resetChallenge(player);
				else 
					player.sendMessage(ChatColor.RED + "You are not in a challenge");
			} else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
				if (Manager.isPlaying(player)) {
					// Calls end implicitly by teleporting the player
					player.teleport(new Location(Bukkit.getWorld("world"), 0, 60, 0));
				} else 
					player.sendMessage(ChatColor.RED + "You are not in a challenge");
			} else if (args.length == 1 && args[0].equalsIgnoreCase("idea")) {
				player.sendMessage(ChatColor.AQUA + "Here's an idea: " + Manager.getIdea());
			} else if (args.length == 1 && args[0].equalsIgnoreCase("?")) {
				messageOptions(player, false);
			} else if (args.length == 1 && args[0].equalsIgnoreCase("freeBuild")) {
				if (Manager.isPlaying(player)) {
					if (Manager.freeBuild(player)) 
						player.sendMessage(ChatColor.GREEN + "Have fun building!");
					else
						player.sendMessage(ChatColor.RED + "You may only free build after a challenge has been finished");
				} else 
					player.sendMessage(ChatColor.RED + "You are not in a challenge");
			} else if (args.length == 1 && args[0].equalsIgnoreCase("speedRun")) {
				Material material = Material.AIR;
				while (material == Material.AIR || !material.isItem() || 
						material.name().contains("SPAWN_EGG") ||
						material.name().contains("COMMAND_BLOCK") || 
						material.name().contains("END_PORTAL") ||
						material.name().contains("NETHER_PORTAL") ||
						material == Material.BARRIER || material == Material.VOID_AIR) {
					material = Material.values()[Manager.getRandom().nextInt(Material.values().length)];
				}
				player.sendMessage(ChatColor.AQUA + "Your random item to speed run is: " + ChatColor.GOLD + material);
			} else {
				messageOptions(player, true);
			}
			
			
		} else {
			System.out.println("Console cannot run this command");
		}
		
		return false;
	}

	private void messageOptions(Player player, boolean invalid) {
		if (invalid)
			player.sendMessage(ChatColor.RED + "Invalid Usage - these are the options:");
		else 
			player.sendMessage(ChatColor.RED + "These are the command options:");
		player.sendMessage(ChatColor.RED + " - /randomBlockChallenge <small/medium/large> <numberOfBlocks>");
		player.sendMessage(ChatColor.RED + " - /randomBlockChallenge newBlocks");
		player.sendMessage(ChatColor.RED + " - /randomBlockChallenge pause");
		player.sendMessage(ChatColor.RED + " - /randomBlockChallenge resume");
		player.sendMessage(ChatColor.RED + " - /randomBlockChallenge reset");
		player.sendMessage(ChatColor.RED + " - /randomBlockChallenge leave");
		player.sendMessage(ChatColor.RED + " - /randomBlockChallenge idea");
		player.sendMessage(ChatColor.RED + " - /randomBlockChallenge ?");
		player.sendMessage(ChatColor.RED + "Only for if you're interersted in continuing after the time is up:");
		player.sendMessage(ChatColor.RED + " - /randomBlockChallenge freeBuild");
		player.sendMessage(ChatColor.RED + "Also, if you're interested in speedRunning a random item (not challenge related):");
		player.sendMessage(ChatColor.RED + " - /randomBlockChallenge speedRun");
	}
	
	
}
