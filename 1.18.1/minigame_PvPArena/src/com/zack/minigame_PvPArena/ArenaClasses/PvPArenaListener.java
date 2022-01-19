package com.zack.minigame_PvPArena.ArenaClasses;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.zack.minigame_PvPArena.Config;
import com.zack.minigame_PvPArena.Manager;

public class PvPArenaListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		//Player player = (Player) event.getWhoClicked();
		
		/*// teams
		if (event.getCurrentItem() != null && event.getView().getTitle().contains("Team Selection") && event.getRawSlot() < 54) {
			Team team = Team.valueOf(event.getCurrentItem().getItemMeta().getLocalizedName());
			
			if (!Manager.getArena(player).getTeam(player).equals(team)) {
				player.sendMessage(ChatColor.GRAY + "You are now on " + team.getDisplay() + ChatColor.GRAY + " team!");
				Manager.getArena(player).setTeam(player, team);
			} else {
				player.sendMessage(ChatColor.GRAY + "You are already on the " + team.getDisplay() + " team!");
			}
			
			event.setCancelled(false);
			player.closeInventory();
		}
		*/
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		
		for (Player player : event.getPlayer().getWorld().getPlayers()) {
			if (Manager.isPlaying(player) && 
					Manager.getPvPArena(player).getState().equals(GameState.LIVE) &&
					!Manager.getPvPArena(player).getGame().isRespawning(player)) {
				if (player.getInventory().contains(GameManager.getTrackingCompass())) {
					int radius = 50;
					// Check for power core
					if (player.getInventory().contains(GameManager.getPowerCore())) { radius = 150; }
					// Find nearby players
					List<Location> playerLocations = new ArrayList<>(); 
					for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
						if (entity instanceof Player) {
							playerLocations.add(entity.getLocation());
						}
					}
					// Point to closest or else spin the compass
					if (playerLocations.isEmpty()) { 
						Manager.getPvPArena(player).getGame().setSpinCompass(player, true);
						return;
					}
					double shortestDistance = radius;
					Manager.getPvPArena(player).getGame().setSpinCompass(player, false);
					for (Location location : playerLocations) {
						double distance = player.getLocation().toVector().distance(location.toVector());
						if (distance < shortestDistance) {
							shortestDistance = distance;
							player.setCompassTarget(location);
						}
					}
				}
			}
		}
		
	}
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent event) {
		
		// For allowing people to join only after the world is loaded
		if (Manager.isPvPArenaWorld(event.getWorld())) {
			Manager.getPvPArena(event.getWorld()).setJoinState(true);
		}
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		
		Player player = event.getEntity();
		
		if (Manager.isPlaying(player)) {
		
			if (player.getKiller() != null && player.getKiller() instanceof Player) {
				
				Player killer = player.getKiller();
				
				Manager.getPvPArena(player).getGame().kill(player, killer);
				
			} else {
				Manager.getPvPArena(player).getGame().death(player);
			}
			
		}
		
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player)) {
		
			// Respawn the player in the center and start a timer
			event.setRespawnLocation(Config.getPvPArenaLobbySpawn(Manager.getPvPArena(player).getID()));
			Manager.getPvPArena(player).getGame().startRespawnTimer(player);
			
		}
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player)) {
			event.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player)) {
			event.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		
		if (event.getEntity() instanceof Arrow) {
			
			Arrow arrow = (Arrow) event.getEntity();
		
			if (arrow.getShooter() instanceof Player) {
				
				Player player = (Player) arrow.getShooter();
				
				if (Manager.isPlaying(player)) {
					// Game isn't live or the player is respawning
					if (!Manager.getPvPArena(player).getState().equals(GameState.LIVE) || 
							Manager.getPvPArena(player).getGame().isRespawning(player)) {
						event.setCancelled(true);
						event.getEntity().remove();
						return;
					}
					
					ItemStack itemInUse = GameManager.getItemInUse(player, DamageCause.PROJECTILE);
					
					if (itemInUse.getType().equals(Material.BOW) &&
							itemInUse.getItemMeta().getLocalizedName().equals("thunderBow")) {
						Manager.getPvPArena(player).getGame().addThunderBowArrow(arrow.getUniqueId());
					}
				}
				
			}
		}
		
	}
	
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		
		if (event.getDamager() instanceof Player) {
			
			Player player = (Player) event.getDamager();
			
			if (Manager.isPlaying(player)) {
				// Game isn't live or the player is respawning
				if (!Manager.getPvPArena(player).getState().equals(GameState.LIVE) || 
						Manager.getPvPArena(player).getGame().isRespawning(player)) {
					event.setCancelled(true);
					return;
				}
				
				ItemStack itemInUse = GameManager.getItemInUse(player, event.getCause());
				
				if (itemInUse == null) 
					return;
				
				if (itemInUse.getType().equals(Material.NETHERITE_AXE) &&
						itemInUse.getItemMeta().getLocalizedName().equals("energyAxe")) {
					event.getEntity().setGlowing(true);
					player.getWorld().strikeLightning(event.getEntity().getLocation());
				}
			}
			
		} else if (event.getDamager() instanceof Arrow) {
			
			Arrow arrow = (Arrow) event.getDamager();
			
			if (Manager.isPvPArenaWorld(arrow.getWorld())) {
				
				if (Manager.getPvPArena(arrow.getWorld()).getGame().getThunderBowArrows().contains(arrow.getUniqueId())) {
					arrow.getWorld().strikeLightning(arrow.getLocation());
					Manager.getPvPArena(arrow.getWorld()).getGame().removeThunderBowArrow(arrow.getUniqueId());
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		
		if (event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			
			if (Manager.isPlaying(player)) {
				// Game isn't live or the player is respawning
				if (!Manager.getPvPArena(player).getState().equals(GameState.LIVE) || 
						Manager.getPvPArena(player).getGame().isRespawning(player)) {
					event.setCancelled(true);
					return;
				}
				
				if (event.getCause().equals(DamageCause.FALL)) {
					if (player.getInventory().getBoots() == null)
						return;
					
					if (player.getInventory().getBoots().getType().equals(Material.NETHERITE_BOOTS) &&
							player.getInventory().getBoots().getItemMeta().getLocalizedName().equals("antiGravityBoots")) {
						event.setCancelled(true);
						player.getInventory().setBoots(new ItemStack(Material.AIR));
						player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
					}
				}
			}
			
		}
		
	}
	
	@EventHandler
	public void onInteract(EntityInteractEvent event) {
		
		if (event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			
			if (Manager.isPlaying(player)) {
				// Game isn't live or the player is respawning
				if (!Manager.getPvPArena(player).getState().equals(GameState.LIVE) || 
						Manager.getPvPArena(player).getGame().isRespawning(player)) {
					event.setCancelled(true);
				}
			}
			
		}
		
	}
	
	@EventHandler
	public void onInteractWithEntity(PlayerInteractEntityEvent  event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player)) {
			// Game isn't live or the player is respawning
			if (!Manager.getPvPArena(player).getState().equals(GameState.LIVE) || 
					Manager.getPvPArena(player).getGame().isRespawning(player)) {
				event.setCancelled(true);
			}
		}
		
	}
	
	@EventHandler
	public void onInteractAtEntity(PlayerInteractAtEntityEvent  event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player)) {
			// Game isn't live or the player is respawning
			if (!Manager.getPvPArena(player).getState().equals(GameState.LIVE) || 
					Manager.getPvPArena(player).getGame().isRespawning(player)) {
				event.setCancelled(true);
			}
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onArrowPickup(PlayerPickupArrowEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player)) {
			// Game isn't live or the player is respawning
			if (!Manager.getPvPArena(player).getState().equals(GameState.LIVE) || 
					Manager.getPvPArena(player).getGame().isRespawning(player)) {
				event.setCancelled(true);
			}
		}
		
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent event) {
		
		if (event.getWhoClicked() instanceof Player) {
			
			Player player = (Player) event.getWhoClicked();
			
			if (Manager.isPlaying(player)) {
				event.setCancelled(true);
			}
		
		}
		
	}
	
	@EventHandler 
	public void onPickUpItem(EntityPickupItemEvent event) {
		
		if (event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			
			// Player's cannot pick up items unless they are actively in the game
			if (Manager.isPlaying(player) && (!Manager.getPvPArena(player).getState().equals(GameState.LIVE) || 
					Manager.getPvPArena(player).getGame().isRespawning(player))) {
				event.setCancelled(true);
			}
			
		}
		
	}
	
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player) && Manager.getPvPArena(player).getState().equals(GameState.LIVE) && 
				(!Manager.getPvPArena(player).getGame().isRespawning(player))) {
			
			ItemStack item = event.getItem();
			
			if (item == null) 
				return;
			
			if (item.getType().equals(Material.HONEY_BOTTLE) && 
					item.getItemMeta().getLocalizedName().equals("booze")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 2400, 1));
			} else if (item.getType().equals(Material.POTION) && 
					item.getItemMeta().getLocalizedName().equals("energyDrink")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2400, 1));
			}
			
		}
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPvPArenaWorld(player.getWorld())) {
			// Send the player to the lobby spawn if they had quit mid-game
			player.teleport(Config.getPvPLobbySpawn());
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player)) {
			Manager.getPvPArena(player).removePlayer(player);
		}
		
	}
	
}

