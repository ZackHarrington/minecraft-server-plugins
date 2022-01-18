package com.zack.SkyblockMinigame;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class SkyblockWorld extends BukkitRunnable {

	private UUID uuid;
	private Location spawn;
	private int blocksGiven;
	private WorldState state;
	
	public SkyblockWorld(UUID uuid) {
		this.uuid = uuid;
		this.spawn = Config.getWorldSpawn(uuid);
		this.blocksGiven = 0;
		this.state = WorldState.OFFLINE;
		// Start giving the player blocks every five seconds
		this.runTaskTimer(Main.getInstance(), 100, 100);
		// Allows you to revert the world back to the previous save
		this.spawn.getWorld().setAutoSave(false);
	}
	
	public void save() { spawn.getWorld().save(); }
	public void reset() {
		// Unload the world without saving so we can revert it
		Bukkit.unloadWorld(spawn.getWorld().getName(), false);
		state = WorldState.OFFLINE;
	}
	
	public void setState(WorldState state) { this.state = state; }
	
	public UUID getPlayerID() { return uuid; }
	public World getWorld() { return spawn.getWorld(); }
	public int getBlocksGiven() { return blocksGiven; }
	public WorldState getState() { return state; }
	
	@Override
	public void run() {
		if (state.equals(WorldState.ACTIVE)) {
			// Give the player a random block
			Material block = Manager.getRandomBlock();
			Bukkit.getPlayer(uuid).getInventory().addItem(new ItemStack(block));
			blocksGiven++;
			Bukkit.getPlayer(uuid).sendMessage(ChatColor.GOLD + "+1" + ChatColor.AQUA + 
					" You recieved a " + ChatColor.LIGHT_PURPLE + block.name() + ChatColor.AQUA + 
					"! Total blocks recieved: " + ChatColor.GREEN + blocksGiven);
		}
	}
	
}
