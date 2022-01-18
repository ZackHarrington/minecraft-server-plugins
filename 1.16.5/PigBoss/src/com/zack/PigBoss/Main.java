package com.zack.PigBoss;

import java.util.HashMap;

import org.bukkit.boss.BossBar;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.zack.PigBoss.events.EntityDamage;
import com.zack.PigBoss.events.EntityDeath;
import com.zack.PigBoss.mobs.PigBoss;

public class Main extends JavaPlugin {

	public HashMap<Integer, PigBoss> pigBossList = new HashMap<>();
	public HashMap<Integer, BossBar> bossBars = new HashMap<>();
	
	@Override
	public void onEnable() {
		System.out.println("PIG BOSS PLUGIN ENABLED");
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new EntityDeath(this), this);
		pm.registerEvents(new EntityDamage(this), this);
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PIG BOSS PLUGIN DISABLED");
	}
	
}

