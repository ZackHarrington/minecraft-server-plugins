package com.zack.MinigameMechanics;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main instance;
	
	@Override
	public void onEnable() {
		
		Main.instance = this;
		
		new Config(this);
		// Manager uses Config
		new Manager();
		
		getCommand("arena").setExecutor(new ArenaCommand());
		
		Bukkit.getPluginManager().registerEvents(new GameListener(), this);
	}
	
	@Override
	public void onDisable() {
		for (Arena arena: Manager.getArenas()) {
			arena.getNPC().remove();
		}
	}
	
	public static Main getInstance() { return instance; }
	
	/*
	 * In server properties set allow-nether to false to keep the nether from being generated for every arena world
	 * To stop the end from generating go into the Bukkit.yml and set allow-end to false
	 * */
	
}
