package com.zack.randomGameChanges_1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.md_5.bungee.api.ChatColor;

public class Manager {

	private static Random rand;
	private static RodofEnderEnchantment rodofEnderEnchantment;
	private static List<Block> placedGravel;

	public Manager() {
		rand = new Random();
		rodofEnderEnchantment = new RodofEnderEnchantment();
		placedGravel = new ArrayList<>();

		createEnchantments();
		loadRecipes();
	}

	public static Color getColor() {
        Color c = Color.WHITE;

        switch (rand.nextInt(17)) {
        case 0: c = Color.AQUA; break;
        case 1: c = Color.BLACK; break;
        case 2: c = Color.BLUE; break;
        case 3: c = Color.FUCHSIA; break;
        case 4: c = Color.GRAY; break;
        case 5: c = Color.GREEN; break;
        case 6: c = Color.LIME; break;
        case 7: c = Color.MAROON; break;
        case 8: c = Color.NAVY; break;
        case 9: c = Color.OLIVE; break;
        case 10: c = Color.ORANGE; break;
        case 11: c = Color.PURPLE; break;
        case 12: c = Color.RED; break;
        case 13: c = Color.SILVER; break;
        case 14: c = Color.TEAL; break;
        case 15: c = Color.WHITE; break;
        case 16: c = Color.YELLOW; break;
        }

		return c;
	}

	public static RodofEnderEnchantment getRodofEnderEnchantment() {
		return rodofEnderEnchantment;
	}
	
	public static void addGravel(Block gravel) {
		placedGravel.add(gravel);
	}
	public static boolean containsGravel(Block gravel) {
		return placedGravel.contains(gravel);
	}
	public static void removeGravel(Block gravel) {
		placedGravel.remove(gravel);
	}

	private static void createEnchantments() {

		try {
			Field field = Enchantment.class.getDeclaredField("acceptingNew");
			field.setAccessible(true);
			field.set(null, true);
			Enchantment.registerEnchantment(rodofEnderEnchantment);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	@SuppressWarnings("unchecked")
	public static void disableEnchantments() {
		// Enchantments
		try {
			// Removes the enchantment so on start up next time there is no issue of duplicate enchantments
			Field keyField = Enchantment.class.getDeclaredField("byKey");
			keyField.setAccessible(true);

			HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);
			if (byKey.containsKey(rodofEnderEnchantment.getKey())) {
				byKey.remove(rodofEnderEnchantment.getKey());
			}

			Field nameField = Enchantment.class.getDeclaredField("byName");
			nameField.setAccessible(true);

			HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);
			if (byName.containsKey(rodofEnderEnchantment.getName())) {
				byName.remove(rodofEnderEnchantment.getName());
			}
		} catch (Exception e) { }

	}

	private static void loadRecipes() {

		// Rod of Ender
		// Output
		ItemStack RodofEnder = new ItemStack(Material.BLAZE_ROD);
		ItemMeta RodofEnderMeta = RodofEnder.getItemMeta();
		RodofEnderMeta.addEnchant(rodofEnderEnchantment, 1, false);
		RodofEnderMeta.setDisplayName(ChatColor.GOLD + "Rod of Ender");
		RodofEnder.setItemMeta(RodofEnderMeta);

		// This is for a crafting table, can make ones for any type of table
		ShapedRecipe RodofEnderRecipe = new ShapedRecipe(new NamespacedKey(Main.getInstance(), "a"), RodofEnder);
		RodofEnderRecipe.shape("eee", "ebe", "eee");

		RodofEnderRecipe.setIngredient('e', Material.ENDER_PEARL);
		RodofEnderRecipe.setIngredient('b', Material.BLAZE_ROD);

		// must reference main class in other classes
		Main.getInstance().getServer().addRecipe(RodofEnderRecipe);
		
		
		// Recreate blaze rods
		ItemStack blazeRod = new ItemStack(Material.BLAZE_ROD);

		ShapedRecipe blazeRodRecipe = new ShapedRecipe(new NamespacedKey(Main.getInstance(), "b"), blazeRod);
		blazeRodRecipe.shape("  p", " p ", "p  ");
		blazeRodRecipe.setIngredient('p', Material.BLAZE_POWDER);

		Main.getInstance().getServer().addRecipe(blazeRodRecipe);

	}
	
	public static ItemStack getEntityHead(EntityType type) {
		ItemStack is = null;
		
		// Custom texture
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		
		switch (type) {
		case AXOLOTL: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2I4M2EzOGE0NThjM2NjYTA3NjFlMmM4MjEwYzZmNWQyZjMzODBlODYwZDUwZDJmNDc1NjUxNmEyNjQyNjE3ZCJ9fX0=")); break;
		case BAT: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjc2NjE5NjUyZmFmZWM5MGNlOThkZjUwMTNjNjNkYzZhNzc3NzZhYjI3ODczYjczZGFmYjJiNmJkZWIxODUifX19")); break;
		case BEE: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTQ3MzIyZjgzMWUzYzE2OGNmYmQzZTI4ZmU5MjUxNDRiMjYxZTc5ZWIzOWM3NzEzNDlmYWM1NWE4MTI2NDczIn19fQ==")); break;
		case BLAZE: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJmZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ==")); break;
		case CAT: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDcyMGY1MjlmYzFiMTU0YjRkZDk4NDEyMzY4NzA3ODk3ZGQ3YzIyMjNmZmVhYTEyNjFlNjk3ZWZjZWRiNDkifX19")); break;
		case CAVE_SPIDER: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDE2NDVkZmQ3N2QwOTkyMzEwN2IzNDk2ZTk0ZWViNWMzMDMyOWY5N2VmYzk2ZWQ3NmUyMjZlOTgyMjQifX19")); break;
		case CHICKEN: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTYzODQ2OWE1OTljZWVmNzIwNzUzNzYwMzI0OGE5YWIxMWZmNTkxZmQzNzhiZWE0NzM1YjM0NmE3ZmFlODkzIn19fQ==")); break;
		case COD: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGEzOGE5YzMyNzEwZTNlMzUxYjEyMmEwZGQ3YjI3OWU5NWE3ZjNlNTQ2YWI3N2U4YmNiZTYzZmUzNWQ4MDgifX19")); break;
		case COW: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWQ2YzZlZGE5NDJmN2Y1ZjcxYzMxNjFjNzMwNmY0YWVkMzA3ZDgyODk1ZjlkMmIwN2FiNDUyNTcxOGVkYzUifX19")); break;
		case CREEPER: is = new ItemStack(Material.CREEPER_HEAD); break;
		case DOLPHIN: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2VmZTdkODAzYTQ1YWEyYWYxOTkzZGYyNTQ0YTI4ZGY4NDlhNzYyNjYzNzE5YmZlZmM1OGJmMzg5YWI3ZjUifX19")); break;
		case DONKEY: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGZiNmMzYzA1MmNmNzg3ZDIzNmEyOTE1ZjgwNzJiNzdjNTQ3NDk3NzE1ZDFkMmY4Y2JjOWQyNDFkODhhIn19fQ==")); break;
		case DROWNED: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzNmN2NjZjYxZGJjM2Y5ZmU5YTYzMzNjZGUwYzBlMTQzOTllYjJlZWE3MWQzNGNmMjIzYjNhY2UyMjA1MSJ9fX0=")); break;
		case ELDER_GUARDIAN: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWM3OTc0ODJhMTRiZmNiODc3MjU3Y2IyY2ZmMWI2ZTZhOGI4NDEzMzM2ZmZiNGMyOWE2MTM5Mjc4YjQzNmIifX19")); break;
		case ENDER_DRAGON: is = new ItemStack(Material.DRAGON_HEAD); break;
		case ENDERMITE: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWExYTA4MzFhYTAzYWZiNDIxMmFkY2JiMjRlNWRmYWE3ZjQ3NmExMTczZmNlMjU5ZWY3NWE4NTg1NSJ9fX0="));break;
		case ENDERMAN: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2E1OWJiMGE3YTMyOTY1YjNkOTBkOGVhZmE4OTlkMTgzNWY0MjQ1MDllYWRkNGU2YjcwOWFkYTUwYjljZiJ9fX0=")); break;
		case EVOKER: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTc5ZjEzM2E4NWZlMDBkM2NmMjUyYTA0ZDZmMmViMjUyMWZlMjk5YzA4ZTBkOGI3ZWRiZjk2Mjc0MGEyMzkwOSJ9fX0=")); break;
		case FOX: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzA2OTE2MTAyNzhlOTA4NDI0NjI4MGYyNzk1YWRlZjliM2E4OGMyNTU0YTY0MWE2YWEyM2I3YjY2NDg4ODM2In19fQ==")); break;
		case GHAST: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGI2YTcyMTM4ZDY5ZmJiZDJmZWEzZmEyNTFjYWJkODcxNTJlNGYxYzk3ZTVmOTg2YmY2ODU1NzFkYjNjYzAifX19")); break;
		case GLOW_SQUID: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmVjZDBiNWViNmIzODRkYjA3NmQ4NDQ2MDY1MjAyOTU5ZGRkZmYwMTYxZTBkNzIzYjNkZjBjYzU4NmQxNmJiZCJ9fX0=")); break;
		case GOAT: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjAzMzMwMzk4YTBkODMzZjUzYWU4YzlhMWNiMzkzYzc0ZTlkMzFlMTg4ODU4NzBlODZhMjEzM2Q0NGYwYzYzYyJ9fX0=")); break;
		case GUARDIAN: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGZiNjc1Y2I1YTdlM2ZkMjVlMjlkYTgyNThmMjRmYzAyMGIzZmE5NTAzNjJiOGJjOGViMjUyZTU2ZTc0In19fQ==")); break;
		case HOGLIN: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWJiOWJjMGYwMWRiZDc2MmEwOGQ5ZTc3YzA4MDY5ZWQ3Yzk1MzY0YWEzMGNhMTA3MjIwODU2MWI3MzBlOGQ3NSJ9fX0=")); break;
		case HORSE: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE5MDI4OTgzMDg3MzBjNDc0NzI5OWNiNWE1ZGE5YzI1ODM4YjFkMDU5ZmU0NmZjMzY4OTZmZWU2NjI3MjkifX19")); break;
		case HUSK: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY3NGM2M2M4ZGI1ZjRjYTYyOGQ2OWEzYjFmOGEzNmUyOWQ4ZmQ3NzVlMWE2YmRiNmNhYmI0YmU0ZGIxMjEifX19")); break;
		case ILLUSIONER: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEyNTEyZTdkMDE2YTIzNDNhN2JmZjFhNGNkMTUzNTdhYjg1MTU3OWYxMzg5YmQ0ZTNhMjRjYmViODhiIn19fQ==")); break;
		case IRON_GOLEM: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODkwOTFkNzllYTBmNTllZjdlZjk0ZDdiYmE2ZTVmMTdmMmY3ZDQ1NzJjNDRmOTBmNzZjNDgxOWE3MTQifX19")); break;
		case LLAMA: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmE1ZjEwZTZlNjIzMmYxODJmZTk2NmY1MDFmMWMzNzk5ZDQ1YWUxOTAzMWExZTQ5NDFiNWRlZTBmZWZmMDU5YiJ9fX0=")); break;
		case MAGMA_CUBE: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzg5NTdkNTAyM2M5MzdjNGM0MWFhMjQxMmQ0MzQxMGJkYTIzY2Y3OWE5ZjZhYjM2Yjc2ZmVmMmQ3YzQyOSJ9fX0=")); break;
		case MULE: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZkY2RhMjY1ZTU3ZTRmNTFiMTQ1YWFjYmY1YjU5YmRjNjA5OWZmZDNjY2UwYTY2MWIyYzAwNjVkODA5MzBkOCJ9fX0=")); break;
		case MUSHROOM_COW: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDBiYzYxYjk3NTdhN2I4M2UwM2NkMjUwN2EyMTU3OTEzYzJjZjAxNmU3YzA5NmE0ZDZjZjFmZTFiOGRiIn19fQ==")); break;
		case OCELOT: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTY1N2NkNWMyOTg5ZmY5NzU3MGZlYzRkZGNkYzY5MjZhNjhhMzM5MzI1MGMxYmUxZjBiMTE0YTFkYjEifX19")); break;
		case PANDA: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTk1ODU5ZWZkYjRmNzYyNmJjMjA1NTM0MWNkMmZhYWIzY2MwNjAyYzhhY2I1YzkxNDg1ZmRiYmFlMzExMzI1NCJ9fX0=")); break;
		case PARROT: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWRmNGIzNDAxYTRkMDZhZDY2YWM4YjVjNGQxODk2MThhZTYxN2Y5YzE0MzA3MWM4YWMzOWE1NjNjZjRlNDIwOCJ9fX0=")); break;
		case PHANTOM: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQ2ODMwZGE1ZjgzYTNhYWVkODM4YTk5MTU2YWQ3ODFhNzg5Y2ZjZjEzZTI1YmVlZjdmNTRhODZlNGZhNCJ9fX0=")); break;
		case PIG: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIxNjY4ZWY3Y2I3OWRkOWMyMmNlM2QxZjNmNGNiNmUyNTU5ODkzYjZkZjRhNDY5NTE0ZTY2N2MxNmFhNCJ9fX0=")); break;
		case PIGLIN: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDNjZTAyMzc0N2M3ODI5YjUxYWI5Y2Q2NmU3OGEyMDM0ZDhmOTg4OTIyMzQyYTQ0ZWU1NmI3YWI1YzVmNTA1YSJ9fX0=")); break;
		case PIGLIN_BRUTE: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2UzMDBlOTAyNzM0OWM0OTA3NDk3NDM4YmFjMjllM2E0Yzg3YTg0OGM1MGIzNGMyMTI0MjcyN2I1N2Y0ZTFjZiJ9fX0=")); break;
		case PILLAGER: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTk3MTg0NjRkYWIwNDljMDY0OGE3MTYwYzZlMzRmMzc1MjIzN2NjMTlhMTljYzcyZDA0MDFiNTE3ZjZjMjQifX19")); break;
		case POLAR_BEAR: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDAxNzEyNTFmNzIyNGU3NTE0MGVhYTljYzIxY2U4OGE5MTRkNzJmOWEzNmYyN2JiMWM5ZDFjMDlkYTJjYSJ9fX0=")); break;
		case PUFFERFISH: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTcxNTI4NzZiYzNhOTZkZDJhMjI5OTI0NWVkYjNiZWVmNjQ3YzhhNTZhYzg4NTNhNjg3YzNlN2I1ZDhiYiJ9fX0=")); break;
		case RABBIT: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2QxMTY5YjI2OTRhNmFiYTgyNjM2MDk5MjM2NWJjZGE1YTEwYzg5YTNhYTJiNDhjNDM4NTMxZGQ4Njg1YzNhNyJ9fX0=")); break;
		case RAVAGER: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWNiOWYxMzlmOTQ4OWQ4NmU0MTBhMDZkOGNiYzY3MGM4MDI4MTM3NTA4ZTNlNGJlZjYxMmZlMzJlZGQ2MDE5MyJ9fX0=")); break;
		case SALMON: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWRmYzU3ZDA5MDU5ZTQ3OTlmYTkyYzE1ZTI4NTEyYmNmYWExMzE1NTc3ZmUzYTI3YWVkMzg5ZTRmNzUyMjg5YSJ9fX0=")); break;
		case SHEEP: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMxZjljY2M2YjNlMzJlY2YxM2I4YTExYWMyOWNkMzNkMThjOTVmYzczZGI4YTY2YzVkNjU3Y2NiOGJlNzAifX19")); break;
		case SHULKER: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWU3MzgzMmUyNzJmODg0NGM0NzY4NDZiYzQyNGEzNDMyZmI2OThjNThlNmVmMmE5ODcxYzdkMjlhZWVhNyJ9fX0=")); break;
		case SILVERFISH: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTJlYzJjM2NiOTVhYjc3ZjdhNjBmYjRkMTYwYmNlZDRiODc5MzI5YjYyNjYzZDdhOTg2MDY0MmU1ODhhYjIxMCJ9fX0=")); break;
		case SKELETON: is = new ItemStack(Material.SKELETON_SKULL); break;
		case SKELETON_HORSE: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdlZmZjZTM1MTMyYzg2ZmY3MmJjYWU3N2RmYmIxZDIyNTg3ZTk0ZGYzY2JjMjU3MGVkMTdjZjg5NzNhIn19fQ==")); break;
		case SLIME: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZhZDIwZmMyZDU3OWJlMjUwZDNkYjY1OWM4MzJkYTJiNDc4YTczYTY5OGI3ZWExMGQxOGM5MTYyZTRkOWI1In19fQ==")); break;
		case SNOWMAN: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZkZmQxZjc1MzhjMDQwMjU4YmU3YTkxNDQ2ZGE4OWVkODQ1Y2M1ZWY3MjhlYjVlNjkwNTQzMzc4ZmNmNCJ9fX0=")); break;
		case SPIDER: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q1NDE1NDFkYWFmZjUwODk2Y2QyNThiZGJkZDRjZjgwYzNiYTgxNjczNTcyNjA3OGJmZTM5MzkyN2U1N2YxIn19fQ==")); break;
		case SQUID: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMDE0MzNiZTI0MjM2NmFmMTI2ZGE0MzRiODczNWRmMWViNWIzY2IyY2VkZTM5MTQ1OTc0ZTljNDgzNjA3YmFjIn19fQ==")); break;
		case STRAY: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM1MDk3OTE2YmMwNTY1ZDMwNjAxYzBlZWJmZWIyODcyNzdhMzRlODY3YjRlYTQzYzYzODE5ZDUzZTg5ZWRlNyJ9fX0=")); break;
		case STRIDER: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMThhOWFkZjc4MGVjN2RkNDYyNWM5YzA3NzkwNTJlNmExNWE0NTE4NjY2MjM1MTFlNGM4MmU5NjU1NzE0YjNjMSJ9fX0=")); break;
		case TRADER_LLAMA: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQyNDc4MGIzYzVjNTM1MWNmNDlmYjViZjQxZmNiMjg5NDkxZGY2YzQzMDY4M2M4NGQ3ODQ2MTg4ZGI0Zjg0ZCJ9fX0=")); break;
		case TROPICAL_FISH: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGQ1NGRiM2U1YWFlZjJjNTM1Mzg4M2NmNmFkZjY5ODYyNzMyYzkxNDllYWZmY2E0MWU5YjhmZGFmZWY5ODZjIn19fQ==")); break;
		case TURTLE: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTJlNTQ4NDA4YWI3NWQ3ZGY4ZTZkNWQyNDQ2ZDkwYjZlYzYyYWE0ZjdmZWI3OTMwZDFlZTcxZWVmZGRmNjE4OSJ9fX0=")); break;
		case VEX: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJlYzVhNTE2NjE3ZmYxNTczY2QyZjlkNWYzOTY5ZjU2ZDU1NzVjNGZmNGVmZWZhYmQyYTE4ZGM3YWI5OGNkIn19fQ==")); break;
		case VILLAGER: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODIyZDhlNzUxYzhmMmZkNGM4OTQyYzQ0YmRiMmY1Y2E0ZDhhZThlNTc1ZWQzZWIzNGMxOGE4NmU5M2IifX19")); break;
		case VINDICATOR: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlYWVjMzQ0YWIwOTViNDhjZWFkNzUyN2Y3ZGVlNjFiMDYzZmY3OTFmNzZhOGZhNzY2NDJjODY3NmUyMTczIn19fQ==")); break;
		case WANDERING_TRADER: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWYxMzc5YTgyMjkwZDdhYmUxZWZhYWJiYzcwNzEwZmYyZWMwMmRkMzRhZGUzODZiYzAwYzkzMGM0NjFjZjkzMiJ9fX0=")); break;
		case WITCH: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGRlZGJlZTQyYmU0NzJlM2ViNzkxZTdkYmRmYWYxOGM4ZmU1OTNjNjM4YmExMzk2YzllZjY4ZjU1NWNiY2UifX19")); break;
		case WITHER: is = new ItemStack(Material.WITHER_SKELETON_SKULL, 3); break;
		case WITHER_SKELETON: is = new ItemStack(Material.WITHER_SKELETON_SKULL); break;
		case WOLF: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjlkMWQzMTEzZWM0M2FjMjk2MWRkNTlmMjgxNzVmYjQ3MTg4NzNjNmM0NDhkZmNhODcyMjMxN2Q2NyJ9fX0=")); break;
		case ZOGLIN: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTY3ZTE4NjAyZTAzMDM1YWQ2ODk2N2NlMDkwMjM1ZDg5OTY2NjNmYjllYTQ3NTc4ZDNhN2ViYmM0MmE1Y2NmOSJ9fX0=")); break;
		case ZOMBIE: is = new ItemStack(Material.ZOMBIE_HEAD); break;
		case ZOMBIE_HORSE: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDIyOTUwZjJkM2VmZGRiMThkZTg2ZjhmNTVhYzUxOGRjZTczZjEyYTZlMGY4NjM2ZDU1MWQ4ZWI0ODBjZWVjIn19fQ==")); break;
		case ZOMBIE_VILLAGER: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTYyMjQ5NDEzMTRiY2EyZWJiYjY2YjEwZmZkOTQ2ODBjYzk4YzM0MzVlZWI3MWEyMjhhMDhmZDQyYzI0ZGIifX19")); break;
		case ZOMBIFIED_PIGLIN: profile.getProperties().put("textures", new Property("textures", 
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzRlOWM2ZTk4NTgyZmZkOGZmOGZlYjMzMjJjZDE4NDljNDNmYjE2YjE1OGFiYjExY2E3YjQyZWRhNzc0M2ViIn19fQ==")); break;
		default: is = null;
		}
		
		if (is == null) {
			Field field;
			try {
				field = skullMeta.getClass().getDeclaredField("profile");
				field.setAccessible(true);
				field.set(skullMeta, profile);
			} catch(NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
				
			}
			skull.setItemMeta(skullMeta);
			return skull;
		} else {
			return is;
		}
	}


}
