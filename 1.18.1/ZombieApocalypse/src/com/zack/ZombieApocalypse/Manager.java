package com.zack.ZombieApocalypse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import com.zack.ZombieApocalypse.Mobs.ZombieType;

public class Manager {

	private static List<ZombieApocalypse> runningApocalypses;
	private static Random random;
	
	public Manager() {
		Manager.runningApocalypses = new ArrayList<>();
		Manager.random = new Random();
	}
	
	public static boolean beginZombieApocalypse(World world) {
		// Check the world currently has no running apocalypses
		for (ZombieApocalypse za : runningApocalypses) {
			if (world.equals(za.getWorld())) {
				return false;
			}
		}
		// Begin the apocalypse
		runningApocalypses.add(new ZombieApocalypse(world));
		
		return true;
	}
	
	public static boolean endZombieApocalypse(World world, boolean killZombies) {
		ZombieApocalypse toRemove = null; // Work around
		for (ZombieApocalypse za : runningApocalypses) {
			if (world.equals(za.getWorld())) {
				za.endApocalypse(killZombies);
				toRemove = za;
			}
		}
		if (toRemove != null) {
			runningApocalypses.remove(toRemove);
			return true;
		}
		
		return false;
	}
	
	public static void obliterate() { // Called when the server shuts down
		for (ZombieApocalypse za : runningApocalypses) {
			za.endApocalypse(true);
		}
		runningApocalypses.clear();
	}
	
	public static boolean isInApocalypse(World world) {
		for (ZombieApocalypse za : runningApocalypses) {
			if (world.equals(za.getWorld())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static ZombieApocalypse getZombieApocalypse(World world) {
		for (ZombieApocalypse za : runningApocalypses) {
			if (world.equals(za.getWorld())) {
				return za;
			}
		}
		
		return null;
	}
	
	public static void sendWorldMessage(World world, String message) {
		if (world != null) {
			for (Player player : world.getPlayers()) {
				player.sendMessage(message);
			}
		}
	}
	
	public static boolean isNight(World world) { return (world.getTime() > 13000 && world.getTime() < 23000); }
	
	public static Environment getPlayerWorldEnvironment(Player player) {
		return player.getWorld().getEnvironment();
	}
	
	public static Location getEmergenceLocation(Location playerLoc) {
		int x = (int) playerLoc.getX() + random.nextInt(41) - 20;
		int z = (int) playerLoc.getZ() + random.nextInt(41) - 20;
		int y;
		
		if (playerLoc.getWorld().getEnvironment().equals(Environment.NETHER)) {
			y = (int) getHighestNetherYAt(playerLoc.getWorld(), x, z, playerLoc.getY());
		} else {
			y = playerLoc.getWorld().getHighestBlockYAt(x, z);
		}
			
		return new Location(playerLoc.getWorld(), x, y, z);
	}
	
	public static String getRandomZombieName(ZombieType type) {
		String name;
		
		switch (random.nextInt(15)) {
		case 0: name = "Arthur"; break;
		case 1: name = "Greg"; break;
		case 2: name = "Bart"; break;
		case 3: name = "Samantha"; break;
		case 4: name = "Leg-less"; break;
		case 5: name = "Arm-less"; break;
		case 6: name = "Grandpa"; break;
		case 7: name = "Grandma"; break;
		case 8: name = "Caroline"; break;
		case 9: name = "Grrrrr"; break;
		case 10: name = "Uuuuuuuggg"; break;
		case 11: name = "Omar"; break;
		case 12: name = "Lawrence"; break;
		case 13: name = "One-eye"; break;
		case 14: name = "Lilly"; break;
		//case : name = ""; break;
		default: name = "default";
		}
		
		switch (type) {
		case STRONG: name = name + " the " + ChatColor.BOLD + "Strong"; break;
		case ALCHEMIST: name = name + " the " + ChatColor.DARK_PURPLE + "Alchemist"; break;
		case JUGGERNAUT: name = ChatColor.BOLD + "" + ChatColor.RED + name + " the Juggernaut"; break;
		default:
			switch (random.nextInt(3)) { // 1/3rd chance
			case 0:
				name = name + " the ";
				switch (random.nextInt(13)) {
				case 0: name = name + "Great"; break;
				case 1: name = name + "Sad"; break;
				case 2: name = name + "Joker"; break;
				case 3: name = name + "Hrmmmp"; break;
				case 4: name = name + "Brain-seeker"; break;
				case 5: name = name + "Back-breaker"; break;
				case 6: name = name + "Ugly"; break;
				case 7: name = name + "Pretty"; break;
				case 8: name = name + "Gay"; break;
				case 9: name = name + "Mediocre"; break;
				case 10: name = name + "Friendly Smiling Zombie"; break;
				case 11: name = name + "Cattle-herder"; break;
				case 12: name = name + "Goldfish adict"; break;
				//case : name = name + ""; break;
				default: name = name + "default";
				}
				break;
			case 1:
				name = name + " ";
				switch (random.nextInt(11)) {
				case 0: name = name + "Lawrence"; break;
				case 1: name = name + "Smiles"; break;
				case 2: name = name + "Hrmington"; break;
				case 3: name = name + "Smith"; break;
				case 4: name = name + "Brains"; break;
				case 5: name = name + "No-Brains"; break;
				case 6: name = name + "Dubley"; break;
				case 7: name = name + "Beardface"; break;
				case 8: name = name + "Kimber"; break;
				case 9: name = name + "Sanger"; break;
				case 10: name = name + "Johnston"; break;
				//case : name = name + ""; break;
				default: name = name + "default";
				}
				break;
			default: break;
			}
		}
		
		return name;
	}
	
	public static ZombieType getZombieType(int wave) {
		ZombieType type = null;
		int num = random.nextInt(100) + 1;
		switch (wave) {
		case 1: 
			if (num > 0 && num < 50) { type = ZombieType.BASIC; }
			else if (num < 75) { type = ZombieType.WEAK_PARTIAL_ARMOR; }
			else if (num < 85) { type = ZombieType.QUICK; }
			else if (num < 92) { type = ZombieType.FARMER; }
			else if (num < 97) { type = ZombieType.IMPOSTER; }
			else { type = ZombieType.STRONG_PARTIAL_ARMOR; }
			break;
		case 2: 
			if (num > 0 && num < 40) { type = ZombieType.BASIC; }
			else if (num < 60) { type = ZombieType.WEAK_PARTIAL_ARMOR; }
			else if (num < 70) { type = ZombieType.QUICK; }
			else if (num < 75) { type = ZombieType.ARCHER; }
			else if (num < 83) { type = ZombieType.FARMER; }
			else if (num < 90) { type = ZombieType.STRONG_PARTIAL_ARMOR; }
			else if (num < 95) { type = ZombieType.IMPOSTER; }
			else if (num < 98) { type = ZombieType.FLAMING; }
			else { type = ZombieType.WEAK_FULL_ARMOR; }
			break;
		case 3: 
			if (num > 0 && num < 20) { type = ZombieType.BASIC; }
			else if (num < 30) { type = ZombieType.WEAK_PARTIAL_ARMOR; }
			else if (num < 38) { type = ZombieType.QUICK; }
			else if (num < 50) { type = ZombieType.ARCHER; }
			else if (num < 55) { type = ZombieType.FARMER; }
			else if (num < 65) { type = ZombieType.FLAMING; }
			else if (num < 75) { type = ZombieType.STRONG_PARTIAL_ARMOR; }
			else if (num < 85) { type = ZombieType.WEAK_FULL_ARMOR; }
			else if (num < 90) { type = ZombieType.STRONG; }
			else if (num < 95) { type = ZombieType.IMPOSTER; }
			else if (num < 98) { type = ZombieType.IRON_MINER; }
			else { type = ZombieType.STRONG_FULL_ARMOR; }
			break;
		case 4: 
			if (num > 0 && num < 10) { type = ZombieType.BASIC; }
			else if (num < 17) { type = ZombieType.WEAK_PARTIAL_ARMOR; }
			else if (num < 24) { type = ZombieType.QUICK; }
			else if (num < 30) { type = ZombieType.ARCHER; }
			else if (num < 34) { type = ZombieType.FARMER; }
			else if (num < 42) { type = ZombieType.FLAMING; }
			else if (num < 50) { type = ZombieType.STRONG_PARTIAL_ARMOR; }
			else if (num < 58) { type = ZombieType.WEAK_FULL_ARMOR; }
			else if (num < 68) { type = ZombieType.MAGE; }
			else if (num < 77) { type = ZombieType.STRONG; }
			else if (num < 87) { type = ZombieType.IRON_MINER; }
			else if (num < 93) { type = ZombieType.STRONG_FULL_ARMOR; }
			else if (num < 98) { type = ZombieType.IMPOSTER; }
			else { type = ZombieType.ALCHEMIST; }
			break;
		case 5: 
			if (num > 0 && num < 10) { type = ZombieType.WEAK_PARTIAL_ARMOR; }
			else if (num < 15) { type = ZombieType.QUICK; }
			else if (num < 20) { type = ZombieType.ARCHER; }
			else if (num < 23) { type = ZombieType.FARMER; }
			else if (num < 28) { type = ZombieType.FLAMING; }
			else if (num < 35) { type = ZombieType.STRONG_PARTIAL_ARMOR; }
			else if (num < 40) { type = ZombieType.WEAK_FULL_ARMOR; }
			else if (num < 50) { type = ZombieType.MAGE; }
			else if (num < 60) { type = ZombieType.STRONG; }
			else if (num < 70) { type = ZombieType.IRON_MINER; }
			else if (num < 80) { type = ZombieType.STRONG_FULL_ARMOR; }
			else if (num < 85) { type = ZombieType.SUICIDE_BOMBER; }
			else if (num < 90) { type = ZombieType.ALCHEMIST; }
			else if (num < 95) { type = ZombieType.IMPOSTER; }
			else { type = ZombieType.JUGGERNAUT; }
			break;
		default: 
			if (num > 0 && num < 10) { type = ZombieType.QUICK; }
			else if (num < 20) { type = ZombieType.ARCHER; }
			else if (num < 25) { type = ZombieType.FLAMING; }
			else if (num < 30) { type = ZombieType.STRONG_PARTIAL_ARMOR; }
			else if (num < 35) { type = ZombieType.WEAK_FULL_ARMOR; }
			else if (num < 45) { type = ZombieType.MAGE; }
			else if (num < 55) { type = ZombieType.STRONG; }
			else if (num < 65) { type = ZombieType.IRON_MINER; }
			else if (num < 75) { type = ZombieType.STRONG_FULL_ARMOR; }
			else if (num < 85) { type = ZombieType.SUICIDE_BOMBER; }
			else if (num < 90) { type = ZombieType.ALCHEMIST; }
			else if (num < 95) { type = ZombieType.IMPOSTER; }
			else { type = ZombieType.JUGGERNAUT; }
			break;
		}
		return type;
	}
	
	public static void equipZombie(LivingEntity zombie, ZombieType type, long time) { // Adds armor and potion effects
		int ticks = 23000 - (int) time;
		switch (type) {
		case IMPOSTER: // Has a different monster's head
			if (random.nextInt(2) == 0) { zombie.getEquipment().setHelmet(new ItemStack(Material.CREEPER_HEAD)); }
			else { zombie.getEquipment().setHelmet(new ItemStack(Material.SKELETON_SKULL)); }
			break;
		case WEAK_PARTIAL_ARMOR: // One piece of leather or gold armor
			switch (random.nextInt(4)) {
			case 0: 
				if (random.nextInt(2) == 0) { zombie.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET)); }
				else { zombie.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET)); }
				break;
			case 1:
				if (random.nextInt(2) == 0) { zombie.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE)); }
				else { zombie.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE)); }
				break;
			case 2:
				if (random.nextInt(2) == 0) { zombie.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS)); }
				else { zombie.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS)); }
				break;
			case 3:
				if (random.nextInt(2) == 0) { zombie.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS)); }
				else { zombie.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS)); }
				break;
			}
			break;
		case ARCHER: // Holding a bow and can shoot arrows
			zombie.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
			break;
		case SLEEP: // Has died leather armor like it was a nightgown
			ItemStack lc = new ItemStack(Material.LEATHER_CHESTPLATE);
			ItemStack ll = new ItemStack(Material.LEATHER_LEGGINGS);
			LeatherArmorMeta lcm = (LeatherArmorMeta) lc.getItemMeta();
			LeatherArmorMeta llm = (LeatherArmorMeta) ll.getItemMeta();
			lcm.setColor(Color.AQUA);
			llm.setColor(Color.AQUA);
			lc.setItemMeta(lcm);
			ll.setItemMeta(llm);
			zombie.getEquipment().setChestplate(lc);
			zombie.getEquipment().setLeggings(ll);
			break;
		case FARMER: // Has a random hoe and partial leather armor
			switch (random.nextInt(4)) {
			case 0: zombie.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_HOE)); break;
			case 1: zombie.getEquipment().setItemInMainHand(new ItemStack(Material.STONE_HOE)); break;
			case 2: zombie.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_HOE)); break;	
			case 3: zombie.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_HOE)); break;	
			}
			for (int i = 0; i < random.nextInt(3) + 1; i++) { // 1-3
				switch (random.nextInt(4)) {
				case 0: zombie.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET)); break;
				case 1: zombie.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE)); break;
				case 2: zombie.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS)); break;
				case 3: zombie.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS)); break;
				}
			}
			break;
		case STRONG_PARTIAL_ARMOR: // One piece of iron or diamond armor
			switch (random.nextInt(4)) {
			case 0: 
				if (random.nextInt(2) == 0) { zombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET)); }
				else { zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET)); }
				break;
			case 1:
				if (random.nextInt(2) == 0) { zombie.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE)); }
				else { zombie.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE)); }
				break;
			case 2:
				if (random.nextInt(2) == 0) { zombie.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS)); }
				else { zombie.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS)); }
				break;
			case 3:
				if (random.nextInt(2) == 0) { zombie.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS)); }
				else { zombie.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS)); }
				break;
			}
			break;
		case FLAMING: // Has fire ticks and fire resistance for the remaining part of the night
			if (ticks > 0) {
				zombie.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, ticks, 1));
				zombie.setFireTicks(ticks);
			}
			break;
		case WEAK_FULL_ARMOR: // Full set with each piece being either leather or gold armor
			if (random.nextInt(2) == 0) { zombie.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET)); }
			else { zombie.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET)); }
			if (random.nextInt(2) == 0) { zombie.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE)); }
			else { zombie.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE)); }
			if (random.nextInt(2) == 0) { zombie.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS)); }
			else { zombie.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS)); }
			if (random.nextInt(2) == 0) { zombie.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS)); }
			else { zombie.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS)); }
			break;
		case MAGE: // Orange died chestplate, can launch fire balls
			ItemStack orangeChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta orangeChestplateMeta = (LeatherArmorMeta) orangeChestplate.getItemMeta();
			orangeChestplateMeta.setColor(Color.ORANGE);
			orangeChestplate.setItemMeta(orangeChestplateMeta);
			zombie.getEquipment().setChestplate(orangeChestplate);
			break;
		case STRONG: // Strength and an iron or diamond axe
			if (ticks > 0) { zombie.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, ticks, 1)); }
			break;
		case IRON_MINER: // Mostly full (2-3 pieces) iron armor and an iron pickaxe
			zombie.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_PICKAXE));
			for (int i = 0; i < random.nextInt(3) + 1; i++) { // 2-3
				switch (random.nextInt(4)) {
				case 0: zombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET)); break;
				case 1: zombie.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE)); break;
				case 2: zombie.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS)); break;
				case 3: zombie.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS)); break;
				}
			}
			break;
		case STRONG_FULL_ARMOR: // Full set with each piece being either iron or diamond armor
			if (random.nextInt(2) == 0) { zombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET)); }
			else { zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET)); }
			if (random.nextInt(2) == 0) { zombie.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE)); }
			else { zombie.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE)); }
			if (random.nextInt(2) == 0) { zombie.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS)); }
			else { zombie.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS)); }
			if (random.nextInt(2) == 0) { zombie.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS)); }
			else { zombie.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS)); }
			break;
		case SUICIDE_BOMBER: // Red chestplate, creeper head, blows up when within 2 blocks of a player
			ItemStack redChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta redChestplateMeta = (LeatherArmorMeta) redChestplate.getItemMeta();
			redChestplateMeta.setColor(Color.RED);
			redChestplate.setItemMeta(redChestplateMeta);
			zombie.getEquipment().setChestplate(redChestplate);
			zombie.getEquipment().setHelmet(new ItemStack(Material.CREEPER_HEAD));
			break;
		case ALCHEMIST: // Holding a potion of harming, has swiftness, strength, regeneration, fire resistance, water breathing, and jump boost
			ItemStack potion = new ItemStack(Material.POTION);
			PotionData potionData = new PotionData(PotionType.INSTANT_DAMAGE);
			PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
			potionMeta.setBasePotionData(potionData);
			potion.setItemMeta(potionMeta);
			zombie.getEquipment().setItemInMainHand(potion);
			if (ticks > 0) { 
				zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, ticks, 1)); 
				zombie.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, ticks, 1)); 
				zombie.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, ticks, 1)); 
				zombie.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, ticks, 1)); 
				zombie.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, ticks, 1)); 
				zombie.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, ticks, 1)); 
			}
			break;
		//case : break;
		case JUGGERNAUT: // Full enchanted diamond armor, an enchanted diamond axe, strength, and regeneration
			if (ticks > 0) { 
				zombie.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, ticks, 1)); 
				zombie.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, ticks, 1)); 
			}
			ItemStack dh = new ItemStack(Material.DIAMOND_HELMET);
			ItemStack dc = new ItemStack(Material.DIAMOND_CHESTPLATE);
			ItemStack dl = new ItemStack(Material.DIAMOND_LEGGINGS);
			ItemStack db = new ItemStack(Material.DIAMOND_BOOTS);
			ItemStack da = new ItemStack(Material.DIAMOND_AXE);
			ItemMeta dhm = dh.getItemMeta();
			ItemMeta dcm = dc.getItemMeta();
			ItemMeta dlm = dl.getItemMeta();
			ItemMeta dbm = db.getItemMeta();
			ItemMeta dam = da.getItemMeta();
			dhm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
			dcm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
			dlm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
			dbm.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
			dam.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
			dh.setItemMeta(dhm);
			dc.setItemMeta(dcm);
			dl.setItemMeta(dlm);
			db.setItemMeta(dbm);
			da.setItemMeta(dam);
			zombie.getEquipment().setHelmet(dh);
			zombie.getEquipment().setChestplate(dc);
			zombie.getEquipment().setLeggings(dl);
			zombie.getEquipment().setBoots(db);
			zombie.getEquipment().setItemInMainHand(da);
			break;
		default: break;
		}
	}
	
	public static Vector getNearestPlayerVector(UUID zombieID, int radius) {
		Entity zombie = Bukkit.getEntity(zombieID);
		
		if (zombie == null)
			return null;
		
		double nearestPlayerDistance = radius;
		Player nearestPlayer = null;
		for (Entity nearbyEntity : zombie.getNearbyEntities(radius, radius, radius)) {
			
			if (nearbyEntity instanceof Player) {
				
				double distance = zombie.getLocation().distance(nearbyEntity.getLocation());
				if (distance < nearestPlayerDistance) {
					nearestPlayer = (Player) nearbyEntity;
					nearestPlayerDistance = distance;
				}				
			}
		}
		
		if (nearestPlayer != null) {
			Vector directionVector = new Vector(
					nearestPlayer.getLocation().getX() - zombie.getLocation().getX(), 
					nearestPlayer.getLocation().getY() - zombie.getLocation().getY(),
					nearestPlayer.getLocation().getZ() - zombie.getLocation().getZ());
			directionVector.normalize();
			return directionVector;
		} else {
			return null;
		}
	}
	
	private static int getHighestNetherYAt(World world, double x, double z, double relativeY) {
		Location downTest = new Location(world, x, relativeY, z);
		Location upTest = new Location(world, x, relativeY + 1, z);
		
		do
		{
			if (downTest.getBlock().getType().equals(Material.AIR)) {
				downTest.subtract(0, 1, 0);
			} else {
				return (int) downTest.getY();
			}
			
			if (upTest.getBlock().getType().equals(Material.AIR)) {
				upTest.subtract(0, 1, 0);
			} else {
				return (int) upTest.getY();
			}
		} while (!upTest.getBlock().getType().equals(Material.BEDROCK) || 
				!downTest.getBlock().getType().equals(Material.BEDROCK));
		
		return (int) relativeY;
	}
	
}
