package com.zack.Runnables_GUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class MenuEvents implements Listener {

	private Main main;
	
	public MenuEvents(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		// Menu toggle
		event.getPlayer().getInventory().addItem(new ItemStack(Material.NETHER_STAR));
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		// Check for the nether star
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getItem() != null && event.getItem().getType().equals(Material.NETHER_STAR)) {
				event.setCancelled(true); // Stop what was going to happen
				main.applyElytraUI(event.getPlayer());
			}
		}
	}
	
	@EventHandler 
	public void onInventoryClick(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		
		if (ChatColor.translateAlternateColorCodes('&', event.getView().getTitle()).equals(ChatColor.GREEN + "Elytra menu!")) {
			// Better security wise because you cannot add colors in game but you can add names
			if (event.getCurrentItem() != null) {
				event.setCancelled(true);
				
				switch (event.getCurrentItem().getType()) {
				case COBWEB:
					player.setVelocity(new Vector(0, 200, 0));
					
					player.sendMessage("Launched");
					break;
				case EMERALD_BLOCK:
					if (player.getInventory().getChestplate() != null) {
						main.chestSlot.put(player, player.getInventory().getChestplate());
					}
					
					player.getInventory().setChestplate(new ItemStack(Material.ELYTRA));
					
					player.sendMessage("Enabled elytra");					
					break;
				case REDSTONE_BLOCK:
					if (main.chestSlot.containsKey(player)) {
						player.getInventory().setChestplate(main.chestSlot.get(player));
					} else {
						player.getInventory().setChestplate(null);
					}					
					
					player.sendMessage("Disabled elytra");
					break;
				default:
					return;
				}
				
				player.closeInventory();
			}
			
		}
		
		//if (event.getView().getTitle().contains("Elytra menu!")) {
			// Does the same as above
		//}	
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		// Return chestplate before they leave
		if (main.chestSlot.containsKey(event.getPlayer())) {
			event.getPlayer().getInventory().setChestplate(main.chestSlot.get(event.getPlayer()));
		}
	}
	
}
