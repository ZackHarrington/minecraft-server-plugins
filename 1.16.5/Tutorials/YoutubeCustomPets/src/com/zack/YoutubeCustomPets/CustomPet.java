package com.zack.YoutubeCustomPets;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityZombieHusk;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R3.PathfinderGoalLookAtPlayer;

public class CustomPet extends EntityZombieHusk {

	public CustomPet(Location loc, Player player) {
		super(EntityTypes.HUSK, ((CraftWorld) loc.getWorld()).getHandle());
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		this.setBaby(true);
		
		this.setInvulnerable(true);
		
		// target
		this.setGoalTarget((EntityLiving)((CraftPlayer)player).getHandle(), TargetReason.CUSTOM, false); // false, otherwise sheep don't work
	}
	
	// Removes all other path finder goals
	@Override
	public void initPathfinder() {
		// Float in water
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		// Our custom pathfinder
		this.goalSelector.a(1, new PathfinderGoalPet(this, 1, 15));
		// Look at player
		this.goalSelector.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));		
	}
	
	public Entity getEnt() {
		return (Entity)this;
	}
	
}
