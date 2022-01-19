package com.zack.minigame_PvPArena.Kits;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.zack.minigame_PvPArena.Main;
import com.zack.minigame_PvPArena.ArenaClasses.Game;
import com.zack.minigame_PvPArena.ArenaClasses.GameManager;

public class RailgunRunnable extends BukkitRunnable {

	private Game game;
	
	public RailgunRunnable(Game game) {
		this.game = game;
	}
	
	public void begin() {
		this.runTaskTimer(Main.getInstance(), 0, 5);
	}
	
	@Override
	public void run() {
		
		List<UUID> toRemove = new ArrayList<>();
		
		// Go through the current railguns and create the laser effects or final effects
		for (UUID uuid : game.getRailguns().keySet()) {
			
			if (game.getRailguns().get(uuid) + 1000 > System.currentTimeMillis()) {
				GameManager.laserEffect(Bukkit.getPlayer(uuid));
			} else {
				GameManager.shootRailgun(Bukkit.getPlayer(uuid)); 
				toRemove.add(uuid);
			}
			
		}
		
		for (UUID uuid : toRemove) {
			game.removeRailgun(uuid);
		}
		
	}
	
}
