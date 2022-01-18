package com.zack.minigame_PvPArena.Kits.Types;

import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import com.zack.minigame_PvPArena.ArenaClasses.GameManager;
import com.zack.minigame_PvPArena.Kits.Kit;

import net.md_5.bungee.api.ChatColor;

public class NinjaKit extends Kit {
	
	public NinjaKit(UUID uuid) {
		super(uuid, KitType.NINJA);
	}

	@Override
	public void onStart(Player player) {
		
		ItemStack invisibilityPotion = new ItemStack(Material.POTION);
		PotionData invisibilityPotionData = new PotionData(PotionType.INVISIBILITY);
		PotionMeta invisibilityPotionMeta = (PotionMeta) invisibilityPotion.getItemMeta();
		invisibilityPotionMeta.setBasePotionData(invisibilityPotionData);
		invisibilityPotionMeta.setLore(GameManager.getLore(ChatColor.GRAY + "10 seconds of invisibility"));
		invisibilityPotionMeta.setLocalizedName("invisibility10");
		invisibilityPotion.setItemMeta(invisibilityPotionMeta);
		
		ItemStack swiftnessPotion = new ItemStack(Material.POTION);
		PotionData swiftnessPotionData = new PotionData(PotionType.SPEED);
		PotionMeta swiftnessPotionMeta = (PotionMeta) swiftnessPotion.getItemMeta();
		swiftnessPotionMeta.setBasePotionData(swiftnessPotionData);
		swiftnessPotion.setItemMeta(swiftnessPotionMeta);
		
		player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
		player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 4));
		player.getInventory().addItem(swiftnessPotion);
		player.getEquipment().setItemInOffHand(invisibilityPotion);
		
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
		
		ItemStack ninjaChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack ninjaLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack ninjaBoots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta ninjaChestplateMeta = (LeatherArmorMeta) ninjaChestplate.getItemMeta();
		LeatherArmorMeta ninjaLeggingsMeta = (LeatherArmorMeta) ninjaLeggings.getItemMeta();
		LeatherArmorMeta ninjaBootsMeta = (LeatherArmorMeta) ninjaBoots.getItemMeta();
		ninjaChestplateMeta.setColor(Color.BLACK);
		ninjaLeggingsMeta.setColor(Color.BLACK);
		ninjaBootsMeta.setColor(Color.BLACK);
		ninjaChestplateMeta.setDisplayName("Ninja Chestplate");
		ninjaLeggingsMeta.setDisplayName("Ninja Leggings");
		ninjaBootsMeta.setDisplayName("Ninja Boots");
		ninjaChestplate.setItemMeta(ninjaChestplateMeta);
		ninjaLeggings.setItemMeta(ninjaLeggingsMeta);
		ninjaBoots.setItemMeta(ninjaBootsMeta);
		
		player.getEquipment().setChestplate(ninjaChestplate);
		player.getEquipment().setLeggings(ninjaLeggings);
		player.getEquipment().setBoots(ninjaBoots);
		
	}
	
}
