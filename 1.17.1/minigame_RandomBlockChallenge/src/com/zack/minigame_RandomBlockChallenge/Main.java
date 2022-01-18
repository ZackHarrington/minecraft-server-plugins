package com.zack.minigame_RandomBlockChallenge;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main main;
	
	@Override
	public void onEnable() {
		System.out.println("RANDOM BLOCK CHALLENGE MINIGAME ENABLED");
		
		Main.main = this;
		
		new Manager();
		
		Bukkit.getPluginManager().registerEvents(new ChallengeListener(), this);
		
		getCommand("randomBlockChallenge").setExecutor(new RandomBlockChallengeCommand());
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		Manager.obliterate();
		
		System.out.println("RANDOM BLOCK CHALLENGE MINIGAME DISABLED");
	}
	
	
	public static Main getInstance() {
		return main;
	}
	
}