package com.zack.MinigameMechanics.Kits.Types;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.zack.MinigameMechanics.Kits.Kit;
import com.zack.MinigameMechanics.Kits.KitType;

public class Fighter extends Kit {

	public Fighter(UUID uuid) {
		super(uuid, KitType.FIGHTER);
	}

	@Override
	public void onStart(Player player) {
		player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
		player.getInventory().addItem(new ItemStack(Material.FLINT_AND_STEEL));
	}
	
}
