package com.zack.hyperHostileMobs;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Hoglin;
import org.bukkit.entity.Husk;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Ravager;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Stray;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Trident;
import org.bukkit.entity.Vex;
import org.bukkit.entity.Vindicator;
import org.bukkit.entity.Witch;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Zoglin;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

public class HostileMobsListener implements Listener {

	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		
		if (!(event.getEntity() instanceof Player))
			return;
		
		Player player = (Player) event.getEntity();
		
		if (!(Manager.isActiveWorld(player.getWorld())))
			return;
		
		if (damager instanceof Zombie || 
				damager instanceof ZombieVillager || 
				damager instanceof Drowned ||
				damager instanceof Evoker ||
				damager instanceof Vindicator ||
				damager instanceof WitherSkeleton ||
				damager instanceof PiglinBrute ||
				damager instanceof Husk ||
				damager instanceof Phantom ||
				damager instanceof Silverfish ||
				damager instanceof Vex ||
				damager instanceof Guardian) {
			
			event.setDamage(event.getDamage() * 10);
			
		} else if (damager instanceof Endermite || damager instanceof Enderman) {
			
			event.setDamage(event.getDamage() * 10);
			Manager.teleportPlayer(player);
			
		} else if (damager instanceof Hoglin || 
				damager instanceof Zoglin ||
				damager instanceof Ravager) {
			
			event.setDamage(event.getDamage() * 10);
			player.setVelocity(damager.getLocation().getDirection());
			
		} else if (damager instanceof Slime || damager instanceof MagmaCube) {
			
			event.setDamage(event.getDamage() * 10);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 5));
			
		} else if (damager instanceof EnderDragon) {
			
			event.setDamage(event.getDamage() * 100);
			
		} else if (damager instanceof Projectile) {
			
			Projectile projectile = (Projectile) damager;
			ProjectileSource projectileSource = projectile.getShooter();
			
			if (projectileSource instanceof Entity) {
				
				Entity shooter = (Entity) projectileSource;
				
				if (projectile instanceof Arrow) {
					// Skeleton, Stray
					if (shooter instanceof Skeleton ||
							shooter instanceof Stray) {
						Manager.knockbackPlayer(player, projectile.getVelocity(), 20);
					}
					
				} else if (projectile instanceof Fireball) {
					
					if (shooter instanceof Ghast) {
						
						player.getWorld().createExplosion(player.getLocation(), 20);
						
					} else if (shooter instanceof Blaze) {
						
						player.setFireTicks(200);
						Manager.knockbackPlayer(player, projectile.getVelocity(), 5);
						
					}
					
				} else if (projectile instanceof ShulkerBullet) {
					
					player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 200, 5));
					Manager.teleportPlayer(player);
					
				} else if (projectile instanceof Trident) {
					
					if (shooter instanceof Drowned) {
						player.damage(event.getDamage() * 10);
						Manager.knockbackPlayer(player, projectile.getLocation().getDirection(), 8);
					}
					
				} else if (projectile instanceof WitherSkull) {
					
					player.getWorld().createExplosion(player.getLocation(), 10);
					
				} else if (projectile instanceof ThrownPotion) {
					
					if (shooter instanceof Witch) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 5));
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 5));
						player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 5));
					}
					
				}
			}
			
		}
		
	}
	
	
	@EventHandler
	public void onCreeperExplode(EntityExplodeEvent event) {
		
		if(event.getEntity() instanceof Creeper) {
			
			if (!(Manager.isActiveWorld(event.getEntity().getWorld())))
				return;
			
			event.setCancelled(true);
			event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 100);
			
		}
		
	}
	
}
