package com.zack.GodEnchantments.Managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import com.zack.GodEnchantments.Main;
import com.zack.GodEnchantments.Mobs.Louce;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.WorldServer;

public class GodManager {

	private List<Player> invulnerablePlayers;
	private HashMap<Player, AscensionManager> playersAscending;
	private List<Player> playersSmashing;
	private HashMap<Player, Integer> smashHeight;
	private HashMap<Player, Long> checkFlightTime;
	private HashMap<Player, Integer> flightCounter;
	private List<Player> playersLevitating;
	private List<Player> playersInCooldown;
	private HashMap<Player, Integer> inventoryRunnables;
	private HashMap<Player, Integer> inventoryIndexes;
	private Random rand;
	
	public GodManager() {
		invulnerablePlayers = new ArrayList<>();
		playersAscending = new HashMap<>();
		playersSmashing = new ArrayList<>();
		smashHeight = new HashMap<>();
		checkFlightTime = new HashMap<>();
		flightCounter = new HashMap<>();
		playersLevitating = new ArrayList<>();
		playersInCooldown = new ArrayList<>();
		inventoryRunnables = new HashMap<>();
		inventoryIndexes = new HashMap<>();
		rand = new Random();
	}
	
	public void beginAscension(Player player) {
		// Place player into list so they may not eat another cookie until over
		playersAscending.put(player, new AscensionManager(player));
		invulnerablePlayers.add(player);
		
		// After a little while smash the player to the ground fully completing the ritual
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
			public void run() {
				if (playersAscending.containsKey(player) && !(playersAscending.get(player).checkCancelled())) {
					player.setGravity(true);
					player.setAllowFlight(false);
					// Make them smash
					playersSmashing.add(player);
					smashHeight.put(player, (int)player.getLocation().getY());
				}
			}
		}, 1180L);
		
		// End invincibility slightly after so remnant lightning and explosion doesn't harm you
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
			public void run() {
				if (playersAscending.containsKey(player) && !(playersAscending.get(player).checkCancelled())) {
					// End invincibility and movement stoppage
					playersAscending.remove(player);
				}
			}
		}, 1200L);
	}
	
	// Getters
	public boolean checkInvulnerable(Player player) {
		return invulnerablePlayers.contains(player);
	}
	public boolean checkAscension(Player player) {
		return playersAscending.containsKey(player);
	}
	public boolean checkSpectacle(Player player) {
		if (checkAscension(player)) {
			return playersAscending.get(player).checkSpectacle();
		}
		else {
			return false;
		}
	}
	public boolean checkSmashing(Player player) {
		return playersSmashing.contains(player);
	}
	public boolean checkLevitate(Player player) {
		return playersLevitating.contains(player);
	}
	public boolean checkCooldown(Player player) {
		return playersInCooldown.contains(player);
	}
	public boolean checkHelmet(Player player) {
		return (player.getInventory().getHelmet() != null &&
				player.getInventory().getHelmet().getEnchantments().containsKey(Enchantment.getByKey(Main.godEnchantment.getKey())));
	}
	public boolean checkChestplate(Player player) {
		return (player.getInventory().getChestplate() != null &&
				player.getInventory().getChestplate().getEnchantments().containsKey(Enchantment.getByKey(Main.godEnchantment.getKey())));
	}
	public boolean checkLeggings(Player player) {
		return (player.getInventory().getLeggings() != null &&
				player.getInventory().getLeggings().getEnchantments().containsKey(Enchantment.getByKey(Main.godEnchantment.getKey())));
	}
	public boolean checkBoots(Player player) {
		return (player.getInventory().getBoots() != null &&
				player.getInventory().getBoots().getEnchantments().containsKey(Enchantment.getByKey(Main.godEnchantment.getKey())));
	}
	// Setters
	public void setFireResistance(Player player) {
		// Add for 30 seconds, harm is canceled so it is more for show
		player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 600, 2));
	}
	public void setConduitPower(Player player) {
		// Add for 30 seconds, effect is continually updated in water so is more for show
		player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 600, 2));
	}
	
	public void cancelAscension(Player player) {
		// Remove the player from lists to stop any further runnables
		if (checkInvulnerable(player)) {
			invulnerablePlayers.remove(player);
		}
		if (checkSpectacle(player)) {
			playersAscending.get(player).cancelSpectacle();
		}
		if (checkAscension(player)) {
			playersAscending.get(player).cancelAscension();
		}
		// Reset player to defaults
		player.setGravity(true);
		player.setAllowFlight(false);
		// Begin inventory deletion
		removeInventory(player);
		// Put the player into a cooldown to ensure that all of the scheduled runnables finish before attempting to start again
		playersInCooldown.add(player);
		// End the cool down after all runnables have had a time to finish and officially remove the player from the ascension
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() { 
			public void run() {
				if (checkAscension(player)) {
					playersAscending.remove(player);
				}
				playersInCooldown.remove(player);
			}
		}, 1000L);

	}
	
	public boolean flightStartCheck(Player player) {
		if (checkFlightTime.containsKey(player)) {
			int timesPressed = flightCounter.get(player) + 1;
			if (timesPressed >= 4) {
				// more than half a second has passed
				if (System.currentTimeMillis() - checkFlightTime.get(player) < 500) {
					return true;
				}
				checkFlightTime.remove(player);
				flightCounter.remove(player);
			} else {
				flightCounter.put(player, timesPressed);
			}
		} else {
			checkFlightTime.put(player, System.currentTimeMillis());
			flightCounter.put(player, 1);
		}
		return false;
	}
	public void updateFlightVariables(Player player) {
		if (checkFlightTime.containsKey(player)) {
			// more than half a second has passed
			if (System.currentTimeMillis() - checkFlightTime.get(player) > 500) {
				checkFlightTime.remove(player);
				flightCounter.remove(player);
			}
		}
	}
	public void startLevitation(Player player) {
		playersLevitating.add(player);
	}
	public void levitate(Player player) {
		if (checkLevitate(player)) {
			// Add for 30 seconds, is canceled so it is more for show, multiplier
			player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 600, 2));
		}
	}
	public void cancelLevitation(Player player) {
		if (playersLevitating.contains(player)) {
			playersLevitating.remove(player);
			player.removePotionEffect(PotionEffectType.LEVITATION);
		}
	}
	
	public void startSmash(Player player, int height) {
		cancelLevitation(player);
		playersSmashing.add(player);
		smashHeight.put(player, height);
		invulnerablePlayers.add(player);
	}
	public void smashEffect(Player player) {
		// Set velocity
		player.setVelocity(new Vector(0, -2, 0));
		
		// Add particles starting at feet and moving outward
		int num = 50;
		for (int i = 0; i < num; i++) {
			// Angle of travel
			double angle = (double)i / (double)num * 2 * Math.PI;
			double speed = 0.25;
			double xVel = Math.cos(angle) * speed;
			double zVel = Math.sin(angle) * speed;
			// Location
			Location spawnLoc = player.getLocation().subtract(0, 5, 0);
			// Particle particle, location, count, double offsetX, double offsetY, double offsetZ
			// If you make count 0, the offset parameters will act as velocity
			player.getWorld().spawnParticle(Particle.CLOUD, spawnLoc, 0, xVel, 0, zVel);
		}
	}
	public void cancelSmash(Player player) {
		playersSmashing.remove(player);
		smashHeight.remove(player);
		invulnerablePlayers.remove(player);
		// set velocity back to 1
		player.setVelocity(new Vector(0, -1, 0));
	}
	public void endSmash(Player player) {
		// Remove player from the list
		if (playersSmashing.contains(player)) {		
			// Create particle effect and leave mark on ground
			HashMap<Player, Double> smashRadius = new HashMap<>();
			smashRadius.put(player, 0.5);
			int fallDistance = smashHeight.get(player) - (int)player.getLocation().getY();
			// Initialize entity list
			List<Entity> entitiesHarmed = new ArrayList<>();
			int smashEndRunnable;
		
			if (fallDistance >= 5) {
				smashEndRunnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
					public void run() {
						Location middleLoc = player.getLocation().subtract(0, 1, 0);
						double radius = smashRadius.get(player);
						smashRadius.put(player, radius + 0.5);
						
						// Harm Entities
						// x1, y1, z1, x2, y2, z2
						BoundingBox boundingBox = new BoundingBox(
								middleLoc.getX() + radius, middleLoc.getY() + 3, middleLoc.getZ() + radius, 
								middleLoc.getX() - radius, middleLoc.getY(), middleLoc.getZ() - radius);
						Collection<Entity> nearbyEntities = player.getWorld().getNearbyEntities(boundingBox);
						double damage = radius;
						
						for (Entity e : nearbyEntities) {
							if (!entitiesHarmed.contains(e)) {
								// Must be a living entity to damage
								if (e instanceof LivingEntity) {
									((LivingEntity) e).damage(damage, player);
								}
								entitiesHarmed.add(e);
							}
						}
						
						// Particles
						int num = (int) (50 * radius);
						for (int i = 0; i < num; i++) {
							if (rand.nextInt((int)(radius/4) + 1) == 0) { // 1 in radius chance so it gets less dense further away
								// Angle of travel
								double angle = (double)i / (double)num * 2 * Math.PI;
								double xPos = middleLoc.getX() + Math.cos(angle) * radius;
								double zPos = middleLoc.getZ() + Math.sin(angle) * radius;
								double ySpeed = 0.1;
								// Location
								Location blockLoc = new Location(player.getWorld(), xPos, middleLoc.getY(), zPos);
								Location spawnLoc = new Location(player.getWorld(), xPos, blockLoc.getY() + 1.5, zPos);
								if (!blockLoc.getBlock().getType().equals(Material.AIR)) { // not air
									// Particle particle, location, count, double offsetX, double offsetY, double offsetZ, T data
									player.getWorld().spawnParticle(Particle.BLOCK_CRACK, spawnLoc, 0, 0, ySpeed, 0, blockLoc.getBlock().getType().createBlockData());
								} else {
									if (rand.nextInt(5) == 0) {
										player.getWorld().spawnParticle(Particle.CLOUD, spawnLoc, 0, 0, 0, 0);
									}
								}
							}
						}
					}
				}, 0L, 1L);
				
				// Stop 2 seconds later max
				int stopAfterTicks = (int) (fallDistance / 2.0);
				if (stopAfterTicks >= 40) {
					stopAfterTicks = 40;
				}
				Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
					public void run() {
						Bukkit.getScheduler().cancelTask(smashEndRunnable);
						if (!entitiesHarmed.isEmpty()) {
							entitiesHarmed.removeAll(entitiesHarmed);
						}
					}
				}, (long)stopAfterTicks);	
			}
			
			playersSmashing.remove(player);
			smashHeight.remove(player);
			// End invulnerability after .5 seconds to insure no fall damage is taken
			Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
				public void run() {
					invulnerablePlayers.remove(player);
				}
			}, 10L);
		}
	}
	
	public void destroy() {
		// Essentially destructor
		// If server stopped and there are players in ascension
		for (Player player : playersAscending.keySet()) {
			// Reset player to defaults
			player.setGravity(true);
			player.setAllowFlight(false);
			// Give them back their cookies
			ItemStack godCookie = new ItemStack(Material.COOKIE, 1);
			godCookie.addEnchantment(Main.godEnchantment, 1);
			ItemMeta godCookieMeta = godCookie.getItemMeta(); 
			godCookieMeta.setDisplayName(
					ChatColor.GOLD.toString() + ChatColor.GOLD + "Curious Cookie");
			godCookie.setItemMeta(godCookieMeta);
			player.getInventory().addItem(godCookie);
			// Call destructor for ascending manager
			playersAscending.get(player).destroy();
		}
		// Cancel levitation if needed
		for (Player player : playersLevitating) {
			cancelLevitation(player);
		}
		// Cancel inventory deletion if needed
		for (Player player : inventoryRunnables.keySet())
		{
			Bukkit.getScheduler().cancelTask(inventoryRunnables.get(player));
		}
		// Delete Lists
		invulnerablePlayers.clear();
		playersAscending.clear();
		playersSmashing.clear();
		smashHeight.clear();
		checkFlightTime.clear();
		flightCounter.clear();
		playersLevitating.clear();
		playersInCooldown.clear();
		inventoryRunnables.clear();
		inventoryIndexes.clear();
	}
	
	// Private member functions --------------------------------------------------------------------------------------------
	private void removeInventory(Player player)
	{
		// Get inventory list
		ItemStack inventory[] = new ItemStack [41];
		int index = 0;
		for (ItemStack item : player.getInventory().getContents())
		{
			inventory[index] = item;
			index++;
		}
		
		inventoryIndexes.put(player, 0);
		ItemStack flesh = new ItemStack(Material.ROTTEN_FLESH, 1);
		inventoryRunnables.put(player, Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			public void run() {
				int i = getInventoryIndex(player);
				incrementInventoryIndex(player);
				
				// Check for being at the end
				if (i >= 41) {
					endInventoryRunnable(player);
				}
				else {
					// Make correct location and velocity vector
					double angle = rand.nextDouble() * 2 * Math.PI;
					double x = Math.cos(angle);
					double y = Math.sin(angle);
					Location summonLoc = new Location(player.getWorld(), player.getLocation().getX() + x * 0.75, 
							player.getLocation().getY() + rand.nextDouble() * 2, player.getLocation().getZ() + y * 0.75);
					
					// Create lice
					Louce louce;
					if (inventory[i] != null) {
						// Remove item
						if (inventory[i].isSimilar(player.getInventory().getHelmet())) // == only checks addresses
						{
							player.getInventory().setHelmet(null);
						}
						else if (inventory[i].isSimilar(player.getInventory().getChestplate()))
						{
							player.getInventory().setChestplate(null);
						}
						else if (inventory[i].isSimilar(player.getInventory().getLeggings()))
						{
							player.getInventory().setLeggings(null);
						}
						else if (inventory[i].isSimilar(player.getInventory().getBoots()))
						{
							player.getInventory().setBoots(null);
						}
						else {
							player.getInventory().remove(inventory[i]);
						}
						// Summon item louce
						louce = new Louce(summonLoc, inventory[i]);
					} else {
						// Summon flesh louce
						louce = new Louce(summonLoc, flesh);
					}
					// Spawn lice
					WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
					world.addEntity(louce);
					// Give it the correct velocity
					Entity louceEntity = ((Entity) louce.getBukkitEntity());
					louceEntity.setVelocity(new Vector(x * 0.1, 0, y * 0.1));
				}
			}
		}, 0L, 1L));
	}
	private int getInventoryIndex(Player player) {
		return inventoryIndexes.get(player);
	}
	private void incrementInventoryIndex(Player player) {
		inventoryIndexes.put(player, inventoryIndexes.get(player) + 1);
	}
	private void endInventoryRunnable(Player player)
	{
		Bukkit.getScheduler().cancelTask(inventoryRunnables.get(player));
		inventoryRunnables.remove(player);
		inventoryIndexes.remove(player);
	}
	
	
}
