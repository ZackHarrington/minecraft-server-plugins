package com.zack.minigame_PvPArena.Kits.Types;

import java.util.UUID;

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

import com.zack.minigame_PvPArena.ArenaClasses.GameManager;
import com.zack.minigame_PvPArena.Kits.Kit;

import net.md_5.bungee.api.ChatColor;

public class ColdSnapKit extends Kit {
	
	public ColdSnapKit(UUID uuid) {
		super(uuid, KitType.COLD_SNAP);
	}

	@Override
	public void onStart(Player player) {
		
		ItemStack inflictor = new ItemStack(Material.IRON_SWORD);
		ItemMeta inflictorMeta = inflictor.getItemMeta();
		inflictorMeta.setDisplayName("Inflictor");
		inflictorMeta.setLocalizedName("inflictor");
		inflictorMeta.setLore(GameManager.getLore(ChatColor.GRAY + "Frost Aspect"));
		inflictor.setItemMeta(inflictorMeta);
		
		ItemStack executor = new ItemStack(Material.IRON_SWORD);
		ItemMeta executorMeta = executor.getItemMeta();
		executorMeta.setDisplayName("Executor");
		executorMeta.setLocalizedName("executor");
		executorMeta.setLore(GameManager.getLore(ChatColor.GRAY + "Double damage to freezing entities"));
		executor.setItemMeta(executorMeta);
		executor.addEnchantment(Enchantment.DAMAGE_ALL, 3);
		
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
		
		player.getInventory().addItem(inflictor);
		player.getInventory().addItem(executor);
		player.getEquipment().setItemInOffHand(snowShield);
		
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
		
		player.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		player.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		player.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
		
	}
	
}
