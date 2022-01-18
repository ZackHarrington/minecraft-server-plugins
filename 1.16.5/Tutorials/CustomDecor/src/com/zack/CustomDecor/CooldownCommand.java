package com.zack.CustomDecor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CooldownCommand implements CommandExecutor {

	Main main;
	public CooldownCommand(Main main) {
		this.main = main;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player) sender;
		
		
		
		// will have cooldown remaining
		/*if (main.cooldown.containsKey(player) && System.currentTimeMillis() < main.cooldown.get(player)) {
			long timeRemaining = main.cooldown.get(player) - System.currentTimeMillis();
			int intRemaining = (int) (timeRemaining / 1000);
			player.sendMessage("Wait " + intRemaining + " seconds");
		} else {
			player.sendMessage("You must wait 10 secondsssss jeeez");
			// Will just replace if already in there?
			// player, time since server started + (seconds * 1000ms)
			main.cooldown.put(player, System.currentTimeMillis() + (10 * 1000));
		} */
		
		if (main.cooldownEnabled.contains(player)) {
			player.sendMessage("Wait");
		} else {
			player.sendMessage("You did it!");
			
			main.cooldownEnabled.add(player);
			Bukkit.getScheduler().runTaskLater(main, new Runnable() {
				public void run() {
					main.cooldownEnabled.remove(player);
				}
			}, 100L);
		}
		
		
		return false;
	}

}
