package com.zack.GodEnchantments.Listeners;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftSilverfish;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.zack.GodEnchantments.Main;
import com.zack.GodEnchantments.Mobs.Louce;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_16_R3.EntitySilverfish;

public class EnchantmentListener implements Listener {
	
	@EventHandler
	public void onEat(PlayerItemConsumeEvent event) {
		ItemStack item = event.getItem();
		
		// Check if it's the god cookie
		if (item.getType().equals(Material.COOKIE) &&
				item.getEnchantments().containsKey(Enchantment.getByKey(Main.godEnchantment.getKey())) && 
				item.getItemMeta().getDisplayName().contains("Curious Cookie")) {
			Player player = event.getPlayer();
			
			// Check if they are allowed to eat one
			if (Main.godManager.checkCooldown(player)) {
				// Cancel consume event
				event.setCancelled(true);
				
				player.sendMessage(ChatColor.RED + "You must wait for the cooldown to end. " + ChatColor.GRAY.toString() + ChatColor.ITALIC + "Max of 2 minutes");
			}
			else if (Main.godManager.checkAscension(player)) {
				// Cancel consume event
				event.setCancelled(true);
				
				player.sendMessage(ChatColor.RED + "You are too power hungry");
				
				// end ascension, revoke god status, delete inventory (maybe drop items for other players), kill player, send to hell (nether / new world I create) XXXXXXXXXXXX
				// for hell try using lava particles as tri circles
				Main.godManager.cancelAscension(player);
			} else {
				// Remove the player from the cookie list
				Main.playersWithCookies.remove(player);
			
				player.setHealth(20);
				player.setFoodLevel(20);
				player.setAbsorptionAmount(20.0);
			
				// Begin the 'ascension'
				Main.godManager.beginAscension(player);
			}
		}
		
		// Check if it is milk to remove potion effects
		if (item.getType().equals(Material.MILK_BUCKET)) {
			// If necessary remove God levitation
			Main.godManager.cancelLevitation(event.getPlayer());
		}
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent event) {
		
		// Check if it's a god cookie
		if (event.getCurrentItem().getType().equals(Material.COOKIE) &&
				event.getCurrentItem().getEnchantments().containsKey(Enchantment.getByKey(Main.godEnchantment.getKey())) &&
				event.getCurrentItem().getItemMeta().getDisplayName().contains("Curious Cookie")) {
			
			Main.playersWithCookies.add((Player)event.getWhoClicked());
			Main.godCookies.add(event.getCurrentItem());
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
		// Check if the player is in the spectacle
		if (Main.godManager.checkSpectacle(player)) {
			// Stop their attempts to move while allowing them to look around
			player.setVelocity(new Vector(0, 0, 0));
		}
		
		// Check if the player has a God helmet and their head is in water
		if (Main.godManager.checkHelmet(player) &&
				player.getLocation().add(0, 1, 0).getBlock().getType().equals(Material.WATER)) {
			// Set conduit power
			Main.godManager.setConduitPower(player);
		}
		
		// Check if the player is levitating
		if (Main.godManager.checkLevitate(player)) {
			if (Main.godManager.checkBoots(player)) {
				Main.godManager.levitate(player);
			} else {
				Main.godManager.cancelLevitation(player);
			}
		}
		// For starting levitation
		if (Main.godManager.checkBoots(player)) {
			Main.godManager.updateFlightVariables(player);
		}
		
		// Check if player is smashing
		if (Main.godManager.checkSmashing(player)) {
			// Check if they are touching the ground
			if (!(player.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.AIR) ||
					player.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.WATER) ||
					player.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.LAVA))) {
				// Remove them from the list (smashing ended)
				Main.godManager.endSmash(player);
			} else {
				// Do the smash
				Main.godManager.smashEffect(player);
			}
		}		
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			// Check if player is invulnerable
			if (Main.godManager.checkInvulnerable(player)) {
				// Cancel damage taken
				event.setCancelled(true);
			}
			
			// Check if player had god enchantment on and is taking fire damage
			// fire: DamageCause.FIRE_TICK, DamageCause.FIRE, DamageCause.LAVA, DamageCause.HOT_FLOOR
			if (Main.godManager.checkLeggings(player) && (event.getCause().equals(DamageCause.FIRE) | 
					event.getCause().equals(DamageCause.FIRE_TICK) | event.getCause().equals(DamageCause.LAVA) |
					event.getCause().equals(DamageCause.HOT_FLOOR))) {
				// Cancel damage taken
				event.setCancelled(true);
				// Set fire resistance
				Main.godManager.setFireResistance(player);
			}
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		
		// Player did the damaging
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			
			// Check if the player has a God chestplate
			if (Main.godManager.checkChestplate(player)) {
				// Double damage
				event.setDamage(event.getDamage() * 2);
			}
		}
		
		// Player was damaged
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			// Check if the player has a God chestplate
			if (Main.godManager.checkChestplate(player)) {
				// Super resistance
				event.setDamage(event.getDamage() / 2);
			}
		}
		
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		Entity ent = event.getEntity();
		
		if (ent instanceof Silverfish) {
			EntitySilverfish silverfish = ((CraftSilverfish)ent).getHandle();
			
			if (silverfish instanceof Louce) {
				ItemStack item = ((Louce) silverfish).getItem();
				
				if (item != null) {
					ent.getWorld().dropItem(ent.getLocation(), item);
				} 
			}
		}
		
	}
	
	@EventHandler
	public void onHoldBreath(EntityAirChangeEvent event) {
		
		// Player held their breath
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			// Check if the player has a God helmet
			// More for if they are AFK in water
			if (Main.godManager.checkHelmet(player)) {
				// Cancel air change (they can breath underwater)
				event.setCancelled(true);
				// Set conduit power
				Main.godManager.setConduitPower(player);
			}
		}
	}
	
	@EventHandler
	public void onFlight(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		
		// Make sure player is in survival and has God boots, and isn't ascending
		if (Main.godManager.checkBoots(player) && !Main.godManager.checkAscension(player)) {
			// Check if its a double shift for flight (aka. 4 shifts, called twice for each shift)
			// returns true if called 4 times within a half second
			if (Main.godManager.flightStartCheck(player)) {
				if (Main.godManager.checkSmashing(player)) {
					// Cancel slam
					Main.godManager.cancelSmash(player);
				}
				Main.godManager.startLevitation(player);
			} else {
				// If the player is already smashing, stop their slam
				// Otherwise if they are above the ground, start it
				// Basically shift hold to keep slamming
				if (Main.godManager.checkSmashing(player)) {
					// Cancel slam
					Main.godManager.cancelSmash(player);
				} else if ((player.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.AIR) ||
						player.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.WATER) ||
						player.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.LAVA))) {
					// Slam them
					Main.godManager.startSmash(player, (int) player.getLocation().getY());
				}
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		// Announce gods logging on
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		// Check if the player is ascending
		if (Main.godManager.checkAscension(event.getPlayer())) {
			// Stop their ascension
			Main.godManager.cancelAscension(event.getPlayer());
		}
	}
	
}
