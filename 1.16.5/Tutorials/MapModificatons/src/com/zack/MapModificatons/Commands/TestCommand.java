package com.zack.MapModificatons.Commands;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zack.MapModificatons.Main;

import net.md_5.bungee.api.ChatColor;

public class TestCommand implements CommandExecutor{

	private Main main;
	public TestCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (args.length == 1) {
				int action;
				
				try {
					action = Integer.parseInt(args[0]);
				} catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + "Invalid instruction!");
					return false;
				}
				
				switch (action) {
				case 1: // kicking
					player.kickPlayer("\nAbsolutly" + ChatColor.YELLOW + " REMOVED\n");
					break;
				case 2: // banning
					// player name, reason, expiration date (null is permanent), null
					Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), "You suck", null, null);
					// Banning doesn't kick so also kick
					// in console: pardon PLAYERNAME will unban someone
					break;
				case 3: // temp banning
					Calendar cal = Calendar.getInstance();
					// month starts at 0
					cal.set(2017, 1, 11, 5, 57);
					Date date = cal.getTime();
					
					Bukkit.getBanList(Type.NAME).addBan(player.getName(), "You suck for a few minutes", date, null);
					break;
				default:
					return false;
				}
			} else {
				player.sendMessage("Not good enough, try again");
			}
		} else {
			System.out.println("Valid in game only");
		}
		
		return false;
	}
	
}
