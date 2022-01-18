package com.zack.PigBoss.mobs;

import java.util.EnumSet;

import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.PathfinderGoal;
import net.minecraft.server.v1_16_R3.RandomPositionGenerator;
import net.minecraft.server.v1_16_R3.Vec3D;

public class PathfinderGoalMoveTowardPlayer extends PathfinderGoal {
	
	private final EntityInsentient a; // the 'this' in pathfinder goals, our pet
	private EntityLiving b; // owner <-- player
	
	private final double f; // pet's speed
	private final float g; // distance between owner & pet
	
	private double c; // x
	private double d; // y
	private double e; // z
	
	public PathfinderGoalMoveTowardPlayer(EntityInsentient a, double speed, float distance) {
		this.a = a;
		this.f = speed;
		this.g = distance;
		this.a(EnumSet.of(Type.MOVE)); // , Type.LOOK for multiple things happening
	}
	
	@Override
	public boolean a() {
		// Will start pathfinding goal if true
		// Runs every single tick
		this.b = this.a.getGoalTarget();
		if (this.b== null) {
			return false; // don't follow
		} else if (this.a.getDisplayName() == null)
			return false;
		else if (!(this.a.getDisplayName().toString().contains(this.b.getName()))) // if the pet name doesn't contains player name
			return false;
		else if (this.b.h(this.a) > (double) (this.g * this.g)) {
			// If the distance from the player is greater than the distance we sent into the constructor
			 // Teleport to player
			a.setPosition(this.b.locX(), this.b.locY(), this.b.locZ());
			return false;
		} else {
			// Follow the player
			
			Vec3D vec = RandomPositionGenerator.a((EntityCreature)this.a, 16, 7, this.b.getPositionVector());
			// If in air, don't teleport, it looks weird
			if (vec == null) {
				return false;
			}
			
			this.c = this.b.locX(); // x
			this.d = this.b.locY(); // y
			this.e = this.b.locZ(); // z
			
			return true; // <-- runs c()
		}
	}
	
	public void c() {
		// runner
		// Finds speed to          x,      y,      z,     speed
		this.a.getNavigation().a(this.c, this.d, this.e, this.f);		
	}
	
	public boolean b() {
		// runs after c()
		// run every tick as long as true (repeats c)
		// Did i make it to the location and am I still within __ blocks (distance)
		return !this.a.getNavigation().m() && this.b.h(this.a) < (double)(this.g * this.g);
	}
	
	public void d() {
		// runs when b() returns false
		this.b = null;
	}

}
