package com.zack.minigame_RandomBlockChallenge;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class ActiveChallenge {
	
	private Player player;
	private World world;
	private int numBlocks;
	private long timerEnd;
	
	public ActiveChallenge(Player player, char size, int numBlocks) {
		this.player = player;
		this.numBlocks = numBlocks;
		
		// Create world based on size
		
		// Clear inventory and give player random blocks
	}
	
	public Player getPlayer() { return player; }
	
	
	public void newBlocks() {
		// give player new blocks
	}
	
	public void reset() {
		// Re-initialize world
	}
	
	public void end() {
		// unload world
	}
	
}
