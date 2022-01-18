package com.zack.MapModificatons;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.zack.MapModificatons.Commands.TestCommand;
import com.zack.MapModificatons.Commands.VanishCommand;

public class Main extends JavaPlugin {

	public List<Player> vanished = new ArrayList<>();
	// Actual implementation remove players when they leave the server
	
	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
		
		this.getCommand("test").setExecutor(new TestCommand(this));
		this.getCommand("poof").setExecutor(new VanishCommand(this));
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
	}
	
}

