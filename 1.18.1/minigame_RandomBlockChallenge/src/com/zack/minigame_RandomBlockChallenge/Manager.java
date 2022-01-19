package com.zack.minigame_RandomBlockChallenge;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Manager {

	private static HashMap<UUID, ActiveChallenge> challenges;
	private static Random random;
	
	public Manager() {
		Manager.challenges = new HashMap<>();
		Manager.random = new Random();
	}
	
	public static Random getRandom() { return random; }
	
	public static boolean isPlaying(Player player) {
		return challenges.containsKey(player.getUniqueId());
	}
	
	public static ActiveChallenge getChallenge(Player player) {
		return challenges.get(player.getUniqueId());
	}
	
	public static void createNewChallenge(Player player, char type, int numBlocks) {
		if (Manager.isPlaying(player)) {
			challenges.get(player.getUniqueId()).end();
		}
		challenges.put(player.getUniqueId(), new ActiveChallenge(player, type, numBlocks));
	}
	
	public static void newBlocks(Player player) {
		challenges.get(player.getUniqueId()).newBlocks();
	}
	
	public static void resetChallenge(Player player) {
		challenges.get(player.getUniqueId()).reset();
	}
	
	public static String getIdea() {
		String idea = "";
		// return idea for thing to create
		
		
		return idea;
	}
	
	public static void obliterate() {
		for (ActiveChallenge challenge : challenges.values()) {
			challenge.end();
		}
		challenges.clear();
	}
	
}
