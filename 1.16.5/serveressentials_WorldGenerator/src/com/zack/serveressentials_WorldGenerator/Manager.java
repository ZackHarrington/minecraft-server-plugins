package com.zack.serveressentials_WorldGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Manager {
	
	private static int worldNum;
	private static List<World> worlds;
	private static HashMap<World, World> worldNethers;
	private static HashMap<World, World> worldEnds;
	
	public Manager() {
		Manager.worldNum = 0;
		Manager.worlds = new ArrayList<>();
		Manager.worldNethers = new HashMap<>();
		Manager.worldEnds = new HashMap<>();
	}

	public static void createNewWorld(Player player, boolean addDimensions) {
		player.sendMessage(ChatColor.GREEN + "The new world is being generated");
		player.sendMessage(ChatColor.GREEN + "You will be teleported in once it has finished");
		
		WorldCreator wc = new WorldCreator("tmpworld" + worldNum);
		worldNum++;
		World newWorld = wc.createWorld();
		worlds.add(newWorld);
		player.teleport(newWorld.getSpawnLocation());
		
		if (addDimensions) {
			player.sendMessage(ChatColor.GREEN + "The nether for " + newWorld.getName() + " is being generated");
			createNewNetherWorld(player, newWorld);
			player.sendMessage(ChatColor.GREEN + "The end for " + newWorld.getName() + " is being generated");
			createNewEndWorld(player, newWorld);
		}
		
		player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + newWorld.getName() + " is all set");
	}
	
	public static boolean isTmpWorld(int tmpWorldNum) {
		if (Bukkit.getWorld("tmpworld" + tmpWorldNum) != null) {
			return true;
		}
		
		return false;
	}
	public static boolean isTmpWorld(World world) {
		return worlds.contains(world);
	}
	public static World getTmpWorldFromNether(World nether) {
		for (World world : worldNethers.keySet()) {
			if (worldNethers.get(world).equals(nether)) {
				return world;
			}
		}
		
		return null;
	}
	public static World getTmpWorldFromEnd(World end) {
		for (World world : worldEnds.keySet()) {
			if (worldEnds.get(world).equals(end)) {
				return world;
			}
		}
		
		return null;
	}
	
	public static boolean hasNether(World world) { return worldNethers.containsKey(world); }
	public static boolean isTmpWorldNether(World nether) { return worldNethers.containsValue(nether); }
	public static World getTmpNether(World world) { return worldNethers.get(world); }
	
	public static boolean hasEnd(World world) {	return worldEnds.containsKey(world); }
	public static boolean isTmpWorldEnd(World end) { return worldEnds.containsValue(end); }
	public static World getTmpEnd(World world) { return worldEnds.get(world); }
	
	public static void unloadTmpWorld(int tmpWorldNum, boolean save) {
		World world = Bukkit.getWorld("tmpworld" + tmpWorldNum);
		
		if (world != null) {
			Bukkit.unloadWorld(world, save);
			
			if (worldNethers.containsKey(world)) {
				Bukkit.unloadWorld(worldNethers.get(world), save);
			}
			if (worldEnds.containsKey(world)) {
				Bukkit.unloadWorld(worldEnds.get(world), save);
			}
		}
	}
	
	public static void unloadTmpWorlds(boolean save) {
		for (World world : worlds) {
			Bukkit.unloadWorld(world, save);
			
			if (worldNethers.containsKey(world)) {
				Bukkit.unloadWorld(worldNethers.get(world), save);
			}
			if (worldEnds.containsKey(world)) {
				Bukkit.unloadWorld(worldEnds.get(world), save);
			}
		}
	}
	
	public static void obliterate() {
		worlds.clear();
		worldNethers.clear();
		worldEnds.clear();
	}
	
	private static void createNewNetherWorld(Player player, World tmpWorld) {		
		WorldCreator wc = new WorldCreator(tmpWorld.getName() + "_nether");
		wc.environment(Environment.NETHER);
		World newWorld = wc.createWorld();
		worldNethers.put(tmpWorld, newWorld);
	}
	
	private static void createNewEndWorld(Player player, World tmpWorld) {
		WorldCreator wc = new WorldCreator(tmpWorld.getName() + "_the_end");
		wc.environment(Environment.THE_END);
		World newWorld = wc.createWorld();
		worldEnds.put(tmpWorld, newWorld);
	}
	
}
