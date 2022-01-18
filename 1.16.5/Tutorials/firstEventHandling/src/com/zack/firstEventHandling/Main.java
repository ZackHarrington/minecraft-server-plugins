package com.zack.firstEventHandling;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		this.getConfig().options().copyDefaults();
		saveDefaultConfig();
		
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player) sender;
		if (cmd.getName().equals("config")) {
			String str = this.getConfig().getString("Word");
			int number = this.getConfig().getInt("Number");
			
			player.sendMessage("The word is: " + str + " The number is: " + number);
			
		}
		
		if (cmd.getName().equals("meow")) {
			editConfig();
		}
		
		return false;
	}
	
	public void editConfig() {
		this.getConfig().set("Word", "Meow");
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		Player player = e.getPlayer();
		
		// If player doesn't have permission to move
		if(!player.hasPermission("testplugin.allowmove")) {
			e.setCancelled(true);
		}
		
		// Doesn't allowed player to move
		//e.setCancelled(true);
		
	}
	
	@EventHandler
	public void onThrow(PlayerEggThrowEvent e) {
		Player player = e.getPlayer();
		
		player.sendMessage(ChatColor.BLACK + "Egg thrown!");
	}
	
}
