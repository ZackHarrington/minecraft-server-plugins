package com.zack.CustomDecor;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class HideBlockEvent implements Listener {

	private Main main;
	
	public HideBlockEvent(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {
		// Cannot use most other events because it attempts to change the block but the rest of the event just resets it
		// Event fires when you both press shift and let go of shift
		
		// get target block returns if there is a block within that many blocks of where you are looking
		//if (event.isSneaking() && event.getPlayer().getTargetBlockExact(5) != null) {
		//	event.getPlayer().sendBlockChange(event.getPlayer().getTargetBlockExact(5).getLocation(), Material.ACACIA_LEAVES.createBlockData());
		//}
		
		Block target = event.getPlayer().getTargetBlockExact(5);
		
		// Signs
		if (event.isSneaking() && target != null && target.getType() == Material.OAK_SIGN) {
			event.getPlayer().sendSignChange(target.getLocation(), new String[] {
					"Hey",
					"testy",
					"test",
					event.getPlayer().getName()});
		}
	}
	
}
