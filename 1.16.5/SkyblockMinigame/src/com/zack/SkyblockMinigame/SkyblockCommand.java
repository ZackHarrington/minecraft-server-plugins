package com.zack.SkyblockMinigame;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class SkyblockCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			UUID uuid = player.getUniqueId();
			
			if (args.length == 1 && args[0].equalsIgnoreCase("save")) {
				if (Manager.isInWorld(uuid)) {
					Manager.getWorld(uuid).save();
					player.sendMessage(ChatColor.GREEN + "Your skyblock world has been saved!");
				} else {
					player.sendMessage(ChatColor.RED + "You are not in your skyblock world");
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("pause")) {
				if (Manager.isInWorld(uuid)) {
					if (!Manager.getWorld(uuid).getState().equals(WorldState.PAUSED)) {
						Manager.getWorld(uuid).setState(WorldState.PAUSED);
						player.sendMessage(ChatColor.GREEN + "Item generation has been paused");
					} else {
						player.sendMessage(ChatColor.RED + "Item generation is already paused");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You are not in your skyblock world");
				}
				
			} else if (args.length == 1 && args[0].equalsIgnoreCase("resume")) {
				if (Manager.isInWorld(uuid)) {
					if (Manager.getWorld(uuid).getState().equals(WorldState.PAUSED)) {
						Manager.getWorld(uuid).setState(WorldState.ACTIVE);
						player.sendMessage(ChatColor.GREEN + "Item generation resumed");
					} else {
						player.sendMessage(ChatColor.RED + "Your item generation is not paused");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You are not in your skyblock world");
				}
				
			} else if (args.length == 1 && args[0].equalsIgnoreCase("reset")) {
				if (Manager.isInWorld(uuid)) {
					player.teleport(Config.getLobbySpawn());
					player.sendMessage(ChatColor.GREEN + "You have been sent to the lobby while your world is being reset");
					Manager.getWorld(uuid).reset();
				} else {
					player.sendMessage(ChatColor.RED + "You are not in your skyblock world");
				}
				
			} else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
				if (Manager.isInWorld(uuid)) {
					player.teleport(Config.getLobbySpawn());
					Manager.getWorld(uuid).setState(WorldState.OFFLINE);
					player.sendMessage(ChatColor.GREEN + "You have left your skyblock world");
				} else {
					player.sendMessage(ChatColor.RED + "You are not in your skyblock world");
				}
				
			} else if (args.length >= 1 && args[0].equalsIgnoreCase("join")) {
				if (!Manager.isInWorld(uuid)) {
					Config.loadWorld(uuid);
					// Teleport the player to their skyblock
					player.teleport(Config.getWorldSpawn(uuid));
					player.sendMessage(ChatColor.GREEN + "Your have been sent to your skyblock world");
					Manager.getWorld(uuid).setState(WorldState.ACTIVE);
				} else {
					player.sendMessage(ChatColor.RED + "You are already in your skyblock world");
				}
				
			} else {
				player.sendMessage(ChatColor.RED + "Invalid usage - these are the options:");
				player.sendMessage(ChatColor.RED + "- /skyblock join");
				player.sendMessage(ChatColor.RED + "- /skyblock pause");
				player.sendMessage(ChatColor.RED + "- /skyblock resume");
				player.sendMessage(ChatColor.RED + "- /skyblock leave");
				player.sendMessage(ChatColor.RED + "- /skyblock save");
				player.sendMessage(ChatColor.RED + "- /skyblock reset");
			}
			
		} else {
			System.out.println("You cannot use this command from the console!");
		}
		
		
		
		return false;
	}
}
