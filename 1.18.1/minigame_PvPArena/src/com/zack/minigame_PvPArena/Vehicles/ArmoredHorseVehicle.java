package com.zack.minigame_PvPArena.Vehicles;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemStack;

public class ArmoredHorseVehicle extends Vehicle {

	public ArmoredHorseVehicle(Location spawnLocation) {
		super(spawnLocation, VehicleType.ARMORED_HORSE);		
	}
	
	@Override
	public void onStart() {
		Horse horse = (Horse) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.HORSE);
		horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		horse.getInventory().setArmor(new ItemStack(Material.IRON_HORSE_ARMOR));
		horse.setTamed(true);
		horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(14);
		horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.15);
		horse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).setBaseValue(0.6);
		
	}
	
}
