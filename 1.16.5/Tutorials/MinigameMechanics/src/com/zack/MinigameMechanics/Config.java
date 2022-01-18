package com.zack.MinigameMechanics;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class Config {

	private static Main main;
	
	public Config(Main main) {
		Config.main = main;
		
		main.getConfig().options().copyDefaults();
		main.saveDefaultConfig();
		
	}
	
	public static int getRequiredPlayers() { return main.getConfig().getInt("required-players"); }
	
	public static int getCountdownSeconds() { return main.getConfig().getInt("countdown-seconds"); }
	
	public static Location getLobbySpawn() {
		return new Location(
				Bukkit.getWorld(main.getConfig().getString("lobby-spawn.world")),
				main.getConfig().getDouble("lobby-spawn.x"), 
				main.getConfig().getDouble("lobby-spawn.y"), 
				main.getConfig().getDouble("lobby-spawn.z"),
				main.getConfig().getInt("lobby-spawn.pitch"),
				main.getConfig().getInt("lobby-spawn.yaw"));
	}
	
	public static Location getArenaSpawn(int id) {
		World world = Bukkit.createWorld(new WorldCreator(main.getConfig().getString("arenas." + id + ".spawn.world")));
		// Allows you to revert the world back after the game is complete
		world.setAutoSave(false);
		
		return new Location(
				world,
				main.getConfig().getDouble("arenas." + id + ".spawn.x"), 
				main.getConfig().getDouble("arenas." + id + ".spawn.y"), 
				main.getConfig().getDouble("arenas." + id + ".spawn.z"),
				main.getConfig().getInt("arenas." + id + ".spawn.pitch"),
				main.getConfig().getInt("arenas." + id + ".spawn.yaw"));
	}
	
	public static int getArenaAmount() {
		return main.getConfig().getConfigurationSection("arenas.").getKeys(false).size();
	}
	
	public static Location getArenaSign(int id) {
		World world = Bukkit.createWorld(new WorldCreator(main.getConfig().getString("arenas." + id + ".sign.world")));		
		return new Location(
				world,
				main.getConfig().getDouble("arenas." + id + ".sign.x"), 
				main.getConfig().getDouble("arenas." + id + ".sign.y"), 
				main.getConfig().getDouble("arenas." + id + ".sign.z"));
	}
	
	public static int isSign(Location location) {
		// -1 for no arena with the sign, else arena number with the sign
		for (int i = 0; i < getArenaAmount(); i++) {
			if (getArenaSign(i).equals(location)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public static Location getArenaNPC(int id) {
		World world = Bukkit.createWorld(new WorldCreator(main.getConfig().getString("arenas." + id + ".npc.world")));
		return new Location(
				world,
				main.getConfig().getDouble("arenas." + id + ".npc.x"), 
				main.getConfig().getDouble("arenas." + id + ".npc.y"), 
				main.getConfig().getDouble("arenas." + id + ".npc.z"),
				main.getConfig().getInt("arenas." + id + ".npc.pitch"),
				main.getConfig().getInt("arenas." + id + ".npc.yaw"));
	}
	
}
