package com.zack.SkyblockMinigame;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;

public class Manager {

	private static ArrayList<SkyblockWorld> worlds;
	
	public Manager() {
		worlds = new ArrayList<>();
		
		if (Config.getWorlds() != null) {
			for (String uuidStr : Config.getWorlds()) {
				worlds.add(new SkyblockWorld(UUID.fromString(uuidStr)));
			}
		}
	}
	
	//public static List<SkyblockWorld> getWorlds() { return worlds; }
	
	public static boolean isInWorld(UUID uuid) {
		if (Bukkit.getPlayer(uuid).getLocation().getWorld().getName().equals(uuid.toString() + "-skyblock")) {
			return true;
		}
		
		return false;
	}
	
	public static SkyblockWorld getWorld(UUID uuid) {
		for (SkyblockWorld world : worlds) {
			if (world.getPlayerID().equals(uuid)) {
				return world;
			}
		}
		
		return null;
	}
	
	public static void addWorld(UUID uuid) {
		// Leaves the function if the player already has a world
		for (SkyblockWorld world : worlds) {
			if (world.getPlayerID().equals(uuid))
			{
				return;
			}
		}
		worlds.add(new SkyblockWorld(uuid));
	}
	
	public static Material getRandomBlock() {
		Material material = Material.APPLE;
		Random random = new Random();
		
		do {
			material = Material.values()[random.nextInt(Material.values().length)];
		} while (!(material.isItem()));
		
		return material;
	}
	
}
