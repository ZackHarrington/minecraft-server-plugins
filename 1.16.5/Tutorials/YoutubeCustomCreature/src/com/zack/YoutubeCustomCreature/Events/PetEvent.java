package com.zack.YoutubeCustomCreature.Events;

import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.zack.YoutubeCustomCreature.Models.CustomCreature;

import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.WorldServer;

public class PetEvent implements Listener {

	@SuppressWarnings("unchecked")
	@EventHandler
	public void OnEntityDeath(EntityDeathEvent event) {
		EntityTypes<? extends EntityCreature> type = 
				(EntityTypes<? extends EntityCreature>)
				((CraftEntity)event.getEntity()).getHandle().getEntityType();
		
		Player owner = event.getEntity().getKiller();
		
		CustomCreature pet = new CustomCreature(type, event.getEntity().getLocation());
		pet.setOwner(owner);
		pet.setName("&a" + owner.getName() + "'s Pet");
		
		WorldServer world = ((CraftWorld)owner.getWorld()).getHandle();
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
