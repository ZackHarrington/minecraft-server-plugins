package com.zack.serveressentials_Clock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Manager {

	private static HashMap<UUID, Stopwatch> playerStopwatches;
	private static HashMap<UUID, Countdown> playerCountdowns;
	private static List<UUID> countdownsToRemove;
	private static BukkitTask stopwatchRunnable;
	
	public Manager() {
		playerStopwatches = new HashMap<>();
		playerCountdowns = new HashMap<>();
		countdownsToRemove = new ArrayList<>();
		
		// Update once every .1 seconds
		stopwatchRunnable = new BukkitRunnable() {
			@Override
			public void run() {
				for (UUID uuid : playerStopwatches.keySet()) {
					playerStopwatches.get(uuid).update(0.1);
				}
				for (UUID uuid : playerCountdowns.keySet()) {
					playerCountdowns.get(uuid).update(0.1, uuid);
				}
				for (UUID uuid : countdownsToRemove) {
					Manager.removePlayerCountdown(uuid);
				}
				countdownsToRemove.clear();
			}
		}.runTaskTimer(Main.getInstance(), 0, 2);
	}
	
	// Stopwatch functions
	public static boolean hasStopwatch(UUID uuid) {
		return playerStopwatches.containsKey(uuid);
	}
	public static double getStopwatchTimeElapsed(UUID playerID) {
		return playerStopwatches.get(playerID).getTimeOnStopwatch();
	}
	public static void startPlayerStopwatch(Player player) {
		playerStopwatches.put(player.getUniqueId(), 
				new Stopwatch(player));
	}
	public static void pausePlayerStopwatch(UUID playerID) {
		playerStopwatches.get(playerID).pauseStopwatch();
	}
	public static void resumePlayerStopwatch(UUID playerID) {
		playerStopwatches.get(playerID).resumeStopwatch();
	}
	public static void resetPlayerStopwatch(UUID playerID) {
		playerStopwatches.get(playerID).resetStopwatch();
	}
	public static void removePlayerStopwatch(UUID playerID) {
		playerStopwatches.get(playerID).endStopwatch();
		playerStopwatches.remove(playerID);
	}
	
	// Countdown functions
	public static boolean hasCountdown(UUID uuid) {
		return playerCountdowns.containsKey(uuid);
	}
	public static void startPlayerCountdown(Player player, double startTime) {
		playerCountdowns.put(player.getUniqueId(), 
				new Countdown(player, startTime));
	}
	public static void pausePlayerCountdown(UUID playerID) {
		playerCountdowns.get(playerID).pauseCountdown();
	}
	public static void resumePlayerCountdown(UUID playerID) {
		playerCountdowns.get(playerID).resumeCountdown();
	}
	public static void restartPlayerCountdown(UUID playerID) {
		playerCountdowns.get(playerID).restartCountdown();
	}
	public static void removePlayerCountdown(UUID playerID) {
		playerCountdowns.get(playerID).endCountdown();
		playerCountdowns.remove(playerID);
	}
	
	public static void removePlayerCountdownSoon(UUID playerID) {
		countdownsToRemove.add(playerID);
	}
	
	// Helper functions
	public static String getStopwatchString(double secondsElapsed) {
		double positiveTimeElapsed = Math.abs(secondsElapsed); // Change to positive
		double decimal = positiveTimeElapsed - Math.floor(positiveTimeElapsed);
		int wholeNumber = (int)Math.floor(secondsElapsed);
		int deciseconds = (int)Math.floor(decimal * 10);
		int seconds = wholeNumber % 60; // just part counting up to 60
		int minutes = wholeNumber / 60; // integer division
		int hours = wholeNumber / (60*60); // integer division
		
		return ((hours > 9) ? "" : "0") + hours + ":" + 
				((minutes > 9) ? "" : "0") + minutes + ":" + 
				((seconds > 9) ? "" : "0") + seconds + "." + deciseconds;
	}
	
	public static void obliterate() {
		playerStopwatches.clear();
		playerCountdowns.clear();
		stopwatchRunnable.cancel();
	}
	
}
