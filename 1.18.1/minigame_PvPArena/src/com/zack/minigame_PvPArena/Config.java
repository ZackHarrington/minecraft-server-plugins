package com.zack.minigame_PvPArena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import com.zack.minigame_PvPArena.Vehicles.VehicleType;

public class Config {

	private static Main main;
	
	public Config(Main main) {
		Config.main = main;
		
		main.getConfig().options().copyDefaults();
		main.saveDefaultConfig();
	}
	
	public static int getDefaultRequiredPlayers() { return main.getConfig().getInt("default-required-players"); }
	public static int getDefaultGameTimer() { return main.getConfig().getInt("default-timer-minutes"); }
	public static int getDefaultRespawnSeconds() { return main.getConfig().getInt("default-respawn-seconds"); }
	public static int getStartCountdownSeconds() { return main.getConfig().getInt("start-countdown-seconds"); }
	public static int getEndCountdownSeconds() { return main.getConfig().getInt("end-countdown-seconds"); }
	
	public static Location getPvPLobbySpawn() {
		World world = Bukkit.createWorld(new WorldCreator(main.getConfig().getString("lobby-spawn.world")));
		
		return new Location(
				world,
				main.getConfig().getDouble("lobby-spawn.x"), 
				main.getConfig().getDouble("lobby-spawn.y"), 
				main.getConfig().getDouble("lobby-spawn.z"),
				main.getConfig().getInt("lobby-spawn.pitch"),
				main.getConfig().getInt("lobby-spawn.yaw"));
	}
	
	public static Location getPvPArenaLobbySpawn(int id) {
		World world = Bukkit.createWorld(new WorldCreator(main.getConfig().getString("pvparenas." + id + ".world")));
		
		return new Location(
				world,
				main.getConfig().getDouble("pvparenas." + id + ".lobby.x"), 
				main.getConfig().getDouble("pvparenas." + id + ".lobby.y"), 
				main.getConfig().getDouble("pvparenas." + id + ".lobby.z"),
				main.getConfig().getInt("pvparenas." + id + ".lobby.pitch"),
				main.getConfig().getInt("pvparenas." + id + ".lobby.yaw"));
	}
	
	public static Location getPvPArenaCenter(int id) {
		World world = Bukkit.createWorld(new WorldCreator(main.getConfig().getString("pvparenas." + id + ".world")));
		// Allows you to revert the world back after the game is complete
		world.setAutoSave(false);
		world.setMonsterSpawnLimit(0); // no monsters
		
		return new Location(
				world,
				main.getConfig().getDouble("pvparenas." + id + ".center.x"), 
				main.getConfig().getDouble("pvparenas." + id + ".center.y"), 
				main.getConfig().getDouble("pvparenas." + id + ".center.z"),
				main.getConfig().getInt("pvparenas." + id + ".center.pitch"),
				main.getConfig().getInt("pvparenas." + id + ".center.yaw"));
	}
	
	public static int getPvPArenaTime(int id) {
		return main.getConfig().getInt("pvparenas." + id + ".time");
	}
	
	public static WeatherType getPvPArenaWeather(int id) {
		return WeatherType.valueOf(main.getConfig().getString("pvparenas." + id + ".weather").toUpperCase());
	}
	
	public static Location getPvPArenaSpawnpoint(int id, int spawnpoint) {
		return new Location(
				Bukkit.getWorld(main.getConfig().getString("pvparenas." + id + ".world")),
				main.getConfig().getDouble("pvparenas." + id + ".spawnpoints." + spawnpoint + ".x"), 
				main.getConfig().getDouble("pvparenas." + id + ".spawnpoints." + spawnpoint + ".y"), 
				main.getConfig().getDouble("pvparenas." + id + ".spawnpoints." + spawnpoint + ".z"),
				main.getConfig().getInt("pvparenas." + id + ".spawnpoints." + spawnpoint + ".pitch"),
				main.getConfig().getInt("pvparenas." + id + ".spawnpoints." + spawnpoint + ".yaw"));
	}
	
	public static Location getPvPArenaVehicleSpawnpoint(int id, int vehicle) {
		return new Location(
				Bukkit.getWorld(main.getConfig().getString("pvparenas." + id + ".world")),
				main.getConfig().getDouble("pvparenas." + id + ".vehicles." + vehicle + ".x"), 
				main.getConfig().getDouble("pvparenas." + id + ".vehicles." + vehicle + ".y"), 
				main.getConfig().getDouble("pvparenas." + id + ".vehicles." + vehicle + ".z"),
				main.getConfig().getInt("pvparenas." + id + ".vehicles." + vehicle + ".pitch"),
				main.getConfig().getInt("pvparenas." + id + ".vehicles." + vehicle + ".yaw"));
	}
	
	public static Location getPvPArenaChestSpawnpoint(int id, int chest) {
		return new Location(
				Bukkit.getWorld(main.getConfig().getString("pvparenas." + id + ".world")),
				main.getConfig().getDouble("pvparenas." + id + ".chests." + chest + ".x"), 
				main.getConfig().getDouble("pvparenas." + id + ".chests." + chest + ".y"), 
				main.getConfig().getDouble("pvparenas." + id + ".chests." + chest + ".z"),
				main.getConfig().getInt("pvparenas." + id + ".chests." + chest + ".pitch"),
				main.getConfig().getInt("pvparenas." + id + ".chests." + chest + ".yaw"));
	}
	
	public static VehicleType getPvPArenaVehicleType(int id, int vehicle) {
		return VehicleType.valueOf(main.getConfig().getString("pvparenas." + id + ".vehicles." + vehicle + ".type").toUpperCase());
	}
	
	public static int getPvPArenaAmount() {
		return main.getConfig().getConfigurationSection("pvparenas.").getKeys(false).size();
	}
	
	public static int getPvPArenaSpawnpointAmount(int id) {
		return main.getConfig().getConfigurationSection("pvparenas." + id + ".spawnpoints.").getKeys(false).size();
	}
	
	public static int getPvPArenaVehicleAmount(int id) {
		return main.getConfig().getConfigurationSection("pvparenas." + id + ".vehicles.").getKeys(false).size();
	}
	
	public static int getPvPArenaChestAmount(int id) {
		return main.getConfig().getConfigurationSection("pvparenas." + id + ".chests.").getKeys(false).size();
	}
	
}