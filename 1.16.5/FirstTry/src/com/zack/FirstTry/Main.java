package com.zack.FirstTry;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	public boolean allowSpawn = false;
	List<EntityMaker> spawnedEntities = new ArrayList<>();
	
	
	@Override
	public void onEnable() {
		System.out.println("FIRST TRY PLUGIN ENABLED");
		
		Bukkit.getPluginManager().registerEvents(new BreakListener(this), this);
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("FIRST TRY PLUGIN DISABLED");
	}
	
	// Commands
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equals("beginspawn")) {
			// Start the break spawning
			allowSpawn = true;
		} else if (cmd.getName().equals("endspawn")) {
			// End the break spawning
			allowSpawn = false;
		} else if (cmd.getName().equals("removespawned")) {
			// Remove all mobs that have been spawned from blocks
			for (int i = 0; i < spawnedEntities.size(); i++) {
				Entity ent = spawnedEntities.get(i).getEntity();
				if (ent.isValid() && !ent.isInvulnerable()) {
					ent.remove();
				}
			}
			// Remove entities from list
			for (int i = 0; i < spawnedEntities.size(); i++) {
				spawnedEntities.remove(0);
			}
		}
		
		return false;
	}
	
	
	
}
