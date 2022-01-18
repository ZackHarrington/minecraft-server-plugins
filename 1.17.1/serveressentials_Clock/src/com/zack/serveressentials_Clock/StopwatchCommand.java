package com.zack.serveressentials_Clock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class StopwatchCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
				if (Manager.hasStopwatch(player.getUniqueId())) {
					player.sendMessage(ChatColor.RED + "You already have a stopwatch!");
				} else {
					Manager.startPlayerStopwatch(player);
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("pause")) {
				if (Manager.hasStopwatch(player.getUniqueId())) {
					Manager.pausePlayerStopwatch(player.getUniqueId());
				} else {
					player.sendMessage(ChatColor.RED + "You do not have a stopwatch to pause!");
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("resume")) {
				if (Manager.hasStopwatch(player.getUniqueId())) {
					Manager.resumePlayerStopwatch(player.getUniqueId());
				} else {
					player.sendMessage(ChatColor.RED + "You do not have a stopwatch to resume!");
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("reset")) {
				if (Manager.hasStopwatch(player.getUniqueId())) {
					Manager.resetPlayerStopwatch(player.getUniqueId());
				} else {
					player.sendMessage(ChatColor.RED + "You do not have a stopwatch to reset!");
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("end")) {
				if (Manager.hasStopwatch(player.getUniqueId())) {
					player.sendMessage(ChatColor.GREEN + "Total time elapsed: " + 
							Manager.getStopwatchString(Manager.getStopwatchTimeElapsed(player.getUniqueId())));
					Manager.removePlayerStopwatch(player.getUniqueId());
				} else {
					player.sendMessage(ChatColor.RED + "You do not have a stopwatch to end!");
				}
			} else {
				player.sendMessage(ChatColor.RED + "Invalid Usage - these are the options:");
				player.sendMessage(ChatColor.RED + " - /stopwatch start");
				player.sendMessage(ChatColor.RED + " - /stopwatch pause");
				player.sendMessage(ChatColor.RED + " - /stopwatch resume");
				player.sendMessage(ChatColor.RED + " - /stopwatch reset");
				player.sendMessage(ChatColor.RED + " - /stopwatch end");
			}
			
		} else {
			System.out.print("Console cannot control player clocks");
		}
		
		
		return false;
	}

}
