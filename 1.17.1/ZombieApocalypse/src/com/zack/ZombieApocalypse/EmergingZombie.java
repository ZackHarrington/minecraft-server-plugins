package com.zack.ZombieApocalypse;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketPlayOutBlockBreakAnimation;

public class EmergingZombie {
	
	private Location location;
	private int secondsCounter;
	
	// For making the blocks appear to be breaking
	//private ReflectionPacketSender packetSender;
	
	public EmergingZombie(Location location) {
		this.location = location;
		this.secondsCounter = 0;
		
		//this.packetSender = new ReflectionPacketSender();
	}
	
	public void update() {
		if (secondsCounter < 5) {
			secondsCounter++;
		}
		
		// Double check the location hasn't been broken
		if (!location.getBlock().getBlockData().getMaterial().equals(Material.AIR)) {
			// Way of sending packets that is version specific:
			// Random entity (doens't matter), the location, break amount (0-9)
			PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(
					new Random().nextInt(2000), new BlockPosition(location.getBlock().getX(), 
							location.getBlock().getY(), location.getBlock().getZ()), 3);
			for (Player player : location.getWorld().getPlayers()) {
				//((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet); // 1.16
				((CraftPlayer) player).getHandle().b.sendPacket(packet); // 1.17
			} 
			
			// Way that isn't version specific:
			//packetSender.sendPacket(location, secondsCounter * 2); // secondsCounter doubles as a status for the block breaking
		}
		
	}
	
	public void cancel() {
		// Break the block
		location.getBlock().setType(Material.AIR);
		
		// return block back to normal
		/*if (!location.getBlock().getBlockData().getMaterial().equals(Material.AIR)) {
			packetSender.sendPacket(location, 0);
		}*/
	}
	
	public int getCounter() { return secondsCounter; }
	public Location getLocation() { return location; }
	
}
