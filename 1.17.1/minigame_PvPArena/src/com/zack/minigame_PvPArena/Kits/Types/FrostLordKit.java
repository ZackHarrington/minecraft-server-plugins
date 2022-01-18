package com.zack.minigame_PvPArena.Kits.Types;

import java.util.ArrayList;
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

import com.zack.minigame_PvPArena.Kits.Kit;

public class FrostLordKit extends Kit {

	public FrostLordKit(UUID uuid) {
		super(uuid, KitType.FROST_LORD);
	}

	@Override
	public void onStart(Player player) {
		
		ItemStack frostbite = new ItemStack(Material.TRIDENT);
		ItemMeta frostbiteMeta = frostbite.getItemMeta();
		frostbiteMeta.setDisplayName("Frostbite");
		frostbite.setItemMeta(frostbiteMeta);
		frostbite.addEnchantment(Enchantment.LOYALTY, 2);
		
		ItemStack eternalSnowball = new ItemStack(Material.SNOWBALL);
		ItemMeta eternalSnowballMeta = eternalSnowball.getItemMeta();
		eternalSnowballMeta.setDisplayName("Eternal Snowball");
		eternalSnowballMeta.setLocalizedName("eternalSnowball");
		eternalSnowball.setItemMeta(eternalSnowballMeta);
		
		ItemStack snowShield = new ItemStack(Material.SHIELD);
		ItemMeta snowShieldMeta = snowShield.getItemMeta();
        BlockStateMeta bmeta = (BlockStateMeta) snowShieldMeta;
        Banner banner = (Banner) bmeta.getBlockState();
        banner.setBaseColor(DyeColor.LIGHT_BLUE);
        banner.addPattern(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE));
        banner.update();
        bmeta.setBlockState(banner);
        bmeta.setDisplayName("Snow Shield");
        snowShield.setItemMeta(bmeta);
		
		player.getInventory().addItem(frostbite);
		player.getInventory().addItem(eternalSnowball);
		player.getEquipment().setItemInOffHand(snowShield);
		
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
		
		ItemStack frostChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack frostLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack frostBoots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta frostChestplateMeta = (LeatherArmorMeta) frostChestplate.getItemMeta();
		LeatherArmorMeta frostLeggingsMeta = (LeatherArmorMeta) frostLeggings.getItemMeta();
		LeatherArmorMeta frostBootsMeta = (LeatherArmorMeta) frostBoots.getItemMeta();
		frostChestplateMeta.setColor(Color.WHITE);
		frostLeggingsMeta.setColor(Color.WHITE);
		frostBootsMeta.setColor(Color.WHITE);
		frostChestplateMeta.setDisplayName("Frost Chestplate");
		frostLeggingsMeta.setDisplayName("Frost Leggings");
		frostBootsMeta.setDisplayName("Frost Boots");
		frostChestplateMeta.setLocalizedName("frostChestplate");
		frostLeggingsMeta.setLocalizedName("frostLeggings");
		frostBootsMeta.setLocalizedName("frostBoots");
		List<String> lore = new ArrayList<>();
		lore.add("Immunity from Snowballs");
		frostChestplateMeta.setLore(lore);
		frostLeggingsMeta.setLore(lore);
		frostBootsMeta.setLore(lore);
		frostChestplate.setItemMeta(frostChestplateMeta);
		frostLeggings.setItemMeta(frostLeggingsMeta);
		frostBoots.setItemMeta(frostBootsMeta);
		
		player.getEquipment().setChestplate(frostChestplate);
		player.getEquipment().setLeggings(frostLeggings);
		player.getEquipment().setBoots(frostBoots);
		
	}
	
}
