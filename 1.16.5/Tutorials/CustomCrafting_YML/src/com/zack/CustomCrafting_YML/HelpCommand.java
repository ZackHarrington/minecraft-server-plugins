package com.zack.CustomCrafting_YML;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class HelpCommand implements CommandExecutor {

	private Main main;
	private int perPage;
	
	public HelpCommand(Main main) {
		this.main = main;
		perPage = 6;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (args.length == 0) {
				sendCommands(player, 1);
			} else if (args.length == 1) {
				int page;
				try {
					page = Integer.parseInt(args[0]);
				} catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Not a valid page");
					return false;
				}
				
				if (page > 0 && page <= getTotalPages()) {
					sendCommands(player, page);
				} else {
					player.sendMessage(ChatColor.RED + "Not a valid page");
				}
			} else {
				player.sendMessage(ChatColor.RED + "Invalid usage! Use /help [page]");
			}
			
			
			
		} else {
			System.out.println("Bad Console");
		}
		
		
		return false;
	}
	
	/* METHODS */
	
	private void sendCommands(Player player, int page) {
		List<String> commands = main.commands;

		int displayPage = page;
		int lowerBound = perPage * (page - 1);
		int upperBound;
		
		if (lowerBound + perPage > commands.size()) {
			upperBound = commands.size();
		} else {
			upperBound = lowerBound + perPage;
		}
		
		List<String> shownCommands = new ArrayList<>();
		for (int i = lowerBound; i < upperBound; i++) {
			shownCommands.add(commands.get(i));
		}
		
		player.sendMessage(ChatColor.RED + "Commands: [Page " + displayPage + "/" + getTotalPages() + "]");
		for (String cmd : shownCommands) {
			player.sendMessage(ChatColor.GREEN + "- " + ChatColor.GRAY + cmd);
		}
	}

	private int getTotalPages() {
		List<String> commands = main.commands;
		
		int totalPages = (int) Math.ceil((double) commands.size() / perPage);
		
		return totalPages;
	}
	
}
