package com.zack.RankSystem;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.EnumUtils;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class RankCommand implements CommandExecutor {

	// setrank <player> <rank>
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player player = (Player) sender;
		
		// How to allow only certian ranks to use certian commands
		//if (Main.getFileManager().getRank(player).equals(Rank.OWNER)) {
		//
		//}
		
		
		if (player.isOp()) {
			if (args.length == 2) {
				if (Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
					if (EnumUtils.isValidEnum(Rank.class, args[1].toUpperCase())) {
						Main.getFileManager().setRank(Bukkit.getOfflinePlayer(args[0]).getUniqueId(), Rank.valueOf(args[1].toUpperCase()));
						player.sendMessage(ChatColor.GREEN + "Rank has been changed!");
						
						if (Bukkit.getOfflinePlayer(args[0]).isOnline()) {
							Bukkit.getOfflinePlayer(args[0]).getPlayer().sendMessage(player.getName() + " just changed your rank to " + args[1]);
						}
							
					} else {
						player.sendMessage(ChatColor.RED + "This rank does not exist!");
					}
					
				} else {
					player.sendMessage(ChatColor.RED + "They have not played on this server before!");
				}
				
			} else {
				player.sendMessage(ChatColor.RED + "Invalid! Use: /setrank <player> <rank>");
			}
			
		} else {
			player.sendMessage(ChatColor.RED + "Not an OP loser!");
		}
		
		
		return false;
	}
	
}
