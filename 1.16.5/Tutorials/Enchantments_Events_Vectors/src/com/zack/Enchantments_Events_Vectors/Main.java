package com.zack.Enchantments_Events_Vectors;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	private static Main main;
	
	public static GlowEnchantment glowEnchantment;
	
	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		// Vector
		this.getServer().getPluginManager().registerEvents(new VectorListener(), this);
		this.getServer().getPluginManager().registerEvents(this, this);
		
		// Enchantments
		Main.main = this;
		registerEnchantment(glowEnchantment = new GlowEnchantment()); // initialize and pass through! :o
		this.getServer().getPluginManager().registerEvents(new EnchantListener(), this);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
		
		// Enchantments
		try {
			// Removes the enchantment so on start up next time there is no issue of duplicate enchantments
			Field keyField = Enchantment.class.getDeclaredField("byKey");
			keyField.setAccessible(true);
			
			HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);
			if (byKey.containsKey(glowEnchantment.getKey())) {
				byKey.remove(glowEnchantment.getKey());
			}
			
			Field nameField = Enchantment.class.getDeclaredField("byName");
			nameField.setAccessible(true);
			
			HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);
			if (byName.containsKey(glowEnchantment.getName())) {
				byName.remove(glowEnchantment.getName());
			}
		} catch (Exception e) { }
	}
	
	
	// Event
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("broadcast")) {
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					builder.append(args[i]).append(" ");
				}
				
				Player player = (Player) sender;
				String message = builder.toString();
				
				ServerBroadcastEvent event = new ServerBroadcastEvent(player, message);
				Bukkit.getPluginManager().callEvent(event);
				
				if (!event.isCancelled()) {
					// Broadcasts a message to the server
					Bukkit.broadcastMessage(message);
				} 
			}
		}
		
		return false;
	}
	
	@EventHandler
	public void onBroadcast(ServerBroadcastEvent event) {
		
		System.out.println("Event Run!");
		
		Random rand = new Random();
		
		if (rand.nextInt(2) == 1) {
			System.out.println("Cancelled");
			event.setCancelled(true);
		} else {
			System.out.println("Allowed");
		}
	}
	
	// Enchantments
	
	public static Main getInstance() { return main; }
	
	private void registerEnchantment(Enchantment enchantment) {
		try {
			Field field = Enchantment.class.getDeclaredField("acceptingNew");
			field.setAccessible(true);
			field.set(null, true);
			Enchantment.registerEnchantment(enchantment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
