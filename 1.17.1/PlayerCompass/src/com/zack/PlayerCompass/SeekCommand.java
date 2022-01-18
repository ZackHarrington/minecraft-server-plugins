package com.zack.PlayerCompass;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class SeekCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (args.length == 1) {
				if (Bukkit.getPlayer(args[0].toString()) != null) {
					Player target = Bukkit.getPlayer(args[0].toString());
					
					if (!target.equals(player)) {
						Manager.addTarget(player.getUniqueId(), target.getUniqueId());
						player.sendMessage(ChatColor.GREEN + "Your Compass will now seek " + 
							ChatColor.AQUA + target.getName() + ChatColor.GREEN + "!");
						player.sendMessage(ChatColor.GREEN + "Right Click your compass to target it to " + 
							ChatColor.AQUA + target.getName() + ChatColor.GREEN + "'s most recent location!");
					} else {
						player.sendMessage(ChatColor.RED + "You cannot target yourself!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Not a valid player!");
				}
			} else {
				player.sendMessage(ChatColor.RED + "Invalid usage!");
				player.sendMessage(ChatColor.RED + "Use /seek <player>");
			}
		} else {
			System.out.println("Console may not use this command!");
		}
		
		return false;
	}

}
