package com.zack.serveressentials_WorldEdit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WorldEditListener implements Listener {

	@EventHandler
	public void onBlockClick(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (!(player.isOp()))
			return;
		
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			ItemStack item = event.getItem();
			Block block = event.getClickedBlock();
			Location blockLocation = block.getLocation();
			
			if (item != null &&
					item.getItemMeta().getDisplayName().contains(ChatColor.GOLD + "SUPER ")) {
				
				int height = Integer.parseInt(item.getItemMeta().getLocalizedName());
				int buildHeight = block.getWorld().getMaxHeight();
				Material type = item.getType();
				
				List<Block> superBlocks = new ArrayList<>();
				for (int i = 0; i < height; i++) {
					blockLocation.add(0, 1, 0);
					if (blockLocation.getY() < buildHeight) {
						if (blockLocation.getBlock().getType().equals(Material.AIR)) {
							blockLocation.getBlock().setType(type);
							superBlocks.add(blockLocation.getBlock());
						}
					} else {
						break;
					}
				}
				Manager.setLastSuperBlock(player, superBlocks);
			}
		}
		
	}
	
}
