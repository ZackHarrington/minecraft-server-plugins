package com.zack.serveressentials_WorldEdit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SuperBlockCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (player.isOp()) {
				
				if (args.length == 1 && args[0].equalsIgnoreCase("remove")) {
					if (Manager.isThereLastSuperBlock(player)) {
						Manager.removeLastSuperBlock(player);
						player.sendMessage(ChatColor.GREEN + "Your last super block has been removed");
					} else {
						player.sendMessage(ChatColor.RED + "You haven't placed a super block recently");
					}
				} else if (args.length == 2) {
					try {
						Material type = Material.valueOf(args[0].toUpperCase());
						if (type.isBlock()) {
							try {
								int height = Integer.parseInt(args[1]);
								
								if (height >= 2 && height <= 1000) {
									ItemStack item = new ItemStack(type);
									ItemMeta itemMeta = item.getItemMeta();
									itemMeta.setDisplayName(ChatColor.GOLD + "SUPER " + type.name() + " " + height);
									itemMeta.setLocalizedName(height + "");
									List<String> lore = new ArrayList<>();
									lore.add(ChatColor.GRAY + "Works best if placed on top of a block");
									itemMeta.setLore(lore);
									item.setItemMeta(itemMeta);
									
									player.getInventory().addItem(item);
									player.sendMessage(ChatColor.GREEN + "You have been given a SUPER " + type.name() + " of height " + height);
								} else {
									player.sendMessage(ChatColor.RED + "Please specify a height between 2 and 1000");
								}
							} catch (NumberFormatException e) {
								player.sendMessage(ChatColor.RED + "That is not a number!");
							}
						} else {
							player.sendMessage(ChatColor.RED + "The specified item is not a block");
						}
					} catch (IllegalArgumentException e) {
						player.sendMessage(ChatColor.RED + "That is not a valid block type!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Invalid usage! Use:");
					player.sendMessage(ChatColor.RED + "- /superbarrier <block type> <height>");
					player.sendMessage(ChatColor.RED + "- /superbarrier remove");
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
