package com.zack.CustomCrafting_YML;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BookMeta.Generation;

import net.md_5.bungee.api.ChatColor;

public class BookCommand implements CommandExecutor {

	Main main;
	public BookCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
			BookMeta meta = (BookMeta) book.getItemMeta();
			meta.setTitle(ChatColor.MAGIC + "muahahaha");
			meta.setAuthor(ChatColor.GREEN + player.getName());
			
			// one string per page
			// 256 characters per page, chops off rest
			meta.addPage(
					"", 
					"\n new line \n hey \n\n dude i'm over here \n        hey", 
					ChatColor.RED + "red");
			meta.setPage(1, ":)");
			
			meta.setGeneration(Generation.ORIGINAL); // prevents book copying by checking generation
			
			// Lore
			List<String> Lore = new ArrayList<>();
			Lore.add(ChatColor.DARK_PURPLE + "Hey");
			Lore.add(ChatColor.DARK_AQUA + "I'm book");
			meta.setLore(Lore);
			book.setItemMeta(meta);
			
			player.getInventory().addItem(book);			
		}
		
		return false;
	}

}
