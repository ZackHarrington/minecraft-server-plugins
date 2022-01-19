package com.zack.ZombieApocalypse;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Stray;
import org.bukkit.entity.Witch;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.zack.ZombieApocalypse.Mobs.ZombieType;

public class ZombieApocalypseListener implements Listener {

	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent event) {
		// Stop entity spawns that detract from an all zombie apocalypse
		
		Entity entity = event.getEntity();
		
		if (!Manager.isInApocalypse(entity.getWorld()))
			return;
		
		if (entity instanceof Creeper ||
				entity instanceof Skeleton ||
				entity instanceof Stray ||
				entity instanceof Witch ||
				entity instanceof WitherSkeleton) {
			event.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		
		Entity entity = event.getEntity();
		
		if (!Manager.isInApocalypse(entity.getWorld()))
			return;
		
		// Check if the entity that died was a ranged zombie
		if (Manager.getZombieApocalypse(entity.getWorld()).isRangedZombie(entity.getUniqueId())) {
			Manager.getZombieApocalypse(entity.getWorld()).removeRangedZombie(entity.getUniqueId());
		}
		
	}
	
	@EventHandler
	public void onPlayerSleep(PlayerBedEnterEvent event) {
		
		World world = event.getPlayer().getWorld();
		
		if (Manager.isNight(world)) {
			if (Manager.isInApocalypse(world)) {
				event.setCancelled(true);
				
				event.getBed().breakNaturally();
				Manager.getZombieApocalypse(world).spawnZombie(
						event.getBed().getLocation(), ZombieType.SLEEP);					
			}			
		}
		
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		
		World fromWorld = event.getFrom().getWorld();
		World toWorld = event.getTo().getWorld();
		
		if (Manager.isInApocalypse(fromWorld)) {
			Manager.getZombieApocalypse(fromWorld).removePlayer(event.getPlayer());
		}
		if (Manager.isInApocalypse(toWorld)) {
			Manager.getZombieApocalypse(toWorld).addPlayer(event.getPlayer());
		}
		if (Manager.isInApocalypse(fromWorld) && toWorld.getEnvironment().equals(Environment.NETHER)) {
			Manager.getZombieApocalypse(fromWorld).addPlayer(event.getPlayer());
		}
		if (Manager.isInApocalypse(fromWorld) && toWorld.getEnvironment().equals(Environment.THE_END)) {
			Manager.getZombieApocalypse(fromWorld).removePlayer(event.getPlayer());
		}
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		World world = event.getPlayer().getWorld();
		
		if (Manager.isInApocalypse(world)) {
			Manager.getZombieApocalypse(world).addPlayer(event.getPlayer());
		}
		
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		World world = event.getPlayer().getWorld();
		
		if (Manager.isInApocalypse(world)) {
			Manager.getZombieApocalypse(world).removePlayer(event.getPlayer());
		}
		
	}
	
	
}
