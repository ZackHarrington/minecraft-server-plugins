package com.zack.CustomDecor;

import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class SkullCommand implements CommandExecutor {

	private Main main;
	
	public SkullCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta meta = (SkullMeta) skull.getItemMeta();
			
			// Makes it player's head 
			// Could use any player in all of minecraft
			// May return a steve head, just run twice, is a bug
			meta.setOwningPlayer(player);
			skull.setItemMeta(meta);
			
			player.getInventory().addItem(skull);
			
			// Custom texture
			ItemStack skull2 = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta meta2 = (SkullMeta) skull.getItemMeta();
			
			GameProfile profile = new GameProfile(UUID.randomUUID(), null);
			
			// Copied from freshcoal.com
			/*give @p skull 1 3 {display:{Name:"Ale"},SkullOwner:{Id:"6c014f69-60c9-496d-9591-44bd9ab78ee5",
				Properties:{textures:[{Value:"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjJkZGY4YTU0NjkzZDkyYTYzMTU0NzRmNGNiY2YyNjU3MTY1ZGY3ZjNmYzZmYmNhNzkzNTNkNzkxMzk4NjJlIn19fQ=="}]}}}
			*/
			// Use only what is after the Value: 
			
			profile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjJkZGY4YTU0NjkzZDkyYTYzMTU0NzRmNGNiY2YyNjU3MTY1ZGY3ZjNmYzZmYmNhNzkzNTNkNzkxMzk4NjJlIn19fQ=="));
			Field field;
			
			try {
				field = meta2.getClass().getDeclaredField("profile");
				field.setAccessible(true);
				field.set(meta2, profile);
			} catch(NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
				
			}
			
			
			skull2.setItemMeta(meta2);
			
			player.getInventory().addItem(skull2);
		}
		
		
		
		
		return false;
	}

}
