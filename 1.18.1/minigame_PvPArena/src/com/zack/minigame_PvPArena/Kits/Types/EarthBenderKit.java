package com.zack.minigame_PvPArena.Kits.Types;

import java.util.List;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.zack.minigame_PvPArena.ArenaClasses.GameManager;
import com.zack.minigame_PvPArena.Kits.Kit;

import net.md_5.bungee.api.ChatColor;

public class EarthBenderKit extends Kit {
	
	public EarthBenderKit(UUID uuid) {
		super(uuid, KitType.EARTH_BENDER);
	}

	@Override
	public void onStart(Player player) {
		
		ItemStack earthStaff = new ItemStack(Material.STICK);
		ItemMeta earthStaffMeta = earthStaff.getItemMeta();
		earthStaffMeta.setDisplayName("Earth Staff");
		earthStaffMeta.setLocalizedName("earthStaff");
		List<String> lore = GameManager.getLore(ChatColor.GRAY + "Left click to grab");
		lore.add(ChatColor.GRAY + "Right click to shoot");
		earthStaffMeta.setLore(lore);
		earthStaff.setItemMeta(earthStaffMeta);
		
		ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
		stoneSword.addEnchantment(Enchantment.DAMAGE_ALL, 5); // Sharpness
		
		ItemStack earthShield = new ItemStack(Material.SHIELD);
		ItemMeta earthShieldMeta = earthShield.getItemMeta();
        BlockStateMeta bmeta = (BlockStateMeta) earthShieldMeta;
        Banner banner = (Banner) bmeta.getBlockState();
        banner.setBaseColor(DyeColor.BROWN);
        banner.addPattern(new Pattern(DyeColor.RED, PatternType.BRICKS));
        banner.addPattern(new Pattern(DyeColor.BROWN, PatternType.TRIANGLES_BOTTOM));
        banner.addPattern(new Pattern(DyeColor.BROWN, PatternType.TRIANGLES_TOP));
        banner.update();
        bmeta.setBlockState(banner);
        bmeta.setDisplayName("Earth Shield");
        bmeta.setLocalizedName("earthShield");
        bmeta.setLore(GameManager.getLore(ChatColor.GRAY + "Stops half the damage of falling blocks"));
        earthShield.setItemMeta(bmeta);
		
		player.getInventory().addItem(earthStaff);
		player.getInventory().addItem(stoneSword);
		player.getEquipment().setItemInOffHand(earthShield);
		
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
		
		ItemStack mudChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack mudLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack mudBoots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta mudChestplateMeta = (LeatherArmorMeta) mudChestplate.getItemMeta();
		LeatherArmorMeta mudLeggingsMeta = (LeatherArmorMeta) mudLeggings.getItemMeta();
		LeatherArmorMeta mudBootsMeta = (LeatherArmorMeta) mudBoots.getItemMeta();
		mudChestplateMeta.setColor(Color.fromRGB(96, 70, 15)); // Mud Brown
		mudLeggingsMeta.setColor(Color.fromRGB(96, 70, 15));
		mudBootsMeta.setColor(Color.fromRGB(96, 70, 15));
		mudChestplateMeta.setDisplayName("Mud Chestplate");
		mudLeggingsMeta.setDisplayName("Mud Leggings");
		mudBootsMeta.setDisplayName("Mud Boots");
		mudChestplate.setItemMeta(mudChestplateMeta);
		mudLeggings.setItemMeta(mudLeggingsMeta);
		mudBoots.setItemMeta(mudBootsMeta);
		
		player.getEquipment().setChestplate(mudChestplate);
		player.getEquipment().setLeggings(mudLeggings);
		player.getEquipment().setBoots(mudBoots);
		
		ItemStack ammo = GameManager.getBlockAmmo(Material.ROOTED_DIRT);
		ammo.setAmount(32);
		player.getInventory().addItem(ammo);
		
	}
	
}
