package com.zack.serveressentials_WorldGenerator.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zack.serveressentials_WorldGenerator.Manager;

import net.md_5.bungee.api.ChatColor;

public class UnloadWorldCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (player.isOp()) {
				
				if (args.length == 1 && args[0].equals("all")) {
					Manager.unloadTmpWorlds(false);
					player.sendMessage(ChatColor.GREEN + "All temporary worlds have been unloaded");
				} else if (args.length == 1 && Integer.getInteger(args[0]) != null) {
					if (Manager.isTmpWorld(Integer.getInteger(args[0]))) {
						Manager.unloadTmpWorld(Integer.getInteger(args[0]), false);
						player.sendMessage(ChatColor.GREEN + "The specified temporary world has been unloaded");
					} else {
						player.sendMessage(ChatColor.RED + "The specified temporary world does not exist");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Invalid usage! Use:");
					player.sendMessage(ChatColor.RED + "/unloadWorld <number>");
					player.sendMessage(ChatColor.RED + "/unloadWorld all");
				}
				
			} else {
				player.sendMessage(ChatColor.RED + "You are not a server operator");
			}
		} else {
			System.out.println("Console cannot run this command at this time");
		}		
		
		return false;
	}

}
