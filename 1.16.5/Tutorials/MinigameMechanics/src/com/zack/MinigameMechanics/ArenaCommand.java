package com.zack.MinigameMechanics;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zack.MinigameMechanics.Kits.KitsGUI;
import com.zack.MinigameMechanics.Teams.TeamsGUI;

import net.md_5.bungee.api.ChatColor;

public class ArenaCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (args.length == 1 && args[0].equalsIgnoreCase("team")) {
				if (Manager.isPlaying(player)) {
					if (Manager.getArena(player).getState().equals(GameState.RECUITING) ||
							Manager.getArena(player).getState().equals(GameState.COUNTDOWN)) {
						new TeamsGUI(player);
					} else {
						player.sendMessage(ChatColor.RED + "You cannot change teams while the game is live!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You are not in an arena!");
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("kit")) { 
				if (Manager.isPlaying(player)) {
					if (Manager.getArena(player).getState().equals(GameState.RECUITING) ||
							Manager.getArena(player).getState().equals(GameState.COUNTDOWN)) {
						new KitsGUI(player);
					} else {
						player.sendMessage(ChatColor.RED + "You cannot edit your kit while the game is live!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You aren't in an arena!");
				}
				
			} else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
				player.sendMessage(ChatColor.GREEN + "These are the available arenas:");
				for (Arena arena : Manager.getArenas()) {
					player.sendMessage(ChatColor.GREEN + "- " + arena.getID());
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
				if (Manager.isPlaying(player)) {
					Manager.getArena(player).removePlayer(player);
					player.sendMessage(ChatColor.GREEN + "You have been removed from the arena");
				} else {
					player.sendMessage(ChatColor.RED + "You are not currently in an arena");
				}
				
			} else if (args.length >= 1 && args[0].equalsIgnoreCase("join")) {
				if (args.length > 1) {
					try {
						int id = Integer.parseInt(args[1]);
						
						if (id >= 0 && id <= (Config.getArenaAmount() - 1)) {
							if (Manager.isRecruiting(id)) {
								if (Manager.getArena(id).canJoin()) {
									Manager.getArena(id).addPlayer(player);
									player.sendMessage(ChatColor.GREEN + "You are now playing in Arena " + id);
								} else {
									player.sendMessage(ChatColor.RED + "Arena " + id + " is currently loading, please give it a few moments");
								}
							} else {
								player.sendMessage(ChatColor.RED + "Arena is live! You cannot join this game right now! Please try another arena");
							}
						} else {
							player.sendMessage(ChatColor.RED + "Invalid arena! See /arena list for available arenas");
						}
					} catch (NumberFormatException e) {
						player.sendMessage(ChatColor.RED + "Invalid arena! See /arena list for available arenas");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Invalid usage! Use /arena join [id]");
				}
			} else {
				player.sendMessage(ChatColor.RED + "Invalid usage - these are the options:");
				player.sendMessage(ChatColor.RED + "- /arena list");
				player.sendMessage(ChatColor.RED + "- /arena join [id]");
				player.sendMessage(ChatColor.RED + "- /arena leave");
			}
			
		} else {
			System.out.println("You cannot use this command from the console!");
		}
		
		
		
		return false;
	}
	
}
