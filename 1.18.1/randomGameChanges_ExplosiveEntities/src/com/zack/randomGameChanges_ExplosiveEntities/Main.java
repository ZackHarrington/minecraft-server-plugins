package com.zack.randomGameChanges_ExplosiveEntities;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main main;
	
	@Override
	public void onEnable() {
		System.out.println("EXPLOSIVE ENTITIES PLUGIN ENABLED");
		
		Main.main = this;
		
		new Manager();
		
		Bukkit.getPluginManager().registerEvents(new ExplosiveEntitiesListener(), this);
		
		getCommand("explosiveentities").setExecutor(new ExplosiveEntitiesCommand());
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		Manager.obliterate();
		
		System.out.println("EXPLOSIVE ENTITIES PLUGIN DISABLED");
	}
	
	
	public static Main getInstance() {
		return main;
	}
	
}
