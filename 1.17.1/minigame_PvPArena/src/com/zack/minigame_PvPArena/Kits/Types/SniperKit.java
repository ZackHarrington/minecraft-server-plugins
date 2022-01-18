package com.zack.minigame_PvPArena.Kits.Types;

import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.zack.minigame_PvPArena.ArenaClasses.GameManager;
import com.zack.minigame_PvPArena.Kits.Kit;

import net.md_5.bungee.api.ChatColor;

public class SniperKit extends Kit {
	
	public SniperKit(UUID uuid) {
		super(uuid, KitType.SNIPER);
	}

	@Override
	public void onStart(Player player) {
		
		ItemStack sniper = new ItemStack(Material.BOW);
		ItemMeta sniperMeta = sniper.getItemMeta();
		sniperMeta.setDisplayName("Sniper");
		sniperMeta.setLocalizedName("sniper");
		List<String> lore = GameManager.getLore(ChatColor.GRAY + "x2 Arrow velocity");
		lore.add(ChatColor.GRAY + "Shoots spectral arrows");
		sniperMeta.setLore(lore);
		sniper.setItemMeta(sniperMeta);
		sniper.addEnchantment(Enchantment.ARROW_DAMAGE, 5);
		
		ItemStack crossbow = new ItemStack(Material.CROSSBOW);
		crossbow.addEnchantment(Enchantment.QUICK_CHARGE, 2);
		
		player.getInventory().addItem(sniper);
		player.getInventory().addItem(crossbow);
		player.getEquipment().setItemInOffHand(new ItemStack(Material.SPYGLASS));
		
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
		
		player.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		player.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
		player.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
		
		player.getInventory().addItem(new ItemStack(Material.ARROW, 32));
		player.getInventory().addItem(new ItemStack(Material.SPECTRAL_ARROW, 8));
		
	}
	
}
