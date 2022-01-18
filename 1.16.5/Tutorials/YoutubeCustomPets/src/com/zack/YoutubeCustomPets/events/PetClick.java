package com.zack.YoutubeCustomPets.events;

import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.zack.YoutubeCustomPets.Main;

public class PetClick implements Listener {

	private Main plugin;
	public PetClick(Main plugin) {
		this.plugin = plugin;
	}
	
	// Right click on mob to make them delete
	@EventHandler
	public void onClick(PlayerInteractEntityEvent e) {
		if (e.getRightClicked().getCustomName() == null)
			return;
		if (e.getRightClicked().getCustomName().contains(e.getPlayer().getName() + "'s Pet")) {
			e.getRightClicked().remove();
			e.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_HUGE,
					e.getPlayer().getLocation().getX(), e.getPlayer().getLocation().getY(),
					e.getPlayer().getLocation().getZ(), 0);
			plugin.pets.remove(e.getPlayer());
		}
	}
}
