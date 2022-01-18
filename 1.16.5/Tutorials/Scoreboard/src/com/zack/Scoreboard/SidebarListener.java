package com.zack.Scoreboard;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;

public class SidebarListener implements Listener {

	private Main main;
	public SidebarListener(Main main) {
		this.main = main;
	}
	
	
	@EventHandler
	// Player PlayerLoginEvent is when they double click on the server
	public void onJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		main.blocksBroken.put(player, 0);
		
		main.BuildSidebar(player);
		

	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		
		// get amount
		int amount = main.blocksBroken.get(player);
		// update		
		main.blocksBroken.put(player, amount + 1);
		// update actual scoreboard
		player.getScoreboard().getTeam("blocks_broken").setSuffix(main.blocksBroken.get(player) + "");
		
	}
	
}
