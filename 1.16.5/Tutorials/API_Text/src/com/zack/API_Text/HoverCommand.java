package com.zack.API_Text;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class HoverCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player) sender;
		
		// bungee
		TextComponent message = new TextComponent("Your favorite web browser ");
		message.setColor(ChatColor.GRAY);
		
		ComponentBuilder cb = new ComponentBuilder(
				"Google: ").bold(true).color(ChatColor.YELLOW)
				.append("google.com").bold(false).color(ChatColor.RED);
		
		TextComponent google = new TextComponent("Google");
		google.setColor(ChatColor.GREEN);
		google.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, cb.create()));
		google.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.google.com"));
		
		message.addExtra(google);
		
		player.spigot().sendMessage(message);
		
		return false;
	}

}
