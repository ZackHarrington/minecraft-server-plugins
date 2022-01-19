package com.zack.minigame_PvPArena.Kits;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.zack.minigame_PvPArena.Main;
import com.zack.minigame_PvPArena.Manager;
import com.zack.minigame_PvPArena.ArenaClasses.Game;
import com.zack.minigame_PvPArena.ArenaClasses.GameManager;

public class FallingBlockRunnable extends BukkitRunnable {

	private Game game;
	
	public FallingBlockRunnable(Game game) {
		this.game = game;
	}
	
	public void begin() {
		this.runTaskTimer(Main.getInstance(), 0, 1);
	}
	
	@Override
	public void run() {
		
		// Check for falling block collisions with entities and falling blocks landing
		boolean remove = false;
		
		for (UUID blockUuid : game.getFallingBlocks().keySet()) {
			Entity blockEntity = Bukkit.getEntity(blockUuid);
			if (blockEntity == null) {
				game.removeFallingBlock(blockUuid);
			} else {
				for (Entity entity : blockEntity.getNearbyEntities(0.5, 0.5, 0.5)) {
					if (entity instanceof Damageable && 
							entity.getUniqueId() != game.getFallingBlockShooters().get(blockUuid)) {
						// Damage nearby entity and check for shield
						if (entity instanceof Player && 
								((Player) entity).getInventory().getItemInOffHand().getItemMeta().getLocalizedName().equals("earthShield")) {
							((Damageable) entity).damage(
									game.getFallingBlocks().get(blockUuid) / 2,
									Bukkit.getPlayer(game.getFallingBlockShooters().get(blockUuid)));
						} else {
							((Damageable) entity).damage(
									game.getFallingBlocks().get(blockUuid),
									Bukkit.getPlayer(game.getFallingBlockShooters().get(blockUuid)));
						}
						remove = true;
					}
				}
				if (remove) {
					GameManager.candleEffect(
							((FallingBlock)blockEntity).getBlockData().getMaterial(),
							Bukkit.getPlayer(game.getCandles().get(blockUuid)),
							blockEntity.getLocation());
					blockEntity.remove();
					game.removeFallingBlock(blockUuid);
				}
			}
		}
		
		// Lumberjack logs
		for (UUID logUuid : game.getLogs().keySet()) {
			Entity logEntity = Bukkit.getEntity(logUuid);
			if (logEntity == null) {
				game.removeLog(logUuid);
			} else {
				for (Entity entity : logEntity.getNearbyEntities(0.5, 0.5, 0.5)) {
					if (entity instanceof Damageable && 
							entity.getUniqueId() != game.getLogs().get(logUuid)) {
						// Damage nearby entity and check for shield
						if (entity instanceof Player && 
								((Player) entity).getInventory().getItemInOffHand().getItemMeta().getLocalizedName().equals("earthShield")) {
							((Damageable) entity).damage(
									0.5, Bukkit.getPlayer(game.getLogs().get(logUuid)));
						} else {
							((Damageable) entity).damage(
									1, Bukkit.getPlayer(game.getLogs().get(logUuid)));
						}
						if (entity instanceof LivingEntity) {
							((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1));
							((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 30, 1));
						}
						remove = true;
					}
				}
				if (remove) {
					logEntity.remove();
					game.removeLog(logUuid);
				}
			}
		}
		
		// Candles
		for (UUID candleUuid : game.getCandles().keySet()) {
			Entity candleEntity = Bukkit.getEntity(candleUuid);
			if (candleEntity == null) {
				game.removeCandle(candleUuid);
			} else {
				for (Entity entity : candleEntity.getNearbyEntities(0.5, 0.5, 0.5)) {
					if (entity instanceof Damageable && 
							entity.getUniqueId() != game.getCandles().get(candleUuid)) {
						// Damage nearby entity and check for shield
						if (entity instanceof Player && 
								((Player) entity).getInventory().getItemInOffHand().getItemMeta().getLocalizedName().equals("earthShield")) {
							((Damageable) entity).damage(
									0.25, Bukkit.getPlayer(game.getCandles().get(candleUuid)));
						} else {
							((Damageable) entity).damage(
									0.5, Bukkit.getPlayer(game.getCandles().get(candleUuid)));
						}
						remove = true;
					}
				}
				if (remove) {
					GameManager.candleEffect(
							((FallingBlock) candleEntity).getBlockData().getMaterial(),
							Bukkit.getPlayer(Manager.getPvPArena(candleEntity.getWorld()).getGame().getCandles().get(candleEntity.getUniqueId())),
							candleEntity.getLocation());
					candleEntity.remove();
					game.removeCandle(candleUuid);
				}
			}
		}
		
	}
	
}
