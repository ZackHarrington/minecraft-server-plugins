package com.zack.CustomCrafting_YML;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private File testFile;
	private YamlConfiguration modifyTestFile;
	ArrayList<String> commands;
	
	@Override
	public void onEnable() {
		System.out.println("PLUGIN ENABLED");
		
		loadRecipe();
		
		this.getCommand("book").setExecutor(new BookCommand(this));
		
		this.getCommand("yml").setExecutor(new YMLCommand(this));
		
		this.getCommand("help").setExecutor(new HelpCommand(this));
		registerCommands();
		
		try {
			initiateFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable() {
		System.out.println("PLUGIN DISABLED");
	}
	
	
	// Crafting
	
	private void loadRecipe() {
		// Output
		ItemStack item = new ItemStack(Material.STONE_STAIRS, 4);
		
		// decide on your own symbols to reference blocks
		/*
		 * ^ - SAND
		 * % - PUMPKIN
		 * $ - DIAMOND
		 */
		
		// put result into the constructor
		// depreciated
		//ShapedRecipe recipe = new ShapedRecipe( item);
		// create a new key to reference the output, output
		//ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "a"), item);
		// 3 rows - a space makes an actual space
		//recipe.shape("^ ^", "$%$", "^^^");
		
		//recipe.setIngredient('^', Material.SAND);
		//recipe.setIngredient('%', Material.PUMPKIN);
		//recipe.setIngredient('$', Material.DIAMOND);
		
		// This is for a crafting table, can make ones for any type of table
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "a"), item);
		recipe.shape("   ", "  s", " ss");
		
		recipe.setIngredient('s', Material.COBBLESTONE);
		
		// must reference main class in other classes
		this.getServer().addRecipe(recipe);
		
	}
	
	
	// YML
	
	public YamlConfiguration getTestFile() { return modifyTestFile; }
	public File getFile() { return testFile; }
	
	public void initiateFiles() throws IOException {
		// Need to create a file called the project name first
		// project name, file name
		testFile = new File(Bukkit.getServer().getPluginManager().getPlugin("CustomCrafting_YML").getDataFolder(), "test.yml");
		if (!testFile.exists()) {
			testFile.createNewFile(); // throws IOException up there means we can create lots of files without lots of try and catches
		}
		
		modifyTestFile = YamlConfiguration.loadConfiguration(testFile); // either is created or had been loaded in
	}
	
	// Help
	
	public void registerCommands() {
		commands = new ArrayList<>();
		
		commands.add("tp player");
		commands.add("set home");
		commands.add("go home");
		commands.add("say");
		commands.add("blow player's head");
		commands.add("blow player's body");
		commands.add("blow player's foot");
		commands.add("blow player's dog [evil]");
	}
	
}
