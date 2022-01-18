package com.zack.serveressentials_WorldEdit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		System.out.println("SERVER ESSENTIALS WORLD EDIT ENABLED");
	
		new Manager();
		
		Bukkit.getPluginManager().registerEvents(new WorldEditListener(), this);
		
		getCommand("superblock").setExecutor(new SuperBlockCommand());
	}

	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("SERVER ESSENTIALS WORLD EDIT DISABLED");
		
		Manager.obliterate();
	}
	
}
