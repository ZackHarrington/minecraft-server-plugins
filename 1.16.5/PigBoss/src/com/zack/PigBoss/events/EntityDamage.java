package com.zack.PigBoss.events;

import org.bukkit.Material;
import org.bukkit.entity.Pig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.zack.PigBoss.Main;

public class EntityDamage implements Listener {
	
	public Main plugin;
	public EntityDamage(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		// Check that it's a Pig
		if (!(e.getEntity() instanceof Pig))
			return;
		// If it doesn't have a name its not a boss
		if (e.getEntity().getCustomName() == null) 
			return;
		// If it does make sure it's not the boss
		if (!e.getEntity().getCustomName().contains("Lord Oinkers")) 
			return;
		
		System.out.println("Damaged");
		
		// It is the boss
		// Update health bar
		plugin.bossBars.get(e.getEntity().getEntityId()).setProgress(
				plugin.pigBossList.get(e.getEntity().getEntityId()).getHealth() /
				plugin.pigBossList.get(e.getEntity().getEntityId()).getMaxHealth());
		// Drop a porkchop if enough damage was dealt at the location
		if (e.getDamage() > 2.0)
		{
			Material material = null;
			if (e.getEntity().getFireTicks() > 0) { // On fire
				material = Material.COOKED_PORKCHOP;
			} else { // Not on fire
				material = Material.PORKCHOP;
			}
			e.getEntity().getWorld().dropItemNaturally(
					e.getEntity().getLocation(), new ItemStack(material, 1));
		}
		// Set on fire
		e.getEntity().setFireTicks(100);
	}
}
