package com.zack.MOTD_Boss_Holograms;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ToggleListener implements Listener {
	
	ArrayList<Player> enabled = new ArrayList<>();
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		
		if (player.getItemInHand().getType().equals(Material.NETHER_STAR)) {
			if (enabled.contains(player)) { // In enabled
				player.sendMessage("Disabled");
				enabled.remove(player);
			} else {
				player.sendMessage("Enabled");
				enabled.add(player);
			}
		}
		
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		
		if (enabled.contains(e.getPlayer())) {
			// Stops the player from chatting
			e.setCancelled(true);
			e.getPlayer().sendMessage("Disabled.");
		}
		
	}

}
