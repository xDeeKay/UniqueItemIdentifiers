package net.dkcraft.uniqueitemidentifiers;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class Config {

	public Main plugin;

	public Config(Main plugin) {
		this.plugin = plugin;
	}

	public void loadItems() {

		plugin.getConfig().options().copyDefaults(true);
		plugin.saveDefaultConfig();

		if (plugin.getConfig().isSet("items")) {

			Set<String> itemSet = plugin.getConfig().getConfigurationSection("items").getKeys(false);

			if (!itemSet.isEmpty()) {

				for (String item : itemSet) {

					String material = plugin.getConfig().getString("items." + item + ".material");
					String name = plugin.getConfig().getString("items." + item + ".name");
					List<String> lore = plugin.getConfig().getStringList("items." + item + ".lore");
					List<String> enchantments = plugin.getConfig().getStringList("items." + item + ".enchantments");
					int count = plugin.getConfig().getInt("items." + item + ".count");

					plugin.items.put(item, new Item(material, name, lore, enchantments, count));
					plugin.getLogger().log(Level.INFO, "Loaded custom item: " + item);
					plugin.getLogger().log(Level.INFO, " Material: " + material);
					plugin.getLogger().log(Level.INFO, " Name: " + name);
					plugin.getLogger().log(Level.INFO, " Lore: " + lore);
					plugin.getLogger().log(Level.INFO, " Enchantments: " + enchantments);
					plugin.getLogger().log(Level.INFO, " Count: " + count);

				}
			}
		}
	}
}
