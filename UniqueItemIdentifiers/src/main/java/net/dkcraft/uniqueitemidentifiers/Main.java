package net.dkcraft.uniqueitemidentifiers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public Main main;

	public Config config;
	
	public Util util;

	public Map<String, Item> items = new HashMap<String, Item>();

	public void onEnable() {

		this.main = this;
		
		config = new Config(this);
		util = new Util(this);
		
		this.getCommand("uii").setExecutor(new UIICommand(this));
		
		config.loadItems();
	}

	public void onDisable() {

	}
}
