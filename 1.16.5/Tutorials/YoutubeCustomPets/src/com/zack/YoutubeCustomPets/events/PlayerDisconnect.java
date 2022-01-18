package com.zack.YoutubeCustomPets.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.zack.YoutubeCustomPets.Main;

public class PlayerDisconnect implements Listener {

	private Main plugin;
	public PlayerDisconnect(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		// Check player has a pet
		if (plugin.pets.get(e.getPlayer()) != null) {
			// Remove pet from the game
			plugin.pets.get(e.getPlayer()).getEnt().remove();
			plugin.pets.remove(e.getPlayer());
		}
	}
	
}
