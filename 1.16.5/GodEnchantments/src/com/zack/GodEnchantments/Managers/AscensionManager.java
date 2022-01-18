package com.zack.GodEnchantments.Managers;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.zack.GodEnchantments.Main;

import net.md_5.bungee.api.ChatColor;

public class AscensionManager {

	// Generic
	private Player player;
	private boolean ascensionCancelled = false;
	private int spectacleRunnable = -1;
	Random rand;
	
	// Circle Effects
	private double effectsMultiplier = 5;
	private Vector c1RotVec;
	private Vector c1RotAddVec;
	private Vector c2RotVec;
	private Vector c2RotAddVec;
	private Vector c3RotVec;
	private Vector c3RotAddVec;
	private int clk = 0;
	
	public AscensionManager(Player player) {
		this.player = player;
		beginAscension();
		rand = new Random();
	}
	
	// Getters ---------------------------------------
	public boolean checkCancelled() {
		return ascensionCancelled;
	}
	public boolean checkSpectacle() {
		return (spectacleRunnable != -1);
	}
	
	// Setters ------------------------------------------
	public void cancelAscension() {
		ascensionCancelled = true;
	}
	public void cancelSpectacle() {
		Bukkit.getScheduler().cancelTask(spectacleRunnable);
	}
	
	// Public member functions ------------------------------------------------------------------------------------------------
	public void beginAscension() {
		// Alert player something may happen
		player.sendMessage(" ");
		player.sendMessage(ChatColor.ITALIC + "...you may want to get to an open field...");
		player.sendMessage(" ");
		
		// Announce to world 
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
			public void run() {
				// First makes sure the player did not cancel their ascension in some way
				if (!ascensionCancelled) {
					Bukkit.broadcastMessage(" ");
					Bukkit.broadcastMessage(ChatColor.ITALIC + "...nervous joy fills to world...");
					Bukkit.broadcastMessage(" ");
				}
			}
		}, 300L);
		
		// Begin levitation
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
			public void run() {
				if (!ascensionCancelled) {					
					player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 200, 5));
					
					Bukkit.broadcastMessage(" ");
					Bukkit.broadcastMessage(ChatColor.ITALIC + "The world turns to watch " + player.getName());
					Bukkit.broadcastMessage(" ");
				}
			}
		}, 600L);
		
		// Begin spectacle
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
			public void run() {
				if (!ascensionCancelled) {
					beginSpectacle();
				}
			}
		}, 800L);		
		
		// End spectacle
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
			public void run() {
				if (!ascensionCancelled) {
					endSpectacle();
				}
			}
		}, 1100L);
	}
	
	public void destroy() {
		// Essentially the destructor
		cancelAscension();
		cancelSpectacle();
	}
	
	// Private member functions ---------------------------------------------------------------------------------------------------
	private void beginSpectacle() {
		// Stop levitating
		player.removePotionEffect(PotionEffectType.LEVITATION);
		// Freeze player in air
		player.setAllowFlight(true); // Stops kick for flying
		player.setGravity(false);
		// Start lightning / effects
		startEffects(player);
	}
	private void endSpectacle() {
		// Enchant the armor
		enchantArmor(player);
		// End lighting
		cancelSpectacle();
		// Announce to world
		Bukkit.broadcastMessage(" ");
		Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "A god has risen");
		Bukkit.broadcastMessage(" ");
		// Create explosive ring
		finaleRing(player);
		// Make explosion sound
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 2.0F, 1.0F); // Explosion
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2.0F, 1.0F); // Explosion
	}
	
	private void startEffects(Player player) {
		// Initialize variables
		generateStartingVectors();
		
		spectacleRunnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			public void run () {
				double multiplier = getEffectsMultiplier();
				// Used for more accurate timing
				int clock = getClk();
				incrementClk();
				
				// Fireworks
				if (rand.nextInt(20) == 0) {
					createFirework(player);
				}
				
				// Sound effects
				if (clock == 20) { // after a second
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_AMBIENT, 2.0F, 1.0F); // Background - every 4 seconds, twice
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 2.0F, 0.5F); // Background 2 - 8 seconds, once
				}
				else if (clock == 100) { // 4 seconds later
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_AMBIENT, 2.0F, 1.0F); // Background
				}
				else if (clock == 180) { // When the beams appear
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 2.0F, 1.0F); // Rings stop and the 3 beams appear
				}
				if (clock % 10 == 0 && clock >= 20 && clock <= 180) { // try every half a second while spinning
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 2.0F, 0.01F); // Rings rubbing against one another
				}
				
				double rho = 10;
				// Particle Effects
				if (clock < 180) { // first 9 seconds // Spinning	
					if (clock > 20) { // after 1 second
						// Change in rotations
						updateCircleRotationVectors();
					}
					
					// Particles moving inwards
					if (clock < 120) { // first 6 seconds
						generateInwardParticles(player, rho);
					}
				}
				else if (clock < 260) { // after 9 seconds, for 4 seconds // Beams and lighting
					if (clock > 200) { // after 1 second
						// Lightning
						if (rand.nextInt(10) == 0) {
		            		player.getWorld().strikeLightning(player.getLocation());
		            	}
					}
					
					if (clock < 240) { // first 3 seconds
						// Beams from points
						createBeam(player, rho, 1);
						createBeam(player, rho, 2);
						createBeam(player, rho, 3);
					}
				}
				else { // Shrinking sphere
					// Collapse sphere
					if (multiplier > 0) {
						multiplier -= 0.2;
						setEffectsMultiplier(multiplier);
						rho = multiplier * 2; // radius
					}
					else
					{
						rho = 0;
					}
				}
				
				if (rho != 0) {
					// Rotating tri-circular sphere
					generateTriCircle(player, rho, 1);
					generateTriCircle(player, rho, 2);
					generateTriCircle(player, rho, 3);
				}
			}
		}, 0L, 1L);
	}
	private double getEffectsMultiplier() {
		return this.effectsMultiplier;
	}
	private void setEffectsMultiplier(double multiplier) {
		this.effectsMultiplier = multiplier;
	}
	private int getClk() {
		return this.clk;
	}
	private void incrementClk() {
		this.clk++;
	}
	private void generateStartingVectors() {
		// Rotational addition vector
		// Randomized but always ends in same position just maybe a half rotation ((2-4)PI * percent)
		double percent = 1.0/160.0; // Iterates for 8 seconds -> 20 times/sec
		c1RotAddVec = new Vector((rand.nextInt(2)+1)*2*Math.PI*percent, 
				(rand.nextInt(2)+1)*2*Math.PI*percent, (rand.nextInt(2)+1)*2*Math.PI*percent);
		c2RotAddVec = new Vector((rand.nextInt(2)+1)*2*Math.PI*percent, 
				(rand.nextInt(2)+1)*2*Math.PI*percent, (rand.nextInt(2)+1)*2*Math.PI*percent);
		c3RotAddVec = new Vector((rand.nextInt(2)+1)*2*Math.PI*percent, 
				(rand.nextInt(2)+1)*2*Math.PI*percent, (rand.nextInt(2)+1)*2*Math.PI*percent);
		// Random start rotation
		double initPitch = rand.nextDouble() * 2*Math.PI;
		double initYaw = rand.nextDouble() * 2*Math.PI;
		double initRoll = rand.nextDouble() * 2*Math.PI;
		// Rotation vector
		c1RotVec = new Vector(initPitch, initYaw, initRoll);
		c2RotVec = new Vector(initPitch, initYaw, initRoll);
		c3RotVec = new Vector(initPitch, initYaw, initRoll);
	}
	private void updateCircleRotationVectors()
	{
		c1RotVec.add(c1RotAddVec);
		c2RotVec.add(c2RotAddVec);
		c3RotVec.add(c3RotAddVec);
	}
	private void generateInwardParticles(Player player, double rho) {
		Location pLoc = player.getLocation();
		double speedMultiplier = 0.014 * Math.sqrt(rho); // Particles move faster as they start further away
		for (double phi = 0; phi <= Math.PI; phi += Math.PI / 60) { // top to bottom
			double yOffset = rho * Math.cos(phi); // z and y are swapped in minecraft
			for (double theta = 0; theta <= 2 * Math.PI; theta += Math.PI / 120) { // circle around
				// Chance of spawning
				if (rand.nextInt(1000) == 0) {
					double xOffset = rho * Math.sin(phi) * Math.cos(theta);
					double zOffset = rho * Math.sin(phi) * Math.sin(theta); // y and z are swapped in minecraft
					
					// Generate spawn location
					Location spawnLoc = new Location(player.getWorld(), 
							pLoc.getX() + xOffset, pLoc.getY() + yOffset, pLoc.getZ() + zOffset);
					// Spawn particles
					player.getWorld().spawnParticle(Particle.CLOUD, spawnLoc, 0, 
							-xOffset * speedMultiplier, -yOffset * speedMultiplier, -zOffset * speedMultiplier);
				}
			}
		}
	}
	private void createBeam(Player player, double rho, int circle) {
		Location pLoc = player.getLocation();
		int num = (int)rho * 5;
		for (int i = 0; i < num; i++) {
			Vector startPos;
			Vector circleRotation;
			double angle = (double)i / (double)num * 2 * Math.PI;
			switch (circle) {
			case 1:
				startPos = new Vector(Math.sin(angle), 0, 0); // xz circle
				circleRotation = c1RotVec;
				break;
			case 2:
				startPos = new Vector(0, 0, Math.sin(angle)); // xy circle
				circleRotation = c2RotVec;
				break;
			default: // 3 - default so it doesn't think it was never initialized
				startPos = new Vector(0, Math.sin(angle), 0); // yz circle
				circleRotation = c3RotVec;
				break;
			}
			// rotated positions for current point on 3 circles
			Vector position = getRotatedPosition(startPos, circleRotation.getX(), circleRotation.getY(), circleRotation.getY());
			// normal axis --> xyz, Minecraft axis --> xzy
			Location c1Loc = new Location(player.getWorld(),
					pLoc.getX() + rho*position.getX(), pLoc.getY() + rho*position.getZ(), pLoc.getZ() + rho*position.getY());
			// particle, location, 0, red (0 - 1), green (0 - 1), blue (0 - 1), 1
			player.getWorld().spawnParticle(Particle.SPELL_MOB, c1Loc, 0, 1, 1, 1, 1);
		}
	}
	private void generateTriCircle(Player player, double rho, int circle) {
		Location pLoc = player.getLocation();
		double angle = 0; // used for actual circles
		int num = (int)rho * 25;
		for (int i = 0; i < num; i++) {
			// circle points angle
			angle = (double)i / (double)num * 2 * Math.PI;
			// Relative circular points
			/*
		 	 * Ended up using matrix multiplication
		 	 * and rotational for pitch, yaw, and roll
		 	 */
			// initial positions for current point on 3 circles
			// normal axis --> xyz, Minecraft axis --> xzy
			Vector startPos;
			Vector circleRotation;
			switch (circle) {
			case 1:
				startPos = new Vector(Math.cos(angle), Math.sin(angle), 0); // xz circle
				circleRotation = c1RotVec;
				break;
			case 2:
				startPos = new Vector(Math.cos(angle), 0, Math.sin(angle)); // xy circle
				circleRotation = c2RotVec;
				break;
			default: // 3 - default so it doesn't think it was never initialized
				startPos = new Vector(0, Math.cos(angle), Math.sin(angle)); // yz circle
				circleRotation = c3RotVec;
				break;
			}
			// rotated positions for current point on 3 circles
			Vector position = getRotatedPosition(startPos, circleRotation.getX(), circleRotation.getY(), circleRotation.getY());
			// normal axis --> xyz, Minecraft axis --> xzy
			Location c1Loc = new Location(player.getWorld(),
					pLoc.getX() + rho*position.getX(), pLoc.getY() + rho*position.getZ(), pLoc.getZ() + rho*position.getY());
			// particle, location, amount,
			player.getWorld().spawnParticle(Particle.COMPOSTER, c1Loc, 1);
		}
	}
	private Vector getRotatedPosition(Vector startPos, double pitch, double yaw, double roll)
	{
		return new Vector(
				startPos.getX()*Math.cos(roll)*Math.cos(yaw) +
				startPos.getY()*(Math.cos(roll)*Math.sin(yaw)*Math.sin(pitch) - Math.sin(roll)*Math.cos(pitch)) + 
				startPos.getZ()*(Math.cos(roll)*Math.sin(yaw)*Math.cos(pitch) + Math.sin(roll)*Math.sin(pitch)),
				startPos.getX()*Math.sin(roll)*Math.cos(yaw) +
				startPos.getY()*(Math.sin(roll)*Math.sin(yaw)*Math.sin(pitch) + Math.cos(roll)*Math.cos(pitch)) + 
				startPos.getZ()*(Math.sin(roll)*Math.sin(yaw)*Math.cos(pitch) - Math.cos(roll)*Math.sin(pitch)),
				startPos.getX()*(-Math.sin(yaw)) + startPos.getY()*Math.cos(yaw)*Math.sin(pitch) + startPos.getZ()*Math.cos(yaw)*Math.cos(pitch));
	}
	
	private void enchantArmor(Player player) {
		if (player.getInventory().getHelmet() != null) {
			player.getInventory().getHelmet().addEnchantment(Main.godEnchantment, 1);
			ItemMeta meta = player.getInventory().getHelmet().getItemMeta();
			meta.setLore(Arrays.asList(ChatColor.GOLD + "God I"));
			player.getInventory().getHelmet().setItemMeta(meta);
		}
		if (player.getInventory().getChestplate() != null &&
				!player.getInventory().getChestplate().getType().equals(Material.ELYTRA)) {
			player.getInventory().getChestplate().addEnchantment(Main.godEnchantment, 1);
			ItemMeta meta = player.getInventory().getChestplate().getItemMeta();
			meta.setLore(Arrays.asList(ChatColor.GOLD + "God I"));
			player.getInventory().getChestplate().setItemMeta(meta);
		}
		if (player.getInventory().getLeggings() != null) {
			player.getInventory().getLeggings().addEnchantment(Main.godEnchantment, 1);
			ItemMeta meta = player.getInventory().getLeggings().getItemMeta();
			meta.setLore(Arrays.asList(ChatColor.GOLD + "God I"));
			player.getInventory().getLeggings().setItemMeta(meta);
		}
		if (player.getInventory().getBoots() != null) {
			player.getInventory().getBoots().addEnchantment(Main.godEnchantment, 1);
			ItemMeta meta = player.getInventory().getBoots().getItemMeta();
			meta.setLore(Arrays.asList(ChatColor.GOLD + "God I"));
			player.getInventory().getBoots().setItemMeta(meta);
		}
	}
	
	private void createFirework(Player player) {		
		// Spawn location
    	Location loc = player.getLocation();
		double angle = rand.nextDouble() * 2 * Math.PI;
		loc.add(Math.cos(angle) * 15, (rand.nextDouble() * 11) - 5, Math.sin(angle) * 15);
		
		Firework fw = player.getWorld().spawn(loc, Firework.class);
		FireworkMeta fwMeta = fw.getFireworkMeta(); 
		
    	// Get the type
    	int rt = rand.nextInt(5) + 1;
    	Type type = Type.BALL;       
    	if (rt == 1) type = Type.BALL;
    	if (rt == 2) type = Type.BALL_LARGE;
    	if (rt == 3) type = Type.BURST;
    	if (rt == 4) type = Type.CREEPER;
    	if (rt == 5) type = Type.STAR;
    	
    	// Get random color
    	Color c1 = getColor();
    	Color c2 = getColor();
    	
    	// Create effect
    	FireworkEffect effect = FireworkEffect.builder().flicker(rand.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(rand.nextBoolean()).build();
    	
    	// Apply effect
    	fwMeta.addEffect(effect);
    	
    	// Apply to rocket
    	fw.setFireworkMeta(fwMeta);
    	
    	// Detonate
    	fw.detonate();
	}
	
	private Color getColor() {		
        Color c = Color.WHITE;
        int rc = rand.nextInt(17) + 1;
        if (rc == 1) c = Color.AQUA;
        if (rc == 2) c = Color.BLACK;
        if (rc == 3) c = Color.BLUE;
        if (rc == 4) c = Color.FUCHSIA;
        if (rc == 5) c = Color.GRAY;
        if (rc == 6) c = Color.GREEN;
        if (rc == 7) c = Color.LIME;
        if (rc == 8) c = Color.MAROON;
        if (rc == 9) c = Color.NAVY;
        if (rc == 10) c = Color.OLIVE;
        if (rc == 11) c = Color.ORANGE;
        if (rc == 12) c = Color.PURPLE;
        if (rc == 13) c = Color.RED;
        if (rc == 14) c = Color.SILVER;
        if (rc == 15) c = Color.TEAL;
        if (rc == 16) c = Color.WHITE;
        if (rc == 17) c = Color.YELLOW;
		
		return c;
	}
	
	private void finaleRing(Player player) {
		int numParticles = 1000;
		int speed = 7;
		for (int i = 0; i < numParticles; i++) {
			// Angle of travel
			double angle = (double)i / (double)numParticles * 2 * Math.PI;
			double xVel = Math.cos(angle) * speed;
			double zVel = Math.sin(angle) * speed;
			// Particle particle, location, count, double offsetX, double offsetY, double offsetZ
			// If you make count 0, the offset parameters will act as velocity
			// offsets (0, 0, 0) with count > 0 make the particle spawn span from the location to that far away
			player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 0, xVel, 0, zVel);
			player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0, 0.25, 0), 0, xVel, 0, zVel);
			player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation().add(0, 0.5, 0), 0, xVel, 0, zVel);
		}	
	}
	
}
