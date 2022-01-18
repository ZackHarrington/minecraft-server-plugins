package com.zack.MinigameMechanics.Kits;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class KitsGUI {

	public KitsGUI(Player player) {
		
		Inventory gui = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Kit Selection");
		
		for (KitType type : KitType.values()) {
			ItemStack is = new ItemStack(type.getMaterial());
			ItemMeta isMeta = is.getItemMeta();
			isMeta.setDisplayName(type.getDisplay());
			isMeta.setLore(Arrays.asList(type.getDescription()));
			isMeta.setLocalizedName(type.name());
			is.setItemMeta(isMeta);			
			
			gui.addItem(is);
		}
		
		
		player.openInventory(gui);
		
	}
	
}
