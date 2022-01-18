package com.zack.ZombieApocalypse;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ZombieApocalypseCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
				if (Manager.beginZombieApocalypse(player.getWorld())) {
					player.sendMessage(ChatColor.GREEN + "The zombie apocalypse has begun");
				} else {
					player.sendMessage(ChatColor.RED + "There is already an apocalypse happening in this world");
				}
				
			} else if (args.length == 2 && args[0].equalsIgnoreCase("end")) {
				if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("t")) {
					if (Manager.endZombieApocalypse(player.getWorld(), true)) {
						player.sendMessage(ChatColor.GREEN + "The zombie apocalypse is over and the zombies have been killed");
					} else {
						player.sendMessage(ChatColor.RED + "No zombie apocalypse is currently happening in this world");
					}
				} else if (args[1].equalsIgnoreCase("false") || args[1].equalsIgnoreCase("f")) {
					if (Manager.endZombieApocalypse(player.getWorld(), false)) {
						player.sendMessage(ChatColor.GREEN + "The zombie apocalypse is over but the remaining zombies are still lurking");
					} else {
						player.sendMessage(ChatColor.RED + "No zombie apocalypse is currently happening in this world");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Invalid Usage - these are the options");
					player.sendMessage(ChatColor.RED + " - /zombieapocalypse start");
					player.sendMessage(ChatColor.RED + " - /zombieapocalypse end <kill remaining zombies>");
				}
				
			} else {
				player.sendMessage(ChatColor.RED + "Invalid Usage - these are the options");
				player.sendMessage(ChatColor.RED + " - /zombieapocalypse start");
				player.sendMessage(ChatColor.RED + " - /zombieapocalypse end <kill remaining zombies>");
			}
			
		} else {
			System.out.println("Console cannot start a Zombie Apocalypse");
		}
		
		return false;
	}

}
