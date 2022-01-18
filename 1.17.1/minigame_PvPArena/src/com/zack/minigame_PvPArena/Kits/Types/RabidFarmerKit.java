package com.zack.minigame_PvPArena.Kits.Types;

import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import com.zack.minigame_PvPArena.ArenaClasses.GameManager;
import com.zack.minigame_PvPArena.Kits.Kit;

import net.md_5.bungee.api.ChatColor;

public class RabidFarmerKit extends Kit {

	public RabidFarmerKit(UUID uuid) {
		super(uuid, KitType.RABID_FARMER);
	}

	@Override
	public void onStart(Player player) {
		
		ItemStack hoe = new ItemStack(Material.DIAMOND_HOE);
		hoe.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
		
		ItemStack tamedWolfEgg = new ItemStack(Material.WOLF_SPAWN_EGG, 5);
		ItemMeta tamedWolfEggMeta = tamedWolfEgg.getItemMeta();
		tamedWolfEggMeta.setDisplayName("Tamed Wolf Spawn Egg");
		tamedWolfEggMeta.setLocalizedName("tamedWolfEgg");
		tamedWolfEgg.setItemMeta(tamedWolfEggMeta);
		
		ItemStack swiftnessPotion = new ItemStack(Material.POTION);
		PotionData swiftnessPotionData = new PotionData(PotionType.SPEED, false, true); // upgraded
		PotionMeta swiftnessPotionMeta = (PotionMeta) swiftnessPotion.getItemMeta();
		swiftnessPotionMeta.setBasePotionData(swiftnessPotionData);
		swiftnessPotionMeta.setLocalizedName("confusedPotion");
		swiftnessPotionMeta.setLore(GameManager.getLore(ChatColor.GRAY + "+ 15s of Confusion"));;
		swiftnessPotion.setItemMeta(swiftnessPotionMeta);
		
		ItemStack brokenShield = new ItemStack(Material.SHIELD);
		Damageable brokenShieldMeta = (Damageable) brokenShield.getItemMeta();
		brokenShieldMeta.setDamage(150);
		brokenShield.setItemMeta((ItemMeta)brokenShieldMeta);
		
		player.getInventory().addItem(hoe);
		player.getInventory().addItem(tamedWolfEgg);
		player.getInventory().addItem(swiftnessPotion);
		player.getEquipment().setItemInOffHand(brokenShield);
		
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
		
		ItemStack tatteredFlannel = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta tatteredFlannelMeta = (LeatherArmorMeta) tatteredFlannel.getItemMeta();
		tatteredFlannelMeta.setColor(Color.MAROON);
		tatteredFlannelMeta.setDisplayName("Tattered Flannel");
		Damageable tatteredFlannelDMeta = (Damageable) tatteredFlannelMeta;
		tatteredFlannelDMeta.setDamage(40);
		tatteredFlannel.setItemMeta((ItemMeta)tatteredFlannelDMeta);
		
		player.getEquipment().setChestplate(tatteredFlannel);
		
	}
	
}
