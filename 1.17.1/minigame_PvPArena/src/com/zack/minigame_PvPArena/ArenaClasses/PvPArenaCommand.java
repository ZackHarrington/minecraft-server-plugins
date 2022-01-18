package com.zack.minigame_PvPArena.ArenaClasses;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zack.minigame_PvPArena.Config;
import com.zack.minigame_PvPArena.Manager;
import com.zack.minigame_PvPArena.Kits.KitGUIType;
import com.zack.minigame_PvPArena.Kits.KitsGUI;

public class PvPArenaCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			/*if (args.length == 1 && args[0].equalsIgnoreCase("team")) {
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
			} else*/ if (args.length == 1 && args[0].equalsIgnoreCase("respawn")) {
				if (Manager.isPlaying(player)) {
					if (Manager.getPvPArena(player).getGame().isRespawning(player)) {
						Manager.getPvPArena(player).getGame().respawn(player);
					} else {
						player.sendMessage(ChatColor.RED + "You are not respawning");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You aren't in a PvPArena!");
				}
				
			} else if (args.length == 1 && args[0].equalsIgnoreCase("resume")) {
				if (Manager.isPlaying(player)) {
					if (Manager.getPvPArena(player).getState().equals(GameState.COUNTDOWN) &&
							Manager.getPvPArena(player).isPaused()) {
						Manager.getPvPArena(player).resume();
					} else {
						player.sendMessage(ChatColor.RED + "The countdown is not paused");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You aren't in a PvPArena!");
				}
				
			} else if (args.length == 1 && args[0].equalsIgnoreCase("pause")) {
				if (Manager.isPlaying(player)) {
					if (Manager.getPvPArena(player).getState().equals(GameState.COUNTDOWN)) {
						if (Manager.getPvPArena(player).isPaused()) {
							player.sendMessage(ChatColor.RED + "The countdown is already paused");
						} else {
							Manager.getPvPArena(player).pause();
						}
					} else {
						player.sendMessage(ChatColor.RED + "You may only pause the countdown for starting the game");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You aren't in a PvPArena!");
				}
				
			} else if (args.length == 1 && args[0].equalsIgnoreCase("kit")) { 
				if (Manager.isPlaying(player)) {
					new KitsGUI(player, KitGUIType.CLASS_SELECTOR);
				} else {
					player.sendMessage(ChatColor.RED + "You aren't in a PvPArena!");
				}
				
			} else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
				player.sendMessage(ChatColor.GREEN + "These are the available PvPArenas:");
				for (PvPArena pvpArena : Manager.getPvPArenas()) {
					player.sendMessage(ChatColor.GREEN + "- " + pvpArena.getID());
				}
				
			} else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
				if (Manager.isPlaying(player)) {
					Manager.getPvPArena(player).removePlayer(player);
					player.sendMessage(ChatColor.GREEN + "You have been removed from the PvPArena");
				} else {
					player.sendMessage(ChatColor.RED + "You are not currently in a PvPArena");
				}
				
			} else if (args.length >= 1 && args[0].equalsIgnoreCase("join")) {
				if (args.length > 1) {
					try {
						int id = Integer.parseInt(args[1]);
						
						if (id >= 0 && id <= (Config.getPvPArenaAmount() - 1)) {
							if (Manager.isRecruiting(id)) {
								if (Manager.getPvPArena(id).canJoin()) {
									if (Manager.isPlaying(player)) {
										if (Manager.getPvPArena(player).getID() == id) {
											player.sendMessage(ChatColor.RED + "You are already in this arena");
											return false;
										} else {
											Manager.getPvPArena(player).removePlayer(player);
										}
									}
									Manager.getPvPArena(id).addPlayer(player);
								} else {
									player.sendMessage(ChatColor.RED + "PvPArena " + id + " is currently loading, please give it a few moments");
								}
							} else {
								player.sendMessage(ChatColor.RED + "PvPArena is live! You cannot join this game right now! Please try another arena");
							}
						} else {
							player.sendMessage(ChatColor.RED + "Invalid arena! See /pvparena list for available arenas");
						}
					} catch (NumberFormatException e) {
						player.sendMessage(ChatColor.RED + "That is not a number! See /pvparena list for available arenas");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Invalid usage! Use /pvparena join [id]");
				}
			} else {
				player.sendMessage(ChatColor.RED + "Invalid usage - these are the options:");
				player.sendMessage(ChatColor.RED + "- /pvparena list");
				player.sendMessage(ChatColor.RED + "- /pvparena join [id]");
				player.sendMessage(ChatColor.RED + "- /pvparena leave");
				player.sendMessage(ChatColor.RED + "- /pvparena kit");
				player.sendMessage(ChatColor.RED + "Timer options:");
				player.sendMessage(ChatColor.RED + "- /pvparena pause");
				player.sendMessage(ChatColor.RED + "- /pvparena resume");
				player.sendMessage(ChatColor.RED + "- /pvparena respawn");
			}
			
		} else {
			System.out.println("You cannot use this command from the console!");
		}
		
		
		
		return false;
	}
	
}

