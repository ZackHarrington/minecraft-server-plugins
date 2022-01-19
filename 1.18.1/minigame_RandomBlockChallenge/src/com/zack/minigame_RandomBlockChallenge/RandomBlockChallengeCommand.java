package com.zack.minigame_RandomBlockChallenge;

import org.bukkit.ChatColor;
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
			} else if (args.length == 1 && args[0].equalsIgnoreCase("reset")) {
				if (Manager.isPlaying(player))
					Manager.resetChallenge(player);
				else 
					player.sendMessage(ChatColor.RED + "You are not in a challenge");
			} else if (args.length == 1 && args[0].equalsIgnoreCase("idea")) {
				if (Manager.isPlaying(player))
					player.sendMessage(ChatColor.AQUA + "Here's an idea: " + Manager.getIdea());
				else
					player.sendMessage(ChatColor.RED + "You are not in a challenge");
			} else if (args.length == 1 && args[0].equalsIgnoreCase("?")) {
				messageOptions(player, false);
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
		player.sendMessage(ChatColor.RED + " - /randomBlockChallenge reset");
		player.sendMessage(ChatColor.RED + " - /randomBlockChallenge idea");
		player.sendMessage(ChatColor.RED + " - /randomBlockChallenge ?");
	}
	
	
}
