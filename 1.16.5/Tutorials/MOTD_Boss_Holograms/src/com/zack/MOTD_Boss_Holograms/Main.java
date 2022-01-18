package com.zack.MOTD_Boss_Holograms;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	public BossBar bossBar;
	
	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		// Registers a listener
		Bukkit.getPluginManager().registerEvents(new PingListener(), this);
		
		// Boss Bar
		Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
		
		bossBar = Bukkit.createBossBar(
				ChatColor.GOLD + "Mbharring's Server",
				BarColor.WHITE,
				BarStyle.SEGMENTED_6); //, BarFlag); make function that does something when it appears
		bossBar.setProgress(1);
		
		// Holograms
		Bukkit.getPluginManager().registerEvents(new HologramListener(this), this);
		
		// Toggling
		Bukkit.getPluginManager().registerEvents(new ToggleListener(), this);
		
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
	}
	
	// Hologram
	public void spawnHologram(Player player) {
		String name = "Quack,Meow,Arf!";
		String[] str = name.split(",");
		
		for (int i = 0; i < str.length; i++) {
			ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, (i*0.2) + 0.5, 0), EntityType.ARMOR_STAND);
			stand.setVisible(false);
			stand.setGravity(false);
			stand.setInvulnerable(true);
			
			stand.setCustomNameVisible(true);
			stand.setCustomName(ChatColor.GOLD + player.getName() + " " + str[i]);
		}
		
	}
	
}
