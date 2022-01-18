package com.zack.hyperHostileMobs;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("HYPER HOSTILE MOBS ENABLED");
		
		new Manager();
		
		getCommand("hyperHostileMobs").setExecutor(new HostileMobsCommand());
		
		Bukkit.getPluginManager().registerEvents(new HostileMobsListener(), this);
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("HYPER HOSTILE MOBS DISABLED");
	}
	
}
