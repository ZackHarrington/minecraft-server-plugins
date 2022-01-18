package com.zack.serveressentials_Clock;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class Stopwatch {

	private boolean running;
	private double stopwatchTimeElapsed;
	private BossBar stopwatchBar;
	
	public Stopwatch(Player player) {
		this.running = true;
		this.stopwatchTimeElapsed = 0;
		
		// Create boss bar
		stopwatchBar = Bukkit.createBossBar(
				ChatColor.WHITE + "Stopwatch: 00:00:00:0",
				BarColor.WHITE,
				BarStyle.SOLID); //, BarFlag); make function that does something when it appears
		stopwatchBar.setProgress(1);
		stopwatchBar.addPlayer(player);
	}
	
	public void pauseStopwatch() { running = false; }
	public void resumeStopwatch() { running = true; }
	public void resetStopwatch() { stopwatchTimeElapsed = 0; }
	public double getTimeOnStopwatch() { return stopwatchTimeElapsed; }
	public void endStopwatch() { stopwatchBar.removeAll(); }
	
	public void update(double secondsAdded) {
		if (running) {
			stopwatchTimeElapsed += secondsAdded;
			
			stopwatchBar.setTitle(ChatColor.WHITE + "Stopwatch: " + Manager.getStopwatchString(stopwatchTimeElapsed));
		}
	}
	
}
