package com.zack.YoutubeAttributeModifer;

import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.Material;

public class BonusCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("bonus")) {
			// /bonus
			
			if (!(sender instanceof Player)) {
				return true;
			}
			Player player = (Player) sender;
			
			if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
				// Holding no items
				player.sendMessage("Must be holding an item!");
				return true;
			}
			
			// UUID (give it a special ID number), name (usually what it does), amount, operation, EquipmentSlot
			AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 100.0,
					Operation.ADD_NUMBER, EquipmentSlot.HAND);
			
			ItemStack item = player.getInventory().getItemInMainHand();
			ItemMeta meta = item.getItemMeta();
			// add a modifier:
			// Attribute, modifier
			meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
			item.setItemMeta(meta);
			player.sendMessage(ChatColor.GOLD + "TRANSFORMED");
			
			// Dude uses this for hiding values in useless attributes
			
			return true;
		}
		
		return false;
	}

}
