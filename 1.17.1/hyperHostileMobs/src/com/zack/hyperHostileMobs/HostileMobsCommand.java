package com.zack.hyperHostileMobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HostileMobsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
				if (Manager.isActiveWorld(player.getWorld())) {
					player.sendMessage(ChatColor.RED + "The world is already full of agressive beasts!");
				} else {
					Manager.addWorld(player.getWorld());
					player.sendMessage(ChatColor.GREEN + "The mobs have been buffed!");
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("end")) {
				if (Manager.isActiveWorld(player.getWorld())) {
					Manager.removeWorld(player.getWorld());
					player.sendMessage(ChatColor.GREEN + "The creatures of this world have reverted back to their normal state");
				} else {
					player.sendMessage(ChatColor.RED + "The world is currently calm");
				}
			}
			
		} else {
			// Console
			
			if (args.length == 2 && Bukkit.getWorld(args[0]) != null) {
				
				World world = Bukkit.getWorld(args[0]);
				
				if (args[1].equalsIgnoreCase("start")) {
					if (Manager.isActiveWorld(world)) {
						System.out.println("The world is already full of agressive beasts!");
					} else {
						Manager.addWorld(world);
						for(Player player : world.getPlayers()) {
							player.sendMessage(ChatColor.GREEN + "The mobs have been buffed!");
						}
					}
				} else if (args[1].equalsIgnoreCase("end")) {
					if (Manager.isActiveWorld(world)) {
						Manager.removeWorld(world);
						for(Player player : world.getPlayers()) {
							player.sendMessage(ChatColor.GREEN + "The creatures of this world have reverted back to their normal state");
						}
					} else {
						System.out.println("The world is currently calm");
					}
				}
				
			}
			
		}
		
		
		return false;
	}

}
