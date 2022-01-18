package com.zack.GodEnchantments.Mobs;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EntitySilverfish;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityVillager;
import net.minecraft.server.v1_16_R3.GenericAttributes;
import net.minecraft.server.v1_16_R3.PathfinderGoalAvoidTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_16_R3.PathfinderGoalPanic;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomStrollLand;

public class Louce extends EntitySilverfish {
	
	ItemStack item;
	
	public Louce(Location loc, ItemStack item) {
		super(EntityTypes.SILVERFISH, ((CraftWorld) loc.getWorld()).getHandle());
		this.item = item;
		
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		
		// Basic attributes
		this.setCustomName(new ChatComponentText(ChatColor.BOLD + "" + ChatColor.WHITE + "Inventory Louce"));
		this.setCustomNameVisible(false);
		this.setHealth(6);
			
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.4D);
		this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(6.0D);
	}
	
	@Override
	protected void initPathfinder() {
		// the first number is the heirarchy of goals, so 0 is the highest
		// Float in water - float is always the most important
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		// 15: distance away until it stops, 1.0: speed of running, 1.0: speed of looking
		this.targetSelector.a(1, new PathfinderGoalAvoidTarget<EntityPlayer>(
				this, EntityPlayer.class, 15, 1.0D, 1.0D));
		// Secondary avoid other human looking creatures
		this.targetSelector.a(2, new PathfinderGoalAvoidTarget<EntityVillager>(
				this, EntityVillager.class, 5, 0.8D, 1.0D));
		// Second goal is to speed up when he panics (gets hit)
		this.goalSelector.a(3, new PathfinderGoalPanic(this, 1.5D));
		// Third goal (none of the above are happening), stroll on land, 0.6 is about normal villager stroll
		// Note random stroll land is explicitly land and random stroll can be land or water
		this.goalSelector.a(4, new PathfinderGoalRandomStrollLand(this, 0.6D));
		// Fourth goal is to slowly glance over at each other
		this.goalSelector.a(5, new PathfinderGoalLookAtPlayer(this, EntitySilverfish.class, 0.5F));
		this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
	}
	
	public ItemStack getItem() {
		return this.item;
	}
	
}
