package com.zack.ZombieApocalypse;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.zack.ZombieApocalypse.Mobs.ApocalypseZombie;
import com.zack.ZombieApocalypse.Mobs.ZombieType;

import net.minecraft.server.v1_16_R3.WorldServer;

public class ZombieApocalypseListener implements Listener {

	@EventHandler
	public void onPlayerSleep(PlayerBedEnterEvent event) {
		
		World world = event.getPlayer().getWorld();
		
		if (Manager.isNight(world)) {
			if (Manager.isInApocalypse(world)) {
				event.setCancelled(true);
				
				event.getBed().breakNaturally();
				WorldServer spawnWorld = ((CraftWorld) world).getHandle();
				
				ApocalypseZombie zombie = new ApocalypseZombie(
							event.getBed().getLocation().add(0.5, 0, 0.5), ZombieType.SLEEP);
				spawnWorld.addEntity(zombie);
				// Cast to the livingEntity currently in game in order to add equipment and potion effects
				LivingEntity zombieEntity = ((LivingEntity) zombie.getBukkitEntity());
				Manager.equipZombie(zombieEntity, zombie.getType(), world.getTime());
					
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
