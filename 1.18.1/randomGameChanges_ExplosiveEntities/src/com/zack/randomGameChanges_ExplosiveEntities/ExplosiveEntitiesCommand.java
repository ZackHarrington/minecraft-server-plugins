package com.zack.randomGameChanges_ExplosiveEntities;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ExplosiveEntitiesCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (args.length == 1 && args[0].equalsIgnoreCase("?")) {
				if (Manager.isExplodingWorld(player.getWorld())) {
					player.sendMessage(ChatColor.YELLOW + "This world is quite explosive.. hmm yesss");
				} else {
					player.sendMessage(ChatColor.YELLOW + "Its a quite world... for how long though?");
				}
				
			} else if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
				if (Manager.addExplodingWorld(player.getWorld())) {
					player.sendMessage(ChatColor.GREEN + "May the dessstruction begin");
				} else {
					player.sendMessage(ChatColor.RED + "Hmm... it appears this world is already dangerous");
				}
				
			} else if (args.length == 1 && args[0].equalsIgnoreCase("end")) {
				if (Manager.removeExplodingWorld(player.getWorld())) {
					player.sendMessage(ChatColor.GREEN + "The world is peaceful again");
				} else {
					player.sendMessage(ChatColor.RED + "This world cannot get more peaceful that it already is");
				}
			} else {
				player.sendMessage(ChatColor.RED + "Invalid Usage - these are the options");
				player.sendMessage(ChatColor.RED + " - /explosiveentities start");
				player.sendMessage(ChatColor.RED + " - /explosiveentities end");
				player.sendMessage(ChatColor.RED + " - /explosiveentities ?");
			}
			
		} else {
			System.out.println("Console cannot affect the explosive entities");
		}
		
		return false;
	}

}
