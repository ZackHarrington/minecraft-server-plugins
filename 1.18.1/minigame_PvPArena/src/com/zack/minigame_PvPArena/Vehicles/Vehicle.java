package com.zack.minigame_PvPArena.Vehicles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.zack.minigame_PvPArena.Main;

public abstract class Vehicle implements Listener {

	protected Location spawnLocation;
	protected VehicleType type;
	
	public Vehicle(Location spawnLocation, VehicleType type) {
		this.spawnLocation = spawnLocation;
		this.type = type;
		
		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
	}
	
	public Location getSpawnLocation() { return spawnLocation; }
	public VehicleType getType() { return type; }
	
	public abstract void onStart();
	
	public void remove() {
		HandlerList.unregisterAll(this);
	}
	
}
