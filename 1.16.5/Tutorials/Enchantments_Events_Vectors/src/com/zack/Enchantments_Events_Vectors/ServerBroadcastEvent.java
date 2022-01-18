package com.zack.Enchantments_Events_Vectors;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

// This event will be able to be listened for by any plugin you're using
public class ServerBroadcastEvent extends Event implements Cancellable {

	// required
	private static final HandlerList HANDLERS = new HandlerList();
	
	private final Player player;
	private final String message;
	private boolean cancelled;
	
	public ServerBroadcastEvent(Player player, String message) {
		this.player = player;
		this.message = message;
		this.cancelled = false;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public String getMessage() {
		return message;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
	@Override
	public HandlerList getHandlers() {
		
		
		return HANDLERS;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.cancelled = arg0;
		
	}

	
	
}
