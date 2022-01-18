package com.zack.MOTD_Boss_Holograms;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;

public class HologramListener implements Listener {

	private Main main;
	
	public HologramListener(Main main) {
		this.main = main;
	}
	
	@EventHandler // spawn hologram
	public void onEggThrow(PlayerEggThrowEvent e) {
		
		Player player = e.getPlayer();
		
		main.spawnHologram(player);
		
	}
	
}
