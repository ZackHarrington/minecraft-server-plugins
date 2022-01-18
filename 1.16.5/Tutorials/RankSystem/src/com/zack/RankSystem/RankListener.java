package com.zack.RankSystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;

public class RankListener implements Listener {

	public RankListener() {
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		if (!event.getPlayer().hasPlayedBefore()) {
			Main.getFileManager().setRank(event.getPlayer(), Rank.GUEST);
		}
		
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		
		Player player = event.getPlayer();
		Rank rank = Main.getFileManager().getRank(player);
		event.setCancelled(true);
		
		for (Player onlinePlayers : event.getRecipients()) {
			onlinePlayers.sendMessage(rank.getColor() + rank.getName() + " " + ChatColor.RESET + player.getName() + ": " + event.getMessage());
		}
		
	}
	
}
