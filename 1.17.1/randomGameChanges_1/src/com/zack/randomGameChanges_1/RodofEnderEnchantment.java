package com.zack.randomGameChanges_1;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class RodofEnderEnchantment extends Enchantment {

	public RodofEnderEnchantment() {
		// main, id unique to all possible enchantments available on your server
		super(new NamespacedKey(Main.getInstance(), "RodofEnder"));
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		// Specify what items can be enchanted / not enchanted
		if (arg0.getType().equals(Material.BLAZE_ROD)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		// Specify possible enchantments that cannot be used together
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		// Specify what type of item you can enchant
		return EnchantmentTarget.WEAPON;
	}

	@Override
	public int getMaxLevel() {
		// Specify the max level the enchantment can be
		return 1;
	}

	@Override
	public String getName() {
		// Specify what the user will see as the name of the enchantment
		return "Rod of Ender";
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

