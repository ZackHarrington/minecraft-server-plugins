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
	
	public static void pauseChallenge(Player player) {
		challenges.get(player.getUniqueId()).pause();
	}
	
	public static void resumeChallenge(Player player) {
		challenges.get(player.getUniqueId()).resume();
	}
	
	public static void resetChallenge(Player player) {
		challenges.get(player.getUniqueId()).reset();
	}
	
	public static void endChallenge(Player player) {
		challenges.get(player.getUniqueId()).end();
		challenges.remove(player.getUniqueId());
	}
	
	public static boolean freeBuild(Player player) {
		return challenges.get(player.getUniqueId()).requestToPlaceBlocks();
	}
	
	public static String getIdea() {
		String idea = "";

		switch(random.nextInt(30)) {
		case 0: idea = "a Temple"; break;
		case 1: idea = "a Fountain"; break;
		case 2: idea = "a Tree"; break;
		case 3: idea = "a House"; break;
		case 4: idea = "a Trojan animal"; break;
		case 5: idea = "an Animal shrine"; break;
		case 6: idea = "a Waterfall"; break;
		case 7: idea = "a Path through the woods"; break;
		case 8: idea = "a Bridge"; break;
		case 9: idea = "a Castle"; break;
		case 10: idea = "a Fortress"; break;
		case 11: idea = "an Igloo"; break;
		case 12: idea = "a Farm"; break;
		case 13: idea = "a Nether portal"; break;
		case 14: idea = "an End portal"; break;
		case 15: idea = "a Fish tank"; break;
		case 16: idea = "a Plant"; break;
		case 17: idea = "Modern art"; break;
		case 18: idea = "an Infestation"; break;
		case 19: idea = "a New biome"; break;
		case 20: idea = "a Nether lair"; break;
		case 21: idea = "an End monument"; break;
		case 22: idea = "a Well"; break;
		case 23: idea = "a Pyramid"; break;
		case 24: idea = "Some unearted dinosaur bones"; break;
		case 25: idea = "an Ancient civilization"; break;
		case 26: idea = "an Old ruined monument"; break;
		case 27: idea = "a Beach"; break;
		case 28: idea = "a Cabin"; break;
		case 29: idea = "Food"; break;
		default: idea = "no"; break;
		}
		
		return idea;
	}
	
	public static void obliterate() {
		for (ActiveChallenge challenge : challenges.values()) {
			challenge.end();
		}
		challenges.clear();
	}
	
}
