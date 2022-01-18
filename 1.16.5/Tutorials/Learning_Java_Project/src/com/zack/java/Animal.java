package com.zack.java;

public class Animal {

	private int legs;
	private String color;
	private boolean waterAnimal;
	
	public Animal(int legs, String color, boolean waterAnimal) {
		this.legs = legs;
		this.color = color;
		this.waterAnimal = waterAnimal;
	}
	
	public int getLegs() { return this.legs; }
	public String getColor() {return this.color; }
	
	public void setConsoleMessage() {
		System.out.println("This animal has " + legs + "legs");
		
	}
	
}
