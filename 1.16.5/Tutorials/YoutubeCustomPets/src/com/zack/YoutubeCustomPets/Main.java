package com.zack.YoutubeCustomPets;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.zack.YoutubeCustomPets.events.PetClick;
import com.zack.YoutubeCustomPets.events.PlayerDisconnect;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.WorldServer;

public class Main extends JavaPlugin {

	public HashMap<Player, CustomPet> pets = new HashMap<>();
	
	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PetClick(this), this);
		pm.registerEvents(new PlayerDisconnect(this), this);
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
		if (label.equalsIgnoreCase("pet")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Console can't have a pet");
				return true;
			}
			Player player = (Player) sender;
			// Check player has a pet
			if (pets.get(player) != null) 
				return true;
			
			// Spawn custom pet
			CustomPet pet = new CustomPet(player.getLocation(), player);
			pet.setCustomName(new ChatComponentText(ChatColor.LIGHT_PURPLE + player.getName() + "'s Pet"));
			WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
			world.addEntity(pet);
			// add to list
			pets.put(player, pet);
			
			player.sendMessage(ChatColor.RED + "Hey " + player.getName() + ", You have a pet!");
		}
		
		// New Tutorial
		if (label.equalsIgnoreCase("spawnchickfilacow")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Console can't summon the Chick-fil-A cow");
				return true;
			}
			
			Player player = (Player) sender;
			
			WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
			CustomCow cow = new CustomCow(player.getLocation());
			world.addEntity(cow);
			player.sendMessage(ChatColor.RED + "Eat More Chicken");
			return true;
		}
		
		return false;
	}
	
}
