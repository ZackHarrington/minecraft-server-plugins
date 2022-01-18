package com.zack.BossPig;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityPig;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.PathfinderGoalFollowEntity;
import net.minecraft.server.v1_16_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_16_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_16_R3.PathfinderGoalPanic;


public class PigBoss extends EntityPig{

	public PigBoss(Location loc, Player player) {
		super(EntityTypes.PIG, ((CraftWorld) loc.getWorld()).getHandle());
		
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		
		this.setCustomName(new ChatComponentText("PigBoss"));
		this.setCustomNameVisible(false);
		this.setHealth(120);
		this.setAggressive(true);
		this.setGoalTarget((@Nullable EntityLiving) player);
		
		this.goalSelector.a(0, new PathfinderGoalMeleeAttack(this, 1.0D, false));
		this.goalSelector.a(1, new PathfinderGoalFollowEntity(this, 1.0D, 1.0F, 1.0F));
		this.goalSelector.a(2, new PathfinderGoalPanic(this, 1.5D));
		this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityPlayer.class, 1.0F));
	}
	
}
