package com.zack.entities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.EnumUtils;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		// (Location(world, x, y, z), entity)
		Entity ent = Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), 0, 100, 0), EntityType.CAVE_SPIDER);
		
		//ent.teleport(new Location(Bukkit.getWorld("world"), x, y, z)
		ent.setGravity(false);
		ent.isInvulnerable();
		// 20 ticks is one second
		ent.setFireTicks(100);
		
		// set to mob type to access mob specific options
		//CaveSpider caveSpider = (CaveSpider) ent;
		
		// Force entity to be like another
		Creeper creeper = (Creeper) ent;
		creeper.setExplosionRadius(25);
		
		
		// Enums
		EnumUtils.isValidEnum(DataTypes.class, "");
		
		
		spawnStand(new Location(Bukkit.getWorld("world"), 0, 90, 0));
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
	}
	
	/*@EventHandler
	public void entity(CreatureSpawnEvent e) {
		ent.remove();
	}
	*/
	
	
	// Armor Stands
	private void spawnStand(Location location) {
		
		ArmorStand stand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
		
		stand.setSmall(true); // half size
		stand.setBasePlate(false); // base plate?
		stand.setArms(true); // adds arms
		
		stand.setGravity(false);
		stand.setInvulnerable(true); // Can still be broken in creative
		
		stand.setCustomName("Name");
		stand.setCustomNameVisible(false);
		stand.setVisible(true);
		
		// Armor / weapons
		stand.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		stand.setItemInHand(new ItemStack(Material.DIAMOND_AXE));
		
		// Pose
		// 52, 125, 81 -> in degrees, EulerAngle in radians
		stand.setHeadPose(new EulerAngle(Math.toRadians(52), Math.toRadians(125), Math.toRadians(81))); // Go to armor stand website to find values
		stand.setLeftArmPose(null);
		stand.setRightLegPose(null);
		stand.setBodyPose(null);
		
	}
	
	
}
