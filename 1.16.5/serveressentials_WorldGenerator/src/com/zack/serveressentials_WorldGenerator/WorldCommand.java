package com.zack.serveressentials_WorldGenerator;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class WorldCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (player.isOp()) {
				
				if (args.length == 1 && (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("t"))) {
					Bukkit.broadcastMessage(ChatColor.YELLOW + "A new world is being loaded, there may be temporary glitches for a few moments");
					Manager.createNewWorld(player, true);
				} else if (args.length == 1 && (args[0].equalsIgnoreCase("false") || args[0].equalsIgnoreCase("f"))) {
					Bukkit.broadcastMessage(ChatColor.YELLOW + "A new world is being loaded, there may be temporary glitches for a few moments");
					Manager.createNewWorld(player, false);
				} else {
					player.sendMessage(ChatColor.RED + "Invalid usage!");
					player.sendMessage(ChatColor.RED + "Use: /generateworld <generate Nether and End>");
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
