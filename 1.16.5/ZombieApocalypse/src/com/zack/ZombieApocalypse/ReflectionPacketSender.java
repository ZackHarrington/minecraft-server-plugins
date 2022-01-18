package com.zack.ZombieApocalypse;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ReflectionPacketSender {

	private Class<?> getNMSClass(String className) {
		try {
			return Class.forName("net.minecraft.server." + 
					Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// This part is only for block break animation
	private Object buildPacket(Location block, int status) {
		
		try {
			Constructor<?> blockConstructor = getNMSClass("BlockPosition").getConstructor(
					int.class, int.class, int.class);
			Object blockPosition = blockConstructor.newInstance(block.getBlockX(), block.getBlockY(), block.getBlockZ());
			Constructor<?> packetConstructor = getNMSClass("PacketPlayOutBlockBreakAnimation").getConstructor(
					int.class, getNMSClass("BlockPosition"), int.class);
			Object packet = packetConstructor.newInstance(new Random().nextInt(2000), blockPosition, status);
			return packet;		
			
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void sendPacket(Location block, int status) {
		try {
			Object packet = buildPacket(block, status);
			for (Player player : block.getWorld().getPlayers()) {
				Object handle = player.getClass().getMethod("getHandle").invoke(player);
				Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
				playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet); 
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
