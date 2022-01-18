package com.zack.Runnables_GUI;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuCommand implements CommandExecutor {

	private Main main;
	
	public MenuCommand(Main main) {
		this.main = main;
	}
	
	@Override public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			main.applyElytraUI((Player) sender);
		}
		
		return false;
	}
	
}
