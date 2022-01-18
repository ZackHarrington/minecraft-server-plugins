package com.zack.ZombieApocalypse.Mobs;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;

import com.zack.ZombieApocalypse.Manager;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.monster.EntityPigZombie;

public class ApocalypseZombifiedPiglin extends EntityPigZombie {

	private ZombieType type;
	
	public ApocalypseZombifiedPiglin(Location loc, ZombieType type) {
		super(EntityTypes.bh, ((CraftWorld) loc.getWorld()).getHandle());
		
		this.type = type;
		
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		
		// Basic attributes
		this.setCustomName(new ChatComponentText(ChatColor.ITALIC + "" + ChatColor.WHITE + Manager.getRandomZombieName(type)));
		this.setCustomNameVisible(false);
		this.setCanPickupLoot(false);
		
		this.getAttributeInstance(GenericAttributes.d).setValue(type.getSpeedMultiplier()); // Movement_Speed
		this.getAttributeInstance(GenericAttributes.b).setValue(50.0D); // Follow_Range
	}
	
	public ZombieType getType() { return this.type; }
	
}
