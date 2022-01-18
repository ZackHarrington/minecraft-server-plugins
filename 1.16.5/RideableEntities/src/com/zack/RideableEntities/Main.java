package com.zack.RideableEntities;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.zack.RideableEntities.Events.PlayerEvent;
import com.zack.RideableEntities.Events.ProjectileEvent;
import com.zack.RideableEntities.Events.RideEvent;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("RIDEABLE ENTITIES PLUGIN ENABLED");
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new RideEvent(), this);
		pm.registerEvents(new ProjectileEvent(), this);
		pm.registerEvents(new PlayerEvent(), this);
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("RIDEABLE ENTITIES PLUGIN DISABLED");
	}
	
}

