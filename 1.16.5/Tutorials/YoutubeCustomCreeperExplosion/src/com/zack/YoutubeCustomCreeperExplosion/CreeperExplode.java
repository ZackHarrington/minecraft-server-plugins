package com.zack.YoutubeCustomCreeperExplosion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitRunnable;
//import org.bukkit.util.Vector;

public class CreeperExplode implements Listener {

	@EventHandler
	public void onBlowUP(EntityExplodeEvent event) {
		if (!(event.getEntity() instanceof Creeper)) 
			return;
		
		Creeper creeper = (Creeper) event.getEntity();
		
		if (!creeper.isPowered())
			return;
		
		Entity crystal = creeper.getWorld().spawnEntity(creeper.getLocation().add(0, 2, 0), EntityType.ENDER_CRYSTAL);
		
		new BukkitRunnable() {
			
			int count = 0;
			//Vector movement = null;
			List<Block> blocks = getNearbyBlocks(creeper.getLocation(), 15);
			List<FallingBlock> fblocks = new ArrayList<>();
			
			public void run() {
				if (count >= 300) {
					// 15 seconds
					cancel(); // Can use cancel in BukkitRunnables
					crystal.remove();
					TNTPrimed tnt = crystal.getWorld().spawn(crystal.getLocation(), TNTPrimed.class);
					tnt.setFuseTicks(0);
					for (FallingBlock b : fblocks) {
						b.setGravity(true);
					}
					return;
				}
				Random r = new Random();
				Block block = blocks.get(r.nextInt(blocks.size() - 0) + 0);
				FallingBlock fblock = creeper.getWorld().spawnFallingBlock(block.getLocation(), block.getBlockData());
				// multiply by 10, fly away, divide it would come towards crystal, and at spped 10
				fblock.setVelocity((fblock.getLocation().toVector().subtract(crystal.getLocation().toVector()).multiply(-10).normalize()));
				fblock.setGravity(false);
				fblock.setDropItem(false);
				fblock.setHurtEntities(true);
				block.setType(Material.AIR);
				fblocks.add(fblock);
				
				count++;
			}
			
		}.runTaskTimer(Main.getPlugin(Main.class), 0, 0);
	}
	
	public List<Block> getNearbyBlocks(Location loc, int radius) {
		List<Block> blocks = new ArrayList<Block>();
		
		for (int x = loc.getBlockX() - radius; x <= loc.getBlockX() + radius; x++) {
			for (int z = loc.getBlockZ() - radius; z <= loc.getBlockZ() + radius; z++) {
				// We are adding highest block (Always air, so get one under)
				blocks.add(loc.getWorld().getHighestBlockAt(x, z).getLocation().subtract(0, 1, 0).getBlock());
			}
		}
		
		return blocks;
	}
}
