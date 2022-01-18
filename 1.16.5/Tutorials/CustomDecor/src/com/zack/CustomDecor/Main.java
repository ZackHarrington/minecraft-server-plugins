package com.zack.CustomDecor;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new SignEvent(this), this);
		pm.registerEvents(new HideBlockEvent(this), this);
		pm.registerEvents(new MapEvent(this), this);
		
		this.getCommand("skull").setExecutor(new SkullCommand(this));
		this.getCommand("banner").setExecutor(new BannerCommand(this));
		this.getCommand("cooldown").setExecutor(new CooldownCommand(this));
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
	}
	
	/* COOLDOWN */
	
	//public HashMap<Player, Long> cooldown = new HashMap<>();
	
	public ArrayList<Player> cooldownEnabled = new ArrayList<>();
	
	
}