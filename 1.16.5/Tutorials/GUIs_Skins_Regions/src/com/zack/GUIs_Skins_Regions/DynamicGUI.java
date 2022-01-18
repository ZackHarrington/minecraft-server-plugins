package com.zack.GUIs_Skins_Regions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class DynamicGUI {

	public DynamicGUI(Player player, int page) {
		
		// null, number of spaces (max 54, multiples of 9), name
		Inventory gui = Bukkit.createInventory(null, 54, "GUI Page - " + page);
		
		
		// Create items in GUI
		List<ItemStack> allItems = new ArrayList<>();
		for (int i = 0; i < 130; i++) {
			allItems.add(new ItemStack(Material.FROSTED_ICE));
		}
		
		
		// Set page change items
		ItemStack left, right;
		ItemMeta leftMeta, rightMeta;
		
		if (PageUtil.isPageValid(allItems, page - 1, 52)) {
			left = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
			leftMeta = left.getItemMeta();
			leftMeta.setDisplayName(ChatColor.GREEN + "Previous Page");
		} else {
			left = new ItemStack(Material.RED_STAINED_GLASS_PANE);
			leftMeta = left.getItemMeta();
			leftMeta.setDisplayName(ChatColor.RED + "No Previous Page");
		}
		// Use localized name to store page number because it can't be seen by the player
		leftMeta.setLocalizedName(page + "");
		left.setItemMeta(leftMeta);
		
		gui.setItem(0, left);
		
		if (PageUtil.isPageValid(allItems, page + 1, 52)) {
			right = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
			rightMeta = right.getItemMeta();
			rightMeta.setDisplayName(ChatColor.GREEN + "Next Page");
		} else {
			right = new ItemStack(Material.RED_STAINED_GLASS_PANE);
			rightMeta = right.getItemMeta();
			rightMeta.setDisplayName(ChatColor.RED + "No Next Page");
		}
		right.setItemMeta(rightMeta);
		
		gui.setItem(8, right);
		
		
		// Assign items to correct pages
		for (ItemStack item : PageUtil.getPageItems(allItems, page, 52)) {
			gui.setItem(gui.firstEmpty(), item);
		}
		
		player.openInventory(gui);
	}
	
}
