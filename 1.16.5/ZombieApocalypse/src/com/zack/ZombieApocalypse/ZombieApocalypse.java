package com.zack.ZombieApocalypse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.zack.ZombieApocalypse.Mobs.ApocalypseZombie;
import com.zack.ZombieApocalypse.Mobs.ApocalypseZombifiedPiglin;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.WorldServer;

public class ZombieApocalypse extends BukkitRunnable {

	private World world;
	private Random random;
	private List<EmergingZombie> emergingZombies;
	private int nightNumber;
	private boolean isNight;
	private List<UUID> players;
	
	public ZombieApocalypse(World world) {
		this.world = world;
		this.random = new Random();
		this.emergingZombies = new ArrayList<>();
		this.nightNumber = 0;
		this.isNight = false;
		this.players = new ArrayList<>();
		for (Player player : world.getPlayers()) {
			players.add(player.getUniqueId());
		}
		
		this.runTaskTimer(Main.getInstance(), 0, 20); // once a second
	}
	
	@Override
	public void run() {
		// World.getTime() is analogous to hours * 1000;
		// In Minecraft night for monsters begins at ~13000 and ends at ~23000
		// Generate emergingZombies
		if (Manager.isNight(world)) {
			if (!isNight) {
				isNight = true;
				nightNumber++;
				switch (random.nextInt(5)) {
				case 0: Manager.sendWorldMessage(world, ChatColor.GRAY + "The apocalypse draws near..."); break;
				case 1: Manager.sendWorldMessage(world, ChatColor.GRAY + "The call for brains draws stronger..."); break;
				case 2: Manager.sendWorldMessage(world, ChatColor.GRAY + "The ground begins to stir..."); break;
				case 3: Manager.sendWorldMessage(world, ChatColor.GRAY + "Mmggh grrruhhh hrrmmm..."); break;
				case 4: Manager.sendWorldMessage(world, ChatColor.GRAY + "The smell of decaying flesh fills your nose..."); break;
				}
			}
		} else {
			if (isNight) {
				isNight = false;
				switch (random.nextInt(5)) {
				case 0: Manager.sendWorldMessage(world, ChatColor.GRAY + "Dawn brings a glimmer of hope..."); break;
				case 1: Manager.sendWorldMessage(world, ChatColor.GRAY + "The apocalypse comes to an end..."); break;
				case 2: Manager.sendWorldMessage(world, ChatColor.GRAY + "The rumbling subsides..."); break;
				}
			}
		}
		
		// Start an emergingZombie if the player is in the correct setting
		if (players.size() > 0) {
			Player possibleSpawnPlayer = Bukkit.getPlayer(players.get(random.nextInt(players.size())));
			if (isNight && !Manager.getPlayerWorldEnvironment(possibleSpawnPlayer).equals(Environment.THE_END)
					|| Manager.getPlayerWorldEnvironment(possibleSpawnPlayer).equals(Environment.NETHER)) {
				// Basically 10 waves
				if (nightNumber >= 10 || random.nextInt(10 - nightNumber) == 0) {
					// Start an emerging zombie around a random player
					emergingZombies.add(new EmergingZombie(Manager.getEmergenceLocation(
							possibleSpawnPlayer.getLocation())));
				}
			}
		}
			
		
		// Update emergingZombies
		List<EmergingZombie> toRemove = new ArrayList<>();
		for (EmergingZombie ez : emergingZombies) {
			ez.update();
			if (ez.getCounter() >= 5) {
				toRemove.add(ez);
				ez.cancel();
				// Spawn the zombie
				spawnZombie(ez.getLocation());
			}
		}
		// Remove necessary emergingZombies
		for (EmergingZombie ez : toRemove) {
			emergingZombies.remove(ez);
		}
		toRemove.clear();
	}
	
	public void endApocalypse(boolean killZombies) {
		// End runnable
		this.cancel();
		// Stop emerging zombies and kill the current ones if specified
		for (EmergingZombie ez : emergingZombies) {
			ez.cancel();
		}
		emergingZombies.clear();
		if (killZombies) {
			for(Entity entity : world.getEntities()) {
				if (entity instanceof ApocalypseZombie || entity instanceof ApocalypseZombifiedPiglin) {
					entity.remove();
				}
			}
		}
		
		players.clear();
	}
	
	public World getWorld() { return world; }
	
	public void addPlayer(Player player) {
		if (!players.contains(player.getUniqueId())) {
			players.add(player.getUniqueId());
		}
	}
	public void removePlayer(Player player) {
		if (players.contains(player.getUniqueId())) {
			players.remove(player.getUniqueId());
		}
	}
	
	private void spawnZombie(Location location) {
		WorldServer spawnWorld = ((CraftWorld) location.getWorld()).getHandle();
		LivingEntity zombieEntity;
		if (location.getWorld().getName().contains("_nether")) {
			ApocalypseZombifiedPiglin zombifiedPiglin = new ApocalypseZombifiedPiglin(location.add(0.5, 0, 0.5), Manager.getZombieType(nightNumber));
			spawnWorld.addEntity(zombifiedPiglin);
			// Cast to the livingEntity currently in game in order to add equipment, potion effects, and the exit velocity
			zombieEntity = ((LivingEntity) zombifiedPiglin.getBukkitEntity());
			Manager.equipZombie(zombieEntity, zombifiedPiglin.getType(), world.getTime());
		} else {
			ApocalypseZombie zombie = new ApocalypseZombie(location.add(0.5, 0, 0.5), Manager.getZombieType(nightNumber));
			spawnWorld.addEntity(zombie);
			// Cast to the livingEntity currently in game in order to add equipment, potion effects, and the exit velocity
			zombieEntity = ((LivingEntity) zombie.getBukkitEntity());
			Manager.equipZombie(zombieEntity, zombie.getType(), world.getTime());
		}
		zombieEntity.setVelocity(new Vector(0, 0.4, 0));
	}
	
}
