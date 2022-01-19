package com.zack.minigame_PvPArena.Kits.Types;

import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.zack.minigame_PvPArena.ArenaClasses.GameManager;
import com.zack.minigame_PvPArena.Kits.Kit;

import net.md_5.bungee.api.ChatColor;

public class SlowBurnKit extends Kit {
	
	public SlowBurnKit(UUID uuid) {
		super(uuid, KitType.SLOW_BURN);
	}

	@Override
	public void onStart(Player player) {
		
		ItemStack staff = new ItemStack(Material.BONE);
		ItemMeta staffMeta = staff.getItemMeta();
		staffMeta.setDisplayName("Conjuring Staff");
		staffMeta.setLocalizedName("conjuringStaff");
		staffMeta.setLore(GameManager.getLore(ChatColor.GRAY + "Uses candles to summon their effects"));
		staff.setItemMeta(staffMeta);
		staff.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
		
		ItemStack poisonCandle = new ItemStack(Material.GREEN_CANDLE, 16);
		ItemMeta poisonCandleMeta = poisonCandle.getItemMeta();
		poisonCandleMeta.setDisplayName("Candle of Poison");
		poisonCandleMeta.setLore(GameManager.getLore(ChatColor.GRAY + "Poisons the surrounding air"));
		poisonCandle.setItemMeta(poisonCandleMeta);
		
		ItemStack frostCandle = new ItemStack(Material.LIGHT_BLUE_CANDLE, 16);
		ItemMeta frostCandleMeta = frostCandle.getItemMeta();
		frostCandleMeta.setDisplayName("Candle of Frost");
		frostCandleMeta.setLore(GameManager.getLore(ChatColor.GRAY + "Freezes the surrounding air"));
		frostCandle.setItemMeta(frostCandleMeta);
		
		ItemStack flameCandle = new ItemStack(Material.ORANGE_CANDLE, 16);
		ItemMeta flameCandleMeta = flameCandle.getItemMeta();
		flameCandleMeta.setDisplayName("Candle of Flame");
		flameCandleMeta.setLore(GameManager.getLore(ChatColor.GRAY + "Ignites the surrounding air"));
		flameCandle.setItemMeta(flameCandleMeta);
		
		ItemStack taintedTotem = new ItemStack(Material.WITHER_ROSE);
		ItemMeta taintedTotemMeta = taintedTotem.getItemMeta();
		taintedTotemMeta.setDisplayName("Tainted Totem");
		taintedTotemMeta.setLocalizedName("taintedTotem");
		taintedTotemMeta.setLore(GameManager.getLore(ChatColor.GRAY + "Releases healing effects upon use"));
		taintedTotem.setItemMeta(taintedTotemMeta);
		
		player.getInventory().addItem(staff);
		player.getInventory().addItem(poisonCandle);
		player.getInventory().addItem(frostCandle);
		player.getInventory().addItem(flameCandle);
		player.getEquipment().setItemInOffHand(taintedTotem);
		
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
		
		ItemStack wizardRobe = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta wizardRobeMeta = (LeatherArmorMeta) wizardRobe.getItemMeta();
		wizardRobeMeta.setColor(Color.PURPLE);
		wizardRobeMeta.setDisplayName("Wizard Robe");
		wizardRobe.setItemMeta(wizardRobeMeta);
		
		player.getEquipment().setChestplate(wizardRobe);
		
	}
	
}
