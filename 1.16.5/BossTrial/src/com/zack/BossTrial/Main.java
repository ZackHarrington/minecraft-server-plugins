package com.zack.BossTrial;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.zack.BossTrial.Events.BossEvent;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new BossEvent(), this);
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
	}
	
}
