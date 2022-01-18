package com.zack.MinigameMechanics.Teams;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class TeamsGUI {

	public TeamsGUI(Player player) {
		
		Inventory gui = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Team Selection");
		
		for (Team team : Team.values()) {
			ItemStack is = new ItemStack(team.getMaterial());
			ItemMeta isMeta = is.getItemMeta();
			isMeta.setDisplayName(team.getDisplay());
			isMeta.setLocalizedName(team.name());
			is.setItemMeta(isMeta);
			
			gui.addItem(is);
		}
		
		player.openInventory(gui);
		
	}
	
}
