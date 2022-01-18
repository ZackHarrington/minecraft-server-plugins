package com.zack.minigame_PvPArena.Kits.Types;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.zack.minigame_PvPArena.Kits.Kit;

public class DefaultKit extends Kit {

	public DefaultKit(UUID uuid) {
		super(uuid, KitType.DEFAULT);
	}

	@Override
	public void onStart(Player player) {
		
		player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
		player.getInventory().addItem(new ItemStack(Material.SNOWBALL, 16));
		player.getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD));
		
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 16));
		
		player.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		player.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		player.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
		
	}
	
}
