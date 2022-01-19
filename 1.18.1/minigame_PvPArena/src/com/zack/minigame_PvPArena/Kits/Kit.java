package com.zack.minigame_PvPArena.Kits;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.zack.minigame_PvPArena.Main;
import com.zack.minigame_PvPArena.Kits.Types.KitType;

public abstract class Kit implements Listener {

	protected UUID uuid;
	protected KitType type;
	
	public Kit(UUID uuid, KitType type) {
		this.uuid = uuid;
		this.type = type;
		
		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
	}
	
	public UUID getUUID() { return uuid; }
	public KitType getType() { return type; }
	
	public abstract void onStart(Player player);
	
	public void remove() {
		HandlerList.unregisterAll(this);
	}
}

