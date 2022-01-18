package com.zack.hyperHostileMobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Manager {

	static List<World> activeWorlds;
	static Random rand;
	
	public Manager() {
		activeWorlds = new ArrayList<>();
	}
	
	
	public static void addWorld(World world) {
		activeWorlds.add(world);
	}
	
	public static void removeWorld(World world) {
		if (activeWorlds.contains(world)) {
			activeWorlds.remove(world);
		}
	}
	
	public static boolean isActiveWorld(World world) {
		return activeWorlds.contains(world);
	}
	
	public static Random getRandom() {
		return rand;
	}
	
	public static void teleportPlayer(Player player) {
		player.teleport(new Location(
				player.getWorld(),
				player.getLocation().getX() + Manager.getRandom().nextInt(5) - 2,
				player.getLocation().getY() + Manager.getRandom().nextInt(5) - 2,
				player.getLocation().getZ() + Manager.getRandom().nextInt(3) - 1
				));
	}
	
	public static void knockbackPlayer(Player player, Vector direction, int multiplier) {
		Vector velocity = direction;
		velocity.multiply(multiplier);
		player.setVelocity(velocity);
	}
	
}
