package com.zack.YoutubeCustomMobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.zack.YoutubeCustomMobs.events.BlockPlace;
import com.zack.YoutubeCustomMobs.events.EntityDamage;
import com.zack.YoutubeCustomMobs.events.EntityDeath;

public class Main extends JavaPlugin {

	// Inventory Items
	public List<ItemStack> stolenItems = new ArrayList<>();
	
	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new EntityDamage(), this);
		pm.registerEvents(new EntityDeath(this), this);
		pm.registerEvents(new BlockPlace(this), this);
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
	}
	
}

