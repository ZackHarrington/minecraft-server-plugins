package com.zack.MinigameMechanics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import com.zack.MinigameMechanics.Kits.KitType;

public class Manager {

	private static ArrayList<Arena> arenas;
	
	public Manager() {
		arenas = new ArrayList<>();
		
		for (int i = 0; i <= (Config.getArenaAmount() - 1); i++) {
			arenas.add(new Arena(i));
		}
	}
	
	public static List<Arena> getArenas() { return arenas; }
	
	public static boolean isPlaying(Player player) {
		for (Arena arena : arenas) {
			if (arena.getPlayers().contains(player.getUniqueId())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static Arena getArena(Player player) {
		for (Arena arena : arenas) {
			if (arena.getPlayers().contains(player.getUniqueId())) {
				return arena;
			}
		}
		
		return null;
	}
	
	public static Arena getArena(int id) {
		for (Arena arena : arenas) {
			if (arena.getID() == id) {
				return arena;
			}
		}
		
		return null;
	}
	
	public static boolean isRecruiting(int id) { return getArena(id).getState() == GameState.RECUITING; }
	
	public static boolean isArenaWorld(World world) {
		for (Arena arena : arenas) {
			if (arena.getWorld().getName().contentEquals(world.getName())) {
				// Will only have a name if the world has loaded
				return true;
			}
		}
		
		return false;
	}
	
	public static Arena getArena(World world) {
		for (Arena arena : arenas) {
			if (arena.getWorld().getName().contentEquals(world.getName())) {
				// Will only have a name if the world has loaded
				return arena;
			}
		}
		
		return null;
	}
	
	public static boolean isNPC(Entity entity) {
		if (entity.getType().equals(EntityType.VILLAGER)) {
			for (Arena arena : arenas) {
				if (arena.getNPC().equals((Villager) entity)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static Arena getArena(Entity entity) {
		if (entity.getType().equals(EntityType.VILLAGER)) {
			for (Arena arena : arenas) {
				if (arena.getNPC().equals((Villager) entity)) {
					return arena;
				}
			}
		}
		
		return null;
	}
	
	public static boolean hasKit(Player player) {
		if (isPlaying(player)) {
			if (getArena(player).getKits().containsKey(player.getUniqueId())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static KitType getKit(Player player) {
		if (isPlaying(player)) {
			if (getArena(player).getKits().containsKey(player.getUniqueId())) {
				return getArena(player).getKits().get(player.getUniqueId()).getType();
			}
		}
		
		return null;
	}
	
}
