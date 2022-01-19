package com.zack.serveressentials_WorldEdit;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Manager {
	
	private static HashMap<UUID, List<Block>> lastSuperBlock;
	
	public Manager() {
		Manager.lastSuperBlock = new HashMap<>();
	}
	
	public static void setLastSuperBlock(Player player, List<Block> lastSuperBarrier) {
		if (Manager.lastSuperBlock.containsKey(player.getUniqueId())) {
			Manager.lastSuperBlock.replace(player.getUniqueId(), lastSuperBarrier);
		} else {
			Manager.lastSuperBlock.put(player.getUniqueId(), lastSuperBarrier);
		}
	}
	public static boolean isThereLastSuperBlock(Player player) {
		return lastSuperBlock.containsKey(player.getUniqueId());
	}
	public static void removeLastSuperBlock(Player player) {
		for (Block block : lastSuperBlock.get(player.getUniqueId())) {
			block.setType(Material.AIR);
		}
		lastSuperBlock.remove(player.getUniqueId());
	}
	
	
	public static void obliterate() {
		lastSuperBlock.clear();
	}
	
}
