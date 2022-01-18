package com.zack.CustomDecor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.md_5.bungee.api.ChatColor;

public class SignEvent implements Listener {

	private Main main;
	
	public SignEvent(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		// Any time a change happens to a sign, write, destroy, place
		
		Player player = event.getPlayer();
		
		// If you want to change the same line being written on you have to cancel the event first
		if (event.getLine(0).equals("123")) {
			event.setLine(1, "456");
			event.setLine(2, ChatColor.BLUE + "789");
			event.setLine(3, "10 11 12");
		}
		
		for (int i = 0; i < 4; i++) {
			String line = event.getLine(i);
			if (line != null && !line.equals("")) {
				// Allows people to change sign colors as they would in normal minecraft
				event.setLine(i, ChatColor.translateAlternateColorCodes('&', line));
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		// Lots of sign types
		if (event.getClickedBlock() != null && 
				((event.getClickedBlock().getType().equals(Material.ACACIA_WALL_SIGN) ||
						event.getClickedBlock().getType().equals(Material.BIRCH_WALL_SIGN) ||
						event.getClickedBlock().getType().equals(Material.CRIMSON_WALL_SIGN) ||
						event.getClickedBlock().getType().equals(Material.DARK_OAK_WALL_SIGN) ||
						event.getClickedBlock().getType().equals(Material.JUNGLE_WALL_SIGN) ||
						event.getClickedBlock().getType().equals(Material.OAK_WALL_SIGN) ||
						event.getClickedBlock().getType().equals(Material.SPRUCE_WALL_SIGN) ||
						event.getClickedBlock().getType().equals(Material.WARPED_WALL_SIGN)) ||
				(event.getClickedBlock().getType().equals(Material.ACACIA_SIGN) ||
						event.getClickedBlock().getType().equals(Material.BIRCH_SIGN) ||
						event.getClickedBlock().getType().equals(Material.CRIMSON_SIGN) ||
						event.getClickedBlock().getType().equals(Material.DARK_OAK_SIGN) ||
						event.getClickedBlock().getType().equals(Material.JUNGLE_SIGN) ||
						event.getClickedBlock().getType().equals(Material.OAK_SIGN) ||
						event.getClickedBlock().getType().equals(Material.SPRUCE_SIGN) ||
						event.getClickedBlock().getType().equals(Material.WARPED_SIGN)))) {
			// .sendSignChange will only make it change for the person who clicked it
			// need location and 4 long string
			player.sendSignChange(event.getClickedBlock().getLocation(), new String[] { 
					"Hello", "This is a sign", "Your other text", "has been hidden" });
			
			// How you get a sign that you just know the location of
			//Sign sign = (Sign) Bukkit.getWorld("world").getBlockAt(0, 0, 0).getState();
			
		}
		
	}
	
}
