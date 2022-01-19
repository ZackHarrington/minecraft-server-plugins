package com.zack.serveressentials_MOTD;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PingListener implements Listener {

	@EventHandler
	public void onPing(ServerListPingEvent e) {
		// When you refresh the server page
		
		// Max Player Count
		e.setMaxPlayers(20);
		
		// MOTD
		e.setMotd("        " + ChatColor.WHITE + "-" + " " + ChatColor.MAGIC + "Duck" + " " + ChatColor.RESET + ChatColor.GOLD + 
				"Mbharring's Server" + " " + ChatColor.WHITE + ChatColor.MAGIC + "Meow" + " " + ChatColor.RESET + ChatColor.WHITE +
				"-" + "\n" + "              " + ChatColor.MAGIC + "Kitty" + " " + ChatColor.RESET + ChatColor.GOLD + "Come" + " " + 
				ChatColor.MAGIC + "And" + " " + ChatColor.RESET + ChatColor.GOLD + "Play" + " " + ChatColor.WHITE + 
				ChatColor.MAGIC + "Doggy");
		
		// Server Image
		try {
			// In case we have trouble finding the file
			e.setServerIcon(Bukkit.loadServerIcon(new File("Duck.jpg")));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
}
