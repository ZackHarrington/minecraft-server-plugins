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

public class LumberjackKit extends Kit {
	
	public LumberjackKit(UUID uuid) {
		super(uuid, KitType.LUMBERJACK);
	}

	@Override
	public void onStart(Player player) {
		
		ItemStack hatchet = new ItemStack(Material.DIAMOND_AXE);
		ItemMeta hatchetMeta = hatchet.getItemMeta();
		hatchetMeta.setDisplayName("Hatchet");
		hatchetMeta.setLocalizedName("hatchet");
		hatchetMeta.setLore(GameManager.getLore(ChatColor.GRAY + "Chops logs from trees"));
		hatchet.setItemMeta(hatchetMeta);
		hatchet.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
		hatchet.addEnchantment(Enchantment.DAMAGE_ALL, 5);
		
		ItemStack log = GameManager.getLog();
		log.setAmount(8);
		
		player.getInventory().addItem(hatchet);
		player.getInventory().addItem(log);
		player.getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD));
		
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
		
		ItemStack flannel = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack jeans = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack workBoots = new ItemStack(Material.LEATHER_BOOTS);
		LeatherArmorMeta flannelMeta = (LeatherArmorMeta) flannel.getItemMeta();
		LeatherArmorMeta jeansMeta = (LeatherArmorMeta) jeans.getItemMeta();
		LeatherArmorMeta workBootsMeta = (LeatherArmorMeta) workBoots.getItemMeta();
		flannelMeta.setColor(Color.MAROON);
		jeansMeta.setColor(Color.BLUE);
		workBootsMeta.setColor(Color.ORANGE);
		flannelMeta.setDisplayName("Flannel");
		jeansMeta.setDisplayName("Jeans");
		workBootsMeta.setDisplayName("Work Boots");
		flannel.setItemMeta(flannelMeta);
		jeans.setItemMeta(jeansMeta);
		workBoots.setItemMeta(workBootsMeta);
		
		player.getEquipment().setChestplate(flannel);
		player.getEquipment().setLeggings(jeans);
		player.getEquipment().setBoots(workBoots);
		
	}
	
}
