package com.zack.minigame_Skyblock;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private static Main instance;
	
	@Override
	public void onEnable() {
		System.out.println("SKYBLOCK MINIGAME ENABLED");
		
		Main.instance = this;
		
		new Config(this);
		// Manager uses Config
		new Manager();
		
		getCommand("skyblock").setExecutor(new SkyblockCommand());
		
		Bukkit.getPluginManager().registerEvents(new SkyblockListener(), this);
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("SKYBLOCK MINIGAME DISABLED");
	}

	
	public static Main getInstance() { return instance; }
	
}
