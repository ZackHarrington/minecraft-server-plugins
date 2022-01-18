package com.zack.Swiper;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("SWIPER ENABLED");
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new SwiperEvent(this), this);
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("SWIPER DISABLED");
	}
	
}
