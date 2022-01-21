package com.zack.randomGameChanges_ExplosiveEntities;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ExplosiveEntitiesListener implements Listener {

	// Addition
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		if (Manager.isExplodingWorld(player.getWorld())) {
			
			for (Entity entity : player.getNearbyEntities(3, 3, 3)) {
				if (!Manager.isExploding(entity.getUniqueId())) {
					Manager.addEntity(entity);
				}
			}
			
		}
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		Entity item = event.getItemDrop();
		
		if (Manager.isExplodingWorld(player.getWorld())) {
			Manager.addEntity(item);
		}
	}
	
	// Removal
	@EventHandler 
	public void onEntityDeath(EntityDeathEvent event) { 
		Entity entity = event.getEntity();
		
		if (Manager.isExploding(entity.getUniqueId()))
			Manager.removeEntity(entity.getUniqueId()); 
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		Entity entity = event.getEntity();
		
		if (Manager.isExploding(entity.getUniqueId()))
			Manager.removeEntity(entity.getUniqueId()); 
	}
	
	@EventHandler
	public void onEntityCombust(EntityCombustEvent event) {
		Entity entity = event.getEntity();
		
		if (Manager.isExploding(entity.getUniqueId()))
			Manager.removeEntity(entity.getUniqueId()); 
	}
	
	@EventHandler 
	public void onPickUp(EntityPickupItemEvent event) {
		Entity item = event.getItem();
		
		if (Manager.isExploding(item.getUniqueId()))
			Manager.removeEntity(item.getUniqueId());
	}
	
}
