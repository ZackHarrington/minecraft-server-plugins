package com.zack.MinigameMechanics;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Game {

	private Arena arena;
	private HashMap<UUID, Integer> points;
	
	public Game(Arena arena) {
		this.arena = arena;
		this.points = new HashMap<>();
	}
	
	public void start() {
		arena.setState(GameState.LIVE);
		
		arena.updateSign(ChatColor.WHITE + "Arena " + arena.getID(),
				ChatColor.GREEN + "Live",
				" ",
				ChatColor.GRAY + "Players: " + arena.getPlayers().size());
		
		arena.sendMessage(ChatColor.GREEN + "Game has started! Be the first to break 20 blocks!");
		
		for (UUID uuid : arena.getKits().keySet()) {
			arena.getKits().get(uuid).onStart(Bukkit.getPlayer(uuid));
		}
		
		for (UUID uuid : arena.getPlayers()) {
			points.put(uuid, 0);
			// For cleaner transition and closing possible GUIs
			Bukkit.getPlayer(uuid).closeInventory();
		}
	}
	
	public void addPoint(Player player) {
		int p = points.get(player.getUniqueId()) + 1;
		
		if (p == 20) {
			arena.sendMessage(ChatColor.GOLD + player.getName() + " WINS!!");
			
			arena.reset();
			return;
		}
		
		points.replace(player.getUniqueId(), p);
	}
	
}
