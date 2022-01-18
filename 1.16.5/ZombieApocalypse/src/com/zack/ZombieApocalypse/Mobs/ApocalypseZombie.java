package com.zack.ZombieApocalypse.Mobs;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

import com.zack.ZombieApocalypse.Manager;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityZombie;
import net.minecraft.server.v1_16_R3.GenericAttributes;

public class ApocalypseZombie extends EntityZombie {

	private ZombieType type;
	
	public ApocalypseZombie(Location loc, ZombieType type) {
		super(EntityTypes.ZOMBIE, ((CraftWorld) loc.getWorld()).getHandle());
		
		this.type = type;
		
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		
		// Basic attributes
		this.setCustomName(new ChatComponentText(ChatColor.ITALIC + "" + ChatColor.WHITE + Manager.getRandomZombieName(type)));
		this.setCustomNameVisible(false);
		this.setCanPickupLoot(false);
		
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(type.getSpeedMultiplier());
		this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(50.0D);
	}
	
	public ZombieType getType() { return this.type; }
	
	/*@Override
	protected void initPathfinder() {
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		// 3 because target selectors go in between
		this.goalSelector.a(3, new PathfinderGoalMeleeAttack(this, 1.0D, true));
		this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 0.2D));
		// this, speed through village, false, 1, null
		this.goalSelector.a(5, new PathfinderGoalMoveThroughVillage(this, 0.2D, false, 1, null));
		this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.2D));
		// Both 8 because they both involve looking and looking at the player isn't necessarily more important
		this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
		this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
		
		// Targets - things you want the entity to do
		// Things to go after
		this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this)); // This way it takes damage when you hit it
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<EntityHuman>(this, EntityHuman.class, true));
	}
	*/
}
