package com.zack.minigame_Skyblock;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import net.md_5.bungee.api.ChatColor;

public class Config {

	private static Main main;
	
	public Config(Main main) {
		Config.main = main;
		
		main.getConfig().options().copyDefaults();
		main.saveDefaultConfig();
	}
	
	public static boolean loadWorld(UUID uuid) {
		Bukkit.getPlayer(uuid).sendMessage(ChatColor.YELLOW + "Your skyblock world is being generated");
		Bukkit.getPlayer(uuid).sendMessage(ChatColor.YELLOW + "You will be teleported in once it loads");
		// Set new section for the player (it automatically creates the hierarchy as long as we supply the address)
		main.getConfig().set("player-worlds." + uuid.toString() + ".spawn.world", uuid.toString() + "-skyblock");
		main.getConfig().set("player-worlds." + uuid.toString() + ".spawn.x", 0.5);
		main.getConfig().set("player-worlds." + uuid.toString() + ".spawn.y", 64);
		main.getConfig().set("player-worlds." + uuid.toString() + ".spawn.z", 0.5);
		main.getConfig().set("player-worlds." + uuid.toString() + ".spawn.pitch", 0);
		main.getConfig().set("player-worlds." + uuid.toString() + ".spawn.yaw", 0);
		main.saveConfig();
		// Create void world
		World world = createNewWorld(uuid);
		// Place singular bedrock under spawn point
		Location location = new Location(world,
				main.getConfig().getDouble("player-worlds." + uuid.toString() + ".spawn.x"),
				main.getConfig().getDouble("player-worlds." + uuid.toString() + ".spawn.y") - 1,
				main.getConfig().getDouble("player-worlds." + uuid.toString() + ".spawn.z"));
		location.getBlock().setType(Material.BEDROCK);
		
		// Allows you to revert the world back to the previous save
		world.setAutoSave(false);
		
		Manager.addWorld(uuid);
			
		return true;
	}
	
	public static boolean doesSkyblockWorldExist(UUID uuid) {
		try {
			return main.getConfig().getConfigurationSection("player-worlds").contains(uuid.toString());
		} catch (Exception e) { // Illegal argument and Null pointer exceptions
			e.printStackTrace();
			return false;
		}
	}
	
	public static Location getLobbySpawn() {
		return new Location(
				Bukkit.getWorld(main.getConfig().getString("lobby-spawn.world")),
				main.getConfig().getDouble("lobby-spawn.x"), 
				main.getConfig().getDouble("lobby-spawn.y"), 
				main.getConfig().getDouble("lobby-spawn.z"),
				main.getConfig().getInt("lobby-spawn.pitch"),
				main.getConfig().getInt("lobby-spawn.yaw"));
	}
	
	public static Set <String> getWorlds() {
		if (main.getConfig().getConfigurationSection("player-worlds") != null) {
			return main.getConfig().getConfigurationSection("player-worlds").getKeys(false);
		} else {
			return null;
		}
	}
	
	public static Location getWorldSpawn(UUID uuid) {
		World world = Bukkit.getWorld(main.getConfig().getString("player-worlds." + uuid.toString() + ".spawn.world"));
		
		return new Location(world,
				main.getConfig().getInt("player-worlds." + uuid.toString() + ".spawn.x"),
				main.getConfig().getInt("player-worlds." + uuid.toString() + ".spawn.y"),
				main.getConfig().getInt("player-worlds." + uuid.toString() + ".spawn.z"),
				main.getConfig().getInt("player-worlds." + uuid.toString() + ".spawn.pitch"),
				main.getConfig().getInt("player-worlds." + uuid.toString() + ".spawn.yaw"));
	}
	
	private static World createNewWorld(UUID uuid) {
		WorldCreator wc = new WorldCreator(main.getConfig().getString("player-worlds." + uuid.toString() + ".spawn.world"));
		wc.generator(new ChunkGenerator() {
			@Override
			public List<BlockPopulator> getDefaultPopulators(World world) {
				return Collections.<BlockPopulator>emptyList();
			}
			@Override
			public boolean shouldGenerateNoise() { return false; }
			@Override
			public boolean shouldGenerateSurface() { return false; }
			@Override
			public boolean shouldGenerateBedrock() { return false; }
			@Override
			public boolean shouldGenerateCaves() { return false; }
			@Override
			public boolean shouldGenerateDecorations() { return false; }
			@Override
			public boolean shouldGenerateMobs() { return false; }
			@Override
			public boolean shouldGenerateStructures() { return false; }
//			@Deprecated
//			@Override
//			public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
//				ChunkData chunkData = super.createChunkData(world);
//				
//				// Set to air
//				for(int x = 0; x < 16; x++) {
//					for(int y = 0; y < 256; y++) {
//						for(int z = 0; z < 16; z++) {
//							chunkData.setBlock(x, y, z, Material.AIR);
//						}
//					}
//				}
//				
//				// Return the new chunk data.
//				return chunkData;
//			}
			@Override
			public boolean canSpawn(World world, int x, int z) {
				return true;
			}
			
			@Override
			public Location getFixedSpawnLocation(World world, Random random) {
				return new Location(world,
						main.getConfig().getInt("player-worlds." + uuid.toString() + ".spawn.x"),
						main.getConfig().getInt("player-worlds." + uuid.toString() + ".spawn.y"),
						main.getConfig().getInt("player-worlds." + uuid.toString() + ".spawn.z"));
			}
		});
		return wc.createWorld();
	}
	
}
