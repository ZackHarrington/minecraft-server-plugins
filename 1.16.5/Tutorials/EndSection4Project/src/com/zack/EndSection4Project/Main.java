package com.zack.EndSection4Project;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	/* COMMANDS
	 * - message <player> <message>
	 * - reply <message>
	 */
	
	private MessageManager manager;
	
	private static Main main; 
	
	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		this.getCommand("message").setExecutor(new MessageCommand(this));
		this.getCommand("reply").setExecutor(new ReplyCommand(this));
		
		manager = new MessageManager();
		
		// Chairs
		this.getServer().getPluginManager().registerEvents(new ChairListener(), this);
		
		Main.main = this;
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
	}
	
	public MessageManager getMessageManager() { return manager; }
	
	// Chairs
	
	public static HashMap<UUID, SitData> sitting = new HashMap<>();
	
	public static void sit(Player player, Block block) {
		if (sitting.containsKey(player.getUniqueId())) {
			if (sitting.get(player.getUniqueId()).chair.equals(block)) { // same one sitting on
				return;
			}
			
			unsit(player);
		}
		
		sitting.put(player.getUniqueId(), new SitData(main, player, block));
	}
	
	public static void unsit(Player player) {
		sitting.get(player.getUniqueId()).unsit(); // in game
		sitting.remove(player.getUniqueId()); // in hash map
	}
	public static void unsit(Block chair) {
		UUID remove = null; // Protects from an error of removing from a HashMap we are currently looping through
		for(UUID uuid : sitting.keySet()) {
			SitData data = sitting.get(uuid);
			
			if (data.chair.equals(chair)) {
				data.unsit();
				remove = uuid;
				break;
			}
		}
		
		if (remove != null) {
			sitting.remove(remove);
		}
	}
	
	public static boolean isOccupied(Block block) {
		
		for (SitData data : sitting.values()) {
			if (data.chair.equals(block)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isSitting(Player player) {
		return sitting.containsKey(player.getUniqueId());
	}
	
}