package com.zack.BossPig;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobDamageListener implements Listener {

	Main main = null;
	
	public MobDamageListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		// Check if the entity hit was a boss pig
		for (int i = 0; i < main.bossPigs.size(); i++) {
			if (e.getEntity().getEntityId() == main.bossPigs.get(i).getEntityId())
			{
				main.bossPigs.get(i).updateBar();
				// Check if it was a hit worthy of knockback
				if (e.getCause() == DamageCause.ENTITY_ATTACK || 
						e.getCause() == DamageCause.ENTITY_SWEEP_ATTACK ||
						e.getCause() == DamageCause.PROJECTILE) {
					main.bossPigs.get(i).attacked();
				}
			}
		}
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		// Check if the entity hit was a boss pig
		for (int i = 0; i < main.bossPigs.size(); i++) {
			if (e.getEntity().getEntityId() == main.bossPigs.get(i).getEntityId())
			{
				main.bossPigs.get(i).death();
				main.bossPigs.remove(i);
			}
		}
	}
	
}
