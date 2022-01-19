package com.zack.minigame_PvPArena.ArenaClasses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.zack.minigame_PvPArena.Manager;
import com.zack.minigame_PvPArena.Kits.Types.KitType;

public class GameManager {
	
	private static ItemStack trackingCompass;
	private static ItemStack powerCore;
	private static ItemStack booze; // Bottle of honey, 30s confusion, 2 minutes strength
	private static ItemStack energyDrink; // Gives you 2 minutes of speed and 30 seconds of regeneration
	private static ItemStack antiGravityBoots; // Stops fall damage then breaks
	private static ItemStack dagger; // Sharp iron sword
	private static ItemStack energyAxe; // Curse of Binding, gives anything it hits glowing and summons lightning
	private static ItemStack thunderBow; // Shoots arrows that summon lighting when they land
	
	public GameManager() {
		// Initiate custom items
		initItems();
	}
	
	public static boolean hasKit(Player player) {
		if (Manager.isPlaying(player)) {
			if (Manager.getPvPArena(player).getKits().containsKey(player.getUniqueId())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static KitType getKit(Player player) {
		if (Manager.isPlaying(player)) {
			if (Manager.getPvPArena(player).getKits().containsKey(player.getUniqueId())) {
				return Manager.getPvPArena(player).getKits().get(player.getUniqueId()).getType();
			}
		}
		
		return null;
	}
	
	public static ItemStack getItemInUse(Player player, DamageCause cause) {
		// Determine hand used and from there the item used
		if (cause.equals(DamageCause.ENTITY_ATTACK) || cause.equals(DamageCause.ENTITY_SWEEP_ATTACK)) {
			// Can only attack with melee weapons from main hand
			return player.getInventory().getItemInMainHand();
		} else if (cause.equals(DamageCause.PROJECTILE)) {
			// Would use main hand over off hand
			if (player.getInventory().getItemInMainHand().getType().equals(Material.BOW) ||
				player.getInventory().getItemInMainHand().getType().equals(Material.CROSSBOW) ||
				player.getInventory().getItemInMainHand().getType().equals(Material.EGG) ||
				player.getInventory().getItemInMainHand().getType().equals(Material.SNOWBALL) || 
				player.getInventory().getItemInMainHand().getType().equals(Material.TRIDENT)) {
				return player.getInventory().getItemInMainHand();
			} else {
				return player.getInventory().getItemInOffHand();
			}
		} else if (cause.equals(DamageCause.MAGIC)) {
			// Would use main hand over off hand
			if (player.getInventory().getItemInMainHand().getType().equals(Material.SPLASH_POTION)) {
				return player.getInventory().getItemInMainHand();
			} else {
				return player.getInventory().getItemInOffHand();
			}
		} else {
			// Default to main hand
			return player.getInventory().getItemInMainHand();
		}
	}
	
	public static ItemStack getTrackingCompass() { return trackingCompass; }
	public static ItemStack getPowerCore() { return powerCore; }
	
	public static List<String> getLore(String line) {
		List<String> lore = new ArrayList<>();
		lore.add(line);
		return lore;
	}
	
	public static ItemStack getAndRemoveItem(Material type, Player player) {
		ItemStack item = null;
		for (int i = 0; i < player.getInventory().getSize(); i++) {
			if (player.getInventory().getItem(i) != null &&
					player.getInventory().getItem(i).getType().equals(type)) {
				item = player.getInventory().getItem(i);
				if (item.getAmount() != 1) {
					item.setAmount(item.getAmount() - 1);
					player.getInventory().setItem(i, item);
				} else {
					player.getInventory().setItem(i, new ItemStack(Material.AIR));
				}
				break;
			}
		}
		return item;
	}
	
	public static ItemStack getBlockAmmo(Material type) {
		ItemStack ammo = new ItemStack(Material.CLAY_BALL, 1);
		ItemMeta ammoMeta = ammo.getItemMeta();
		ammoMeta.setDisplayName(ChatColor.GRAY + "Block ammo");
		ammoMeta.setLocalizedName(type + "");
		ammoMeta.setLore(getLore(ChatColor.GRAY + "" + type));
		List<String> lore = ammoMeta.getLore();
		lore.add(ChatColor.GRAY + "Each ammo does 0.1 damage");
		ammoMeta.setLore(lore);
		ammo.setItemMeta(ammoMeta);
		return ammo;
	}
	
	public static ItemStack getLog() {
		ItemStack log = new ItemStack(Material.OAK_LOG);
		ItemMeta logMeta = log.getItemMeta();
		logMeta.setDisplayName("Log");
		logMeta.setLocalizedName("log");
		logMeta.setLore(getLore(ChatColor.GRAY + "Throw to stun enemies"));
		log.setItemMeta(logMeta);

		return log;
	}
	
	public static void loadChest(Block block) {
		block.setType(Material.CHEST);
		Chest chest = (Chest) block.getState();
		int itemLocation;
		
		// Make sure Chest isn't full
		boolean full = true;
		for (int i = 0; i < chest.getInventory().getSize(); i++) {
			if (chest.getInventory().getItem(i) == null) {
				full = false;
				break;
			}
		}
		if (full == true) { return; }
		
		// Get an unoccupied location
		do {
			itemLocation = Manager.getRandom().nextInt(chest.getBlockInventory().getSize());
		} while (chest.getBlockInventory().getItem(itemLocation) != null);
		
		// Add something based on it's likelihood
		ItemStack item;
		int randomChoice = Manager.getRandom().nextInt(100) + 1;
		if (randomChoice < 10) { item = powerCore; }
		else if (randomChoice < 15) { item = getLog(); item.setAmount(Manager.getRandom().nextInt(5) + 3); }
		else if (randomChoice < 25) { item = booze; }
		else if (randomChoice < 35) { item = energyDrink; }
		else if (randomChoice < 40) { item = antiGravityBoots; }
		else if (randomChoice < 45) { item = dagger; }
		else if (randomChoice < 55) { item = energyAxe; }
		else if (randomChoice < 60) { item = thunderBow; }
		else if (randomChoice < 65) { item = new ItemStack(Material.FIRE_CHARGE, Manager.getRandom().nextInt(2) + 2);; }
		else if (randomChoice < 75) { item = new ItemStack(Material.ARROW, Manager.getRandom().nextInt(24) + 8); }
		else if (randomChoice < 85) { item = new ItemStack(Material.SNOWBALL, Manager.getRandom().nextInt(12) + 4); }
		else if (randomChoice < 90) { item = new ItemStack(Material.BAKED_POTATO, Manager.getRandom().nextInt(11) + 5); }
		else if (randomChoice < 93) { item = new ItemStack(Material.COOKIE, Manager.getRandom().nextInt(5) + 3); }
		else if (randomChoice < 95) { item = new ItemStack(Material.GOLDEN_APPLE); }
		else if (randomChoice < 97) { item = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE); }
		else { item = new ItemStack(Material.BREAD, Manager.getRandom().nextInt(9) + 3); }
		
		chest.getBlockInventory().setItem(itemLocation, item);
		
		if (item.equals(thunderBow)) {
			// Make sure Chest isn't full
			full = true;
			for (int i = 0; i < chest.getInventory().getSize(); i++) {
				if (chest.getInventory().getItem(i) == null) {
					full = false;
					break;
				}
			}
			if (full == true) { return; }
			// Add arrows too
			do {
				itemLocation = Manager.getRandom().nextInt(chest.getBlockInventory().getSize());
			} while (chest.getBlockInventory().getItem(itemLocation) != null);
			chest.getBlockInventory().setItem(itemLocation, new ItemStack(Material.ARROW, Manager.getRandom().nextInt(24) + 8));
		}
	}
	
	public static void comboEffect(Location location) {
		for (int i = 0; i < 100; i++) {
			// particles going random directions
			location.getWorld().spawnParticle(Particle.SNOWFLAKE, location, 0, 
					(Manager.getRandom().nextInt(3)+1) * 0.12 * ((Manager.getRandom().nextDouble() * 2) - 1), 
					(Manager.getRandom().nextInt(3)+1) * 0.12 * ((Manager.getRandom().nextDouble() * 2) - 1),
					(Manager.getRandom().nextInt(3)+1) * 0.12 * ((Manager.getRandom().nextDouble() * 2) - 1));
		}
	}
	
	public static void candleEffect(Material type, Player player, Location location) {
		AreaEffectCloud effectCloud = (AreaEffectCloud) location.getWorld().spawnEntity(location, EntityType.AREA_EFFECT_CLOUD);
		effectCloud.clearCustomEffects();
		effectCloud.setDuration(160); // 8 seconds
		effectCloud.setRadius(3.0f); // 2.5 block radius
		effectCloud.setRadiusPerTick(-0.01f); // Decrease the radius 0.01 per tick
		effectCloud.setRadiusOnUse(0); // Doesn't decrease the radius on use
		effectCloud.setDurationOnUse(20); // Decreases the cloud's duration by a second for each use
		effectCloud.setReapplicationDelay(10); // re applies every 0.5 seconds
		effectCloud.setSource(player);
		
		switch (type) {
		case GREEN_CANDLE: 
			// Effect, overwrite other of the same effects on the player
			effectCloud.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 100, 1), true);
			effectCloud.setColor(Color.GREEN);
			break;
		case LIGHT_BLUE_CANDLE: 
			// Slow means add frost ticks
			effectCloud.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1), true);
			effectCloud.setColor(Color.WHITE);
			break;
		case ORANGE_CANDLE: 
			// Fire resistance means add fire ticks
			effectCloud.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 1), true);
			effectCloud.setColor(Color.ORANGE);
			break;
		default: break;
		}
	}
	
	public static void startTaintedTotemEffects(Player player) {
		player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
		player.setHealth(5);
		player.setAbsorptionAmount(4);
		player.playEffect(EntityEffect.TOTEM_RESURRECT);
		
		AreaEffectCloud healCloud = (AreaEffectCloud) player.getLocation().getWorld().spawnEntity(
				player.getLocation(), EntityType.AREA_EFFECT_CLOUD);
		healCloud.clearCustomEffects();
		healCloud.setDuration(160); // 8 seconds
		healCloud.setRadius(3.0f);
		healCloud.setRadiusPerTick(0);
		healCloud.setRadiusOnUse(0);
		healCloud.setDurationOnUse(0);
		healCloud.setReapplicationDelay(10); // re applies every 0.5 seconds
		healCloud.setSource(player);
		healCloud.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 40, 1), true);
		healCloud.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1), true);
		healCloud.setColor(Color.PURPLE);
	}
	
	public static void laserEffect(Player player) {
		// Laser effect only lasts 256 blocks
		// (render distance is 16chunks x 16block/chunk)
		Location spawnLocation = player.getLocation().add(0, 1.2, 0);
		for (int i = 0; i < 256; i++) {
			spawnLocation.add(player.getLocation().getDirection());
			if (!spawnLocation.getBlock().getType().equals(Material.AIR))
				return;
			// Colored
			// bukkit.org.Color, size
			DustOptions dustOptions = new DustOptions(Color.RED, 2);
			// particle, location, amount, options for color and size
			player.getWorld().spawnParticle(Particle.REDSTONE, spawnLocation, 1, dustOptions);
		}
	}
	
	public static void shootRailgun(Player player) {
		// Remove power crystal
		getAndRemoveItem(Material.AMETHYST_SHARD, player);
		// Create laser while scanning for enemies to hit
		Location spawnLocation = player.getLocation().add(0, 1.2, 0);
		DustOptions dustOptions = new DustOptions(Color.AQUA, 2);
		DustOptions dustOptions2 = new DustOptions(Color.BLUE, 2);
		
		for (int i = 0; i < 512; i++) {
			// Check one location and use the other to add more color
			spawnLocation.add(player.getLocation().getDirection().multiply(0.25)); // divide by 4
			if (!spawnLocation.getBlock().getType().equals(Material.AIR))
				return;
			player.getWorld().spawnParticle(Particle.REDSTONE, spawnLocation, 1, dustOptions);
			
			Collection<Entity> nearbyEntities = spawnLocation.getWorld().getNearbyEntities(spawnLocation, 1.0, 1.0, 1.0);
			if (nearbyEntities.size() != 0) {
				for (Entity entity : nearbyEntities) {
					if (entity instanceof LivingEntity && entity.getUniqueId() != player.getUniqueId()) {
						((LivingEntity) entity).damage(40); // big damage
						return;
					}
				}
			}
			
			// mid 
			spawnLocation.add(player.getLocation().getDirection().multiply(0.25)); // divide by 4
			if (!spawnLocation.getBlock().getType().equals(Material.AIR))
				return;
			player.getWorld().spawnParticle(Particle.REDSTONE, spawnLocation, 1, dustOptions2);
		}
	}
	
	public static void removeDogs(Player player) {
		for (Entity entity : player.getWorld().getEntities()) {
			if (entity instanceof Wolf) {
				Wolf wolf = (Wolf) entity;
				if (wolf.getOwner().equals(player)) {
					entity.remove();
				}
			}
		}
	}
	
	private static void initItems() {
		// Tracking compass
		trackingCompass = new ItemStack(Material.COMPASS);
		ItemMeta trackingCompassMeta = trackingCompass.getItemMeta();
		trackingCompassMeta.setDisplayName(ChatColor.WHITE + "Tracking Compass");
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GRAY + "Targets the nearest enemy within");
		lore.add(ChatColor.GRAY + "50 blocks of your location");
		trackingCompassMeta.setLore(lore);
		trackingCompassMeta.setLocalizedName("trackingCompass");
		trackingCompass.setItemMeta(trackingCompassMeta);
		
		// Power core
		powerCore = new ItemStack(Material.END_CRYSTAL);
		ItemMeta powerCoreMeta = powerCore.getItemMeta();
		powerCoreMeta.setDisplayName(ChatColor.WHITE + "Power Core");
		lore.clear();
		lore.add(ChatColor.GRAY + "Boosts compass range by 100 blocks");
		powerCoreMeta.setLore(lore);
		powerCoreMeta.setLocalizedName("powerCore");
		powerCore.setItemMeta(powerCoreMeta);
		
		// Booze
		booze = new ItemStack(Material.HONEY_BOTTLE);
		ItemMeta boozeMeta = booze.getItemMeta();
		boozeMeta.setDisplayName("Booze");
		lore.clear();
		lore.add(ChatColor.GRAY + "The world may be spinning,");
		lore.add(ChatColor.GRAY + "but you sure will feel stronger");
		boozeMeta.setLore(lore);
		boozeMeta.setLocalizedName("booze");
		booze.setItemMeta(boozeMeta);
		
		// Energy drink
		energyDrink = new ItemStack(Material.POTION);
		ItemMeta energyDrinkMeta = energyDrink.getItemMeta();
		energyDrinkMeta.setDisplayName("Energy Drink");
		lore.clear();
		lore.add(ChatColor.GRAY + "Temporarily increase your");
		lore.add(ChatColor.GRAY + "speed and regeneration");
		energyDrinkMeta.setLore(lore);
		energyDrinkMeta.setLocalizedName("energyDrink");
		energyDrink.setItemMeta(energyDrinkMeta);
		
		// Anti-Gravity boots
		antiGravityBoots = new ItemStack(Material.NETHERITE_BOOTS);
		ItemMeta antiGravityBootsMeta = antiGravityBoots.getItemMeta();
		antiGravityBootsMeta.setDisplayName("Anti-Gravity Boots");
		lore.clear();
		lore.add(ChatColor.GRAY + "Completely negates all fall damage");
		lore.add(ChatColor.GRAY + "Breaks after one use");
		antiGravityBootsMeta.setLore(lore);
		antiGravityBootsMeta.setLocalizedName("antiGravityBoots");
		antiGravityBoots.setItemMeta(antiGravityBootsMeta);
		
		// Dagger
		dagger = new ItemStack(Material.IRON_SWORD);
		ItemMeta daggerMeta = dagger.getItemMeta();
		daggerMeta.setDisplayName("Dagger");
		dagger.setItemMeta(daggerMeta);
		dagger.addEnchantment(Enchantment.DAMAGE_ALL, 5);
		
		// Energy Axe
		energyAxe = new ItemStack(Material.NETHERITE_AXE);
		ItemMeta energyAxeMeta = energyAxe.getItemMeta();
		energyAxeMeta.setDisplayName("Energy Axe");
		lore.clear();
		lore.add(ChatColor.GRAY + "Causes entities to glow");
		lore.add(ChatColor.GRAY + "and can summon lightning");
		energyAxeMeta.setLore(lore);
		energyAxeMeta.setLocalizedName("energyAxe");
		energyAxe.setItemMeta(energyAxeMeta);
		energyAxe.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
		energyAxe.addUnsafeEnchantment(Enchantment.BINDING_CURSE, 1);
		
		// Thunder Bow
		thunderBow = new ItemStack(Material.BOW);
		ItemMeta thunderBowMeta = thunderBow.getItemMeta();
		thunderBowMeta.setDisplayName("Thunder Bow");
		lore.clear();
		lore.add(ChatColor.GRAY + "Arrows summon lightning");
		lore.add(ChatColor.GRAY + "on entities that are hit");
		thunderBowMeta.setLore(lore);
		thunderBowMeta.setLocalizedName("thunderBow");
		thunderBow.setItemMeta(thunderBowMeta);
		thunderBow.addEnchantment(Enchantment.ARROW_DAMAGE, 5);
	}
	
}
