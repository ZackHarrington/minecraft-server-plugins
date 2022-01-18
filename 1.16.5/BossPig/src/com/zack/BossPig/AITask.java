package com.zack.BossPig;

import org.bukkit.scheduler.BukkitRunnable;

public class AITask extends BukkitRunnable {

	BossPig bossPig;
	
	public AITask(BossPig bossPig) {
		this.bossPig = bossPig;
	}
	
	@Override
	public void run() {
		if (bossPig.getPause() < 0) {
			bossPig.update();
		} else {
			bossPig.decrementPause();
		}
	}
	
}
