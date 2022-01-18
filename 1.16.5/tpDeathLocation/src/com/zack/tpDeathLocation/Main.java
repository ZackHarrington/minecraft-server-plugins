package com.zack.tpDeathLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener{

	HashMap<UUID, Location> locations = new HashMap<>();
	List<Player> recentDeaths = new ArrayList<>();
	
	@Override
	public void onEnable() {
		System.out.println("TP DEATH LOCATION PLUGIN ENABLED");
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("TP DEATH LOCATION PLUGIN DISABLED");
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			UUID uuid = player.getUniqueId();
			if (cmd.getName().equals("tpdeathloc")) {
				if (recentDeaths.contains(player)) {
					player.teleport(locations.get(uuid));
					recentDeaths.remove(player);
				} else {
					player.sendMessage(ChatColor.RED + "You don't have a recent death location");
				}
			}
			
		}
		
		return false;
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		recentDeaths.add(player);
		player.sendMessage("Death Locaitons Saved");
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (locations.containsKey(event.getPlayer().getUniqueId())) {
			locations.remove(event.getPlayer().getUniqueId());
		}
		locations.put(event.getPlayer().getUniqueId(), event.getPlayer().getLocation());
	}

	
}

