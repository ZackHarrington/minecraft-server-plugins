package com.zack.serveressentials_WorldGenerator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class WorldNameCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			
			player.sendMessage(ChatColor.GREEN + "The world you are in is named " + 
					ChatColor.WHITE + player.getLocation().getWorld().getName());
			
		} else {
			System.out.println("Console cannot use this command");
		}
		
		return false;
	}

}
