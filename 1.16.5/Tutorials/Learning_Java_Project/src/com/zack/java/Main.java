package com.zack.java;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class Main {

	public static void main(String[] args) {
		
		int number = 100;
		
		List<Double> list = new ArrayList<>();
		list.add(10.8);
		list.add(4.1);
		list.add(5.7);
		
		System.out.println(list.get(2) + list.size());
		
		// Hashmap
		
		HashMap<Double, String> hashmap = new HashMap<>();
		
		hashmap.put(14.5, "meow");
		
		Boolean t = hashmap.containsKey(14.5);
		
		String yarrow = hashmap.get(14.5);
		
		// Linked list
		
		LinkedList<String> linkedlist = new LinkedList<>();
		
		//LinkedHashMap<double, String> linkedhashmap = new LinkedHashMap<>();
		
		
		for (int i = 0; i < 50; i++) {
			System.out.println(i);
		}
		
		for (Double n : hashmap.keySet()) {
			System.out.println(n);
		}
		
		
		// PArt 5
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT")); // wont cahnge, gets current time and thats it
		int hour = cal.get(Calendar.HOUR);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		
		cal.set(Calendar.MINUTE, 47); // set calendar
		
		// Random
		Random rand = new Random();
		System.out.println(list.get(rand.nextInt(list.size())));
		
		List<Animal> animals = new ArrayList<>();
		animals.add(new Cow());
		
		
	}

}
