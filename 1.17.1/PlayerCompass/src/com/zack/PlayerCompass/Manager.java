package com.zack.PlayerCompass;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Manager {

	private static HashMap<UUID, UUID> targets;
	private static HashMap<UUID, BukkitRunnable> spinners;
	
	public Manager() {
		Manager.targets = new HashMap<>();
		Manager.spinners = new HashMap<>();
	}
	
	public static Player getTarget(UUID seeker) {
		if (targets.containsKey(seeker)) {
			return Bukkit.getPlayer(targets.get(seeker));
		}
		
		return null;
	}
	
	public static Location getTargetLocation(UUID seeker) {
		if (targets.containsKey(seeker)) {
			return Bukkit.getPlayer(targets.get(seeker)).getLocation();
		}
		
		return null;
	}
	
	public static boolean addTarget(UUID seeker, UUID target) {
		if (seeker.equals(target)) {
			return false;
		}
		
		if (targets.containsKey(seeker)) {
			targets.remove(seeker);
		}
		targets.put(seeker, target);
		
		return true;		
	}
	
	public static void removeTarget(UUID seeker) {
		if (targets.containsKey(seeker)) {
			targets.remove(seeker);
		}
	}
	
	public static boolean containsSeeker(UUID seeker) {
		return targets.containsKey(seeker);
	}
	
	public static void spinCompass(UUID seeker) {
		if (!spinners.containsKey(seeker)) {
			spinners.put(seeker, new BukkitRunnable() {
				Player player = Bukkit.getPlayer(seeker);
				float angle = 0;
				float radius = 2;
				
				@Override
				public void run() {
					float x = radius * (float) Math.cos(angle);
					float z = radius * (float) Math.sin(angle);
					Location loc = new Location (player.getWorld(), player.getLocation().getX() + x, 
							player.getLocation().getY(), player.getLocation().getZ() + z);
					player.setCompassTarget(loc);
					angle += 0.25;
					if (angle > (float) Math.PI * 2) {
						angle = 0;
					}
				}
			});
			spinners.get(seeker).runTaskTimer(Main.getInstance(), 2, 2);
		}
	}
	
	public static void stopSpin(UUID seeker) {
		if (spinners.containsKey(seeker)) {
			spinners.get(seeker).cancel();
		}
	}
	
	public static void obliterate() {
		targets.clear();
		for (UUID uuid : spinners.keySet()) {
			stopSpin(uuid);
		}
		spinners.clear();
	}

}