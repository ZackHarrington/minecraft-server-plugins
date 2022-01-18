package com.zack.CustomCrafting_YML;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class YMLCommand implements CommandExecutor {

	Main main;
	public YMLCommand(Main main) {
		this.main = main;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		main.getTestFile().createSection("Test");
		main.getTestFile().createSection("Test2");
		main.getTestFile().set("Test", "value");
		
		// Must save the file after changes or it wont.. save
		try {
			main.getTestFile().save(main.getFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}

}
