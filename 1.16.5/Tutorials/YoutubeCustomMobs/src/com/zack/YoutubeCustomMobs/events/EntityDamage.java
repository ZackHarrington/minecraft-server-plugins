package com.zack.YoutubeCustomMobs.events;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class EntityDamage implements Listener{

	private ItemStack[] goldSack = {
		new ItemStack(Material.GOLD_NUGGET, 16),	
		new ItemStack(Material.GOLD_BLOCK, 2),	
		new ItemStack(Material.GOLDEN_AXE, 1),	
		new ItemStack(Material.IRON_INGOT, 16),
		new ItemStack(Material.IRON_HELMET, 1),
		new ItemStack(Material.DIAMOND, 1),
	};
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Villager))
			return;
		if (e.getEntity().getCustomName() == null)
			return;
		if (!e.getEntity().getCustomName().contains("Thief"))
			return;
		
		Random r = new Random();
		e.getEntity().getWorld().dropItemNaturally(
				e.getEntity().getLocation(), goldSack[r.nextInt(goldSack.length + 0) - 0]);
	}
}
