package com.zack.CustomDecor;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

public class Renderer extends MapRenderer {

	@Override
	public void render(MapView view, MapCanvas canvas, Player player) {
		
		// x, y, color
		canvas.setPixel(5, 5, MapPalette.DARK_GREEN); // deprecated but works fine
		canvas.setPixel(50, 50, MapPalette.RED);
		
		for (int x = 25; x < 50; x++) {
			for (int y = 25; y < 50; y++) {
				canvas.setPixel(x, y, MapPalette.TRANSPARENT);
			}
		}
		
		// x, y, only 1 font avail. , text
		// Can't change color, or anything really
		canvas.drawText(25, 100, MinecraftFont.Font, "Hey");
		
		try {
			// If using more than once or twice, create image elseware to save lots of storage and processing
			// .jpg, .png only
			// larger than 128 x 128 and only part loads
			BufferedImage image = ImageIO.read(new URL("https://i.imgur.com/aOMGW5V.jpg"));
			canvas.drawImage(0, 0, image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
	
}
