package com.zack.RideableEntities.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class RideEvent implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event) {
		
		event.getRightClicked().addPassenger(event.getPlayer());
		
		// Kicks player off
		//event.getPlayer().leaveVehicle();
		
	}
	
}
