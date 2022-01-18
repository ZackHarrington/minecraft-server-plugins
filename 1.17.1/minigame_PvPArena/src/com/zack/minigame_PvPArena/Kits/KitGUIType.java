package com.zack.minigame_PvPArena.Kits;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.zack.minigame_PvPArena.Kits.Types.KitType;

public enum KitGUIType {

	CLASS_SELECTOR(ChatColor.AQUA + "Class Selection", null, new String[] {""}, new KitType[] {}),
	ARCHERY_CLASS(ChatColor.DARK_GREEN + "Archery Class", Material.BOW, new String[] {ChatColor.GRAY + "Kits for shooting stuff"}, 
			new KitType[] {KitType.LONGBOW, KitType.SNIPER, KitType.SHOTGUN, KitType.RAILGUN}),
	MELEE_CLASS(ChatColor.DARK_RED + "Melee Class", Material.DIAMOND_SWORD, new String[] {ChatColor.GRAY + "Kits for hitting stuff"}, 
			new KitType[] {KitType.LUMBERJACK, KitType.COLD_SNAP, KitType.NINJA, KitType.RABID_FARMER}),
	MAGE_CLASS(ChatColor.DARK_PURPLE + "Mage Class", Material.AMETHYST_SHARD, new String[] {ChatColor.GRAY + "Kits for doing magic"}, 
			new KitType[] {KitType.FROST_LORD, KitType.EARTH_BENDER, KitType.FIRE_MASTER, KitType.FOREST_NYMPH, KitType.SLOW_BURN});
	
	private String display;
	private Material material;
	private String[] description;
	private KitType[] subKits;
	
	private KitGUIType(String display, Material material, String[] description, KitType[] subKits) {
		this.display = display;
		this.material = material;
		this.description = description;
		this.subKits = subKits;
	}
	
	public String getDisplay() { return display; }
	public Material getMaterial() { return material; }
	public String[] getDescription() { return description; }
	public KitType[] getSubKits() { return subKits; }
	
}
