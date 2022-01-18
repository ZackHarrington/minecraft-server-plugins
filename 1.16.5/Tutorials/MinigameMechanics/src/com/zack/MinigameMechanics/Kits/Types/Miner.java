package com.zack.MinigameMechanics.Kits.Types;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.zack.MinigameMechanics.Kits.Kit;
import com.zack.MinigameMechanics.Kits.KitType;

public class Miner extends Kit {
	
	public Miner(UUID uuid) {
		super(uuid, KitType.MINER);
	}
	
	@Override
	public void onStart(Player player) {
		player.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
		player.getInventory().addItem(new ItemStack(Material.TORCH, 64));
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		System.out.println(event.getPlayer().getName() + " just broke a block!");
		
	}

}
