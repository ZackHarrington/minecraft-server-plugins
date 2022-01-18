package com.zack.serveressentials_WorldGenerator.Commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class TpWorldCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (player.isOp()) {
				
				if (args.length == 2 && Bukkit.getPlayer(args[0]) != null && Bukkit.getWorld(args[1]) != null) {
					Player tpPlayer = Bukkit.getPlayer(args[0]);
					World tpWorld = Bukkit.getWorld(args[1]);
					tpPlayer.teleport(tpWorld.getSpawnLocation());
				} else {
					player.sendMessage(ChatColor.RED + "Invalid usage! Use:");
					player.sendMessage(ChatColor.RED + " - /tpworld <player_name> <world>");
				}
				
			} else {
				player.sendMessage(ChatColor.RED + "You are not a server operator");
			}
		} else {
			System.out.println("Console cannot run this command at this time");
		}
		
		return false;
	}

}
