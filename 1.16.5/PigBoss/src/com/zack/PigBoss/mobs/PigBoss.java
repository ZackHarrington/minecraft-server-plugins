package com.zack.PigBoss.mobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomLookaround;

public class PigBoss extends EntityCreature {
	//this.setHealth(120);
	//System.out.println(this.getHealth() + " Max: " + this.getMaxHealth());
	// Burns Periodically (when attacked)
	//this.setOnFire(100);
	//this.setGoalTarget((@Nullable EntityLiving) player);
	public PigBoss(EntityTypes<? extends EntityCreature> type, Location loc) {
		super(type, ((CraftWorld) loc.getWorld()).getHandle());
		
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		
		this.setInvulnerable(true);
	}
	
	@Override
	protected void initPathfinder() {
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(1, new PathfinderGoalMoveTowardPlayer(this, 1.0, 25)); // Our pathfinder
		this.goalSelector.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F)); // 8.0 is speed it will look at you
		this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
	}
	
	public int getID() {
		return this.getId();
	}
	
	public void setTarget(Player player) {
		this.setGoalTarget((EntityLiving)((CraftPlayer)player).getHandle(), TargetReason.CUSTOM, false);
	}
		
	public void setName(String name) {
		this.setCustomName(new ChatComponentText(ChatColor.translateAlternateColorCodes('&', name)));
		this.setCustomNameVisible(true);
	}
	
}

