package com.zack.ZombieApocalypse;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

public class EmergingZombie {
	
	private Location location;
	private Location particleLocation;
	private int secondsCounter;
	
	public EmergingZombie(Location location) {
		this.location = location;
		this.particleLocation = new Location(location.getWorld(), 
				location.getX() + 0.5, location.getY() + 1.0, location.getZ() + 0.5);
		this.secondsCounter = 0;
	}
	
	public void update() {
		if (secondsCounter < 5) {
			secondsCounter++;
		}
		
		// Double check the location hasn't been broken
		if (!location.getBlock().getBlockData().getMaterial().equals(Material.AIR)) {
			for (int i = 0; i < 50; i++) {
				// Particle particle, location, count, double offsetX, double offsetY, double offsetZ, T data
				location.getWorld().spawnParticle(
						Particle.BLOCK_CRACK, particleLocation, 1, 0.25, 0.1, 0.25, location.getBlock().getType().createBlockData());
			}
		}
		
	}
	
	public void cancel() {
		// Break the block
		location.getBlock().setType(Material.AIR);
	}
	
	public int getCounter() { return secondsCounter; }
	public Location getLocation() { return location; }
	
}
