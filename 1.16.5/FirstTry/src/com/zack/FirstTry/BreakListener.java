package com.zack.FirstTry;

import java.util.Random;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakListener implements Listener {

	Main main = null;
	private Random rand = new Random();
	
	public BreakListener(Main main) {
		this.main = main;
	}
	
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		
		// Check if allowed to spawn entities
		if (main.allowSpawn) {
			// 1 in 2 chance
			if (rand.nextInt(3) == 0) {
				// Get block that was broken
				Block b = e.getBlock();
			
				// Try to spawn entity
				EntityMaker entityMaker = new EntityMaker();
				entityMaker.spawnEntity(b);
				// If spawned add it to the list
				if (entityMaker.getEntity() != null) {
					main.spawnedEntities.add(entityMaker);
				}
			}
		}
		
	}
	
}
