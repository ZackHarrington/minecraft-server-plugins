package com.zack.ZombieApocalypse.Mobs;

public enum ZombieType {

	/* * When new types are added be sure to update Manager.getZombie() and Manager.equipZombie() * */
	BASIC(1.0f),
	IMPOSTER(1.1f), // Has a different monster's head and is slightly faster
	QUICK(1.5f), // Normal, just he can move fast
	WEAK_PARTIAL_ARMOR(0.9f), // One piece of leather or gold armor
	ARCHER(1.0f), // Holding a bow, can shoot arrows
	SLEEP(1.1f), // Has died leather armor like it was a nightgown
	FARMER(1.1f), // Has a random hoe and partial leather armor
	STRONG_PARTIAL_ARMOR(0.8f), // One piece of iron or diamond armor
	FLAMING(1.3f), // Has fire ticks and fire resistance for the remaining part of the night
	WEAK_FULL_ARMOR(0.7f), // Full set with each piece being either leather or gold armor
	MAGE(0.8f), // Orange died chestplate, can launch fire balls
	STRONG(0.7f), // Strength and an iron or diamond axe
	IRON_MINER(0.9f), // Mostly full (2-3 pieces) iron armor and an iron pickaxe
	STRONG_FULL_ARMOR(0.6f), // Full set with each piece being either iron or diamond armor
	SUICIDE_BOMBER(1.3f), // Red chestplate, creeper head, blows up when within 2 blocks of a player
	ALCHEMIST(1.0f), // Holding a potion of harming, has swiftness, strength, regeneration, fire resistance, water breathing, and jump boost
	
	JUGGERNAUT(1.2f); // Full enchanted diamond armor, an enchanted diamond axe, strength, and regeneration

	private float speedMultiplier;
	
	private ZombieType(float speedMultiplier) {
		this.speedMultiplier = speedMultiplier * 0.23f;
	}
	
	public float getSpeedMultiplier() {
		return this.speedMultiplier;
	}
	
}
