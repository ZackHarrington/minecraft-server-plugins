package com.zack.YoutubeCustomCreature.Models;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.inventory.ItemStack;

import com.zack.YoutubeCustomCreature.PathfinderGoalPet;

import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_16_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_16_R3.PathfinderGoalRandomLookaround;

public class CustomCreature extends EntityCreature {

	// Entity Creature has no pathfinder goals by default
	
	// we don't know what type the creature will be yet
	public CustomCreature(EntityTypes<? extends EntityCreature> type, Location loc) {
		super(type, ((CraftWorld)loc.getWorld()).getHandle());
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		
		this.setInvulnerable(true);
	}
	
	@Override
	protected void initPathfinder() {
		this.goalSelector.a(0, new PathfinderGoalFloat(this));
		this.goalSelector.a(1, new PathfinderGoalPet(this, 1.0, 25)); // Our pathfinder
		this.goalSelector.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F)); // 8.0 is speed it will look at you
		this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
	}
	
	public void setOwner(Player player) {
		this.setGoalTarget((EntityLiving)((CraftPlayer)player).getHandle(), TargetReason.CUSTOM, false); // false, otherwise sheep don't work
	}
	
	// Don't give animals armor or you'll get errors
	public void setItem(EnumItemSlot slot, ItemStack item) {
		this.setSlot(slot, CraftItemStack.asNMSCopy(item));
	}
	
	public void setName(String name) {
		this.setCustomName(new ChatComponentText(ChatColor.translateAlternateColorCodes('&', name)));
		this.setCustomNameVisible(true);
	}
	
}
