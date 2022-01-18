package com.zack.Swiper;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftFox;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R3.EntityFox;
import net.minecraft.server.v1_16_R3.WorldServer;

public class SwiperEvent implements Listener {

	Random rand = new Random();
	Main main;
	
	public SwiperEvent (Main main) {
		this.main = main;
	}
	
	@EventHandler 
	public void onDrop(PlayerDropItemEvent event) {
		
		if (rand.nextInt(5) == 0) { // 1 in 5 chance
			Player player = event.getPlayer();
			
			// Wait .75 seconds before spawing swiper so that it can hit the ground
			Bukkit.getScheduler().runTaskLater(main, new Runnable() {
				public void run() {
					// Create swiper
					Swiper swiper = new Swiper(player.getLocation().add(0, 0.1, 0), event.getItemDrop().getItemStack());
				
					// Spawn swiper
					WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
					world.addEntity(swiper);
					
					// Remove the item drop
					event.getItemDrop().remove();
				}
			}, 15L);
		}
	}
	
	@EventHandler
	public void onSwiperDeath(EntityDeathEvent event) {
		Entity ent = event.getEntity();
		
		if (ent instanceof Fox) {
			EntityFox fox = ((CraftFox)ent).getHandle();
			
			if (fox instanceof Swiper) {
				ItemStack item = ((Swiper) fox).getItem();
				
				if (item.getAmount() > 1) { 
					// if there is more than one, drop one less than the amount to compensate for the one in it's mouth
					item.setAmount(item.getAmount() - 1);
					ent.getWorld().dropItem(ent.getLocation(), item);
				} 
			}
		}
	}
	
}
