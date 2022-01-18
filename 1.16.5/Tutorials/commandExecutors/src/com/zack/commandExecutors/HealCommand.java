package com.zack.commandExecutors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		
		Player player = (Player) sender;
		
		player.setHealth(20.0);
		player.sendMessage("Healed!");
		
		return false;
	}

}
