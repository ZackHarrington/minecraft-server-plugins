package com.zack.YoutubeCustomPets;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityChicken;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityZombieHusk;
import net.minecraft.server.v1_16_R3.PathfinderGoalAvoidTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_16_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_16_R3.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_16_R3.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_16_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomStroll;

class CustomCow extends EntityZombieHusk {

	public CustomCow(Location loc) {
		super(EntityTypes.HUSK, ((CraftWorld) loc.getWorld()).getHandle());
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		
		this.setBaby(true);
		this.setCustomName(new ChatComponentText("Hey"));
		this.setCustomNameVisible(true);
	}
	
	// remove pathfinders and set custom
	@Override 
	public void initPathfinder() {
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		// 4 because target selectors go in between
		this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, true));
		this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 0.2D));
		// this, speed through village, false, 1, null
		this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 0.2D, false, 1, null));
		this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 0.2D));
		// Both 8 because they both involve looking and looking at the player isn't necessarily more important
		this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
		this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		
		// Targets - things you want the entity to do at
		// Things you want it to go after
		this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this)); // This way it takes damage when you hit it
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityChicken>(this, EntityChicken.class, true)); // attack nearest chicken
		// this, type of target, distance, speed, speed look
		this.targetSelector.a(3, new PathfinderGoalAvoidTarget<EntityHuman>(this, EntityHuman.class, 20, 1.0D, 1.0D));
	}
	
}
