package com.zack.minigame_PvPArena.ArenaClasses;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.zack.minigame_PvPArena.Config;
import com.zack.minigame_PvPArena.Main;

public class Countdown extends BukkitRunnable {
	
	private CountdownType type;
	private PvPArena pvpArena;
	private int seconds;
	private Player player;
	private boolean paused;

	public Countdown(PvPArena arena, CountdownType type, Player player) {
		this.pvpArena = arena;
		this.type = type;
		this.player = player;
		this.paused = false;
		
		switch (type) {
		case GAME_START: this.seconds = Config.getStartCountdownSeconds(); break; 
		case GAME_DURATION: this.seconds = Config.getDefaultGameTimer() * 60; break; 
		case GAME_END: this.seconds = Config.getEndCountdownSeconds(); break; 
		case RESPAWN_TIMER: this.seconds = Config.getDefaultRespawnSeconds(); break;
		}
		
	}
	
	public void begin() {
		switch (type) {
		case GAME_START: pvpArena.setState(GameState.COUNTDOWN); break;
		case GAME_DURATION: 
			pvpArena.setState(GameState.LIVE);
			if (seconds % 180 != 0) {
				pvpArena.getGame().loadVehicles();
			}
			break;
		case GAME_END: pvpArena.setState(GameState.OVER); break;
		case RESPAWN_TIMER: break;
		}
		
		this.runTaskTimer(Main.getInstance(), 0, 20);
	}
	
	public void setPaused(boolean pause) { paused = pause; }
	public boolean getPaused() { return paused; }
	
	@Override
	public void run() {
		if (!paused) {
			if (seconds == 0) {
				cancel();
				switch (type) {
				case GAME_START: pvpArena.start(); break;
				case GAME_DURATION: 
					pvpArena.sendMessage(ChatColor.AQUA + "Game over"); 
					pvpArena.end(); 
					break;
				case GAME_END: pvpArena.reset(); break;
				case RESPAWN_TIMER: 
					pvpArena.getGame().respawn(player); 
					break;
				}
				return;
			}
			
			switch (type) {
			case GAME_START:
				if (seconds % 30 == 0 || seconds % 15  == 0 || seconds <= 10) {
					if (seconds == 1) {
						pvpArena.sendMessage(ChatColor.AQUA + "The game will start in 1 second");
					} else {
						pvpArena.sendMessage(ChatColor.AQUA + "The game will start in " + seconds + " seconds");
					}
				}
				
				if (pvpArena.getPlayers().size() < Config.getDefaultRequiredPlayers()) {
					cancel();
					pvpArena.setState(GameState.RECUITING);
					pvpArena.sendMessage(ChatColor.RED + "There are too few players. Countdown stopped.");
					
					return;
				}
				break;
			case GAME_DURATION:
				if (seconds % 60 == 0 || (seconds < 60 && (seconds % 30 == 0 || seconds % 15  == 0 || seconds <= 10))) {
					// Keep weather steady
					pvpArena.setWeather();
					
					if (seconds == 1) {
						pvpArena.sendMessage(ChatColor.AQUA + "1 second remaining");
					} else {
						if (seconds % 60 == 0 && seconds > 60) {
							pvpArena.sendMessage(ChatColor.AQUA + "" + seconds/60 + " minutes remaining");
							// Add items to the chests
							pvpArena.getGame().loadChests();
						} else {
							pvpArena.sendMessage(ChatColor.AQUA + "" + seconds + " seconds remaining");
							if (seconds == 60) { pvpArena.getGame().loadChests(); }
						}
					}
				}
				if (seconds % 180 == 0) {
					pvpArena.getGame().loadVehicles();
				}
				// Keep time as steady as possible
				pvpArena.setTime();
				
				break;
			case GAME_END: 
				
				break;
			case RESPAWN_TIMER: 
				if (seconds == 1) {
					player.sendMessage(ChatColor.AQUA + "Respawning in 1 second");
				} else {
					player.sendMessage(ChatColor.AQUA + "Respawning in " + seconds + " seconds");
				}
				break;
			}
			
			
			seconds--;
		}
	}
	
}