package com.zack.BossTrial.Events;

import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.zack.BossTrial.Models.CustomCreature;

import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.WorldServer;

public class BossEvent implements Listener {

	@SuppressWarnings("unchecked")
	@EventHandler
	public void OnEntityDeath(EntityDeathEvent event) {
		EntityTypes<? extends EntityCreature> type = 
				(EntityTypes<? extends EntityCreature>)
				((CraftEntity)event.getEntity()).getHandle().getEntityType();
		
		Player player = event.getEntity().getKiller();
		
		CustomCreature pet = new CustomCreature(type, event.getEntity().getLocation());
		pet.setOwner(player);
		// The name must have the player's name in it for the entity to follow them
		pet.setName("&a" + player.getName() + " Boss");
		
		WorldServer world = ((CraftWorld)player.getWorld()).getHandle();
		world.addEntity(pet);
		return;
	}
	
	@EventHandler
	public void onEntityDelete(PlayerInteractEntityEvent event) {
		if (event.getRightClicked().getCustomName() == null)
			return;
		if (event.getRightClicked().getCustomName().contains(event.getPlayer().getName())) {
			event.getRightClicked().remove();
		}
	}
	
}
