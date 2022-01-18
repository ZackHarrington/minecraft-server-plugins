package com.zack.randomGameChanges_1;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main main;

	@Override
	public void onEnable() {
		System.out.println("RANDOM GAME CHANGES 1 PLUGIN ENABLED");

		Main.main = this;

		new Manager();

		Bukkit.getPluginManager().registerEvents(new RandomEventsListener(), this);
	}

	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("RANDOM GAME CHANGES 1 PLUGIN DISABLED");
	}

	public static Main getInstance() {
		return main;
	}

}

