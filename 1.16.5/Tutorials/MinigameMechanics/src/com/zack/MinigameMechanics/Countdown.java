package com.zack.MinigameMechanics;

import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class Countdown extends BukkitRunnable {
	
	private Arena arena;
	private int seconds;

	public Countdown(Arena arena) {
		this.arena = arena;
		this.seconds = Config.getCountdownSeconds();
	}
	
	public void begin() {
		arena.setState(GameState.COUNTDOWN);
		this.runTaskTimer(Main.getInstance(), 0, 20);
		
		arena.updateSign(ChatColor.WHITE + "Arena " + arena.getID(), 
				ChatColor.BLUE + "Countdown", 
				" ", 
				ChatColor.GRAY + "Click to Join!");
	}
	
	@Override
	public void run() {
		if (seconds == 0) {
			cancel();
			arena.start();
			return;
		}
		
		if (seconds % 30 == 0 || seconds % 15  == 0 || seconds <= 10) {
			if (seconds == 1) {
				arena.sendMessage(ChatColor.AQUA + "Game will start in 1 second");
			} else {
				arena.sendMessage(ChatColor.AQUA + "Game will start in " + seconds + " seconds");
			}
		}
		
		if (arena.getPlayers().size() < Config.getRequiredPlayers()) {
			cancel();
			arena.setState(GameState.RECUITING);
			arena.sendMessage(ChatColor.RED + "There are too few players. Countdown stopped.");
			
			arena.updateSign(ChatColor.WHITE + "Arena " + arena.getID(),
					ChatColor.GOLD + "Recruiting",
					" ",
					ChatColor.GRAY + "Click to Join!");
			return;
		}
		
		seconds--;
	}
	
}
