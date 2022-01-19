package com.zack.minigame_PvPArena;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.zack.minigame_PvPArena.ArenaClasses.GameState;
import com.zack.minigame_PvPArena.ArenaClasses.PvPArena;

public class Manager {

	private static ArrayList<PvPArena> pvpArenas;
	private static Random random;
	
	public Manager() {
		Manager.pvpArenas = new ArrayList<>();
		Manager.random = new Random();
		
		for (int i = 0; i <= (Config.getPvPArenaAmount() - 1); i++) {
			pvpArenas.add(new PvPArena(i));
		}
		// Initiate Lobby
		Config.getPvPLobbySpawn();
	}
	
	public static List<PvPArena> getPvPArenas() { return pvpArenas; }
	public static Random getRandom() { return random; }
	
	public static boolean isPlaying(Player player) {
		for (PvPArena pvpArena : pvpArenas) {
			if (pvpArena.getPlayers().contains(player.getUniqueId())) {
				return true;
			}
		}
		
		return false;
	}
	
	public static PvPArena getPvPArena(Player player) {
		for (PvPArena pvpArena : pvpArenas) {
			if (pvpArena.getPlayers().contains(player.getUniqueId())) {
				return pvpArena;
			}
		}
		
		return null;
	}
	
	public static PvPArena getPvPArena(int id) {
		for (PvPArena pvpArena : pvpArenas) {
			if (pvpArena.getID() == id) {
				return pvpArena;
			}
		}
		
		return null;
	}
	
	public static boolean isRecruiting(int id) { return getPvPArena(id).getState() == GameState.RECUITING; }
	
	public static boolean isPvPArenaWorld(World world) {
		for (PvPArena pvpArena : pvpArenas) {
			if (pvpArena.getWorld().getName().contentEquals(world.getName())) {
				// Will only have a name if the world has loaded
				return true;
			}
		}
		
		return false;
	}
	
	public static PvPArena getPvPArena(World world) {
		for (PvPArena pvpArena : pvpArenas) {
			if (pvpArena.getWorld().getName().contentEquals(world.getName())) {
				// Will only have a name if the world has loaded
				return pvpArena;
			}
		}
		
		return null;
	}
	
	public static void obliterate() {
		for (PvPArena pvpArena : pvpArenas) {
			pvpArena.reset();
		}
		pvpArenas.clear();
	}
	
}

