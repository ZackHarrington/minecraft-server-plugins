package com.zack.serveressentials_MOTD;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("SERVER ESSENTIALS MOTD ENABLED");
		
		// Registers Listener
		Bukkit.getPluginManager().registerEvents(new PingListener(), this);
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("SERVER ESSENTIALS MOTD DISABLED");
	}
	
}

