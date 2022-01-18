package com.zack.FirstTry;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EntityMaker {
	
	private Entity ent = null;
	private Random rand = new Random();
	
	// Public functions
	public void spawnEntity(Block block) {
				
		//= Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), 0, 0, 0), EntityType.WOLF);
		
		// Get the block's data
		Location blockLoc = block.getLocation();
		Block blockAbove = block.getRelative(BlockFace.UP, 1);
		Block blockBelow = block.getRelative(BlockFace.DOWN, 1);
		Block blockEast = block.getRelative(BlockFace.EAST, 1);
		Block blockWest = block.getRelative(BlockFace.WEST, 1);
		Block blockNorth = block.getRelative(BlockFace.NORTH, 1);
		Block blockSouth = block.getRelative(BlockFace.SOUTH, 1);
		byte blockLightLevel = 0;
		Biome blockBiome = block.getBiome();
		// Light level
		for (int i = 0; i < 6; i++) {
			byte level = 1;
			switch (i) {
			case 0: level = blockAbove.getLightLevel(); break;
			case 1: level = blockBelow.getLightLevel(); break;
			case 2: level = blockEast.getLightLevel(); break;
			case 3: level = blockWest.getLightLevel(); break;
			case 4: level = blockNorth.getLightLevel(); break;
			case 5: level = blockSouth.getLightLevel(); break;
			}
			if (level > blockLightLevel) {
				blockLightLevel = level;
			}
		}
		boolean isDay = blockLoc.getY() > 40 && blockLightLevel > 8;
		
		if (blockBiome == Biome.BASALT_DELTAS ||                  // Nether
				blockBiome == Biome.CRIMSON_FOREST ||
				blockBiome == Biome.NETHER_WASTES ||
				blockBiome == Biome.SOUL_SAND_VALLEY ||
				blockBiome == Biome.WARPED_FOREST) {
			spawnNetherEntity(blockLoc);
		} else if (blockBiome == Biome.END_BARRENS ||             // End
				blockBiome == Biome.END_HIGHLANDS || 
				blockBiome == Biome.END_MIDLANDS ||
				blockBiome == Biome.THE_END ||
				blockBiome == Biome.SMALL_END_ISLANDS) {
			spawnEndEntity(blockLoc);
		} else if ((block.getType().equals(Material.DIAMOND_ORE) || 
				block.getType().equals(Material.REDSTONE_ORE) ||
				block.getType().equals(Material.GOLD_ORE) ||
				block.getType().equals(Material.LAPIS_ORE) ||
				block.getType().equals(Material.EMERALD_ORE)) &&
				!isDay) {
			spawnDifficultEntity(blockLoc);
		} else if (blockAbove.getType().equals(Material.WATER)) { // Water
			spawnWaterEntity(blockLoc);
		} else if (blockBiome == Biome.BEACH && isDay) {          // Beach and Day
			spawnBeachEntity(blockLoc);
		} else {
			if (isDay) {                                          // Day
				spawnDayEntity(blockLoc);				
			} else {                                              // Night / cave
				spawnNightEntity(blockLoc);	
			}
		}
	}
	
	public Entity getEntity() {
		return ent;
	}
	
	// Private functions
	private void spawnDayEntity(Location location) {
		EntityType type = null;
		int num = rand.nextInt(17);
		
		switch (num) {
		case 0: type = EntityType.BEE; break;
		case 1: type = EntityType.CAT; break;
		case 2: type = EntityType.CHICKEN; break;
		case 3: 
			num = rand.nextInt(10);
			if (num == 0) { type = EntityType.MUSHROOM_COW; }
			else { type = EntityType.COW; }
			break;
		case 4: type = EntityType.FOX; break;
		case 5: 
			num = rand.nextInt(8);
			if (num == 0) { type = EntityType.DONKEY; }
			else if (num == 1) { type = EntityType.MULE; }
			else { type = EntityType.HORSE; }
			break;
		case 6:
			num = rand.nextInt(8);
			if (num == 0) { type = EntityType.TRADER_LLAMA; }
			else { type = EntityType.LLAMA; }
			break;			
		case 7: type = EntityType.OCELOT; break;		
		case 8: type = EntityType.PANDA; break;		
		case 9: type = EntityType.PARROT; break;		
		case 10: type = EntityType.POLAR_BEAR; break;		
		case 11: type = EntityType.RABBIT; break;		
		case 12: type = EntityType.SHEEP; break;		
		case 13: type = EntityType.SNOWMAN; break;		
		case 14:
			num = rand.nextInt(10);
			if (num == 0) { type = EntityType.WANDERING_TRADER; } 
			else { type = EntityType.VILLAGER; }
			break;	
		case 15: type = EntityType.WOLF; break;
		case 16: type = EntityType.PILLAGER; break;
		}
		
		if (type != null) {
			ent = location.getWorld().spawnEntity(location, type);
		}
	}
	
	private void spawnBeachEntity(Location location) {
		int num = rand.nextInt(5);
		
		if (num == 0) { // 1 in 5 of being a turtle
			ent = location.getWorld().spawnEntity(location, EntityType.TURTLE);
		} else {
			spawnDayEntity(location);
		}
	}
	
	private void spawnWaterEntity(Location location) {
		EntityType type = null;
		int num = rand.nextInt(8);
		
		switch (num) {
		case 0: type = EntityType.COD; break;
		case 1: type = EntityType.DOLPHIN; break;
		case 2: type = EntityType.DROWNED; break;
		case 3: type = EntityType.GUARDIAN; break;
		case 4: type = EntityType.PUFFERFISH; break;
		case 5: type = EntityType.SALMON; break;
		case 6: type = EntityType.SQUID; break;		
		case 7: type = EntityType.TROPICAL_FISH; break;			
		}
		
		if (type != null) {
			ent = location.getWorld().spawnEntity(location, type);
		}
	}
	
	private void spawnNightEntity(Location location) {
		EntityType type = null;
		int num = rand.nextInt(10);
		
		switch (num) {
		case 0: type = EntityType.BAT; break;
		case 1: type = EntityType.CAVE_SPIDER; break;
		case 2: type = EntityType.CREEPER; break;
		case 3: type = EntityType.ENDERMAN; break;
		case 4: type = EntityType.HUSK; break;
		case 5: type = EntityType.PHANTOM; break;
		case 6: type = EntityType.SILVERFISH; break;		
		case 7: type = EntityType.SKELETON; break;		
		case 8: type = EntityType.SKELETON_HORSE; break;		
		case 9: type = EntityType.SLIME; break;		
		case 10: type = EntityType.SPIDER; break;		
		case 11: type = EntityType.STRAY; break;		
		case 12: type = EntityType.WITCH; break;		
		case 13: type = EntityType.ZOMBIE; break;		
		case 14: type = EntityType.ZOMBIE_HORSE; break;		
		case 15: type = EntityType.ZOMBIE_VILLAGER; break;		
		}
		
		if (type != null) {
			ent = location.getWorld().spawnEntity(location, type);
		}
	}
	
	private void spawnDifficultEntity(Location location) {
		EntityType type = null;
		int num = rand.nextInt(2);
		
		if (num == 0) { // 1 in 2 chance
			num = rand.nextInt(4);
			switch (num) {
			case 0: type = EntityType.EVOKER; break;
			//case 1: type = EntityType.ILLUSIONER; break;
			case 1: type = EntityType.IRON_GOLEM; break;
			case 2: type = EntityType.VEX; break;
			case 3: type = EntityType.VINDICATOR; break;		
			}
		} else {
			spawnNightEntity(location);
		}
		
		if (type != null) {
			ent = location.getWorld().spawnEntity(location, type);
		}
	}
	
	private void spawnNetherEntity(Location location) {
		EntityType type = null;
		int num = rand.nextInt(10);
		
		switch (num) {
		case 0: type = EntityType.BLAZE; break;
		case 1: type = EntityType.GHAST; break;
		case 2: type = EntityType.HOGLIN; break;
		case 3: type = EntityType.MAGMA_CUBE; break;
		case 4: type = EntityType.PIGLIN; break;
		case 5: type = EntityType.PIGLIN_BRUTE; break;
		case 6: type = EntityType.SKELETON; break;		
		case 7: type = EntityType.WITHER_SKELETON; break;		
		case 8: type = EntityType.ZOGLIN; break;		
		case 9: type = EntityType.ZOMBIFIED_PIGLIN; break;		
		}
		
		if (type != null) {
			ent = location.getWorld().spawnEntity(location, type);
		}
	}
	
	private void spawnEndEntity(Location location) {
		EntityType type = null;
		int num = rand.nextInt(3);
		
		switch (num) {
		case 0: type = EntityType.ENDERMAN; break;
		case 1: type = EntityType.ENDERMITE; break;
		case 2: type = EntityType.SHULKER; break;	
		}
		
		if (type != null) {
			ent = location.getWorld().spawnEntity(location, type);
		}
	}
	
}
