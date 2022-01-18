package com.zack.serveressentials_Clock;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ClockListener implements Listener {

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.hasStopwatch(player.getUniqueId())) {
			Manager.removePlayerStopwatch(player.getUniqueId());
		}
		
		if (Manager.hasCountdown(player.getUniqueId())) {
			Manager.removePlayerCountdown(player.getUniqueId());
		}
	}
	
}
