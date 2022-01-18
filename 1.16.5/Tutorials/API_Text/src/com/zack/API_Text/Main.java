package com.zack.API_Text;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		this.getCommand("hovertext").setExecutor(new HoverCommand());
		
		// Using plugin API
		
		Bukkit.getPluginManager().registerEvents(new PluginListener(this), this);
		
		if (getAPI() != null) {
			System.out.println("----> WorldEdit was found!");
		} else {
			System.out.println("----> WorldEdit was not found! Plugin disabled.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		
		// #55 video on Udemy has video on making your plugin an API
		
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
	}
	
	
	// world edit
	
	public WorldEditPlugin getAPI() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		
		if (plugin instanceof WorldEditPlugin) {
			return (WorldEditPlugin) plugin;
		} else {
			return null;
		}
	}
	
}
