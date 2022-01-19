package com.zack.serveressentials_WorldGenerator;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.zack.serveressentials_WorldGenerator.Commands.TpWorldCommand;
import com.zack.serveressentials_WorldGenerator.Commands.UnloadWorldCommand;
import com.zack.serveressentials_WorldGenerator.Commands.WorldCommand;
import com.zack.serveressentials_WorldGenerator.Commands.WorldNameCommand;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		System.out.println("SERVER ESSENTIALS WORLD GENERATOR ENABLED");
		System.out.println(" - Custom worlds will be proceeded by tmpworld");
		System.out.println(" - Custom worlds may have their own nether and end worlds generated and linked automatically");
		System.out.println(" - Custom nether worlds will be followed by _nether");
		System.out.println(" - Custom end worlds will be followed by _the_end");
	
		new Manager();
		
		Bukkit.getPluginManager().registerEvents(new WorldGeneratorListener(), this);
		
		getCommand("generateworld").setExecutor(new WorldCommand());
		getCommand("unloadworld").setExecutor(new UnloadWorldCommand());
		getCommand("worldname").setExecutor(new WorldNameCommand());
		getCommand("tpworld").setExecutor(new TpWorldCommand());
	}

	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("SERVER ESSENTIALS WORLD GENERATOR DISABLED");
		
		Manager.obliterate();
	}
	
}
