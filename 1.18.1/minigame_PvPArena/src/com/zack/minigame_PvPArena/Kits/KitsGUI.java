package com.zack.minigame_PvPArena.Kits;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.zack.minigame_PvPArena.Kits.Types.KitType;

public class KitsGUI {

	public KitsGUI(Player player, KitGUIType type) {
		
		Inventory gui = Bukkit.createInventory(null, 54, type.getDisplay());
		
		if (type.equals(KitGUIType.CLASS_SELECTOR)) {
			int spotNum = 0;
			
			for (KitGUIType kitGUIType : KitGUIType.values()) {
				if (!kitGUIType.equals(KitGUIType.CLASS_SELECTOR)) {
					ItemStack is = new ItemStack(kitGUIType.getMaterial());
					ItemMeta isMeta = is.getItemMeta();
					isMeta.setDisplayName(kitGUIType.getDisplay());
					isMeta.setLore(Arrays.asList(kitGUIType.getDescription()));
					isMeta.setLocalizedName(kitGUIType.name());
					is.setItemMeta(isMeta);			
					
					int itemSpot = 20 + (2 * spotNum);
					spotNum++;
					
					gui.setItem(itemSpot, is);
				}
			}
			
			// Default
			ItemStack is = new ItemStack(KitType.DEFAULT.getMaterial());
			ItemMeta isMeta = is.getItemMeta();
			isMeta.setDisplayName(KitType.DEFAULT.getDisplay());
			isMeta.setLore(Arrays.asList(KitType.DEFAULT.getDescription()));
			isMeta.setLocalizedName(KitType.DEFAULT.name());
			is.setItemMeta(isMeta);
			
			// Random
			ItemStack isr = new ItemStack(KitType.RANDOM.getMaterial());
			ItemMeta isrMeta = isr.getItemMeta();
			isrMeta.setDisplayName(KitType.RANDOM.getDisplay());
			isrMeta.setLore(Arrays.asList(KitType.RANDOM.getDescription()));
			isrMeta.setLocalizedName(KitType.RANDOM.name());
			isr.setItemMeta(isrMeta);	
			
			gui.setItem(39, is);
			gui.setItem(41, isr);
		} else {
			int location = 18;
			if (type.getSubKits().length == 4) { location = 19; }
			for (KitType kitType : type.getSubKits()) {
				ItemStack is = new ItemStack(kitType.getMaterial());
				ItemMeta isMeta = is.getItemMeta();
				isMeta.setDisplayName(kitType.getDisplay());
				isMeta.setLore(Arrays.asList(kitType.getDescription()));
				isMeta.setLocalizedName(kitType.name());
				is.setItemMeta(isMeta);			
			
				gui.setItem(location, is);
				location += 2;
			}
		}
		
		
		player.openInventory(gui);
		
	}
	
}
