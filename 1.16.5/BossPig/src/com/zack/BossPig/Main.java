package com.zack.BossPig;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private final JavaPlugin plugin = this;
	
	public boolean bossPigSpawned = false;
	public List<BossPig> bossPigs = new ArrayList<>();
	public List<Player> players = new ArrayList<>();
	
	@Override
	public void onEnable() {
		System.out.println("BOSS PIG PLUGIN ENABLED");
		
		Bukkit.getPluginManager().registerEvents(new MobDamageListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerListener(this, plugin), this);
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("BOSS PIG PLUGIN DISABLED");
	}
	
	
	// Commands
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equals("summonBossPig")) {
			if (sender instanceof Player) {
				// Summon Boss Pig
				Player player = (Player) sender;
				BossPig bossPig = new BossPig(plugin, player);
				bossPigSpawned = true;
				
				// Append to list
				bossPigs.add(bossPig);
			} else {
				System.out.println("Console cannot summon a boss pig");
			}
		}
		
		return false;
	}

	
}

