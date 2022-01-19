package com.zack.minigame_PvPArena.ArenaClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import com.zack.minigame_PvPArena.Config;
import com.zack.minigame_PvPArena.Manager;
import com.zack.minigame_PvPArena.Kits.Kit;
import com.zack.minigame_PvPArena.Kits.Types.ColdSnapKit;
import com.zack.minigame_PvPArena.Kits.Types.DefaultKit;
import com.zack.minigame_PvPArena.Kits.Types.EarthBenderKit;
import com.zack.minigame_PvPArena.Kits.Types.FireMasterKit;
import com.zack.minigame_PvPArena.Kits.Types.ForestNymphKit;
import com.zack.minigame_PvPArena.Kits.Types.FrostLordKit;
import com.zack.minigame_PvPArena.Kits.Types.KitType;
import com.zack.minigame_PvPArena.Kits.Types.LongbowKit;
import com.zack.minigame_PvPArena.Kits.Types.LumberjackKit;
import com.zack.minigame_PvPArena.Kits.Types.NinjaKit;
import com.zack.minigame_PvPArena.Kits.Types.RabidFarmerKit;
import com.zack.minigame_PvPArena.Kits.Types.RailgunKit;
import com.zack.minigame_PvPArena.Kits.Types.RandomKit;
import com.zack.minigame_PvPArena.Kits.Types.ShotgunKit;
import com.zack.minigame_PvPArena.Kits.Types.SlowBurnKit;
import com.zack.minigame_PvPArena.Kits.Types.SniperKit;
import com.zack.minigame_PvPArena.Vehicles.ArmoredHorseVehicle;
import com.zack.minigame_PvPArena.Vehicles.FastHorseVehicle;
import com.zack.minigame_PvPArena.Vehicles.HorseVehicle;
import com.zack.minigame_PvPArena.Vehicles.Vehicle;

public class PvPArena {

	private int id;
	private ArrayList<UUID> players;
	private HashMap<UUID, String> playerNames;
	//private HashMap<UUID, Team> teams;
	private HashMap<UUID, Kit> kits;
	private List<Vehicle> vehicles;
	private List<Block> chests;
	private Location center;
	private Location spawn;
	private GameState state;
	private Countdown countdown;
	private Game game;
	private int time;
	private WeatherType weather;
	// For checking if the world is currently loading or not
	private boolean canJoin;
	
	public PvPArena(int id) {
		this.id = id;
		this.players = new ArrayList<>();
		this.playerNames = new HashMap<>();
		//this.teams = new HashMap<>();
		this.kits = new HashMap<>();
		this.vehicles = new ArrayList<>();
		this.chests = new ArrayList<>();
		loadWorld();
		this.spawn = Config.getPvPArenaLobbySpawn(id);
		this.state = GameState.RECUITING;
		this.countdown = new Countdown(this, CountdownType.GAME_START, null);
		this.game = new Game(this);
		this.time = Config.getPvPArenaTime(id);
		setTime();
		this.weather = Config.getPvPArenaWeather(id);
		setWeather();
		
		this.canJoin = true;
	}
	
	public void start() {
		// Remove entities then spawn in arena entities
		removeEntities();
		
		game.start();
		countdown = new Countdown(this, CountdownType.GAME_DURATION, null);
		countdown.begin();
	}
	
	public void pause() {
		countdown.setPaused(true);
		sendMessage(ChatColor.YELLOW + "The countdown has been paused");
		sendMessage(ChatColor.AQUA + "Why not spend this time checking out the available kits!");
		sendMessage(ChatColor.AQUA + "Use " + ChatColor.BLUE + "/pvparena kit");
	}
	public boolean isPaused() { return countdown.getPaused(); }
	public void resume() { countdown.setPaused(false); }
	
	public void end() {
		countdown.cancel();
		countdown = new Countdown(this, CountdownType.GAME_END, null);
		countdown.begin();
		
		game.announceWinner();
		
		for (UUID uuid : players) {
			if (game.isRespawning(Bukkit.getPlayer(uuid))) {
				game.respawn(Bukkit.getPlayer(uuid));
			}
			Bukkit.getPlayer(uuid).closeInventory();
		}
	}
	
	public void reset() {
		for (UUID uuid : players) {			
			removeKit(uuid);
			
			// Causes concurent exception
			//removePlayer(Bukkit.getPlayer(uuid));
			GameManager.removeDogs(Bukkit.getPlayer(uuid));
			Bukkit.getPlayer(uuid).teleport(Config.getPvPLobbySpawn());
			
			Bukkit.getPlayer(uuid).getInventory().clear();
			Bukkit.getPlayer(uuid).setFireTicks(0);
			Bukkit.getPlayer(uuid).setAbsorptionAmount(0);
			for (PotionEffect effect : Bukkit.getPlayer(uuid).getActivePotionEffects()) {
				Bukkit.getPlayer(uuid).removePotionEffect(effect.getType());
			}
			
			// Remove player's scoreboards
			game.removeScoreboard(Bukkit.getPlayer(uuid));
			game.removeRailgun(uuid);
		}
		
		if (!state.equals(GameState.RECUITING)) {
			countdown.cancel();
		}
		state = GameState.RECUITING;
		players.clear();
		vehicles.clear();
		chests.clear();
		//teams.clear();
		countdown = new Countdown(this, CountdownType.GAME_START, null);
		game.end();
		game = new Game(this);
		
		removeEntities();
		
		// Unload the world without saving so we can revert it next time
		Bukkit.unloadWorld(center.getWorld().getName(), false);
		// Reload the world for the next game
		loadWorld();
	}
	
	public void sendMessage(String message) {
		for (UUID uuid : players) {
			Bukkit.getPlayer(uuid).sendMessage(message);
		}
	}
	
	public void setWeather() {
		if (weather.equals(WeatherType.DOWNFALL)) {
			center.getWorld().setStorm(true);
			center.getWorld().setWeatherDuration(Config.getDefaultGameTimer());
		} else {
			center.getWorld().setStorm(false);
			center.getWorld().setClearWeatherDuration(Config.getDefaultGameTimer());
		}
	}
	public void setTime() {
		center.getWorld().setTime(time);
	}
	
	public void addPlayer(Player player) {
		players.add(player.getUniqueId());
		playerNames.put(player.getUniqueId(), player.getName());
		player.teleport(spawn);
		
		player.sendMessage(ChatColor.GREEN + "You are now playing in PvPArena " + id);
		// Binary search tree I think
		/*TreeMultimap<Integer, Team> count = TreeMultimap.create();
		for(Team team : Team.values()) {
			count.put(getTeamCount(team), team);
		}
		
		Team selected = (Team) count.values().toArray()[0];
		setTeam(player, selected);
		player.sendMessage(ChatColor.GRAY + "You were placed on " + selected.getDisplay() + ChatColor.GRAY + " team!");
		*/
		// Give them the default kit
		setKit(player.getUniqueId(), KitType.DEFAULT);
		player.sendMessage(ChatColor.AQUA + "You have been given the Default kit");
		player.sendMessage(ChatColor.AQUA + "Use " + ChatColor.BLUE +"/pvparena kit" + ChatColor.AQUA + " to edit your kit");
		
		if (players.size() >= Config.getDefaultRequiredPlayers()) {
			countdown.begin();
		}
	}
	
	public void removePlayer(Player player) {
		players.remove(player.getUniqueId());
		GameManager.removeDogs(player);
		player.teleport(Config.getPvPLobbySpawn());
		
		//removeTeam(player);
		removeKit(player.getUniqueId());
		
		player.getInventory().clear(); // remove kit items
		player.setFireTicks(0);
		player.setAbsorptionAmount(0);
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		
		if (state == GameState.LIVE || state == GameState.OVER) {
			game.removePlayer(player);
		}
		
		if (players.size() <= Config.getDefaultRequiredPlayers() && state.equals(GameState.COUNTDOWN)) {
			reset();
		}
		
		if (players.size() == 0 && state.equals(GameState.LIVE)) {
			reset();
		}
	}
	
	public int getID() { return id; }
	public List<UUID> getPlayers() { return players; }
	public String getPlayerName(UUID uuid) { return playerNames.get(uuid); }
	public HashMap<UUID, Kit> getKits() { return kits; }
	public List<Vehicle> getVehicles() { return vehicles; }
	public List<Block> getChests() { return chests; }
	public GameState getState() { return state; }
	public Game getGame() { return game; }
	public World getWorld() { return center.getWorld(); }
	public boolean canJoin() { return canJoin; }
	
	public void setState(GameState state) { this.state = state; }
	public void setJoinState(boolean state) { this.canJoin = state; }
	/*public Team getTeam(Player player) { return teams.get(player.getUniqueId()); }
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
	*/
	
	public void removeKit(UUID uuid) {
		if (kits.containsKey(uuid)) {
			kits.get(uuid).remove(); // end Listener
			kits.remove(uuid);
		}
	}
	
	public void setKit(UUID uuid, KitType type) {
		kits.remove(uuid); // In case they are changing the kit
		
		switch (type) {
		case DEFAULT: kits.put(uuid, new DefaultKit(uuid)); break;
		case RANDOM: kits.put(uuid, new RandomKit(uuid)); break;
		case LONGBOW: kits.put(uuid, new LongbowKit(uuid)); break;
		case SNIPER: kits.put(uuid, new SniperKit(uuid)); break;
		case SHOTGUN: kits.put(uuid, new ShotgunKit(uuid)); break;
		case RAILGUN: kits.put(uuid, new RailgunKit(uuid)); break;
		case LUMBERJACK: kits.put(uuid, new LumberjackKit(uuid)); break;
		case COLD_SNAP: kits.put(uuid, new ColdSnapKit(uuid)); break;
		case NINJA: kits.put(uuid, new NinjaKit(uuid)); break;
		case RABID_FARMER: kits.put(uuid, new RabidFarmerKit(uuid)); break;
		case FROST_LORD: kits.put(uuid, new FrostLordKit(uuid)); break;
		case EARTH_BENDER: kits.put(uuid, new EarthBenderKit(uuid)); break;
		case FIRE_MASTER: kits.put(uuid, new FireMasterKit(uuid)); break;
		case FOREST_NYMPH: kits.put(uuid, new ForestNymphKit(uuid)); break;
		case SLOW_BURN: kits.put(uuid, new SlowBurnKit(uuid)); break;
		}
	}
	
	public void loadVehicles() {
		// Load vehicles
		for (int i = 0; i < Config.getPvPArenaVehicleAmount(id); i++) {
			// 2/3 chance of the vehicle appearing
			if (Manager.getRandom().nextInt(3) != 0) {
				switch (Config.getPvPArenaVehicleType(id, i)) {
				case HORSE: vehicles.add(new HorseVehicle(Config.getPvPArenaVehicleSpawnpoint(id, i))); break;
				case FAST_HORSE: vehicles.add(new FastHorseVehicle(Config.getPvPArenaVehicleSpawnpoint(id, i))); break;
				case ARMORED_HORSE: vehicles.add(new ArmoredHorseVehicle(Config.getPvPArenaVehicleSpawnpoint(id, i))); break;
				}
			}
		}
	}
	
	private void loadWorld() {
		canJoin = false;
		// Config.getPvPArenaCenter also loads the world and sets saving to false
		center = Config.getPvPArenaCenter(id);
		
		loadVehicles();
		
		// Load chests
		for (int i = 0; i < Config.getPvPArenaChestAmount(id); i++) {
			// 3/4 chance of the chest appearing
			if (Manager.getRandom().nextInt(4) != 0) {
				Block block = Config.getPvPArenaChestSpawnpoint(id, i).getBlock();
				chests.add(block);
			}
		}
	}
	
	private void removeEntities() {
		for (Entity e : center.getWorld().getEntities()) {
			// Remove all entities just in case
			if (!(e instanceof Player)) {
				e.remove();
				if (e instanceof Damageable) {
					// Double precaution
					((Damageable) e).damage(1000);
				}
			}
		}
		// Remove chests
		for (int i = 0; i < Config.getPvPArenaChestAmount(id); i++) {
			Config.getPvPArenaChestSpawnpoint(id, i).getBlock().setType(Material.AIR);
		}
	}
	
}

