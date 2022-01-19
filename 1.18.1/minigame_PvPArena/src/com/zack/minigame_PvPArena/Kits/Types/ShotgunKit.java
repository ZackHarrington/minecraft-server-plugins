package com.zack.minigame_PvPArena.Kits.Types;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.zack.minigame_PvPArena.Kits.Kit;

public class ShotgunKit extends Kit {
	
	public ShotgunKit(UUID uuid) {
		super(uuid, KitType.SHOTGUN);
	}

	@Override
	public void onStart(Player player) {
		
		ItemStack shotgun = new ItemStack(Material.CROSSBOW);
		ItemMeta shotgunMeta = shotgun.getItemMeta();
		shotgunMeta.setDisplayName("Shotgun");
		shotgun.setItemMeta(shotgunMeta);
		shotgun.addEnchantment(Enchantment.QUICK_CHARGE, 3);
		shotgun.addEnchantment(Enchantment.MULTISHOT, 1);
		
		player.getInventory().addItem(shotgun);
		player.getInventory().addItem(new ItemStack(Material.SNOWBALL, 16));
		player.getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD));
		
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
		
		player.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
		player.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
		player.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));
		
		player.getInventory().addItem(new ItemStack(Material.ARROW, 32));
		
	}
	
}
