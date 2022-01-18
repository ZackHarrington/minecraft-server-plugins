package com.zack.CustomDecor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class BannerCommand implements CommandExecutor {

	Main main;
	public BannerCommand(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			ItemStack item = new ItemStack(Material.WHITE_BANNER);
			BannerMeta meta = (BannerMeta) item.getItemMeta();
			
			//meta.setBaseColor(DyeColor.BLUE); // defaults to black, not used because the material types are the base color
			List<Pattern> patternStack = new ArrayList<>();
			patternStack.add(new Pattern(DyeColor.BLUE, PatternType.TRIANGLE_TOP));
			patternStack.add(new Pattern(DyeColor.RED, PatternType.TRIANGLE_BOTTOM));
			patternStack.add(new Pattern(DyeColor.BLUE, PatternType.TRIANGLES_BOTTOM));
			
			meta.setPatterns(patternStack);
			item.setItemMeta(meta);
			
			player.getInventory().addItem(item);
		}
		
		
		return false;
	}

}
