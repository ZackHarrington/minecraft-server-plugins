package com.zack.minigame_RandomBlockChallenge;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

enum ChallengeState {
	COUNTDOWN,
	ACTIVE,
	OVER
}

public class ActiveChallenge extends BukkitRunnable {
	
	private Player player;
	private World world;
	private char size;
	private ChallengeState state;
	private int numBlocks;
	private boolean canPlaceBlocks;
	private int timeLeft;
	private boolean paused;
	
	public ActiveChallenge(Player player, char size, int numBlocks) {
		this.player = player;
		this.size = size;
		this.numBlocks = numBlocks;
		
		initWorld(player);
		
		this.runTaskTimer(Main.getInstance(), 20, 20);	// Every second
	}
	
	@Override
	public void run() {
		if (paused || state == ChallengeState.OVER)
			return;
		
		timeLeft -= 1;
		// Check if timer has ended
		if (timeLeft <= 0) {
			if (state == ChallengeState.ACTIVE) {
				// Time is up
				canPlaceBlocks = false;
				state = ChallengeState.OVER;
				player.sendMessage(ChatColor.RED + "Time is up!");
			} else {
				// Countdown to start has ended
				state = ChallengeState.ACTIVE;
				canPlaceBlocks = true;
				if (size == 's') { timeLeft = 20; }		// 20 seconds
				if (size == 'm') { timeLeft = 120; }	// 2 minutes
				if (size == 'l') { timeLeft = 1200; }	// 20 minutes
				player.sendMessage(ChatColor.GREEN + "Begin!");
				player.sendMessage(ChatColor.AQUA + Integer.toString(timeLeft) + " seconds starts now");
			}
		} else {
			if (timeLeft > 10 && timeLeft % 15 != 0)
				return;
			
			if (state == ChallengeState.ACTIVE) {
				// Tell player the time left
				player.sendMessage(ChatColor.AQUA + Integer.toString(timeLeft) + " " +
						(timeLeft != 1 ? "seconds" : "second") + " left");
			} else {
				// Tell player the countdown to start
				player.sendMessage(ChatColor.AQUA + Integer.toString(timeLeft) + " " +
						(timeLeft != 1 ? "seconds" : "second") + " until the challenge starts");
			}
		}
	}
	
	
	public Player getPlayer() { return player; }
	public World getWorld() {return world;}
	public boolean getCanPlaceBlocks() { return canPlaceBlocks; }
	
	public boolean requestToPlaceBlocks() {
		// Only allow if the timer has already finished
		if (state == ChallengeState.OVER) {
			canPlaceBlocks = true;
			return true;
		}
		return false;
	}
	
	public void newBlocks() {
		// Clear inventory and give player random blocks
		player.getInventory().clear();
		
		int count = 0;
		Material item;
		do {
			item = Material.values()[Manager.getRandom().nextInt(Material.values().length)];
			
			if (item.isBlock() && !(player.getInventory().contains(item)) && item != Material.AIR) {
				player.getInventory().addItem(new ItemStack(item));
				count += 1;
			}
		} while (count <= numBlocks);
	}
	
	public void pause() { paused = true; }
	public void resume() { paused = false; }
	
	public void reset() {
		unloadWorld();
		
		// Re-initialize world
		initWorld(player);
	}
	
	public void end() {
		unloadWorld();
		this.cancel();
	}
	
	private void initWorld(Player player) {
		state = ChallengeState.COUNTDOWN;
		canPlaceBlocks = false;
		timeLeft = 30;
		paused = false;
		
		// Create world based on size
		world = createNewWorld(player.getUniqueId());
		
		initCenterPad();
		// Allows you to revert the world back to the previous save
		world.setAutoSave(false);
		
		player.teleport(world.getSpawnLocation());
		player.setGameMode(GameMode.CREATIVE);
		newBlocks();
		
		player.sendMessage(ChatColor.AQUA + "30 seconds until the challenge starts");
		player.sendMessage(ChatColor.GRAY + "In the mean time feel free to check out ");
		player.sendMessage(ChatColor.ITALIC + " - /randomBlockChallenge ?");
		player.sendMessage(ChatColor.GRAY + "You can change up your block pallet with ");
		player.sendMessage(ChatColor.ITALIC + " - /randomBlockChallenge newBlocks");
		player.sendMessage(ChatColor.GRAY + "Or get some inspiration with ");
		player.sendMessage(ChatColor.ITALIC + " - /randomBlockChallenge idea");
		player.sendMessage(ChatColor.GRAY + "Note: this world is not saved");
	}
	
	private void unloadWorld() {
		player.setGameMode(GameMode.SURVIVAL);
		// Unload the world without saving so we can revert it
		Main.getInstance().getServer().unloadWorld(world.getName(), false);
	}
	
	private void initCenterPad() {
		int radius = 4;
		if (size == 'm') { radius = 6; }
		if (size == 'l') { radius = 8; }
		
		Location location;
		// Place bedrock around perimeter
		location = new Location(world, radius + 1, 59, radius + 1);
		for (int i = 0; i < 2*radius + 2; i++) {
			location.getBlock().setType(Material.BEDROCK);
			location.subtract(0, 0, 1);
		}
		for (int i = 0; i < 2*radius + 2; i++) {
			location.getBlock().setType(Material.BEDROCK);
			location.subtract(1, 0, 0);
		}
		for (int i = 0; i < 2*radius + 2; i++) {
			location.getBlock().setType(Material.BEDROCK);
			location.add(0, 0, 1);
		}
		for (int i = 0; i < 2*radius + 2; i++) {
			location.getBlock().setType(Material.BEDROCK);
			location.add(1, 0, 0);
		}
		
		// Fill the inside three deep with dirt
		for (int x = -radius; x < radius + 1; x++) {
			for (int z = -radius; z < radius + 1; z++) {
				//spawn = new Location(world, 0, 60, 0);
				location = new Location(world, x, 59, z);
				location.getBlock().setType(Material.GRASS_BLOCK);
				location.subtract(0, 1, 0).getBlock().setType(Material.DIRT);
				location.subtract(0, 1, 0).getBlock().setType(Material.DIRT);
			}
		}
	}
	
	private World createNewWorld(UUID uuid) {
		WorldCreator wc = new WorldCreator(uuid.toString() + "-RandomBlockChallenge");
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
			@Override
			public boolean canSpawn(World world, int x, int z) {
				return true;
			}
			
			@Override
			public Location getFixedSpawnLocation(World world, Random random) {
				return new Location(world, 0, 60, 0);
			}
		});
		return Bukkit.createWorld(wc);//wc.createWorld();
	}
	
}
