package com.zack.entities;

public enum DataTypes {

	ORANGE("Orange"),
	APPLE("Apple"),
	BANANA("Banana"),
	KIWI("Kiwi"),
	PLUM("Plum");
	
	public String name;
	
	private DataTypes(String name) {
		this.name = name;
	}
	
}
