package com.zack.PlayerCompass;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class CompassListener implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || 
				event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (player.getInventory().getItemInMainHand().equals(new ItemStack(Material.COMPASS))) {
				if (Manager.containsSeeker(player.getUniqueId())) {
					// Target is in the same world as the seeker
					if (Manager.getTargetLocation(player.getUniqueId()).getWorld().getName().equals(player.getWorld().getName())) {
						Manager.stopSpin(player.getUniqueId());
						player.setCompassTarget(Manager.getTargetLocation(player.getUniqueId()));
					} else {
						Manager.spinCompass(player.getUniqueId());
					}
					
					player.sendMessage(ChatColor.GREEN + "Compass updated! Targeting " + 
					ChatColor.YELLOW + Manager.getTarget(player.getUniqueId()).getName());
				}
			}
		}
		
	}
	
}
