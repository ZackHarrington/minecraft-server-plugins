package com.zack.minigame_PvPArena.Vehicles;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemStack;

public class FastHorseVehicle extends Vehicle {

	public FastHorseVehicle(Location spawnLocation) {
		super(spawnLocation, VehicleType.FAST_HORSE);		
	}
	
	@Override
	public void onStart() {
		Horse horse = (Horse) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.HORSE);
		horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		horse.setTamed(true);
		horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(6);
		horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.35);
		horse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(0.8);
		
	}
	
}
