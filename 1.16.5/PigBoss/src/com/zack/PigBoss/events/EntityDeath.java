package com.zack.PigBoss.events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.zack.PigBoss.Main;
import com.zack.PigBoss.mobs.PigBoss;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.WorldServer;

public class EntityDeath implements Listener {

	public Main plugin;
	public EntityDeath(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onDamage(EntityDeathEvent e) {
		// Check that it's a Pig
		if (!(e.getEntity() instanceof Pig))
			return;
		// If it doesn't have a name its not a boss
		if (e.getEntity().getCustomName() == null) {
			// Check if a player killed it
			if (e.getEntity().getKiller() instanceof Player) {
				
				// Has a 1 in 10 chance of becoming a boss
				Random rand = new Random();
				if ((rand.nextInt(1 - 0) + 0) == 0) {
					Player player = e.getEntity().getKiller();
					
					// Create PigBoss
					@SuppressWarnings("unchecked")
					EntityTypes<? extends EntityCreature> type = 
							(EntityTypes<? extends EntityCreature>)
							((CraftEntity)e.getEntity()).getHandle().getEntityType();
					PigBoss pig = new PigBoss(type, e.getEntity().getLocation());
					pig.setTarget(player);
					pig.setName("&a" + ChatColor.RED + "Lord Oinkers");
					// Add to world
					WorldServer world = ((CraftWorld) e.getEntity().getWorld()).getHandle();
					world.addEntity(pig);
					// Add to array
					plugin.pigBossList.put(pig.getID(), pig);
					
					// Create Boss Bar
					BossBar bossBar = Bukkit.createBossBar(
							ChatColor.WHITE + "Lord Oinkers",
							BarColor.RED,
							BarStyle.SEGMENTED_6); //, BarFlag); make function that does something when it appears
					bossBar.setProgress(1);
					// Assign to player
					bossBar.addPlayer(player);
					// Add to array
					plugin.bossBars.put(pig.getID(), bossBar);
				}
			}
			return;
		}
		// If it does make sure it's the boss
		if (e.getEntity().getCustomName().contains("Lord Oinkers")) {
			// Drop some goodies at the location
			Random rand = new Random();
			e.getEntity().getWorld().dropItemNaturally(
					e.getEntity().getLocation(), new ItemStack(Material.COOKED_PORKCHOP, rand.nextInt(32 - 16) + 32)); // 32-48
			// Remove entity and associated bossBar
			plugin.pigBossList.remove(e.getEntity().getEntityId());
			plugin.bossBars.get(e.getEntity().getEntityId()).removeAll();
			plugin.bossBars.remove(e.getEntity().getEntityId());
			return;
		}
	}
}
