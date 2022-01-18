package com.zack.RideableEntities.Events;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerEvent implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		//Player player = event.getPlayer();
		
		// Needs to be a direct download link and should be in a google drive, Dropbox, etc.
		//player.setResourcePack("");
	}
	
	// Version 1.8+
	@EventHandler
	public void packStatus(PlayerResourcePackStatusEvent event) {
		
		// Event may be called twice under these circumstances:
		//PlayerResourcePackStatusEvent.Status.ACCEPTED;
		//PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED;
		// ^ is how you check if they got the resource pack or not
		
	}
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {
		
		// Sounds
		
		// Way 1 - only plays to player
		event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0F, 0.8F);
		
		// parameters: location, sound, volume(0.0-1.0) ( >1.0 makes it heard farther away), pitch (0.5-2.0) (how fast)
		
		// Way 2 - plays to everyone around the location
		Bukkit.getWorld("world").playSound(event.getPlayer().getLocation(), Sound.AMBIENT_CAVE, 0.7F, 1.0F);
	}
	
}
