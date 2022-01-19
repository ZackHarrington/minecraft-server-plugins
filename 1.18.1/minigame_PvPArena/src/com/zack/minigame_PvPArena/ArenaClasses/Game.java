package com.zack.minigame_PvPArena.ArenaClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import com.zack.minigame_PvPArena.Config;
import com.zack.minigame_PvPArena.Manager;
import com.zack.minigame_PvPArena.Kits.FallingBlockRunnable;
import com.zack.minigame_PvPArena.Kits.RailgunRunnable;
import com.zack.minigame_PvPArena.Vehicles.Vehicle;

public class Game {

	private PvPArena pvpArena;
	private HashMap<UUID, Integer> kills;
	private HashMap<UUID, Integer> deaths;
	private HashMap<UUID, Countdown> respawnTimers;
	private HashMap<UUID, Float> fallingBlocks;
	private HashMap<UUID, UUID> fallingBlockShooters;
	private HashMap<UUID, UUID> logShooters;
	private HashMap<UUID, UUID> candleShooters;
	private HashMap<UUID, Long> railgunClocks;
	private FallingBlockRunnable fallingBlockRunnable;
	private RailgunRunnable railgunRunnable;
	private List<UUID> thunderArrows;
	private List<UUID> spinningCompasses;
	private CompassSpinner compassSpinner;
	private Scoreboard scoreboard;
	private Objective objective;
	
	public Game(PvPArena pvpArena) {
		this.pvpArena = pvpArena;
		this.kills = new HashMap<>();
		this.deaths = new HashMap<>();
		this.respawnTimers = new HashMap<>();
		this.fallingBlocks = new HashMap<>();
		this.fallingBlockShooters = new HashMap<>();
		this.logShooters = new HashMap<>();
		this.candleShooters = new HashMap<>();
		this.railgunClocks = new HashMap<>();
		this.fallingBlockRunnable = new FallingBlockRunnable(this);
		this.railgunRunnable = new RailgunRunnable(this);
		this.thunderArrows = new ArrayList<>();
		this.spinningCompasses = new ArrayList<>();
		this.compassSpinner = new CompassSpinner(this);
		
		buildScoreboard();
	}
	
	public void start() {
		pvpArena.setState(GameState.LIVE);
		
		pvpArena.sendMessage(ChatColor.GREEN + "The game has started!");
		
		int placement = 0;
		for (UUID uuid : pvpArena.getPlayers()) {
			kills.put(uuid, 0);
			deaths.put(uuid, 0);
			spinningCompasses.add(uuid);
			assignScoreboard(uuid, placement);
			placement++;
			
			// Teleport everyone to a random spawn point
			spawn(Bukkit.getPlayer(uuid));
		}
		
		compassSpinner.begin();
		fallingBlockRunnable.begin();
		railgunRunnable.begin();
	}
	
	public void end() {
		if (pvpArena.getState().equals(GameState.LIVE) ||
				pvpArena.getState().equals(GameState.OVER)) {
			if (!compassSpinner.isCancelled()) {
				compassSpinner.cancel();
			}
			if (!fallingBlockRunnable.isCancelled()) {
				fallingBlockRunnable.cancel();
			}
			if (!railgunRunnable.isCancelled()) {
				railgunRunnable.cancel();
			}
		}
		spinningCompasses.clear();
		for (UUID uuid : respawnTimers.keySet()) {
			respawnTimers.get(uuid).cancel();
		}
		for (UUID blockUuid : fallingBlocks.keySet()) {
			Bukkit.getEntity(blockUuid).remove();
		}
		for (UUID blockUuid : logShooters.keySet()) {
			Bukkit.getEntity(blockUuid).remove();
		}
		respawnTimers.clear();
		fallingBlocks.clear();
		fallingBlockShooters.clear();
		logShooters.clear();
		thunderArrows.clear();
	}
	
	public void respawn(Player player) {
		if (!respawnTimers.get(player.getUniqueId()).isCancelled()) {
			respawnTimers.get(player.getUniqueId()).cancel();
		}
		respawnTimers.remove(player.getUniqueId());
		
		spawn(player);
	}
	public void startRespawnTimer(Player player) {
		respawnTimers.put(player.getUniqueId(), new Countdown(pvpArena, CountdownType.RESPAWN_TIMER, player));
		respawnTimers.get(player.getUniqueId()).begin();
		player.sendMessage(ChatColor.AQUA + "To respawn quicker use " + ChatColor.BLUE + "/pvparena respawn");
	}
	public boolean isRespawning(Player player) {
		return respawnTimers.containsKey(player.getUniqueId());
	}
	
	public void setSpinCompass(Player player, boolean spin) {
		if (spinningCompasses.contains(player.getUniqueId())) {
			if (!spin) { spinningCompasses.remove(player.getUniqueId()); }
		} else {
			if (spin) { spinningCompasses.add(player.getUniqueId()); }
		}
	}
	public List<UUID> getSpinningCompasses() { return spinningCompasses; }
	
	public void addFallingBlock(UUID block, float damage, Player shooter) {
		fallingBlocks.put(block, damage);
		fallingBlockShooters.put(block, shooter.getUniqueId());
	}
	public void removeFallingBlock(UUID block) {
		if (fallingBlocks.containsKey(block)) {
			fallingBlocks.remove(block);
			fallingBlockShooters.remove(block);
		}
	}
	public HashMap<UUID, Float> getFallingBlocks() { return fallingBlocks; }
	public HashMap<UUID, UUID> getFallingBlockShooters() { return fallingBlockShooters; }
	
	public void addLog(UUID block, Player shooter) {
		logShooters.put(block, shooter.getUniqueId());
	}
	public void removeLog(UUID block) {
		if (logShooters.containsKey(block)) {
			logShooters.remove(block);
		}
	}
	public HashMap<UUID, UUID> getLogs() { return logShooters; }
	
	public void addCandle(UUID block, Player shooter) {
		candleShooters.put(block, shooter.getUniqueId());
	}
	public void removeCandle(UUID block) {
		if (candleShooters.containsKey(block)) {
			candleShooters.remove(block);
		}
	}
	public HashMap<UUID, UUID> getCandles() { return candleShooters; }
	
	public void addThunderBowArrow(UUID arrowUuid) {
		thunderArrows.add(arrowUuid);
	}
	public void removeThunderBowArrow(UUID arrowUuid) {
		if (thunderArrows.contains(arrowUuid)) {
			thunderArrows.remove(arrowUuid);
		}
	}
	public List<UUID> getThunderBowArrows() { return thunderArrows; }
	
	public void addRailgun(UUID playerUuid) {
		Long time = System.currentTimeMillis();
		
		if (railgunClocks.containsKey(playerUuid)) {
			railgunClocks.replace(playerUuid, time);
		} else {
			railgunClocks.put(playerUuid, time);
		}
	}
	public void removeRailgun(UUID playerUuid) {
		if (railgunClocks.containsKey(playerUuid)) {
			railgunClocks.remove(playerUuid);
		}
	}
	public HashMap<UUID, Long> getRailguns() { return railgunClocks; }
	
	// Called when a player is killed by another player
	public void kill(Player player, Player killer) {
		int k = kills.get(killer.getUniqueId()) + 1;
		kills.replace(killer.getUniqueId(), k);
		pvpArena.sendMessage(ChatColor.GOLD + " +1 Kill for " + killer.getName());
		
		// Run through player actually dying
		death(player);
		
		if (k == 20) {
			pvpArena.sendMessage(ChatColor.GOLD + killer.getName() + " WINS!");
			
			pvpArena.end();
			return;
		}
		
		// Update Scoreboards
		updateScoreboards();
	}
	
	public void loadChests() {
		for (Block block : pvpArena.getChests()) {
			// Put between 1 and 4 items into the chest
			for (int i = 0; i < (Manager.getRandom().nextInt(4) + 1); i++) {
				GameManager.loadChest(block);
			}
		}
	}
	
	public void loadVehicles() {
		pvpArena.loadVehicles();
		
		for (Vehicle vehicle : pvpArena.getVehicles()) {
			vehicle.onStart();
		}
	}
	
	// Called when a player dies from anything
	public void death(Player player) {
		int d = deaths.get(player.getUniqueId()) + 1;
		deaths.replace(player.getUniqueId(), d);
	}
	
	public void removePlayer(Player player) {
		if (respawnTimers.containsKey(player.getUniqueId())) {
			respawnTimers.get(player.getUniqueId()).cancel();
			respawnTimers.remove(player.getUniqueId());
		}
		// Remove scoreboard
		removeScoreboard(player);
		// Remove from spinning compass
		if (spinningCompasses.contains(player.getUniqueId())) {
			spinningCompasses.remove(player.getUniqueId());
		}
		
		removeRailgun(player.getUniqueId());
	}
	
	public void removeScoreboard(Player player) {
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
	
	public void announceWinner() {		
		int highestKills = 0;
		List<UUID> possibleWinners = new ArrayList<>();
		
		// Find possible winners
		for (UUID uuid : kills.keySet()) {
			if (kills.get(uuid) > highestKills) { highestKills = kills.get(uuid); }
		}
		for (UUID uuid : kills.keySet()) {
			if (kills.get(uuid) == highestKills) { possibleWinners.add(uuid); }
		}
		
		// Announce
		if (possibleWinners.size() == 1) {
			pvpArena.sendMessage(ChatColor.GOLD + "The winner with " + highestKills
					+ " kills is " + pvpArena.getPlayerName(possibleWinners.get(0)));
		} else if (possibleWinners.size() == 0) {
			pvpArena.sendMessage(ChatColor.RED + "We have an issue... No possible winners");
		} else {
			String message = ChatColor.GOLD + "Tieing for the win with " + highestKills + " kills is ";
			for (int i = 0; i < possibleWinners.size(); i++) {
				if (i == 0) {
					message = message + pvpArena.getPlayerName(possibleWinners.get(0));
				} else {
					message = message + " and " + pvpArena.getPlayerName(possibleWinners.get(i));
				}
			}
			pvpArena.sendMessage(message);
		}
	}
	
	private void spawn(Player player) {		
		// For cleaner transition and closing possible GUIs
		player.closeInventory();
		
		// Give player their kit and tracking compass
		player.getInventory().clear();
		player.getInventory().addItem(GameManager.getTrackingCompass());
		if (pvpArena.getKits().containsKey(player.getUniqueId())) {
			pvpArena.getKits().get(player.getUniqueId()).onStart(player);
		} else {
			pvpArena.sendMessage(ChatColor.RED + "Uh-oh something went wrong! Someone didn't get their kit!");
		}
		
		// In case player was falling right as they get teleported
		player.setVelocity(new Vector(0.0, 0.1, 0.0));
		
		// Set gamemode to survival and heal player just in case
		player.setGameMode(GameMode.SURVIVAL);
		player.setHealth(20);
		player.setFoodLevel(20);
		
		int respawnPoint = Manager.getRandom().nextInt(Config.getPvPArenaSpawnpointAmount(pvpArena.getID()));
		player.teleport(Config.getPvPArenaSpawnpoint(pvpArena.getID(), respawnPoint));
	}
	
	private void buildScoreboard() {
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		
		// reference name, not used
		objective = scoreboard.registerNewObjective(
				"pvpArenaBoard", "displayScores", ChatColor.GREEN + "" + ChatColor.BOLD + "Kills");
		//objective.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Board");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR); // on the side
				
		//Score line = objective.getScore("--------------");
		//line.setScore(4);		
	}
	
	private void assignScoreboard(UUID uuid, int placement) {
		String playerName = pvpArena.getPlayerName(uuid);
		Team blocksBroken = scoreboard.registerNewTeam(playerName); // identifier
		blocksBroken.addEntry(placement + ""); // need to give a unique identifier
		// prefix and suffix can only have 16 characters a piece
		blocksBroken.setPrefix(playerName + ": "); // makes it a static part in the front
		blocksBroken.setSuffix(this.kills.get(uuid) + ""); // makes it a string
		objective.getScore(placement + "").setScore(placement);
		
		Bukkit.getPlayer(uuid).setScoreboard(scoreboard);
	}
	
	private void updateScoreboards() {
		// Update everyone's score
		for (UUID uuid : pvpArena.getPlayers()) {
			scoreboard.getTeam(pvpArena.getPlayerName(uuid)).setSuffix(this.kills.get(uuid) + "");
		}
		// Reassign the scoreboard
		for (UUID uuid : pvpArena.getPlayers()) {
			Bukkit.getPlayer(uuid).setScoreboard(scoreboard);
		}
	}
	
}
