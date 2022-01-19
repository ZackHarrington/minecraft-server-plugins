package com.zack.serveressentials_Clock;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main main;
	
	@Override
	public void onEnable() {
		System.out.println("SERVER ESSENTIALS CLOCK ENABLED");
		
		Main.main = this;
		
		new Manager();
		
		Bukkit.getPluginManager().registerEvents(new ClockListener(), this);
		
		getCommand("stopwatch").setExecutor(new StopwatchCommand());
		getCommand("countdown").setExecutor(new CountdownCommand());
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		Manager.obliterate();
		
		System.out.println("SERVER ESSENTIALS CLOCK DISABLED");
	}
	
	
	public static Main getInstance() {
		return main;
	}
	
}

