package com.zack.serveressentials_Clock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CountdownCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (args.length == 2) {
				double seconds = 0;
				
				if (args[0].equalsIgnoreCase("start") ||
						args[0].equalsIgnoreCase("start_m")) {
					seconds = getSeconds(args[1], "minutes", player);
				} else if (args[0].equalsIgnoreCase("start_s")) {
					seconds = getSeconds(args[1], "seconds", player);
				} else if (args[0].equalsIgnoreCase("start_h")) {
					seconds = getSeconds(args[1], "hours", player);
				} else {
					messageInvalidUsage(player);
				}
				
				if (seconds > 0) {
					if (Manager.hasCountdown(player.getUniqueId())) {
						player.sendMessage(ChatColor.RED + "You already have a countdown!");
					} else {
						Manager.startPlayerCountdown(player, seconds);
					}
				}
				
			} else if (args.length == 1 && args[0].equalsIgnoreCase("pause")) {
				if (Manager.hasCountdown(player.getUniqueId())) {
					Manager.pausePlayerCountdown(player.getUniqueId());
				} else {
					player.sendMessage(ChatColor.RED + "You do not have a countdown to pause!");
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("resume")) {
				if (Manager.hasCountdown(player.getUniqueId())) {
					Manager.resumePlayerCountdown(player.getUniqueId());
				} else {
					player.sendMessage(ChatColor.RED + "You do not have a countdown to resume!");
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("restart")) {
				if (Manager.hasCountdown(player.getUniqueId())) {
					Manager.restartPlayerCountdown(player.getUniqueId());
				} else {
					player.sendMessage(ChatColor.RED + "You do not have a countdown to restart!");
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("end")) {
				if (Manager.hasCountdown(player.getUniqueId())) {
					Manager.removePlayerCountdown(player.getUniqueId());
				} else {
					player.sendMessage(ChatColor.RED + "You do not have a countdown to end!");
				}
			} else {
				messageInvalidUsage(player);
			}
			
		} else {
			System.out.print("Console cannot control player clocks");
		}
		
		
		return false;
	}

	public double getSeconds(String argument, String argumentType, Player messager) {
		double seconds = 0;
		
		try {
			switch(argumentType) {
			case "seconds":
				seconds = Double.parseDouble(argument);
				break;
			case "minutes":
				double minutes = Double.parseDouble(argument);
				seconds = minutes * 60;
				break;
			case "hours":
				double hours = Double.parseDouble(argument);
				seconds = hours * 60 * 60;
				break;
			default:
				break;
			}
			
		} catch (NumberFormatException e) {
			messager.sendMessage(ChatColor.RED + "That is not a number! Please use a time in " + argumentType);
		}
		
		return seconds;
	}
	
	public void messageInvalidUsage(Player player) {
		player.sendMessage(ChatColor.RED + "Invalid Usage - these are the options:");
		player.sendMessage(ChatColor.RED + " - /countdown start <time_in_minutes>");
		player.sendMessage(ChatColor.RED + " - /countdown start_s <time_in_seconds>");
		player.sendMessage(ChatColor.RED + " - /countdown start_m <time_in_minutes>");
		player.sendMessage(ChatColor.RED + " - /countdown start_h <time_in_hours>");
		player.sendMessage(ChatColor.RED + " - /countdown pause");
		player.sendMessage(ChatColor.RED + " - /countdown resume");
		player.sendMessage(ChatColor.RED + " - /countdown restart");
		player.sendMessage(ChatColor.RED + " - /countdown end");
	}
	
}
