package com.zack.PlayerCompass;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main main;
	
	@Override
	public void onEnable() {
		System.out.println("PLAYERCOMPASS ENABLED");
		
		Main.main = this;
		
		new Manager();
		
		getCommand("seek").setExecutor(new SeekCommand());
		
		Bukkit.getPluginManager().registerEvents(new CompassListener(), this);
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLAYERCOMPASS DISABLED");
		
		Manager.obliterate();
	}
	
	
	public static Main getInstance() {
		return main;
	}
	
}

