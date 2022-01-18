package com.zack.YoutubeCustomMobs.events;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.zack.YoutubeCustomMobs.Main;
import com.zack.YoutubeCustomMobs.mobs.Thief;

import net.minecraft.server.v1_16_R3.WorldServer;

public class BlockPlace implements Listener {

	private Main plugin;
	public BlockPlace (Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (!e.getBlock().getType().equals(Material.GOLD_BLOCK)) {
			return;
		}
		
		Random r = new Random();
		if ((r.nextInt(1000 + 0) - 0) > 100) {
			return;
		}
		
		Thief dirtyJoe = new Thief(e.getPlayer().getLocation());
		WorldServer world = ((CraftWorld) e.getPlayer().getWorld()).getHandle();
		world.addEntity(dirtyJoe);
		
		
		// From here on it's for the thief, Up to here is all you need for a custom mob -----
		e.setCancelled(true);
		
		for (ItemStack item : e.getPlayer().getInventory().getContents())
			plugin.stolenItems.add(item);
		
		e.getPlayer().getInventory().clear();
	}
	
}
