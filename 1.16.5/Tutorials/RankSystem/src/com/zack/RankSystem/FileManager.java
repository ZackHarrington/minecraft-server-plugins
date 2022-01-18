package com.zack.RankSystem;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class FileManager {

	// You need to create a folder in your Plugins folder with the same name as your plugin inorder for files to work
	// Server -> world -> playerdata
	// You need to delete all playerdata when first implmenting this or else you'll get an error because the player has been logged in before but has no rank
	
	private File file;
	private YamlConfiguration config;
	
	/*
	 * UUID:
	 *   Rank:
	 *   Coins:
	 *   
	 * using
	 * UUID: Rank
	 * */
	
	public FileManager(Main main) {
		
		file = new File(main.getDataFolder(), "data.yml");
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		config = YamlConfiguration.loadConfiguration(file);		
	}
	
	public void setRank(Player player, Rank rank) {
		config.set(player.getUniqueId().toString(), rank.name());
		save();
	}
	public void setRank(UUID uuid, Rank rank) {
		config.set(uuid.toString(), rank.name());
		save();
	}
	
	public Rank getRank(Player player) {
		return Rank.valueOf(config.getString(player.getUniqueId().toString()));
	}
	
	// Allows file to save in real time not just in memory waiting to save when the server restarts
	private void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
