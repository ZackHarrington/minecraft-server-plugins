package com.zack.minigame_PvPArena.Kits.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.zack.minigame_PvPArena.Kits.Kit;

public class FireMasterKit extends Kit {
	
	public FireMasterKit(UUID uuid) {
		super(uuid, KitType.FIRE_MASTER);
	}

	@Override
	public void onStart(Player player) {
		
		ItemStack goldSword = new ItemStack(Material.GOLDEN_SWORD);
		goldSword.addEnchantment(Enchantment.FIRE_ASPECT, 2);
		
		player.getInventory().addItem(goldSword);
		player.getInventory().addItem(new ItemStack(Material.FIRE_CHARGE, 8));
		player.getEquipment().setItemInOffHand(new ItemStack(Material.TOTEM_OF_UNDYING));
		
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
		
		ItemStack ignitedChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack ignitedLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack ignitedBoots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta ignitedChestplateMeta = (LeatherArmorMeta) ignitedChestplate.getItemMeta();
		LeatherArmorMeta ignitedLeggingsMeta = (LeatherArmorMeta) ignitedLeggings.getItemMeta();
		LeatherArmorMeta ignitedBootsMeta = (LeatherArmorMeta) ignitedBoots.getItemMeta();
		ignitedChestplateMeta.setColor(Color.ORANGE);
		ignitedLeggingsMeta.setColor(Color.ORANGE);
		ignitedBootsMeta.setColor(Color.ORANGE);
		ignitedChestplateMeta.setDisplayName("Ignited Chestplate");
		ignitedLeggingsMeta.setDisplayName("Ignited Leggings");
		ignitedBootsMeta.setDisplayName("Ignited Boots");
		ignitedChestplateMeta.setLocalizedName("ignitedChestplate");
		ignitedLeggingsMeta.setLocalizedName("ignitedLeggings");
		ignitedBootsMeta.setLocalizedName("ignitedBoots");
		List<String> lore = new ArrayList<>();
		lore.add("Immunity from fire");
		ignitedChestplateMeta.setLore(lore);
		ignitedLeggingsMeta.setLore(lore);
		ignitedBootsMeta.setLore(lore);
		ignitedChestplate.setItemMeta(ignitedChestplateMeta);
		ignitedLeggings.setItemMeta(ignitedLeggingsMeta);
		ignitedBoots.setItemMeta(ignitedBootsMeta);
		
		player.getEquipment().setChestplate(ignitedChestplate);
		player.getEquipment().setLeggings(ignitedLeggings);
		player.getEquipment().setBoots(ignitedBoots);
		
	}
	
}
