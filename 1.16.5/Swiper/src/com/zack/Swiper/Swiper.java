package com.zack.Swiper;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityChicken;
import net.minecraft.server.v1_16_R3.EntityFox;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EntityRabbit;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.GenericAttributes;
import net.minecraft.server.v1_16_R3.Item;
import net.minecraft.server.v1_16_R3.PathfinderGoalAvoidTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_16_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_16_R3.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_16_R3.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_16_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalPanic;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomStrollLand;

public class Swiper extends EntityFox{

	ItemStack fullItem;
	Random rand = new Random();
	
	public Swiper(Location loc, ItemStack item) {
		super(EntityTypes.FOX, ((CraftWorld) loc.getWorld()).getHandle());
		this.fullItem = item;
		
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		
		// Set item as in his mouth
		Item magicItem = CraftMagicNumbers.getItem(item.getType());
		net.minecraft.server.v1_16_R3.ItemStack mouthItem = new net.minecraft.server.v1_16_R3.ItemStack(magicItem);
		this.setSlot(EnumItemSlot.MAINHAND, mouthItem);
		
		// Basic attributes
		this.setCustomName(new ChatComponentText(ChatColor.BOLD + "" + ChatColor.WHITE + "Swiper"));
		this.setCustomNameVisible(false);
		this.setHealth(20);
		this.setFoxType(Type.RED);
		
		if (rand.nextInt(20) == 0) { // 1 in 20 chance, same as natural spawning
			this.setBaby(true);
		}
		
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.425D);
	}
	
	@Override
	protected void initPathfinder() {
		// the first number is the heirarchy of goals, so 0 is the highest
		// Float in water - float is always the most important
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		// 12: distance away until it stops, 1.0: speed of running, 1.0: speed of looking
		this.targetSelector.a(1, new PathfinderGoalAvoidTarget<EntityPlayer>(
				this, EntityPlayer.class, 12, 1.0D, 1.0D));
		// Second goal is to speed up when he panics (gets hit)
		this.goalSelector.a(2, new PathfinderGoalPanic(this, 1.5D));
		// Third, move through a village - this, speed through village, false, 1, null
		this.goalSelector.a(3, new PathfinderGoalMoveThroughVillage(this, 0.8D, false, 1, null));
		
		// After ensuring it is away from the player it can focus on attacking like a normal fox
		this.targetSelector.a(4, new PathfinderGoalHurtByTarget(this));
		this.targetSelector.a(5, new PathfinderGoalNearestAttackableTarget<EntityChicken>(this, EntityChicken.class, true));
		this.targetSelector.a(5, new PathfinderGoalNearestAttackableTarget<EntityRabbit>(this, EntityRabbit.class, true));
		// Reality but it messes too much with the intent of the program
		//this.targetSelector.a(5, new PathfinderGoalNearestAttackableTarget<EntityCod>(this, EntityCod.class, true));
		//this.targetSelector.a(5, new PathfinderGoalNearestAttackableTarget<EntitySalmon>(this, EntitySalmon.class, true));
		//this.targetSelector.a(5, new PathfinderGoalNearestAttackableTarget<EntityTropicalFish>(this, EntityTropicalFish.class, true));
		this.goalSelector.a(6, new PathfinderGoalMeleeAttack(this, 1.0D, true));
		this.goalSelector.a(7, new PathfinderGoalMoveTowardsRestriction(this, 0.2D));
		
		// Fourth goal (none of the above are happening), stroll on land, 0.6 is about normal villager stroll
		// Note random stroll land is explicitly land and random stroll can be land or water
		this.goalSelector.a(8, new PathfinderGoalRandomStrollLand(this, 0.6D));
		// Fifth goal is to slowly glance over at you
		this.goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 0.5F));
		this.goalSelector.a(10, new PathfinderGoalRandomLookaround(this));
	}
	
	public ItemStack getItem() {
		return this.fullItem;
	}
	
}
