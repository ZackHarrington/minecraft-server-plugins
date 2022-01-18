package com.zack.YoutubeCustomMobs.mobs;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityVillager;
import net.minecraft.server.v1_16_R3.PathfinderGoalAvoidTarget;
import net.minecraft.server.v1_16_R3.PathfinderGoalPanic;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomStrollLand;

public class Thief extends EntityVillager {

	public Thief(Location loc) {
		super(EntityTypes.VILLAGER, ((CraftWorld) loc.getWorld()).getHandle());
		
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		
		this.setCustomName(new ChatComponentText(ChatColor.GOLD + "" + ChatColor.BOLD + "Thief"));
		this.setCustomNameVisible(true);
		this.setHealth(100);
		
		// the first number is the heirarchy of goals, so 0 is the highest
		// 15: distance away until it runs, 1.0: speed of running, 1.0: speed of looking
		this.goalSelector.a(0, new PathfinderGoalAvoidTarget<EntityPlayer>(
				this, EntityPlayer.class, 15, 1.0D, 1.0D));
		// Second goal is to speed up when he panics (gets hit)
		this.goalSelector.a(1, new PathfinderGoalPanic(this, 2.0D));
		// Third goal (none of the above are happening), stroll on land, 0.6 is about normal villager stroll
		// Note random stroll land is explicitly land and random stroll can be land or water
		this.goalSelector.a(2, new PathfinderGoalRandomStrollLand(this, 0.6D));
		this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
	}
	
}
