package com.zack.minigame_PvPArena.Kits.Types;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.zack.minigame_PvPArena.Kits.Kit;

public class LongbowKit extends Kit {
	
	public LongbowKit(UUID uuid) {
		super(uuid, KitType.LONGBOW);
	}

	@Override
	public void onStart(Player player) {
		
		ItemStack longbow = new ItemStack(Material.BOW);
		ItemMeta longbowMeta = longbow.getItemMeta();
		longbowMeta.setDisplayName("Longbow");
		longbow.setItemMeta(longbowMeta);
		longbow.addEnchantment(Enchantment.ARROW_DAMAGE, 5);
		
		player.getInventory().addItem(longbow);
		player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
		player.getEquipment().setItemInOffHand(new ItemStack(Material.SPYGLASS));
		
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
		
		player.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		player.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
		player.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
		
		player.getInventory().addItem(new ItemStack(Material.ARROW, 64));
		
	}
	
}
