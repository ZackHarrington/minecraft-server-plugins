package com.zack.Enchantments_Events_Vectors;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class EnchantListener implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			
			if (player.getInventory().getItemInMainHand() != null && 
					player.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.getByKey(Main.glowEnchantment.getKey()))) {
				event.getEntity().setGlowing(true);
			}
		}
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		ItemStack is = new ItemStack(Material.IRON_SWORD);
		is.addUnsafeEnchantment(Main.glowEnchantment, 1);
		ItemMeta meta = is.getItemMeta();
		meta.setLore(Arrays.asList(ChatColor.GRAY + "Glow 1"));
		is.setItemMeta(meta);
		
		event.getPlayer().getInventory().addItem(is);
		
	}
	
}
