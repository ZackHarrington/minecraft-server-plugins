package com.zack.MapModificatons.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zack.MapModificatons.Main;

public class VanishCommand implements CommandExecutor{

	private Main main;
	public VanishCommand (Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			// Toggle
			if (main.vanished.contains(player)) {
				main.vanished.remove(player);
				for (Player target : Bukkit.getOnlinePlayers()) {
					target.showPlayer(player);
				}
				
				player.sendMessage("Unvanished");
			} else {
				main.vanished.add(player);
				for (Player target : Bukkit.getOnlinePlayers()) {
					target.hidePlayer(player);
				}
				
				player.sendMessage("Vanished");
			}
			
			
		}
		
		return false;
	}
	
}
