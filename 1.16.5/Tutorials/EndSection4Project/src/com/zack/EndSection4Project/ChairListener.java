package com.zack.EndSection4Project;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.spigotmc.event.entity.EntityDismountEvent;

public class ChairListener implements Listener{
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && 
				event.getHand().equals(EquipmentSlot.HAND) &&
				(event.getClickedBlock().getType().name().contains("STAIRS") || 
				event.getClickedBlock().getType().name().contains("SLAB"))) {
			// Cancel other event
			event.setCancelled(true);
			
			// Make the person sit
			if (!Main.isOccupied(event.getClickedBlock())) {
				Main.sit(event.getPlayer(), event.getClickedBlock());
			}
		}
		
	}
	
	@EventHandler
	public void onEntityDismount(EntityDismountEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			if (Main.isSitting(player)) { // protects from messing with horse dismounting
				Main.unsit(player);
			}
		}
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) { // Called any time a player is teleported, changes dimensions, is killed etc.
		Player player = event.getPlayer();
		
		if (Main.isSitting(player)) {
			Main.unsit(player);
		}
	}
	
	@EventHandler
	public void onPlayeDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		
		if (Main.isSitting(player)) {
			Main.unsit(player);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		if (Main.isOccupied(event.getBlock())) {
			Main.unsit(event.getBlock());
		}
		
	}
}
