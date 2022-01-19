package com.zack.serveressentials_WorldGenerator;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class WorldGeneratorListener implements Listener {
	
	@EventHandler
	public void onPortal(PlayerPortalEvent event) {
		Location from = event.getFrom();
	    Player player = event.getPlayer();

	    // The player is teleporting from a generated world
		if (Manager.isTmpWorld(from.getWorld()) || 
				Manager.isTmpWorldNether(from.getWorld()) || 
				Manager.isTmpWorldEnd(from.getWorld())) {
			
			if (event.getCause() == TeleportCause.NETHER_PORTAL) {
				event.setCanCreatePortal(true);
					
	           	Location location = null;
	           	if (Manager.isTmpWorld(from.getWorld())) { // Teleported to the nether
	           		if (Manager.hasNether(from.getWorld())) {
	           			location = new Location(Manager.getTmpNether(from.getWorld()), 
	           					from.getBlockX() / 8, 
	           					from.getBlockY(), 
	           					from.getBlockZ() / 8);
	           		} else {
						event.setCancelled(true);
						player.sendMessage(ChatColor.RED + "This world does not have a nether");
					}
	           	} else if (Manager.isTmpWorldNether(from.getWorld())) { // Teleported from the nether
	            	location = new Location(Manager.getTmpWorldFromNether(from.getWorld()), 
	            			from.getBlockX() * 8, 
	            			from.getBlockY(), 
	            			from.getBlockZ() * 8);
	           	}
	           	event.setTo(location);
	       	} else if (event.getCause() == TeleportCause.END_PORTAL) {
	           	if (Manager.isTmpWorld(from.getWorld())) { // Teleported to the end
	           		if (Manager.hasEnd(from.getWorld())) {
	           			Location loc = new Location(Manager.getTmpEnd(from.getWorld())
	           					, 100, 50, 0); // This is the vanilla location for obsidian platform.
	               		event.setTo(loc);
	               		// Build platform
	               		Block block = loc.getBlock();
	               		for (int x = block.getX() - 2; x <= block.getX() + 2; x++) {
	               			for (int z = block.getZ() - 2; z <= block.getZ() + 2; z++) {
	                       		Block platformBlock = loc.getWorld().getBlockAt(x, block.getY() - 1, z);
	                       		if (platformBlock.getType() != Material.OBSIDIAN) {
	                           		platformBlock.setType(Material.OBSIDIAN);
	                       		}
	                       		for (int yMod = 1; yMod <= 3; yMod++) {
	                           		Block b = platformBlock.getRelative(BlockFace.UP, yMod);
	                           		if (b.getType() != Material.AIR) {
	                               		b.setType(Material.AIR);
	                           		}
	                       		}
	                   		}
	               		}
	           		} else {
	           			event.setCancelled(true);
	           			player.sendMessage(ChatColor.RED + "This world does not have a end");
	           		}
	           	} else if (Manager.isTmpWorldEnd(from.getWorld())) { // Teleported from the end
	               	event.setTo(Manager.getTmpWorldFromEnd(from.getWorld()).getSpawnLocation());
	           	}
	        }
		}
	}
	
	@EventHandler
	public void onPlayerDeath (PlayerDeathEvent event) {
		
		Player player = event.getEntity();
		World world = player.getLocation().getWorld();
		
		if (Manager.isTmpWorld(world)) { // Player died in generated world
			// If the player doesn't already have a set spawn in the world
			if (!player.getBedSpawnLocation().getWorld().equals(world)) {
				player.teleport(world.getSpawnLocation());			
			}
		} else if (Manager.isTmpWorldNether(world)) { // Player died in the generated world's nether
			// If the player doesn't already have a set spawn in the world
			if (!player.getBedSpawnLocation().getWorld().equals(Manager.getTmpWorldFromNether(world))) {
				player.teleport(Manager.getTmpWorldFromNether(world).getSpawnLocation());			
			}
		} else if (Manager.isTmpWorldEnd(world)) { // Player died in the generated world's end
			// If the player doesn't already have a set spawn in the world
			if (!player.getBedSpawnLocation().getWorld().equals(Manager.getTmpWorldFromEnd(world))) {
				player.teleport(Manager.getTmpWorldFromEnd(world).getSpawnLocation());			
			}
		}		
		
	}
	
}
