package com.zack.YoutubeCustomMobs.events;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.zack.YoutubeCustomMobs.Main;

public class EntityDeath implements Listener{

	private Main plugin;
	public EntityDeath(Main plugin) {
		this.plugin = plugin;
	}
	
	private ItemStack[] goldSack = {
		new ItemStack(Material.GOLD_INGOT, 7),	
		new ItemStack(Material.GOLD_BLOCK, 4),	
		new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 4),	
		new ItemStack(Material.IRON_INGOT, 24),
		new ItemStack(Material.DIAMOND, 3),
	};
	
	@EventHandler
	public void onDamage(EntityDeathEvent e) {
		if (!(e.getEntity() instanceof Villager))
			return;
		if (e.getEntity().getCustomName() == null)
			return;
		if (!e.getEntity().getCustomName().contains("Thief"))
			return;
		
		// Gives random item in the array
		Random r = new Random();
		e.getEntity().getWorld().dropItemNaturally(
				e.getEntity().getLocation(), goldSack[r.nextInt(goldSack.length + 0) - 0]);
		
		for  (ItemStack item : plugin.stolenItems) {
			if (item != null) {
				e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), item);
			}
		}
	}
}
