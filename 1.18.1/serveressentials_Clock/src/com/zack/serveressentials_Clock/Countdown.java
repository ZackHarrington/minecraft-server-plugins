package com.zack.serveressentials_Clock;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Countdown {

	private boolean running;
	private double countdownStartTime;
	private double countdownTimeRemaining;
	private BossBar countdownBar;
	
	public Countdown(Player player, double startTimeSeconds) {
		this.running = true;
		this.countdownStartTime = startTimeSeconds;
		this.countdownTimeRemaining = startTimeSeconds;
		
		// Create boss bar
		countdownBar = Bukkit.createBossBar(
				ChatColor.WHITE + "Countdown: 00:00:00:0",
				BarColor.GREEN,
				BarStyle.SEGMENTED_6); //, BarFlag); make function that does something when it appears
		countdownBar.setProgress(1);
		countdownBar.addPlayer(player);
	}
	
	public void pauseCountdown() { running = false; }
	public void resumeCountdown() { running = true; }
	public void restartCountdown() { countdownTimeRemaining = countdownStartTime; }
	public double getTimeOnCountdown() { return countdownTimeRemaining; }
	public void endCountdown() { countdownBar.removeAll(); }
	
	public void update(double secondsRemoved, UUID playerID) {
		if (running) {
			countdownTimeRemaining -= secondsRemoved;
			
			countdownBar.setTitle(ChatColor.WHITE + "Countdown: " + Manager.getStopwatchString(countdownTimeRemaining));
			
			// Update progress
			double percentRemaining = countdownTimeRemaining / countdownStartTime;
			countdownBar.setProgress((percentRemaining > 0) ? percentRemaining : 0);
			
			if (percentRemaining > 0.333) {
				countdownBar.setColor(BarColor.GREEN);
			} else if (percentRemaining > 0.166) {
				countdownBar.setColor(BarColor.YELLOW);
			} else {
				countdownBar.setColor(BarColor.RED);
			}

			if (countdownTimeRemaining <= 0) {
				Manager.removePlayerCountdownSoon(playerID);
				Bukkit.getPlayer(playerID).sendMessage(ChatColor.RED + "The countdown has ended!");
			}
		}
	}
	
}