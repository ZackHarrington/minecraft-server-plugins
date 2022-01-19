package com.zack.minigame_PvPArena.Kits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.Trident;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import com.zack.minigame_PvPArena.Config;
import com.zack.minigame_PvPArena.Manager;
import com.zack.minigame_PvPArena.ArenaClasses.GameManager;
import com.zack.minigame_PvPArena.ArenaClasses.GameState;
import com.zack.minigame_PvPArena.Kits.Types.KitType;

public class KitListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		
		if (event.getView().getTitle().contains(KitGUIType.CLASS_SELECTOR.getDisplay()) 
				&& event.getRawSlot() <= 54 && event.getCurrentItem() != null) {
			event.setCancelled(true);
			player.closeInventory();
			// Check if they clicked the default, or random kit or not
			if (event.getCurrentItem().getItemMeta().getLocalizedName().equals(KitType.DEFAULT.toString())) {
				if (GameManager.hasKit(player) && GameManager.getKit(player).equals(KitType.DEFAULT)) {
					player.sendMessage(ChatColor.RED + "You have already equipped this kit!");
				} else {
					player.sendMessage(ChatColor.GREEN + "You have equipped the " + KitType.DEFAULT.getDisplay() + ChatColor.GREEN + " kit!");
					Manager.getPvPArena(player).setKit(player.getUniqueId(), KitType.DEFAULT);
				}
			} else if (event.getCurrentItem().getItemMeta().getLocalizedName().equals(KitType.RANDOM.toString())) {
				if (GameManager.hasKit(player) && GameManager.getKit(player).equals(KitType.RANDOM)) {
					player.sendMessage(ChatColor.RED + "You have already equipped this kit!");
				} else {
					player.sendMessage(ChatColor.GREEN + "You have equipped the " + KitType.RANDOM.getDisplay() + ChatColor.GREEN + " kit!");
					Manager.getPvPArena(player).setKit(player.getUniqueId(), KitType.RANDOM);
				}
			} else {
				// Create new GUI for kit classes
				KitGUIType type = KitGUIType.valueOf(event.getCurrentItem().getItemMeta().getLocalizedName());
				new KitsGUI(player, type);
			}
			
		} else if ((event.getView().getTitle().contains(KitGUIType.ARCHERY_CLASS.getDisplay()) ||
				event.getView().getTitle().contains(KitGUIType.MELEE_CLASS.getDisplay()) ||
				event.getView().getTitle().contains(KitGUIType.MAGE_CLASS.getDisplay()))
				&& event.getRawSlot() <= 54 && event.getCurrentItem() != null) {
			
			KitType type = KitType.valueOf(event.getCurrentItem().getItemMeta().getLocalizedName());
			
			if (GameManager.hasKit(player) && GameManager.getKit(player).equals(type)) {
				player.sendMessage(ChatColor.RED + "You have already equipped this kit!");
			} else {
				player.sendMessage(ChatColor.GREEN + "You have equipped the " + type.getDisplay() + ChatColor.GREEN + " kit!");
				Manager.getPvPArena(player).setKit(player.getUniqueId(), type);
			}
			
			event.setCancelled(true);
			player.closeInventory();
		}
		
	}
	
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		
		if (Manager.isPvPArenaWorld(event.getDamager().getWorld())) {
			
			if (event.getDamager() instanceof Snowball && event.getEntity() instanceof LivingEntity) {
				
				LivingEntity frostedEntity = (LivingEntity) event.getEntity();
				
				if (frostedEntity instanceof Player) {
					Player player = (Player) frostedEntity;
					
					if ((player.getEquipment().getChestplate() != null &&
							player.getEquipment().getChestplate().getItemMeta().getLocalizedName().contains("frostChestplate")) ||
							(player.getEquipment().getLeggings() != null &&
							player.getEquipment().getLeggings().getItemMeta().getLocalizedName().contains("frostLeggings")) ||
							(player.getEquipment().getBoots() != null &&
							player.getEquipment().getBoots().getItemMeta().getLocalizedName().contains("frostBoots"))) {
						event.setCancelled(true);
						return;
					}
				}
				
				// Slowness for 1.5 seconds
				frostedEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 1));
				if (frostedEntity.getFireTicks() > 0) {
					frostedEntity.setFireTicks(0);
				}
				
			} else if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
				
				Player player = (Player) event.getDamager();
				LivingEntity livingEntity = (LivingEntity) event.getEntity();
				ItemStack itemInUse = GameManager.getItemInUse(player, event.getCause());
				
				if (itemInUse != null && 
						itemInUse.getItemMeta().getLocalizedName().equals("inflictor")) {
					// Add freezing ticks
					livingEntity.setFreezeTicks(140); // max ticks, sets as "freezing"
				} else if (itemInUse != null && 
						itemInUse.getItemMeta().getLocalizedName().equals("executor")) {
					// Check for freezing ticks and do double damage
					if (livingEntity.getFreezeTicks() > 0) {
						event.setDamage(event.getDamage() * 2);
						GameManager.comboEffect(livingEntity.getLocation().add(0, 1, 0));
						livingEntity.setFreezeTicks(0); // remove any extra freeze ticks
					}
				}
				
			}
		
		}
		
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		
		if (event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			
			if (Manager.isPlaying(player)) {
				if (event.getCause().equals(DamageCause.FIRE) || 
						event.getCause().equals(DamageCause.FIRE_TICK) ||
						event.getCause().equals(DamageCause.HOT_FLOOR) || 
						event.getCause().equals(DamageCause.LAVA)) {
					if ((player.getEquipment().getChestplate() != null &&
							player.getEquipment().getChestplate().getItemMeta().getLocalizedName().contains("ignitedChestplate")) ||
							(player.getEquipment().getLeggings() != null &&
							player.getEquipment().getLeggings().getItemMeta().getLocalizedName().contains("ignitedLeggings")) ||
							(player.getEquipment().getBoots() != null &&
							player.getEquipment().getBoots().getItemMeta().getLocalizedName().contains("ignitedBoots"))) {
						event.setCancelled(true);
					}
				}
				
				// Check if the player will die
				if (((player.getHealth() - event.getFinalDamage()) <= 0)) {
					if (player.getInventory().getItemInOffHand() != null &&
							player.getInventory().getItemInOffHand().getType().equals(Material.WITHER_ROSE) &&
							player.getInventory().getItemInOffHand().getItemMeta().getLocalizedName().equals("taintedTotem")) {
						event.setCancelled(true);
						GameManager.startTaintedTotemEffects(player);
					}
				}
			}
			
		}
		
	}
	
	@EventHandler
	public void onUse(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (!Manager.isPlaying(player)) 
			return;
		
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || 
				event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (event.getItem() != null &&
					event.getItem().getType().equals(Material.SNOWBALL) && 
					event.getItem().getItemMeta().getLocalizedName().contains("eternalSnowball")) {
				// Replace the used eternal snowball
				ItemStack eternalSnowball = event.getItem();
				player.getInventory().addItem(eternalSnowball);
				
			} else if (event.getItem() != null &&
					event.getItem().getType().equals(Material.STICK) && 
					event.getItem().getItemMeta().getLocalizedName().contains("earthStaff")) {
				// Check for ammunition
				ItemStack ammo = new ItemStack(Material.AIR);
				for (int i = 0; i < player.getInventory().getSize(); i++) {
					if (player.getInventory().getItem(i) != null &&
							player.getInventory().getItem(i).getType().equals(Material.CLAY_BALL)) {
						ammo = player.getInventory().getItem(i);
					}
				}
				if (ammo.getType().equals(Material.CLAY_BALL)) {
					Material type = Material.valueOf(ammo.getItemMeta().getLocalizedName());
					
					// Summon a falling block
					FallingBlock block = player.getWorld().spawnFallingBlock(
							player.getLocation().add(0, 1.1, 0).add(player.getLocation().getDirection().normalize()), 
							type.createBlockData());
					block.setVelocity(player.getLocation().getDirection().normalize());
					block.setHurtEntities(true);
					block.setDropItem(false);
					block.setVelocity(block.getVelocity().multiply(2));
					Manager.getPvPArena(player).getGame().addFallingBlock(block.getUniqueId(), ammo.getAmount() / 10, player);
					
					// Remove ammo
					player.getInventory().remove(ammo);
				} else {
					player.sendMessage(ChatColor.GRAY + "You have no block ammo");
				}
				
			} else if (event.getItem() != null &&
					event.getItem().getType().equals(Material.OAK_LOG) && 
					event.getItem().getItemMeta().getLocalizedName().contains("log")) {
				// Find and remove log
				GameManager.getAndRemoveItem(Material.OAK_LOG, player);
				// Summon a falling log
				FallingBlock block = player.getWorld().spawnFallingBlock(
						player.getLocation().add(0, 1.1, 0).add(player.getLocation().getDirection().normalize()), 
						Material.OAK_LOG.createBlockData());
				block.setVelocity(player.getLocation().getDirection().normalize());
				block.setHurtEntities(false);
				block.setDropItem(false);
				block.setVelocity(block.getVelocity().multiply(2));
				Manager.getPvPArena(player).getGame().addLog(block.getUniqueId(), player);
				
			} else if (event.getItem() != null &&
					event.getItem().getType().equals(Material.WOLF_SPAWN_EGG) && 
					event.getItem().getItemMeta().getLocalizedName().contains("tamedWolfEgg")) {
				// Find and remove egg
				GameManager.getAndRemoveItem(Material.WOLF_SPAWN_EGG, player);
				// Summon a tamed wolf
				event.setCancelled(true);
				Wolf wolf = (Wolf) player.getWorld().spawnEntity(player.getLocation(), EntityType.WOLF);
				wolf.setAdult();
				wolf.setSitting(false);
				wolf.setAware(true);
				wolf.setCollarColor(DyeColor.RED);
				wolf.setOwner(player);
				
			} else if (event.getItem() != null &&
					event.getItem().getType().equals(Material.BONE) && 
					event.getItem().getItemMeta().getLocalizedName().contains("conjuringStaff")) {
				// Remove a candle and throw it
				ItemStack candle = new ItemStack(Material.AIR);
				for (int i = 0; i < player.getInventory().getSize(); i++) {
					if (player.getInventory().getItem(i) != null &&
							(player.getInventory().getItem(i).getType().equals(Material.GREEN_CANDLE) ||
									player.getInventory().getItem(i).getType().equals(Material.LIGHT_BLUE_CANDLE) ||
									player.getInventory().getItem(i).getType().equals(Material.ORANGE_CANDLE))) {
						candle = player.getInventory().getItem(i);
						if (candle.getAmount() == 1) {
							player.getInventory().setItem(i, new ItemStack(Material.AIR));
						} else {
							candle.setAmount(candle.getAmount() - 1);
							player.getInventory().setItem(i, candle);
						}
						break;
					}
				}
				if (!(candle.getType().equals(Material.AIR))) {					
					// Summon a falling block
					FallingBlock block = player.getWorld().spawnFallingBlock(
							player.getLocation().add(0, 1.1, 0).add(player.getLocation().getDirection().normalize()), 
							candle.getType().createBlockData());
					block.setVelocity(player.getLocation().getDirection().normalize());
					block.setHurtEntities(true);
					block.setDropItem(false);
					block.setVelocity(block.getVelocity().multiply(2));
					Manager.getPvPArena(player).getGame().addCandle(block.getUniqueId(), player);
					
				} else {
					player.sendMessage(ChatColor.GRAY + "You have no candles");
				}
			} else if (event.getItem() != null &&
					event.getItem().getType().equals(Material.FIRE_CHARGE)) {
				GameManager.getAndRemoveItem(Material.FIRE_CHARGE, player);
				Fireball fireball = (Fireball) player.getWorld().spawnEntity(player.getLocation().add(0, 1.1, 0), EntityType.FIREBALL);
				fireball.setBounce(false);
				fireball.setDirection(player.getLocation().getDirection());
				fireball.setShooter(player);
			} else if (event.getItem() != null &&
					event.getItem().getType().equals(Material.TRIDENT) && 
					event.getItem().getItemMeta().getLocalizedName().contains("railgun")) {
				// Ensure the player has power crystals to use
				if (player.getInventory().contains(Material.AMETHYST_SHARD)) {
					// Show laser and check if we should release it
					GameManager.laserEffect(player);
					Manager.getPvPArena(player).getGame().addRailgun(player.getUniqueId());
				} else {
					player.sendMessage(ChatColor.GRAY + "You have no power crystals");
				}
			}
		} else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			// Check to add ammo
			if (event.getItem() != null &&
					event.getItem().getType().equals(Material.STICK) && 
					event.getItem().getItemMeta().getLocalizedName().contains("earthStaff")) {
				if (event.getClickedBlock().getType().isItem()) {
					player.getInventory().addItem(GameManager.getBlockAmmo(event.getClickedBlock().getType()));
				}
			}
		}
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player)) {
			if ((player.getEquipment().getChestplate() != null &&
					player.getEquipment().getChestplate().getItemMeta().getLocalizedName().contains("ignitedChestplate")) ||
					(player.getEquipment().getLeggings() != null &&
					player.getEquipment().getLeggings().getItemMeta().getLocalizedName().contains("ignitedLeggings")) ||
					(player.getEquipment().getBoots() != null &&
					player.getEquipment().getBoots().getItemMeta().getLocalizedName().contains("ignitedBoots"))) {
				player.setFireTicks(Config.getDefaultGameTimer() * 1200);
			}
		}
		
	}
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		
		if (event.getEntity() instanceof Arrow) {
			
			Arrow arrow = (Arrow) event.getEntity();
			
			if (arrow.getShooter() instanceof Player) {
			
				Player player = (Player) arrow.getShooter();
				ItemStack itemInUse = player.getItemInUse();
				
				// Check for Bow of the Woods
				if (itemInUse != null && itemInUse.getType().equals(Material.BOW) &&
						itemInUse.getItemMeta().getLocalizedName().equals("bowOfTheWoods")) {
					arrow.setBasePotionData(new PotionData(PotionType.POISON));
				}
				
				// Check for Sniper
				if (itemInUse != null && itemInUse.getType().equals(Material.BOW) &&
						itemInUse.getItemMeta().getLocalizedName().equals("sniper")) {
					if (player.getInventory().contains(Material.SPECTRAL_ARROW)) {
						// Call a new event with spectral arrow
						player.launchProjectile(SpectralArrow.class, arrow.getVelocity());
						// Remove spectral arrow
						for (int i = player.getInventory().getSize() - 1; i >= 0; i--) {
							if (player.getInventory().getItem(i) != null && 
									player.getInventory().getItem(i).getType().equals(Material.SPECTRAL_ARROW)) {
								if (player.getInventory().getItem(i).getAmount() > 1) {
									player.getInventory().getItem(i).setAmount(player.getInventory().getItem(i).getAmount() - 1);
								} else {
									player.getInventory().remove(player.getInventory().getItem(i));
								}
								break;
							}
						}
					} else {
						player.sendMessage(ChatColor.GRAY + "Snipers can only shoot spectral arrows");
					}
					event.setCancelled(true);
				}
				
			}
			
		} else if (event.getEntity() instanceof SpectralArrow) {
			
			SpectralArrow spectralArrow = (SpectralArrow) event.getEntity();
			
			if (spectralArrow.getShooter() instanceof Player) {
				
				Player player = (Player) spectralArrow.getShooter();
				ItemStack itemInUse = player.getItemInUse();
				
				// Check for Sniper
				if (itemInUse != null && itemInUse.getType().equals(Material.BOW) &&
						itemInUse.getItemMeta().getLocalizedName().equals("sniper")) {
					// Double velocity
					spectralArrow.setVelocity(spectralArrow.getVelocity().multiply(2));
					// 5 seconds glowing
					spectralArrow.setGlowingTicks(100);
				}
				
			}
			
		} else if (event.getEntity() instanceof Trident) {
			
			Trident trident = (Trident) event.getEntity();
			
			if (trident.getShooter() instanceof Player) {
			
				Player player = (Player) trident.getShooter();
				ItemStack itemInUse = player.getItemInUse();
				
				// Check for railgun
				if (itemInUse != null && itemInUse.getType().equals(Material.TRIDENT) &&
						itemInUse.getItemMeta().getLocalizedName().equals("railgun")) {
					event.setCancelled(true);
					Manager.getPvPArena(player).getGame().removeRailgun(player.getUniqueId());
				}
			
			}
			
		}
		
	}
	
	@EventHandler
	public void onBlockLand(EntityChangeBlockEvent event) {
		
		if (Manager.isPvPArenaWorld(event.getEntity().getWorld())) {
			
			// Stop falling blocks from landing
			if (event.getEntity() instanceof FallingBlock) {
				event.setCancelled(true);
				
				FallingBlock block = (FallingBlock) event.getEntity();
				
				// Check for if it is a sorcerer candle falling
				if (Manager.getPvPArena(block.getWorld()).getGame().getCandles().containsKey(block.getUniqueId())) {
					GameManager.candleEffect(
							block.getBlockData().getMaterial(),
							Bukkit.getPlayer(Manager.getPvPArena(block.getWorld()).getGame().getCandles().get(block.getUniqueId())),
							block.getLocation());
					Manager.getPvPArena(block.getWorld()).getGame().removeCandle(block.getUniqueId());
				}
			}
			
		}
		
	}
	
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player)) {
			// Check for 10 second invisibility potion
			if (event.getItem().getType().equals(Material.POTION) &&
					event.getItem().getItemMeta().getLocalizedName().equals("invisibility10")) {
				event.setCancelled(true);
				if (player.getInventory().getItemInOffHand().equals(event.getItem())) {
					player.getInventory().setItemInOffHand(new ItemStack(Material.GLASS_BOTTLE));
				} else {
					player.getInventory().remove(event.getItem());
					player.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE));
				}
				player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 1));
			} else if (event.getItem().getType().equals(Material.POTION) &&
					event.getItem().getItemMeta().getLocalizedName().equals("confusedPotion")) {
				// Add confused affect for 10 seconds
				player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 1));
			}
		}
		
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		
		if (Manager.isPlaying(player)) {
			if (event.getBlock().getType().toString().contains("LOG") &&
					player.getInventory().getItemInMainHand() != null &&
					player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName().equals("hatchet")) {
				player.getInventory().addItem(GameManager.getLog());
			}
		}
		
	}
	
	@EventHandler
	public void onAreaEffectCloud(AreaEffectCloudApplyEvent event) {
		
		AreaEffectCloud effectCloud = event.getEntity();
		
		if (Manager.isPvPArenaWorld(effectCloud.getWorld())) {
			if (!(effectCloud.getCustomEffects().get(0).getType().equals(PotionEffectType.POISON) ||
					effectCloud.getCustomEffects().get(0).getType().equals(PotionEffectType.SLOW) ||
					effectCloud.getCustomEffects().get(0).getType().equals(PotionEffectType.FIRE_RESISTANCE))) {
				return;
			}
			event.setCancelled(true);
			effectCloud.setWaitTime(10);
			
			// Go through the affected entities and check if they are allowed to be affected
			for (Entity entity : event.getAffectedEntities()) {
				if (entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					if (entity instanceof Player) {
						Player player = (Player) entity;
						if (!(Manager.getPvPArena(player).getState().equals(GameState.LIVE)) ||
								Manager.getPvPArena(player).getGame().isRespawning(player)) {
							continue;
						}
					
					} else {
						if (!(Manager.getPvPArena(effectCloud.getWorld()).getState().equals(GameState.LIVE))) {
							continue;
						}
					}
					// Do effect
					if (effectCloud.getCustomEffects().get(0).getType().equals(PotionEffectType.POISON)) {
						livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 0));
					} else if (effectCloud.getCustomEffects().get(0).getType().equals(PotionEffectType.SLOW)) {
						livingEntity.setFreezeTicks(140);
						livingEntity.damage(1);
					} else if (effectCloud.getCustomEffects().get(0).getType().equals(PotionEffectType.FIRE_RESISTANCE)) {
						livingEntity.setFireTicks(100);
					}
				}
			}
		}
		
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		
		Entity entity = event.getEntity();
		
		if (Manager.isPvPArenaWorld(entity.getWorld())) {
			
			if (entity instanceof Fireball) {
				Fireball fireball = (Fireball) entity;				
				event.setCancelled(true);
				// Location, power(4 is tnt), set fire, break blocks, entity source
				entity.getWorld().createExplosion(
						entity.getLocation(), 5.0f, false, false, (Entity) fireball.getShooter());
			}
		
		}
		
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		
		Player player = event.getEntity();
		
		if (Manager.isPlaying(player)) {			
			// Remove any possible tamed dogs
			GameManager.removeDogs(player);
		}
		
	}
	
}
