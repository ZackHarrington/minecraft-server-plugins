package com.zack.commandExecutors;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		// Command heal is called, then call HealCommand
		getCommand("heal").setExecutor(new HealCommand());
		getCommand("number").setExecutor(new teleportCommand());
		getCommand("consoleonly").setExecutor(new consoleCommand());
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
	}
	
}
