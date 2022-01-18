package com.zack.API_Text;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;

import net.md_5.bungee.api.ChatColor;

public class PluginListener implements Listener {

	private Main main;
	
	public PluginListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onEggThrow(PlayerEggThrowEvent event) {
		
		Player player = event.getPlayer();
		
		// Old way
		//Selection selec = main.getAPI().getSelection(player);
		// Supposed new way
		BukkitPlayer bPlayer = BukkitAdapter.adapt(player);
		Region region;
		try {
			region = WorldEdit.getInstance().getSessionManager().get(bPlayer).getSelection(bPlayer.getWorld());
		} catch (IncompleteRegionException e) {
			e.printStackTrace();
			return;
		}
		
		if (region == null) {
			player.sendMessage(ChatColor.RED + "You do not have anything selected!");
			return;
		}
		
		// Was:
		// world, first location, second location
		//CuboidSelection cS = new CuboidSelection();
		// Should now be:
		// first location, second location
		CuboidRegion cS = new CuboidRegion(region.getMinimumPoint(), region.getMaximumPoint());
		player.sendMessage("You have created the area!");
		
	}
	
	
	
}
