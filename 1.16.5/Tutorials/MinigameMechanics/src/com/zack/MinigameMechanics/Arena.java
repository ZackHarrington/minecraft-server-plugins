package com.zack.MinigameMechanics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

import com.google.common.collect.TreeMultimap;
import com.zack.MinigameMechanics.Kits.Kit;
import com.zack.MinigameMechanics.Kits.KitType;
import com.zack.MinigameMechanics.Kits.Types.Fighter;
import com.zack.MinigameMechanics.Kits.Types.Miner;
import com.zack.MinigameMechanics.Teams.Team;

import net.md_5.bungee.api.ChatColor;

public class Arena {

	private int id;
	private ArrayList<UUID> players;
	private HashMap<UUID, Team> teams;
	private HashMap<UUID, Kit> kits;
	private Location sign;
	private Location spawn;
	private GameState state;
	private Countdown countdown;
	private Game game;
	// For checking if the world is currently loading or not
	private boolean canJoin;
	// NPC
	private Villager npc;
	
	public Arena(int id) {
		this.id = id;
		this.players = new ArrayList<>();
		this.teams = new HashMap<>();
		this.kits = new HashMap<>();
		this.sign = Config.getArenaSign(id);
		this.spawn = Config.getArenaSpawn(id);
		this.state = GameState.RECUITING;
		this.countdown = new Countdown(this);
		this.game = new Game(this);
		
		this.canJoin = true;
		
		updateSign(ChatColor.WHITE + "Arena " + id,
				ChatColor.GOLD + "Recruiting",
				" ",
				ChatColor.GRAY + "Click to Join!");
		
		npc = (Villager) Config.getArenaNPC(id).getWorld().spawnEntity(Config.getArenaNPC(id), EntityType.VILLAGER);
		npc.setAI(false);
		npc.setInvulnerable(true);
		npc.setCustomName(ChatColor.AQUA + "Arena " + id + ChatColor.GRAY + " (Click to Join)");
		npc.setCustomNameVisible(true);
		npc.setCollidable(false);
		npc.setProfession(Profession.CARTOGRAPHER);
	}
	
	public void start() {
		game.start();
	}
	
	public void reset() {
		for (UUID uuid : players) {
			removeKit(uuid);
			
			// Causes concurent exception
			//removePlayer(Bukkit.getPlayer(uuid));
			Bukkit.getPlayer(uuid).teleport(Config.getLobbySpawn());
			
			Bukkit.getPlayer(uuid).getInventory().clear();
		}
		
		updateSign(ChatColor.WHITE + "Arena " + id,
				ChatColor.GOLD + "Recruiting",
				" ",
				ChatColor.GRAY + "Click to Join!");
		
		state = GameState.RECUITING;
		players.clear();
		teams.clear();
		countdown = new Countdown(this);
		game = new Game(this);
		
		// Unload the world without saving so we can revert it next time
		Bukkit.unloadWorld(spawn.getWorld().getName(), false);
		// Reload the world for the next game
		canJoin = false;
		spawn = Config.getArenaSpawn(id);
	}
	
	public void sendMessage(String message) {
		for (UUID uuid : players) {
			Bukkit.getPlayer(uuid).sendMessage(message);
		}
	}
	
	public void addPlayer(Player player) {
		players.add(player.getUniqueId());
		player.teleport(spawn);
		
		// Binary search tree I think
		TreeMultimap<Integer, Team> count = TreeMultimap.create();
		for(Team team : Team.values()) {
			count.put(getTeamCount(team), team);
		}
		
		Team selected = (Team) count.values().toArray()[0];
		setTeam(player, selected);
		player.sendMessage(ChatColor.GRAY + "You were placed on " + selected.getDisplay() + ChatColor.GRAY + " team!");
		
		if (players.size() >= Config.getRequiredPlayers()) {
			countdown.begin();
		}
	}
	
	public void removePlayer(Player player) {
		players.remove(player.getUniqueId());
		player.teleport(Config.getLobbySpawn());
		
		if (state.equals(GameState.LIVE)) {
		updateSign(ChatColor.WHITE + "Arena " + id,
				ChatColor.GREEN + "Live",
				" ",
				ChatColor.GRAY + "Players: " + getPlayers().size());
		}
		
		removeTeam(player);
		removeKit(player.getUniqueId());
		
		player.getInventory().clear(); // remove kit items
		
		if (players.size() <= Config.getRequiredPlayers() && state.equals(GameState.COUNTDOWN)) {
			reset();
		}
		
		if (players.size() == 0 && state.equals(GameState.LIVE)) {
			reset();
		}
	}
	
	public void updateSign(String line1, String line2, String line3, String line4) {
		Sign newSign = (Sign) sign.getBlock().getState();
		newSign.setLine(0, line1);
		newSign.setLine(1, line2);
		newSign.setLine(2, line3);
		newSign.setLine(3, line4);
		newSign.update();
	}
	
	public int getID() { return id; }
	public List<UUID> getPlayers() { return players; }
	public HashMap<UUID, Kit> getKits() { return kits; }
	public GameState getState() { return state; }
	public Game getGame() { return game; }
	public World getWorld() { return spawn.getWorld(); }
	public Villager getNPC() { return npc; }
	public boolean canJoin() { return canJoin; }
	
	public void setState(GameState state) { this.state = state; }
	public void setJoinState(boolean state) { this.canJoin = state; }
	public Team getTeam(Player player) { return teams.get(player.getUniqueId()); }
	public void setTeam(Player player, Team team) {
		// remove incase of duplicates
		removeTeam(player);
		teams.put(player.getUniqueId(), team);
	}
	public void removeTeam(Player player) {
		if(teams.containsKey(player.getUniqueId())) {
			teams.remove(player.getUniqueId());
		}
	}
	
	public int getTeamCount(Team team) {
		int count = 0;
		for(Team t : teams.values()) {
			if (t.equals(team)) {
				count++;
			}
		}
		return count;		
	}
	
	public void removeKit(UUID uuid) {
		if (kits.containsKey(uuid)) {
			kits.get(uuid).remove(); // end Listener
			kits.remove(uuid);
		}
	}
	
	public void setKit(UUID uuid, KitType type) {
		kits.remove(uuid); // Incase they are already in there and just changing their kit 
		
		// Use switch statement if you have above three types
		if (type.equals(KitType.FIGHTER)) {
			kits.put(uuid, new Fighter(uuid));
		} else if (type.equals(KitType.MINER)) {
			kits.put(uuid, new Miner(uuid));
		}
	}
	
}
