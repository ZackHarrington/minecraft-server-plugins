package com.zack.GUIs_Skins_Regions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.ChunkSection;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.IBlockData;

public class Main extends JavaPlugin implements Listener {

	Cuboid cuboid;
	
	@Override
	public void onEnable() {
		
		// Fastest setBlock methods -------------------------------------------------------------------------------------------------
		
		// Bukkit's setBlock ~ 60k blocks/sec
		// 1st method (everything is loaded and in view) ~ 80-90k blocks/sec
		// 2nd method (Not anyone on or any thing in view) ~ 14 mil blocks/sec
		Bukkit.getPluginManager().registerEvents(this, this);
		setBlockMethod2(Bukkit.getWorld("world"), -126, 64, -320, 5, (byte) 0, false);
		
		// Regions -------------------------------------------------------------------------------------------------
		
		// Use enums to save many regions
		cuboid = new Cuboid(
				new Location(Bukkit.getWorld("world"), -159, 64, -363),
				new Location(Bukkit.getWorld("world"), -150, 69, -353));
		
		// Forcing Custom Skins ------------------------------------------------------------------------------------------
		
		
		// GUIs -----------------------------------------------------------------------------------------------------------------
		
		
		
	}
	
	
	// Fastest setBlock methods -------------------------------------------------------------------------------------------------
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			setBlockLoadedMethod1(event.getClickedBlock().getWorld(), event.getClickedBlock().getLocation().getBlockX(),
					event.getClickedBlock().getLocation().getBlockY(), event.getClickedBlock().getLocation().getBlockZ(),
					1, (byte) 0, true); // BlockId of 1 is stone, uses basic Minecraft block IDs w/ the data value as the ._ such as 7.2
		}
	}
	
	public static void setBlockLoadedMethod1(World world, int x, int y, int z, int blockId, byte data, boolean applyPhysics) {
		
		net.minecraft.server.v1_16_R3.World nmsWorld = ((CraftWorld) world).getHandle();
		BlockPosition bp = new BlockPosition(x, y, z);
		IBlockData ibd = net.minecraft.server.v1_16_R3.Block.getByCombinedId(blockId + (data << 12)); // unsure why left shifting to multiply by 2
		nmsWorld.setTypeAndData(bp, ibd, applyPhysics ? 3 : 2); // 3 for physics, 2 for no, unsure why
		
	}
	
	public static void setBlockMethod2(World world, int x, int y, int z, int blockId, byte data, boolean applyPhysics) {
		
		net.minecraft.server.v1_16_R3.World nmsWorld = ((CraftWorld) world).getHandle();
		net.minecraft.server.v1_16_R3.Chunk nmsChunk = nmsWorld.getChunkAt(x >> 4, z >> 4); // divide by 2 4 times, probably because chunks are 16x16 in the x and z
		IBlockData ibd = net.minecraft.server.v1_16_R3.Block.getByCombinedId(blockId + (data << 12));
		
		ChunkSection cs = nmsChunk.getSections()[y >> 4];
		if (cs == nmsChunk.a()) {
			cs = new ChunkSection(y >> 4 << 4); // creates a new chunk section
			nmsChunk.getSections()[y >> 4] = cs;
		}
		
		if (applyPhysics) {
			cs.getBlocks().setBlock(x & 15, y & 15, z & 15, ibd); // bitwise &
		} else {
			cs.getBlocks().b(x & 15, y & 15, z & 15, ibd);
		}
		
	}
	
	
	// Regions -------------------------------------------------------------------------------------------------
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (!event.getPlayer().isGlowing() && cuboid.containsLocation(event.getTo())) {
			event.getPlayer().setGlowing(true);
		} else if (event.getPlayer().isGlowing() && !cuboid.containsLocation(event.getTo())) {
			event.getPlayer().setGlowing(false);
		}
	}
	
	// Forcing Custom Skins ------------------------------------------------------------------------------------------
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		setSkin(event.getPlayer());
		
	}
	
	private void setSkin(Player player) {
		EntityPlayer ep = ((CraftPlayer) player).getHandle();
		GameProfile gp = ep.getProfile(); // GameProfile holds the basic meta for each player, their IDs, skins, etc
		PropertyMap pm = gp.getProperties();
		Property property = pm.get("textures").iterator().next(); // Only want the first one, there is no map
		// Remove, then add from online (mineskin.org (if you like a player's skin you can just type in their user name to get the skin))
		pm.remove("textures", property);
		// "textures", texture data, texture signature
		pm.put("textures", new Property("textures",
				"ewogICJ0aW1lc3RhbXAiIDogMTYyMTMxNzYxMTAwMiwKICAicHJvZmlsZUlkIiA6ICJjMjFjODhmZmIzNWE0YTk2OTMyYTNmOGJhMzNjYzVmZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJEdW1ibGVkb3JlIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzkzYWFmOGYyYTdmYTc3OWU1MjViNWQ3YWI5NTkyNGIzNzc0MmM2Zjg4ZmMzZDE2YWI5YjJmMGNjNjA4NDAxNjkiCiAgICB9CiAgfQp9",
				"OY0g0qa5l64tDoptPV3i8lJCtIIbgJhsZktHwWCwMFbE5pLJgpz+ds+KbeB6xloGw+VJ0P+TWbYwJq5GvE/jpyKgl2EnLuFJcq6tDUeaXKpnExeFLHxoUW3gv8prx5jZcPypG8Ykkx9kHFHAmSEDph3A6y+AOUaGbENUv8Qf1QsOvo33IB1vOismyhpjT0+mcOBSyuhjRoLIv9L7BrO7JrW335gqXdeuKIn4D6PhwC+FKTYDFt71B0uiSApKPA2B67oR4mms6TXohsK+AGeeV3AysYHTqJHmJEqjRRAUDGHS7FDiWHjsOCzSPd1HLKPn1dMh3vyuE30BqYhT711fULufs4z4jr40NJu5UCeY3AKI+cnBZ9NAo5DpFsTo9LDAP8kPJ/Bb2XJhPu5ImgmOjxRjh/Wc1h9fSRM6otw30aWLI2yDwEo7kDZmEIU3968clCt2Wkd0Nfmaxo0/mMtTpdfAyWLPxs9yCHgy3XbMDu7dv79rg7PV1ujQOGr6l/CO1fX+HiEzpCghFygXI0AdWN+YTx2IlJ7M3Jl8J6J+VFztjZPMVqiIyhtaES5qO+8dVWLNC6P0MBfQMu5kN8Ih6ipMLK9eePFMPpo7gqPmzMEfhNB3n6UpN/ZPRoAnq8b4mDl4u9fl//3tsAvP6lfsCQ8o7uxSZhC9LzxvB6JicMQ="));
	}
	
	// GUIs -----------------------------------------------------------------------------------------------------------------
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("gui")) {
				new DynamicGUI((Player) sender, 1);
			}
		}
		
		return false;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		Inventory inv = event.getInventory();
		
		if (item != null && item.getType() != null && inv.getType().name().contains("GUI Page - ")) {
			// Get page number from the left item meta
			int page = Integer.parseInt(inv.getItem(0).getItemMeta().getLocalizedName());
			
			if (event.getRawSlot() == 0 && item.getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
				new DynamicGUI(player, page - 1);
			} else if (event.getRawSlot() == 8 && item.getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
				new DynamicGUI(player, page + 1);
			}
			
			event.setCancelled(isEnabled());
		}
		
	}
	
}
