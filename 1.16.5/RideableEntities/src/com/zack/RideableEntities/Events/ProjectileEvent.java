package com.zack.RideableEntities.Events;

import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ProjectileEvent implements Listener {

	@EventHandler
	public void onLaunch(ProjectileLaunchEvent event) {
		
		// Get shooter
		//event.getEntity().getShooter();
		
		// Type of projectile
		//event.getEntityType().equals(EntityType.ARROW);
		
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || 
				event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getPlayer().getItemInHand() != null && 
					event.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_HOE)) {
				Egg egg = event.getPlayer().launchProjectile(Egg.class, event.getPlayer().getLocation().getDirection());
				//egg.setVelocity(null);
			}
		}
		
	}
	
}
