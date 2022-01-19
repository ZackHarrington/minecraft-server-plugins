package com.zack.ZombieApocalypse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.zack.ZombieApocalypse.Mobs.ZombieType;

public class ZombieApocalypse extends BukkitRunnable {

	private World world;
	private Random random;
	private List<EmergingZombie> emergingZombies;
	private HashMap<UUID, ZombieType> rangedZombies; 
	private int nightNumber;
	private boolean isNight;
	private List<UUID> players;
	
	public ZombieApocalypse(World world) {
		this.world = world;
		this.random = new Random();
		this.emergingZombies = new ArrayList<>();
		this.rangedZombies = new HashMap<>();
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
				// Basically 6 waves
				if (nightNumber >= 6 || random.nextInt(6 - nightNumber) == 0) {
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
				spawnZombie(ez.getLocation(), Manager.getZombieType(nightNumber));
			}
		}
		// Remove necessary emergingZombies
		for (EmergingZombie ez : toRemove) {
			emergingZombies.remove(ez);
		}
		toRemove.clear();
		
		// Update ranged zombies
		for (UUID rangedZombie : rangedZombies.keySet()) {
			switch(rangedZombies.get(rangedZombie)) {
			case ARCHER:
				if (random.nextInt(5) == 0) { // 1 in 5 chance
					Vector nearestPlayerVector = Manager.getNearestPlayerVector(rangedZombie, 50);
					if (nearestPlayerVector != null) { // There is a player near
						Entity zombie = Bukkit.getEntity(rangedZombie);
						Location location = zombie.getLocation();
						location.add(0, 1.5, 0);
						zombie.getWorld().spawnArrow(location, nearestPlayerVector, 1.0f, 12);
					}
				}
				break;
			case MAGE:
				if (random.nextInt(15) == 0) { // 1 in 15 chance
					Vector nearestPlayerVector = Manager.getNearestPlayerVector(rangedZombie, 50);
					if (nearestPlayerVector != null) { // There is a player near
						Entity zombie = Bukkit.getEntity(rangedZombie);
						Location location = zombie.getLocation();
						location.add(0, 1.5, 0);
						Fireball fireball = (Fireball) zombie.getWorld().spawnEntity(location, EntityType.FIREBALL);
						fireball.setVelocity(nearestPlayerVector);
					}
				}
				break;
			case SUICIDE_BOMBER:
				Vector nearestPlayerVector = Manager.getNearestPlayerVector(rangedZombie, 2);
				if (nearestPlayerVector != null) { // There is a player near
					Entity zombie = Bukkit.getEntity(rangedZombie);
					Location location = zombie.getLocation();
					location.add(0, 1, 0);
					zombie.getWorld().createExplosion(location, 3.0f);
				}
				break;
			default: break;
			}
		}
		
	}
	
	public void endApocalypse(boolean killZombies) {
		// End runnable
		this.cancel();
		// Stop emerging zombies and kill the current ones if specified
		for (EmergingZombie ez : emergingZombies) {
			ez.cancel();
		}
		emergingZombies.clear();
		rangedZombies.clear();
		if (killZombies) {
			for(Entity entity : world.getEntities()) {
				if (entity instanceof Zombie) {
					entity.remove();
				}
			}
		}
		
		players.clear();
	}
	
	public World getWorld() { return world; }
	public boolean isRangedZombie(UUID uuid) { return rangedZombies.containsKey(uuid); }
	public void removeRangedZombie(UUID uuid) {
		if (rangedZombies.containsKey(uuid)) {
			rangedZombies.remove(uuid);
		}
	}
	
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
	
	public void spawnZombie(Location location, ZombieType type) {
		LivingEntity zombieEntity;
		if (!location.getWorld().getName().contains("_nether")) {			
			zombieEntity = (LivingEntity) location.getWorld().spawnEntity(location.add(0.5, 0, 0.5), EntityType.ZOMBIE);
		} else {
			zombieEntity = (LivingEntity) location.getWorld().spawnEntity(location.add(0.5, 0, 0.5), EntityType.ZOMBIFIED_PIGLIN);
		}
		checkRanged(type, zombieEntity.getUniqueId());
		
		// Initialize Zombie
		zombieEntity.setCustomName(ChatColor.ITALIC + "" + ChatColor.WHITE + Manager.getRandomZombieName(type));
		zombieEntity.setCustomNameVisible(false);
		zombieEntity.setCanPickupItems(false);
		zombieEntity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(type.getSpeedMultiplier());
		zombieEntity.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(50.0D);
		Manager.equipZombie(zombieEntity, type, world.getTime());
		zombieEntity.setVelocity(new Vector(0, 0.4, 0));
	}
	
	private void checkRanged(ZombieType type, UUID uuid) {
		if (type.equals(ZombieType.ARCHER) ||
				type.equals(ZombieType.MAGE) ||
				type.equals(ZombieType.SUICIDE_BOMBER)) {
			rangedZombies.put(uuid, type);
		}
	}
	
}
