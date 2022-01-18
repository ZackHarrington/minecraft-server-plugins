package com.zack.minigame_PvPArena.Kits.Types;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum KitType {

	DEFAULT(ChatColor.GRAY + "Default", Material.COAL, new String[] {
			ChatColor.DARK_GRAY + "The standard kit recieved", ChatColor.DARK_GRAY + "when none is chosen", "",
			ChatColor.GRAY + " - Iron sword",
			ChatColor.GRAY + " - Snowballs x16",
			ChatColor.GRAY + " - Shield",
			ChatColor.GRAY + " - Iron armor"}),
	RANDOM(ChatColor.YELLOW + "Random", Material.EXPERIENCE_BOTTLE, new String[] {
			ChatColor.GRAY + "Get assigned a random kit!"}),	
	LONGBOW(ChatColor.DARK_GREEN + "Longbow", Material.BOW, new String[] {
			ChatColor.GRAY + "A kit built for a marksman", "",
			ChatColor.DARK_GREEN + " - Bow (Power V) + Arrows x64",
			ChatColor.DARK_GREEN + " - Iron sword",
			ChatColor.DARK_GREEN + " - Spyglass",
			ChatColor.DARK_GREEN + " - Leather armor"}),
	SNIPER(ChatColor.GREEN + "Sniper", Material.SPECTRAL_ARROW, new String[] {
			ChatColor.GRAY + "Precision in the form of a kit", "",
			ChatColor.GREEN + " - Bow (Power V, x2 Arrow velocity)",
			ChatColor.GREEN + " - Crossbow (Quick Charge II)",
			ChatColor.GREEN + " - Arrows x32, Spectral Arrows x8",
			ChatColor.GREEN + " - Spyglass",
			ChatColor.GREEN + " - Leather armor"}),
	SHOTGUN(ChatColor.DARK_AQUA + "Shotgun", Material.CROSSBOW, new String[] {
			ChatColor.GRAY + "A kit for closer shooting", "",
			ChatColor.DARK_AQUA + " - Crossbow (Quick Charge III, Multishot)",
			ChatColor.DARK_AQUA + " - Snowballs x16",
			ChatColor.DARK_AQUA + " - Arrows x32",
			ChatColor.DARK_AQUA + " - Shield",
			ChatColor.DARK_AQUA + " - Leather armor"}),
	RAILGUN(ChatColor.DARK_PURPLE + "Railgun", Material.NETHER_STAR, new String[] {
			ChatColor.GRAY + "Quite the powerful kit", "",
			ChatColor.DARK_PURPLE + " - Trident + Power crystals x8",
			ChatColor.DARK_PURPLE + " - Spyglass",
			ChatColor.DARK_PURPLE + " - Leather armor"}),
	LUMBERJACK(ChatColor.DARK_GREEN + "Lumberjack", Material.DIAMOND_AXE, new String[] {
			ChatColor.GRAY + "A kit for chopping", "",
			ChatColor.DARK_GREEN + " - Diamond axe (Efficiency III, Sharpness V)",
			ChatColor.DARK_GREEN + " - Logs x8",
			ChatColor.DARK_GREEN + " - Shield",
			ChatColor.DARK_GREEN + " - Jeans and a Flannel"}),
	COLD_SNAP(ChatColor.BLUE + "Cold Snap", Material.ICE, new String[] {
			ChatColor.GRAY + "A kit designed for combos", "",
			ChatColor.BLUE + " - Iron sword (Frost Aspect)",
			ChatColor.BLUE + " - Iron sword (Sharpness III)",
			ChatColor.BLUE + " - Snow Shield",
			ChatColor.BLUE + " - Iron armor"}),
	NINJA(ChatColor.DARK_RED + "Ninja", Material.ENDER_PEARL, new String[] {
			ChatColor.GRAY + "A kit born to evade detection", "",
			ChatColor.DARK_RED + " - Iron sword",
			ChatColor.DARK_RED + " - Ender pearls x4",
			ChatColor.DARK_RED + " - Potion of swiftness",
			ChatColor.DARK_RED + " - Potion of invisibility",
			ChatColor.DARK_RED + " - Ninja armor"}),
	RABID_FARMER(ChatColor.GREEN + "Rabid Farmer", Material.DIAMOND_HOE, new String[] {
			ChatColor.GRAY + "A kit infused with confused rage", "",
			ChatColor.GREEN + " - Diamond hoe (Sharpness V)",
			ChatColor.GREEN + " - Tamed wolf spawn eggs x5",
			ChatColor.GREEN + " - Potion of swiftness",
			ChatColor.GREEN + " - Broken shield",
			ChatColor.GREEN + " - Tattered Flannel"}),
	FROST_LORD(ChatColor.BLUE + "Frost Lord", Material.SNOWBALL, new String[] {
			ChatColor.GRAY + "A very cold kit", "",
			ChatColor.BLUE + " - Trident (Loyalty)",
			ChatColor.BLUE + " - Eternal Snowball",
			ChatColor.BLUE + " - Snow Shield",
			ChatColor.BLUE + " - Frost armor (Immunity from Snowballs)"}),
	EARTH_BENDER(ChatColor.GRAY + "Earth Bender", Material.ROOTED_DIRT, new String[] {
			ChatColor.DARK_GRAY + "Quite the dirty kit", "",
			ChatColor.GRAY + " - Earth Staff + Block ammo x32",
			ChatColor.GRAY + " - Stone sword (Sharpness V)",
			ChatColor.GRAY + " - Earth Shield (Halves falling block damage)",
			ChatColor.GRAY + " - Mud armor"}),
	FIRE_MASTER(ChatColor.DARK_RED + "Fire Master", Material.LAVA_BUCKET, new String[] {
			ChatColor.GRAY + "The kit of eternal flame", "",
			ChatColor.DARK_RED + " - Gold Sword (Fire Aspect II)",
			ChatColor.DARK_RED + " - Firecharge x8",
			ChatColor.DARK_RED + " - Totem of Undying",
			ChatColor.DARK_RED + " - Ignited armor (Immunity from fire)"}),
	FOREST_NYMPH(ChatColor.DARK_GREEN + "Forest Nymph", Material.DARK_OAK_SAPLING, new String[] {
			ChatColor.GRAY + "A kit from the spirits", "",
			ChatColor.DARK_GREEN + " - Bow (Shoots poison arrows) + Arrows x16",
			ChatColor.DARK_GREEN + " - Potion of swiftness",
			ChatColor.DARK_GREEN + " - Potion of leaping",
			ChatColor.DARK_GREEN + " - Totem of Undying",
			ChatColor.DARK_GREEN + " - Leaf armor"}),
	SLOW_BURN(ChatColor.LIGHT_PURPLE + "Slow Burn", Material.LINGERING_POTION, new String[] {
			ChatColor.GRAY + "A dark magic infected kit", "",
			ChatColor.LIGHT_PURPLE + " - Conjuring Staff",
			ChatColor.LIGHT_PURPLE + " - Candle of Poison x16",
			ChatColor.LIGHT_PURPLE + " - Candle of Frost x16",
			ChatColor.LIGHT_PURPLE + " - Candle of Flame x16",
			ChatColor.LIGHT_PURPLE + " - Tainted Totem",
			ChatColor.LIGHT_PURPLE + " - Wizard Robe"});
	
	private String display;
	private Material material;
	private String[] description;
	
	private KitType(String display, Material material, String[] description) {
		this.display = display;
		this.material = material;
		this.description = description;
	}
	
	public String getDisplay() { return display; }
	public Material getMaterial() { return material; }
	public String[] getDescription() { return description; }
	
}