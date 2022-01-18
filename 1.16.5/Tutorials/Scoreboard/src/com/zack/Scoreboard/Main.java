package com.zack.Scoreboard;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	public HashMap<Player, Integer> blocksBroken;
	
	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		this.getServer().getPluginManager().registerEvents(new SidebarListener(this), this);
		
		blocksBroken = new HashMap<>();
		
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
	}
	
	
	public void BuildSidebar(Player player) {
		
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		
		// reference name, not used
		Objective obj = board.registerNewObjective("testytest", "dummy");
		obj.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Board");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR); // on the side
		
		// Dynamic
		
		Team blocksBroken = board.registerNewTeam("blocks_broken"); // identifier
		blocksBroken.addEntry(ChatColor.RED.toString()); // need to give a unique identifier
		// prefix and suffix can only have 16 characters a piece
		blocksBroken.setPrefix("Blocks broken: "); // makes it a static part in the front
		blocksBroken.setSuffix(this.blocksBroken.get(player) + ""); // makes it a string
		obj.getScore(ChatColor.RED.toString()).setScore(5);
		
		
		Score line = obj.getScore("--------------");
		line.setScore(4);
		
		// Static
		
		// Max 32 characters in each line
		// ColorCodes are 2 characters '&e' is yellow
		Score website = obj.getScore(ChatColor.YELLOW + "www.google.com");
		website.setScore(1); // line
		
		// static
		// Empty line
		Score blank = obj.getScore(" ");
		blank.setScore(2);
		
		// Each line must be unique, cannot use the same " ", must use "  " for next space
		
		Score playerName = obj.getScore(ChatColor.GREEN + "IGN: " + player.getName());
		playerName.setScore(3);
		
		player.setScoreboard(board);
		
	}
	
}

