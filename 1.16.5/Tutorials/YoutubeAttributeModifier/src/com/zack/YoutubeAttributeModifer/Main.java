package com.zack.YoutubeAttributeModifer;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		this.getCommand("bonus").setExecutor(new BonusCommand());
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
	}
	
}

