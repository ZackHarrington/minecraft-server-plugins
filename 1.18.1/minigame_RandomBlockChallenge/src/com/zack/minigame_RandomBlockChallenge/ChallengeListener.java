package com.zack.minigame_RandomBlockChallenge;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class ChallengeListener implements Listener {
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		
		// If the player quits while in their skyblock world
		if (Manager.isPlaying(event.getPlayer())) {
			Manager.endChallenge(event.getPlayer());
		}
		
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player)) {
			// If the player is teleported away from their world
			if (!event.getTo().getWorld().equals(Manager.getChallenge(player).getWorld())) {
				Manager.endChallenge(player);
			}
		}
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		
		Player player = event.getEntity();
		
		if (Manager.isPlaying(player)) {
			Manager.endChallenge(player);
		}
		
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player)) {
			// See if the player can place blocks
			if(!Manager.getChallenge(player).getCanPlaceBlocks())
				event.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onPlace(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player)) {
			// See if the player can break blocks
			if(!Manager.getChallenge(player).getCanPlaceBlocks())
				event.setCancelled(true);
		}
		
	}
	
	
}
