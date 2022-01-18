package com.zack.randomGameChanges_1;

import org.bukkit.ChatColor;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.FireworkMeta;

public class RandomEventsListener implements Listener {

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {

		Player player = event.getPlayer();
		Block block = event.getBlock();
		Location centralLocation = block.getLocation().add(0.5, 0.5, 0.5);

		// Smelting iron pickaxe
		if (player.getEquipment().getItemInMainHand().getType().equals(Material.IRON_PICKAXE)) {
			if (block.getType().equals(Material.IRON_ORE) || (block.getType().equals(Material.DEEPSLATE_IRON_ORE))) {
				event.setDropItems(false);
				block.getWorld().dropItemNaturally(
						centralLocation, new ItemStack(Material.IRON_INGOT));
			} else if (block.getType().equals(Material.GOLD_ORE) || (block.getType().equals(Material.DEEPSLATE_GOLD_ORE))) {
				event.setDropItems(false);
				block.getWorld().dropItemNaturally(
						centralLocation, new ItemStack(Material.GOLD_INGOT));
			} else if (block.getType().equals(Material.COPPER_ORE) || (block.getType().equals(Material.DEEPSLATE_COPPER_ORE))) {
				event.setDropItems(false);
				block.getWorld().dropItemNaturally(
						centralLocation, new ItemStack(Material.COPPER_INGOT));
			}
			
		}

		// Firework dirt
		if (block.getType().equals(Material.GRASS_BLOCK)) {
			Firework firework = (Firework) block.getWorld().spawnEntity(centralLocation, EntityType.FIREWORK);
			FireworkMeta fireworkMeta = firework.getFireworkMeta();
			fireworkMeta.addEffect(FireworkEffect.builder().trail(true)
					.withColor(Manager.getColor()).flicker(true).with(Type.BALL_LARGE).build());
			firework.setFireworkMeta(fireworkMeta);
		}

		// Always stick leaves
		if (block.getType().name().contains("LEAVES")) {
			block.getWorld().dropItemNaturally(
					centralLocation, new ItemStack(Material.STICK));
		}
		
		// Gravel doesn't drop flint unless it was placed by a player
		if (block.getType().equals(Material.GRAVEL)) {
			event.setDropItems(false);
			if (Manager.containsGravel(block)) {
				block.getWorld().dropItemNaturally(
						centralLocation, new ItemStack(Material.FLINT));
				Manager.removeGravel(block);
			} else {
				block.getWorld().dropItemNaturally(
						centralLocation, new ItemStack(Material.GRAVEL));
			}
		}

	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		
		if (event.getBlock().getType().equals(Material.GRAVEL)) {
			Manager.addGravel(event.getBlock());
		}
		
	}

	@EventHandler
	public void onCraft(CraftItemEvent event) {

		if (event.getWhoClicked() instanceof Player) {

			Player player = (Player) event.getWhoClicked();
			Recipe recipe = event.getRecipe();

			// Stop stick crafting
			if (recipe.getResult().getType().equals(Material.STICK)) {
				event.setCancelled(true);
			}

			// Alter eye of ender crafting
			if (recipe.getResult().getType().equals(Material.ENDER_EYE)) {
				event.setCancelled(true);
				player.closeInventory();
				
				ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
				BookMeta bookMeta = (BookMeta) book.getItemMeta();
				bookMeta.addPage(ChatColor.GOLD + "\n\n                       pow"
						+ "\n                       der\n\n            pow\n            der\n\npow\nder");
				bookMeta.addPage(ChatColor.GREEN + "\n\n\n\n     eye   eye   eye\n\n     eye"
						+ ChatColor.GOLD + "   rod" + ChatColor.GREEN + "   eye\n\n     eye   eye   eye");
				bookMeta.setTitle(ChatColor.GOLD + "You Thought");
				bookMeta.setAuthor(ChatColor.MAGIC + "The Adjustor");
				book.setItemMeta(bookMeta);
				player.getWorld().dropItemNaturally(player.getLocation(), book);
			}

		}

	}

	@EventHandler
	public void onClick(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		// Rod of Ender
		if (player.getEquipment().getItemInMainHand().getType().equals(Material.BLAZE_ROD) &&
				player.getEquipment().getItemInMainHand().getItemMeta().hasEnchant(Manager.getRodofEnderEnchantment())) {
			// Infinite eyes of ender
			if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
				EnderSignal enderSignal = (EnderSignal) player.getWorld().spawnEntity(
						player.getLocation().add(0.5, 1, 0.5), EntityType.ENDER_SIGNAL);
				enderSignal.setDespawnTimer(20);
				enderSignal.setTargetLocation(player.getLocation().add(0, 10, 0));
			}
		
			// Fill portal frames with rod
			if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) &&
					block.getType().equals(Material.END_PORTAL_FRAME)) { 
				EndPortalFrame frame = (EndPortalFrame) block.getBlockData();
				frame.setEye(true);
				block.setBlockData(frame);
			}
		}
		
		// Stop normal filling of end portal frames
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) &&
				block.getType().equals(Material.END_PORTAL_FRAME) &&
				player.getEquipment().getItemInMainHand().getType().equals(Material.ENDER_EYE)) {
			event.setCancelled(true);
		}

	}
	
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		
		if (event.getDamager() instanceof Player) {
			
			Player player = (Player) event.getDamager();
			
			// Fire wooden hoe
			if (player.getEquipment().getItemInMainHand().getType().equals(Material.WOODEN_HOE)) {
				event.getEntity().setFireTicks(60);
			}
			
		}
		
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		
		// Entity head drop
		if (Manager.getEntityHead(event.getEntityType()) != null) {
			event.getEntity().getWorld().dropItemNaturally(
					event.getEntity().getLocation(), Manager.getEntityHead(event.getEntityType()));
		}
		
	}

}
