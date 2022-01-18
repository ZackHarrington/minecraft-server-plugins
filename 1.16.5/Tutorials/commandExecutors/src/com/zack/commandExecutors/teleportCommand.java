package com.zack.commandExecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class teleportCommand implements CommandExecutor {

	// number <number> <test1> <test2>
	// args[]:   0        1       2
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player) sender;
		
		if (args[0].equalsIgnoreCase("1")) {
			player.sendMessage("2");
		} else if (args[1].equalsIgnoreCase("2")) {
			player.sendMessage("1");
		} else {
			player.sendMessage("NO!! Go away >:(");
		}
		
		return false;
	}

}
