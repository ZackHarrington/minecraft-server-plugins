package com.zack.Enchantments_Events_Vectors;

import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class VectorListener implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		
		Random rand = new Random();
		
		event.getPlayer().setVelocity(new Vector(rand.nextInt(5), rand.nextInt(2), rand.nextInt(5)));
		
	}
	
}
