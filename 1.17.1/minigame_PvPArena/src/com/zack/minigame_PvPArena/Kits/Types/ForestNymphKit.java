package com.zack.minigame_PvPArena.Kits.Types;

import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import com.zack.minigame_PvPArena.ArenaClasses.GameManager;
import com.zack.minigame_PvPArena.Kits.Kit;

import net.md_5.bungee.api.ChatColor;

public class ForestNymphKit extends Kit {
	
	public ForestNymphKit(UUID uuid) {
		super(uuid, KitType.FOREST_NYMPH);
	}

	@Override
	public void onStart(Player player) {
		
		ItemStack bowOfTheWoods = new ItemStack(Material.BOW);
		ItemMeta bowOfTheWoodsMeta = bowOfTheWoods.getItemMeta();
		bowOfTheWoodsMeta.setDisplayName("Bow of the Woods");
		bowOfTheWoodsMeta.setLocalizedName("bowOfTheWoods");
		bowOfTheWoodsMeta.setLore(GameManager.getLore(ChatColor.GRAY + "Poisons all arrows"));
		bowOfTheWoods.setItemMeta(bowOfTheWoodsMeta);
		
		ItemStack swiftnessPotion = new ItemStack(Material.POTION);
		PotionData swiftnessPotionData = new PotionData(PotionType.SPEED);
		PotionMeta swiftnessPotionMeta = (PotionMeta) swiftnessPotion.getItemMeta();
		swiftnessPotionMeta.setBasePotionData(swiftnessPotionData);
		swiftnessPotion.setItemMeta(swiftnessPotionMeta);
		
		ItemStack jumpPotion = new ItemStack(Material.POTION);
		PotionData jumpPotionData = new PotionData(PotionType.JUMP);
		PotionMeta jumpPotionMeta = (PotionMeta) jumpPotion.getItemMeta();
		jumpPotionMeta.setBasePotionData(jumpPotionData);
		jumpPotion.setItemMeta(jumpPotionMeta);
		
		player.getInventory().addItem(bowOfTheWoods);
		player.getInventory().addItem(swiftnessPotion);
		player.getInventory().addItem(jumpPotion);
		player.getEquipment().setItemInOffHand(new ItemStack(Material.TOTEM_OF_UNDYING));
		
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
		
		ItemStack leafChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack leafLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack leafBoots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta leafChestplateMeta = (LeatherArmorMeta) leafChestplate.getItemMeta();
		LeatherArmorMeta leafLeggingsMeta = (LeatherArmorMeta) leafLeggings.getItemMeta();
		LeatherArmorMeta leafBootsMeta = (LeatherArmorMeta) leafBoots.getItemMeta();
		leafChestplateMeta.setColor(Color.GREEN);
		leafLeggingsMeta.setColor(Color.GREEN);
		leafBootsMeta.setColor(Color.GREEN);
		leafChestplateMeta.setDisplayName("Leaf Chestplate");
		leafLeggingsMeta.setDisplayName("Leaf Leggings");
		leafBootsMeta.setDisplayName("Leaf Boots");
		leafChestplate.setItemMeta(leafChestplateMeta);
		leafLeggings.setItemMeta(leafLeggingsMeta);
		leafBoots.setItemMeta(leafBootsMeta);
		
		player.getEquipment().setChestplate(leafChestplate);
		player.getEquipment().setLeggings(leafLeggings);
		player.getEquipment().setBoots(leafBoots);
		
		player.getInventory().addItem(new ItemStack(Material.ARROW, 16));
	}
	
}
