package com.zack.ZombieApocalypse;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main main;
	
	@Override
	public void onEnable() {
		System.out.println("ZOMBIE APOCALYPSE PLUGIN ENABLED");
		
		Main.main = this;
		
		new Manager();
		
		Bukkit.getPluginManager().registerEvents(new ZombieApocalypseListener(), this);
		
		getCommand("zombieapocalypse").setExecutor(new ZombieApocalypseCommand());
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		Manager.obliterate();
		
		System.out.println("ZOMBIE APOCALYPSE PLUGIN DISABLED");
	}
	
	
	public static Main getInstance() {
		return main;
	}
	
}

