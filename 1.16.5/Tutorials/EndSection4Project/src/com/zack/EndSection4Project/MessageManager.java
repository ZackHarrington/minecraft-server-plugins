package com.zack.EndSection4Project;

import java.util.HashMap;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class MessageManager {
	
	public HashMap<Player, Player> recentlyMessaged = new HashMap<>();
	
	public void sendMessage(Player player, Player target, String message) {
		
		player.sendMessage(ChatColor.GREEN + "-> " + target.getName() + ChatColor.GRAY + " " + message.toString());
		target.sendMessage(ChatColor.GREEN + "<- " + player.getName() + ChatColor.GRAY + " " + message.toString());
		
	}
	
}
