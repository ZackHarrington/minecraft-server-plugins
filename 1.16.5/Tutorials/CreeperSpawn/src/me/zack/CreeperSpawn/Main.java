package me.zack.CreeperSpawn;

import org.bukkit.plugin.java.JavaPlugin;

import me.zack.CreeperSpawn.commands.CreeperCommand;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		new CreeperCommand(this);
	}
}
