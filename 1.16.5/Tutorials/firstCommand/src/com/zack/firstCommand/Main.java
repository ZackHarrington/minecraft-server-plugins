package com.zack.firstCommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		System.out.println("Plugin Enabled");
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("Plugin Disabled");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equals("hello")) {
			// Check if the command sender is a player in the game
			if (sender instanceof Player) {
				// Cast sender as a player
				Player player = (Player) sender;
				
				// Send message to player
				player.sendMessage(ChatColor.DARK_AQUA + "Hello" + ChatColor.GREEN + player.getName() + 
						ChatColor.DARK_GRAY + "Your health has been restored!");
				
				// Set players health
				player.setHealth(20.0);
				
			} else {
				System.out.println("You cannot use this command through console!");
			}
		}
		
		return false;
	}
	
}
