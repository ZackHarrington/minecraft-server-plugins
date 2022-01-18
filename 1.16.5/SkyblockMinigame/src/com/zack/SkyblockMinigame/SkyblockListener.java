package com.zack.SkyblockMinigame;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SkyblockListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		event.getPlayer().teleport(Config.getLobbySpawn());
		
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		
		// If the player quits while in their skyblock world
		if (Manager.isInWorld(event.getPlayer().getUniqueId())) {
			Manager.getWorld(event.getPlayer().getUniqueId()).setState(WorldState.OFFLINE);
		}
		
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		
		if (Manager.isInWorld(uuid)) {
			// If the player is teleported away from their skyblock world
			if (event.getFrom().getWorld().equals(Manager.getWorld(uuid).getWorld())) {
				Manager.getWorld(uuid).setState(WorldState.OFFLINE);
			}
			// If the player is teleported to their skyblock world
			if (event.getTo().getWorld().equals(Manager.getWorld(uuid).getWorld())) {
				Manager.getWorld(uuid).setState(WorldState.ACTIVE);
			}
		}
		
	}
	
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		
		Player player = event.getEntity();
		UUID uuid = player.getUniqueId();
		
		if (Manager.isInWorld(uuid)) {
			if (player.getLocation().getWorld().equals(Manager.getWorld(uuid).getWorld())) {
				Manager.getWorld(uuid).setState(WorldState.OFFLINE);
			}
		}
		
		player.teleport(Config.getLobbySpawn());
		
	}
	
}
