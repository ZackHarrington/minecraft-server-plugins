package com.zack.BossPig;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class BossPig {
	
	private Pig bossPig = null;
	private BossBar bossBar = null;
	AITask task;
	private int pauseTicksLeft = 0;
	
	public BossPig (JavaPlugin plugin, Player player) {
		// Create animal in game
		summon(player);
		// Start runnable
		task = new AITask(this);
		startThread(plugin);
	}
	
	// Public Functions ---------------------------------------	
	public void update() {
		// Find closest player
		Player closestPlayer = findClosestPlayer();
		
		// Make sure there are players online
		if (closestPlayer != null) {
			
			// Check for being close to player
			if (checkCloseEnough(closestPlayer))
			{
				// If close enough then attack the player
				bossPig.swingMainHand();
				closestPlayer.damage(2, bossPig);
				// Wait for a second before staring again
				stopForTicks(20);
			} else {
				// Head towards them - doesn't account for upward motion
				target(closestPlayer, 0.2);
			}
			
			// Check need for jump
			Block blockInFront = getBlockInFront();
			if (!blockInFront.isPassable()) {
				// Jump up
				jump();
			}
		} else {
			endThread();
		}
	}
	
	public void startThread(JavaPlugin plugin) {
		try {
			task.runTaskTimer(plugin, 100, 1);
		} catch (Exception e) {
			System.out.println("Thread Already Started");
		}
	}
	
	public void attacked() {
		// Cancel timer for knockback then start again
		stopForTicks(20);
	}
	
	public void updateBar() {
		// Update bossBar according to health
		bossBar.setProgress(bossPig.getHealth()/120);
	}
	
	public int getEntityId() {
		return bossPig.getEntityId();
	}
	
	public int getPause() {
		return pauseTicksLeft;
	}
	
	public void decrementPause() {
		pauseTicksLeft--;
	}
	
	public void death() {
		// Remove boss bar
		bossBar.removeAll();
		
		// Alert Players
		List<Player> players = new ArrayList<>();
		players.addAll(Bukkit.getWorld("world").getPlayers());
		for(int i = 0; i < players.size(); i++) {
			players.get(i).sendMessage(ChatColor.RED + "Lord Oinkers has been defeated");
		}
		
		// End runnable task
		endThread();
	}
	
	// Private Functions ----------------------------------
	@SuppressWarnings("deprecation")
	private void summon(Player player) {
		// Summon in the direction the player is looking, and a few blocks away
		Location summonLocation = getSummonLocation(player);
		
		// Destroy blocks that would be in it's way
		destroyBlocks(summonLocation);
		
		// Create Entity
		Pig pig = (Pig) Bukkit.getWorld("world").spawnEntity(summonLocation, EntityType.PIG);
		// Cast to Phantom
		//LivingEntity livingPig = (LivingEntity) pig;
		//bossPig = (Phantom) livingPig;
		bossPig = pig;
		
		// Set boss stats
		bossPig.setMaxHealth(120);
		bossPig.setHealth(120);
		bossPig.setGlowing(true);
		bossPig.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100, 10)); // 5 seconds
		
		// Create Boss Bar
		bossBar = Bukkit.createBossBar(
				ChatColor.WHITE + "Lord Oinkers",
				BarColor.RED,
				BarStyle.SEGMENTED_6); //, BarFlag); make function that does something when it appears
		bossBar.setProgress(1);
		bossBar.addPlayer(player);
	}
	
	private void stopForTicks(int ticks) {
		pauseTicksLeft = ticks;
	}
	
	private void endThread() {
		task.cancel();
	}
	
	private Location getSummonLocation(Player player) {
		Location playerLocation = player.getLocation();
		Location summonLocation = null;
		double rotation = playerLocation.getYaw() - 180;
		double x = playerLocation.getX();
		double y = playerLocation.getY() + 1;
		double z = playerLocation.getZ();
        
		// Find direction
		if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5) {
            // North
        	z -= 2;
        }
        if (22.5 <= rotation && rotation < 67.5) {
            // North East
        	x += 1;
        	z -= 1;
        }
        if (67.5 <= rotation && rotation < 112.5) {
            // East
        	x += 2;
        }
        if (112.5 <= rotation && rotation < 157.5) {
            // South East
        	x += 1;
        	z += 1;
        }
        if (157.5 <= rotation && rotation < 202.5) {
            // South
        	z += 2;
        }
        if (202.5 <= rotation && rotation < 247.5) {
            // South West
        	x -= 1;
        	z += 1;
        }
        if (247.5 <= rotation && rotation < 292.5) {
            // West
        	x -= 2;
        }
        if (292.5 <= rotation && rotation < 337.5) {
            // North West
        	x -= 1;
        	z -= 1;
        }
        if (337.5 <= rotation && rotation <= 360) {
            // North
        	z -= 2;
        }
		
        summonLocation = new Location(Bukkit.getWorld("world"), x, y, z);
        
		return summonLocation;
	}
	
	private void destroyBlocks (Location location) {
		// Destroy in a 3x3 area
		Location blockLocation = new Location(Bukkit.getWorld("world"), 0, 0, 0);
		double summonX = location.getBlockX();
		double summonY = location.getBlockY();
		double summonZ = location.getBlockZ();
		
		for(int i = 0; i < 3; i++) {
			blockLocation.setX(summonX - 1 + i);
			for(int j = 0; j < 3; j++) {
				blockLocation.setY(summonY - 1 + j);
				for(int k = 0; k < 3; k++) {
					blockLocation.setZ(summonZ - 1 + k);
					// Break block
					Bukkit.getWorld("world").getBlockAt(blockLocation).breakNaturally();
				}
			}
		}
	}
	
	private Player findClosestPlayer() {
		List<Player> players = new ArrayList<>();
		players.addAll(Bukkit.getWorld("world").getPlayers());
		
		Vector pigVector = bossPig.getLocation().toVector();
		Player closest = null;
		Vector v = null, difference = null, lastDif = null;
		
		for(int i = 0; i < players.size(); i++) {
			// Get distance
			v = players.get(i).getLocation().toVector();
			difference = v.subtract(pigVector);
			// Check if it's the closest
			if (i == 0 || difference.length() < lastDif.length()) {
				closest = players.get(i);
			}
			lastDif = difference;
		}
		
		return closest;
	}
	
	private boolean checkCloseEnough(Player player) {
		boolean closeEnough = false;
		Vector v = player.getLocation().toVector();
		v.subtract(bossPig.getLocation().toVector());
		if (v.length() <= 1) {
			closeEnough = true;
		}
		
		return closeEnough;
	}
	
	private void target(Player player, double multiplier) {
		Vector v = player.getLocation().toVector();
		Vector pigLoc = bossPig.getLocation().toVector();
		Location blockUnderLoc = new Location(Bukkit.getWorld("world"), 
				pigLoc.getBlockX(), pigLoc.getY() - 1, pigLoc.getZ());
		v.subtract(pigLoc);
		v.normalize(); // Makes the vector length 1; in this case it allows uniform speed
		v.multiply(multiplier);
		if (v.getY() >= 0 && !blockUnderLoc.getBlock().isPassable())
		{
			v.setY(0); // No upward motion
		} else {
			// Move down according to gravity (32m/s^2 ~ 1.6m/tick^2)
			v.setY(bossPig.getVelocity().getY() - 0.075);
		}
		bossPig.setVelocity(v);
	}
	
	private Block getBlockInFront() {
		Block block;
		
		double rotation = bossPig.getLocation().getYaw() - 180;
		double x = bossPig.getLocation().getX();
		double y = bossPig.getLocation().getY();
		double z = bossPig.getLocation().getZ();
        
		// Find direction
		if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 45) {
            // North
        	z -= 1;
        }
        if (45 <= rotation && rotation < 135) {
            // East
        	x += 1;
        }
        if (135 <= rotation && rotation < 225) {
            // South
        	z += 1;
        }
        if (225 <= rotation && rotation < 315) {
            // West
        	x -= 1;
        }
        if (315 <= rotation && rotation <= 360) {
            // North
        	z -= 1;
        }		
        
        Location location = new Location(Bukkit.getWorld("world"), x, y, z);
        block = location.getBlock();
        
		return block;
	}
	
	private void jump() {
		Vector v = bossPig.getVelocity();
		v.setY(1);
		bossPig.setVelocity(v);
	}
	
}
