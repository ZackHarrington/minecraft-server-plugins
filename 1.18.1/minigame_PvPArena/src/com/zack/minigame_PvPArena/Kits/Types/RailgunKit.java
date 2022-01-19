package com.zack.minigame_PvPArena.Kits.Types;

import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.zack.minigame_PvPArena.ArenaClasses.GameManager;
import com.zack.minigame_PvPArena.Kits.Kit;

import net.md_5.bungee.api.ChatColor;

public class RailgunKit extends Kit {
	
	public RailgunKit(UUID uuid) {
		super(uuid, KitType.RAILGUN);
	}

	@Override
	public void onStart(Player player) {
		
		ItemStack railgun = new ItemStack(Material.TRIDENT);
		ItemMeta railgunMeta = railgun.getItemMeta();
		railgunMeta.setDisplayName("Railgun");
		railgunMeta.setLocalizedName("railgun");
		List<String> lore = GameManager.getLore(ChatColor.GRAY + "Consumes power crystals");
		lore.add(ChatColor.GRAY + "Takes a second to charge");
		railgunMeta.setLore(lore);
		railgun.setItemMeta(railgunMeta);
		
		ItemStack powerCrystal = new ItemStack(Material.AMETHYST_SHARD, 8);
		ItemMeta powerCrystalMeta = powerCrystal.getItemMeta();
		powerCrystalMeta.setDisplayName("Power Crystal");
		powerCrystal.setItemMeta(powerCrystalMeta);
		
		player.getInventory().addItem(railgun);
		player.getEquipment().setItemInOffHand(new ItemStack(Material.SPYGLASS));
		
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
		
		player.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		player.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
		player.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
		
		player.getInventory().addItem(powerCrystal);
		
	}
	
}
