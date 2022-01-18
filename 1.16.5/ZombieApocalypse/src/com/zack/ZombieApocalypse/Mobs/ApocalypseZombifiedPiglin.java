package com.zack.ZombieApocalypse.Mobs;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

import com.zack.ZombieApocalypse.Manager;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityPigZombie;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.GenericAttributes;

public class ApocalypseZombifiedPiglin extends EntityPigZombie {

	private ZombieType type;
	
	public ApocalypseZombifiedPiglin(Location loc, ZombieType type) {
		super(EntityTypes.ZOMBIFIED_PIGLIN, ((CraftWorld) loc.getWorld()).getHandle());
		
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
	
}
