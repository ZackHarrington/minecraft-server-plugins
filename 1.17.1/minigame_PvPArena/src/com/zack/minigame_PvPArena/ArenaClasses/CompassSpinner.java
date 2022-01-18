package com.zack.minigame_PvPArena.ArenaClasses;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.zack.minigame_PvPArena.Main;

public class CompassSpinner extends BukkitRunnable {

	private Game game;
	private float angle;
	private float radius;
	
	public CompassSpinner(Game game) {
		this.game = game;
		this.angle = 0;
		this.radius = 2;
	}
	
	public void begin() {
		this.runTaskTimer(Main.getInstance(), 0, 1);
	}
	
	@Override
	public void run() {
		
		// Spin players compasses
		float x = radius * (float) Math.cos(angle);
		float z = radius * (float) Math.sin(angle);
		angle += 0.25;
		if (angle > (float) Math.PI * 2) {
			angle = 0;
		}
		for (UUID uuid : game.getSpinningCompasses()) {
			Player player = Bukkit.getPlayer(uuid);
			if (!game.isRespawning(player)) {
				Location loc = new Location (player.getWorld(), player.getLocation().getX() + x, 
						player.getLocation().getY(), player.getLocation().getZ() + z);
				player.setCompassTarget(loc);
			}
		}
		
	}
	
}
