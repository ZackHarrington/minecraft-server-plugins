package com.zack.EndSection4Project;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ReplyCommand implements CommandExecutor {

private Main main;
	
	public ReplyCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player) sender;
		if (args.length > 0) {
			if (main.getMessageManager().recentlyMessaged.containsKey(player)) {
				if (main.getMessageManager().recentlyMessaged.get(player) != null) {
				
					Player target = main.getMessageManager().recentlyMessaged.get(player);
				
					StringBuilder message = new StringBuilder();
					for (int i = 1; i < args.length; i++) { // i = 1, starts after the player argument
						message.append(args[i]).append(" ");
					}
				
					main.getMessageManager().sendMessage(player, target, message.toString());
				
				} else {
					player.sendMessage(ChatColor.RED + "Player is no longer online!");
				}			
			} else {
				player.sendMessage(ChatColor.RED + "You have not messaged anyone recently.");
			}
		} else {
			player.sendMessage(ChatColor.RED + "Invalid Usage! /reply <message>");
		}
		
		
		return false;
	}

}
