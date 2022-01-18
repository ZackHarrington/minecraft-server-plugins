package com.zack.GodEnchantments;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.zack.GodEnchantments.Enchantments.GodEnchantment;
import com.zack.GodEnchantments.Listeners.EnchantmentListener;
import com.zack.GodEnchantments.Managers.GodManager;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	protected static Main main;
	public static GodEnchantment godEnchantment;
	public static GodManager godManager;
	public static List<Player> playersWithCookies;
	public static List<ItemStack> godCookies;
	private int runnable;
	
	@Override
	public void onEnable() {
		System.out.println("GOD ENCHANTMENTS ENABLED");
		
		Main.main = this;
		godCookies = new ArrayList<>();
		playersWithCookies = new ArrayList<>();
		
		godManager = new GodManager();
		
		registerEnchantment(godEnchantment = new GodEnchantment());
		loadRecipes();
		startRunnable();

		this.getServer().getPluginManager().registerEvents(new EnchantmentListener(), this);
	}
	
	@Override
	// Run with stop command, not red X
	public void onDisable() {
		System.out.println("GOD ENCHANTMENTS DISABLED");
		
		// End any possible godEnchantment runnables
		godManager.destroy(); // before destroying enchantment because may have to return player's cookies
		
		// Removes the enchantment so on start up next time there is no issue of duplicate enchantments
		removeEnchantment(godEnchantment);
		
		// End runnable
		Bukkit.getScheduler().cancelTask(runnable);
	}
	
	
	// Start up and power down functions -------------------------------------------------
	
	public static Main getInstance() { return main; }
	
	private void startRunnable() {
		runnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				for (int i = 0; i < godCookies.size(); i++) {
					ItemMeta godCookieMeta = godCookies.get(i).getItemMeta();
					
					// Make random color
					Random rand = new Random();
					switch (rand.nextInt(14)) {
					case 0:
						godCookieMeta.setDisplayName(ChatColor.GOLD + "Curious Cookie");
						break;
					case 1:
						godCookieMeta.setDisplayName(ChatColor.WHITE + "Curious Cookie");
						break;
					case 2:
						godCookieMeta.setDisplayName(ChatColor.RED + "Curious Cookie");
						break;
					case 3:
						godCookieMeta.setDisplayName(ChatColor.GREEN + "Curious Cookie");
						break;
					case 4:
						godCookieMeta.setDisplayName(ChatColor.AQUA + "Curious Cookie");
						break;
					case 5:
						godCookieMeta.setDisplayName(ChatColor.DARK_PURPLE + "Curious Cookie");
						break;
					}
					godCookies.get(i).setItemMeta(godCookieMeta);
				}
				for (int i = 0; i < playersWithCookies.size(); i++) {
					playersWithCookies.get(i).updateInventory();
				}
			}
		}, 0L, 5L); // Ticks per repeat, ticks until start
	}
	
	private void registerEnchantment(Enchantment enchantment) {
		try {
			Field field = Enchantment.class.getDeclaredField("acceptingNew");
			field.setAccessible(true);
			field.set(null, true);
			Enchantment.registerEnchantment(enchantment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	private void removeEnchantment(Enchantment enchantment) {
		try {
			Field keyField = Enchantment.class.getDeclaredField("byKey");
			keyField.setAccessible(true);
			
			HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);
			if (byKey.containsKey(enchantment.getKey())) {
				byKey.remove(enchantment.getKey());
			}
			
			Field nameField = Enchantment.class.getDeclaredField("byName");
			nameField.setAccessible(true);
			
			HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);
			if (byName.containsKey(enchantment.getName())) {
				byName.remove(enchantment.getName());
			}
		} catch (Exception e) { }
	}
	
	// Recipe Manager?
	public void loadRecipes() {
		// Output
		ItemStack godCookie = new ItemStack(Material.COOKIE, 1);
		godCookie.addEnchantment(godEnchantment, 1);
		ItemMeta godCookieMeta = godCookie.getItemMeta(); 
		godCookieMeta.setDisplayName(
				ChatColor.GOLD.toString() + ChatColor.MAGIC + "Curious Cookie");
		//godCookieMeta.setLore(Arrays.asList(ChatColor.GOLD + "God"));
		godCookie.setItemMeta(godCookieMeta);
				
		// This is for a crafting table, can make ones for any type of table
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "g"), godCookie);
		recipe.shape("dwd", "acn", "dsd");
		
		recipe.setIngredient('d', Material.DIAMOND_BLOCK);
		recipe.setIngredient('c', Material.COOKIE);
		recipe.setIngredient('w', Material.CONDUIT);
		recipe.setIngredient('a', Material.ENCHANTED_GOLDEN_APPLE);
		recipe.setIngredient('n', Material.NETHERITE_BLOCK);
		recipe.setIngredient('s', Material.SHULKER_BOX);
				
		// must reference main class in other classes
		this.getServer().addRecipe(recipe);
	}
	
}