package com.zack.minigame_PvPArena.Kits.Types;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.zack.minigame_PvPArena.Manager;
import com.zack.minigame_PvPArena.Kits.Kit;

import net.md_5.bungee.api.ChatColor;

public class RandomKit extends Kit {

	public RandomKit(UUID uuid) {
		super(uuid, KitType.RANDOM);
	}

	@Override
	public void onStart(Player player) {
		
		Kit kit = null;
		
		do {
			switch (Manager.getRandom().nextInt(13)) {
			case 0: kit = new LongbowKit(uuid); break;
			case 1: kit = new SniperKit(uuid); break;
			case 2: kit = new ShotgunKit(uuid); break;
			case 3: kit = new RailgunKit(uuid); break;
			case 4: kit = new LumberjackKit(uuid); break;
			case 5: kit = new ColdSnapKit(uuid); break;
			case 6: kit = new NinjaKit(uuid); break;
			case 7: kit = new RabidFarmerKit(uuid); break;
			case 8: kit = new FrostLordKit(uuid); break;
			case 9: kit = new EarthBenderKit(uuid); break;
			case 10: kit = new FireMasterKit(uuid); break;
			case 11: kit = new ForestNymphKit(uuid); break;
			case 12: kit = new SlowBurnKit(uuid); break;
			default: break;
			}
		} while (kit == null);
		
		player.sendMessage(ChatColor.GREEN + "You have been equipped the " + kit.getType().getDisplay() + ChatColor.GREEN + " kit!");
		
		kit.onStart(player);
		
	}
	
}
