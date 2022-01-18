package com.zack.GodEnchantments.Enchantments;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import com.zack.GodEnchantments.Main;

public class GodEnchantment extends Enchantment {

	public GodEnchantment() {
		// main, id unique to all possible enchantments available on your server
		super(new NamespacedKey(Main.getInstance(), "god"));
	}

	@Override
	public boolean canEnchantItem(ItemStack item) {
		// Specify what items can be enchanted / not enchanted
		
		if (item.getType().equals(Material.COOKIE) ||
				item.getType().equals(Material.LEATHER_HELMET) ||
				item.getType().equals(Material.LEATHER_CHESTPLATE) ||
				item.getType().equals(Material.LEATHER_LEGGINGS) ||
				item.getType().equals(Material.LEATHER_BOOTS) ||
				item.getType().equals(Material.IRON_HELMET) ||
				item.getType().equals(Material.IRON_CHESTPLATE) ||
				item.getType().equals(Material.IRON_LEGGINGS) ||
				item.getType().equals(Material.IRON_BOOTS) ||
				item.getType().equals(Material.GOLDEN_HELMET) ||
				item.getType().equals(Material.GOLDEN_CHESTPLATE) ||
				item.getType().equals(Material.GOLDEN_LEGGINGS) ||
				item.getType().equals(Material.GOLDEN_BOOTS) ||
				item.getType().equals(Material.DIAMOND_HELMET) ||
				item.getType().equals(Material.DIAMOND_CHESTPLATE) ||
				item.getType().equals(Material.DIAMOND_LEGGINGS) ||
				item.getType().equals(Material.DIAMOND_BOOTS) ||
				item.getType().equals(Material.NETHERITE_HELMET) ||
				item.getType().equals(Material.NETHERITE_CHESTPLATE) ||
				item.getType().equals(Material.NETHERITE_LEGGINGS) ||
				item.getType().equals(Material.NETHERITE_BOOTS) ||
				item.getType().equals(Material.CHAINMAIL_HELMET) ||
				item.getType().equals(Material.CHAINMAIL_CHESTPLATE) ||
				item.getType().equals(Material.CHAINMAIL_LEGGINGS) ||
				item.getType().equals(Material.CHAINMAIL_BOOTS)
				) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean conflictsWith(Enchantment enchantment) {
		// Specify possible enchantments that cannot be used together
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		// Specify what type of item you can enchant
		return EnchantmentTarget.ARMOR;
	}

	@Override
	public int getMaxLevel() {
		// Specify the max level the enchantment can be
		return 1;
	}

	@Override
	public String getName() {
		// Specify what the user will see as the name of the enchantment
		return "God";
	}

	@Override
	public int getStartLevel() {
		// 1 or 0
		return 1;
	}

	@Override
	public boolean isCursed() {
		// Basically deprecated
		return false;
	}

	@Override
	public boolean isTreasure() {
		// Treasure means it can only be received through looting, trading, or fishing
		return false;
	}
	
}