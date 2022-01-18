package com.zack.minigame_PvPArena;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.zack.minigame_PvPArena.ArenaClasses.GameManager;
import com.zack.minigame_PvPArena.ArenaClasses.PvPArenaCommand;
import com.zack.minigame_PvPArena.ArenaClasses.PvPArenaListener;
import com.zack.minigame_PvPArena.Kits.KitListener;

public class Main extends JavaPlugin {

	private static Main instance;
	
	@Override
	public void onEnable() {
		System.out.println("PVPARENA MINIGAME ENABLED");
		
		Main.instance = this;
		
		new Config(this);
		// Manager uses Config
		new Manager();
		
		new GameManager();
		
		getCommand("pvpArena").setExecutor(new PvPArenaCommand());
		
		Bukkit.getPluginManager().registerEvents(new PvPArenaListener(), this);
		Bukkit.getPluginManager().registerEvents(new KitListener(), this);
	}
	
	@Override
	public void onDisable() {
		System.out.println("PVPARENA MINIGAME DISABLED");
		
		Manager.obliterate();
	}
	
	public static Main getInstance() { return instance; }
	
}
