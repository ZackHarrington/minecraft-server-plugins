package com.zack.CustomDecor;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;
import org.bukkit.map.MapView.Scale;

public class MapEvent implements Listener {

	Main main;
	public MapEvent(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onMapInitialise(MapInitializeEvent event) {
		
		MapView view = event.getMap();
		
		//view.setScale(Scale.CLOSEST);
		//view.setUnlimitedTracking(true); // even thousands of chunks away the person will be on it
		
		view.getRenderers().clear();
		
		view.addRenderer(new Renderer());
		
	}
	
}
