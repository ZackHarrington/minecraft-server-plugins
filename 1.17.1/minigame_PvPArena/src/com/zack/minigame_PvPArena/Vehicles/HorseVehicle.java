package com.zack.minigame_PvPArena.Vehicles;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemStack;

public class HorseVehicle extends Vehicle {

	public HorseVehicle(Location spawnLocation) {
		super(spawnLocation, VehicleType.HORSE);		
	}
	
	@Override
	public void onStart() {
		Horse horse = (Horse) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.HORSE);
		horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		horse.setTamed(true);
		horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(10);
		horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.225);
		horse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(0.7);
		
	}
	
}
