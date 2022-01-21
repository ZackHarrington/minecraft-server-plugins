package com.zack.randomGameChanges_ExplosiveEntities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class Manager extends BukkitRunnable {

	private static List<World> explodingWorlds;
	private static HashMap<UUID, Integer> explodingEntities;
	private static Random random;
	
	public Manager() {
		explodingWorlds = new ArrayList<>();
		explodingEntities = new HashMap<>();
		random = new Random();
		
		this.runTaskTimer(Main.getInstance(), 20, 1);			// Every tick it shows a fire particle
	}
	
	public void run() {
		List<UUID> toRemove = new ArrayList<>();
		Entity entity;
		
		for (World world : explodingWorlds) {
			for (UUID entityID : explodingEntities.keySet()) {
				entity = Bukkit.getEntity(entityID);
				
				if (entity == null) {
					toRemove.add(entityID);
					break;
				}
				
				// Check if it's time to explode, decrease timer
				explodingEntities.put(entityID, explodingEntities.get(entityID) - 1);
				entity.setCustomName(ChatColor.RED + 
						Integer.toString(explodingEntities.get(entityID) / 20) + "." + 
						Integer.toString((explodingEntities.get(entityID) / 2) % 10));
				if (explodingEntities.get(entityID) <= 0) {
					// location, power, setFire, breakBlocks, source
					world.createExplosion(entity.getLocation(), 2, false, true, entity);
					toRemove.add(entityID);
					entity.remove();							// Remove from the world
				}
				
				// Create fire particle
				// particle, location, count, offsetX, offsetY, offsetZ, extra(speed)
				world.spawnParticle(Particle.FLAME, entity.getLocation().add(0, 0.8, 0), 1, 0.1, 0.1, 0.1, 0.01);
			}
			
			for (UUID e : toRemove) {
				explodingEntities.remove(e);				
			}
		}
	}
	
	public static boolean addExplodingWorld(World world) {
		if (!explodingWorlds.contains(world))
			explodingWorlds.add(world);
		else
			return false;
		return true;
	}
	public static boolean isExplodingWorld(World world) {
		return explodingWorlds.contains(world);
	}
	public static boolean removeExplodingWorld(World world) {
		if (explodingWorlds.contains(world))
			explodingWorlds.remove(world);
		else
			return false;
		return true;
	}
	
	public static void addEntity(Entity entity) {
		if (!explodingEntities.containsKey(entity.getUniqueId())) {
			int timer = (random.nextInt(3) + 3) * 20;
			explodingEntities.put(entity.getUniqueId(), timer);	// 3 seconds
			entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1.0f, 1.0f);
			entity.setCustomName(ChatColor.RED + 
					Integer.toString(timer / 20) + "." + Integer.toString((timer / 2) % 10));
			entity.setCustomNameVisible(true);
		}
	}
	public static boolean isExploding(UUID entityID) {
		return explodingEntities.containsKey(entityID);
	}
	public static void removeEntity(UUID entityID) {
		if (explodingEntities.containsKey(entityID))
			explodingEntities.remove(entityID);
	}
	
	public static void obliterate() {
		explodingWorlds.clear();
		explodingEntities.clear();
	}
}
