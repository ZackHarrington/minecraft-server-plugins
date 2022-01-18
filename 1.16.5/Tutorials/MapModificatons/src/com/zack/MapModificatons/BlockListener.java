package com.zack.MapModificatons;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		
		Player player = e.getPlayer();
		
		if (e.getBlockPlaced().getType().equals(Material.BLACK_WOOL)) {
			e.getBlockPlaced().setType(Material.LAVA);	
		}
		
		//player.getWorld()
		
	}
	
}
