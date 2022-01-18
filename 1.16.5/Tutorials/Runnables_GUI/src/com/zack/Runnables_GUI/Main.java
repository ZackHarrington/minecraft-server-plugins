package com.zack.Runnables_GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	//private int counter;
	
	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		// Runnables:
		/*
		Bukkit.getScheduler().runTaskLater(this, new Runnable() {
			public void run() {
				System.out.println("Plugin enabled for 5 seconds");			
			}
		}, 100L); // L is ticks
		
		counter = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				System.out.println("Another 10 secs");
			}
		}, 200L, 200L); // Ticks per repeat, ticks until start
		
		Bukkit.getScheduler().cancelTask(counter);
		*/
		
		this.getCommand("menu").setExecutor(new MenuCommand(this));
		
		this.getServer().getPluginManager().registerEvents(new MenuEvents(this), this);
		
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
	}
	
	
	/* ELYTRA */
	public HashMap<Player, ItemStack> chestSlot = new HashMap<>(); // for saving chestplate
	
	/* ELYTRA UI */
	public void applyElytraUI(Player player) {
		
		// BEGINNING
		Inventory gui = Bukkit.createInventory(null, 45, ChatColor.GREEN + "Elytra menu!"); // player, multiple of 9, name
		
		// LORES
		List<String> enableLore = new ArrayList<>();
		enableLore.add(ChatColor.GRAY + "Click me for the");
		enableLore.add(ChatColor.GRAY + "best time evvaa");
		
		List<String> disableLore = new ArrayList<>();
		disableLore.add(ChatColor.GRAY + "Click me for the");
		disableLore.add(ChatColor.GRAY + "worst time ever");
		
		List<String> launchLore = new ArrayList<>();
		launchLore.add(ChatColor.GRAY + "Click to be launched");
		launchLore.add(ChatColor.GRAY + "up 200 blocks! :D");
		
		// ITEMSTACKS
		ItemStack toggle;
		ItemMeta toggleMeta;
		if (player.getInventory().getChestplate() != null && 
				player.getInventory().getChestplate().getType().equals(Material.ELYTRA)) {
			// Disable
			toggle = new ItemStack(Material.REDSTONE_BLOCK); // material, amount, data value (color)
			
			toggleMeta = toggle.getItemMeta();
			toggleMeta.setDisplayName(ChatColor.RED + "Disable Elytra");
			toggleMeta.setLore(disableLore);
		} else {
			// Enable
			toggle = new ItemStack(Material.EMERALD_BLOCK);
			
			toggleMeta = toggle.getItemMeta();
			toggleMeta.setDisplayName(ChatColor.GREEN + "Enable Elytra");
			toggleMeta.setLore(enableLore);
		}
		toggle.setItemMeta(toggleMeta);
		
		ItemStack launch = new ItemStack(Material.COBWEB);
		ItemMeta launchMeta = launch.getItemMeta();
		launchMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Launch into the air!");
		launchMeta.setLore(launchLore);
		launch.setItemMeta(launchMeta);
		
		// ITEM SETTING
		gui.setItem(31, toggle); // Slot (0 1 2 ... \n 9 10 ..... 44), item
		gui.setItem(4, launch); 
		
		// FINAL
		player.openInventory(gui);
		
	}
	
}

