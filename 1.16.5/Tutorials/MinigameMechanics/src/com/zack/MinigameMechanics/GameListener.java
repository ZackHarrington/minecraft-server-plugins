package com.zack.MinigameMechanics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.zack.MinigameMechanics.Kits.KitType;
import com.zack.MinigameMechanics.Teams.Team;

import net.md_5.bungee.api.ChatColor;

public class GameListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		
		// teams
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
		
		// kits
		if (event.getView().getTitle().contains("Kit Selection") && event.getRawSlot() <= 54 && event.getCurrentItem() != null) {
			KitType type = KitType.valueOf(event.getCurrentItem().getItemMeta().getLocalizedName());
			
			if (Manager.hasKit(player) && Manager.getKit(player).equals(type)) {
				player.sendMessage(ChatColor.RED + "You have already equipped this kit!");
			} else {
				player.sendMessage(ChatColor.GREEN + "You have equipped the " + type.getDisplay() + ChatColor.GREEN + " kit!");
				Manager.getArena(player).setKit(player.getUniqueId(), type);
			}
			
			event.setCancelled(true);
			player.closeInventory();
			
		}
		
	}
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent event) {
		
		if (Manager.isArenaWorld(event.getWorld())) {
			Manager.getArena(event.getWorld()).setJoinState(true);
		}
		
	}
	
	@EventHandler 
	public void onSignClick(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (event.hasBlock() && event.getClickedBlock().getType().equals(Material.OAK_WALL_SIGN)) { // not air
			int id = Config.isSign(event.getClickedBlock().getLocation());
				
			if (id != -1) {
				if (Manager.getArena(id).getState().equals(GameState.COUNTDOWN) ||
						Manager.getArena(id).getState().equals(GameState.RECUITING)) {
					if (!Manager.getArena(id).getPlayers().contains(player.getUniqueId())) {
						Manager.getArena(id).addPlayer(player);
					} else {
						player.sendMessage(ChatColor.RED + "You are already in this game!");
					}
				} else {
					player.sendMessage(ChatColor.RED + "You cannot join right now, Arena " + id + " is Live");
				}
				
					
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onEntityInteract(PlayerInteractAtEntityEvent event) {
		// Called twice, once for each hand
		
		Player player = event.getPlayer();
		
		if (event.getHand().equals(EquipmentSlot.HAND) && Manager.isNPC(event.getRightClicked())) {
			Arena arena = Manager.getArena(event.getRightClicked());
			
			if (arena.getState().equals(GameState.RECUITING) ||
					arena.getState().equals(GameState.COUNTDOWN)) {
				if (!arena.getPlayers().contains(player.getUniqueId())) {
					arena.addPlayer(player);
				} else {
					player.sendMessage(ChatColor.RED + "You are already in this game!");
				}
			} else {
				player.sendMessage(ChatColor.RED + "You cannot join right now, Arena " + arena.getID() + " is Live");
			}
		}
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player) && Manager.getArena(player).getState().equals(GameState.LIVE)) {
			player.sendMessage(ChatColor.GOLD + "+1 Point!");
			
			Manager.getArena(player).getGame().addPoint(player);
		}
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player)) {
			Manager.getArena(player).removePlayer(player);
		}
		
	}
	
}
