package com.zack.BossPig;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerListener implements Listener {

	Main main;
	JavaPlugin plugin;
	
	public PlayerListener(Main main, JavaPlugin plugin) {
		this.main = main;
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		main.players.add(e.getPlayer());
		// Check if the first player
		if (main.players.size() == 1) {
			// Start the boss pig threads
			for (int i = 0; i < main.bossPigs.size(); i++) {
				main.bossPigs.get(i).startThread(plugin);
			}
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		for (int i = 0; i < main.players.size(); i++) {
			if (main.players.get(i) == e.getPlayer()) {
				main.players.remove(i);
			}
		}
	}
	
}
