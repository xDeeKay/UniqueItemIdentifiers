package net.dkcraft.uniqueitemidentifiers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Util {

	public Main plugin;

	public Config config;

	public Util(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
	}
	
	@SuppressWarnings("deprecation")
	public void giveCustomItem(Player targetPlayer, String itemName, int amount) {
		
		Item item = plugin.items.get(itemName);
		int count = item.getCount();
		
		ItemStack itemStack = new ItemStack(Material.valueOf(item.getMaterial().toUpperCase()), amount);
		ItemMeta meta = itemStack.getItemMeta();
		
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', item.getName().replace("%id%", String.valueOf(item.getCount()))));
		
		List<String> loreList = new ArrayList<String>();
		for (String loreLine : item.getLore()) {
			if (loreLine.contains("%id%")) {
				loreLine = loreLine.replace("%id%", String.valueOf(count));
			}
			loreList.add(ChatColor.translateAlternateColorCodes('&', loreLine));
		}
		meta.setLore(loreList);
		
		List<String> enchantmentList = new ArrayList<String>();
		enchantmentList.addAll(item.getEnchantments());
		for (String enchantment : enchantmentList) {
			String[] enchantmentParts = enchantment.split(":");
			String enchantmentName = enchantmentParts[0];
			int enchantmentLevel = Integer.parseInt(enchantmentParts[1]);
			meta.addEnchant(Enchantment.getByName(enchantmentName), enchantmentLevel, true);
		}
		
		itemStack.setItemMeta(meta);
		
		item.setCount(count + 1);
		plugin.getConfig().set("items." + itemName + ".count", count + 1);
		plugin.saveConfig();
		
		targetPlayer.getInventory().addItem(itemStack);
	}
	
	public void listCustomItems(CommandSender sender) {
		
		sender.sendMessage(ChatColor.GREEN + "Available UII Custom Items:");
		
		for (String itemName : plugin.items.keySet()) {
			Item item = plugin.items.get(itemName);
			sender.sendMessage(ChatColor.GREEN + " " + itemName + ":");
			sender.sendMessage(ChatColor.GREEN + "  Material: " + ChatColor.WHITE + item.getMaterial());
			sender.sendMessage(ChatColor.GREEN + "  Name: " + ChatColor.translateAlternateColorCodes('&', item.getName()));
			sender.sendMessage(ChatColor.GREEN + "  Lore: " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', String.join(", ", item.getLore())) + ChatColor.WHITE + "]");
			sender.sendMessage(ChatColor.GREEN + "  Enchantments: " + ChatColor.WHITE + item.getEnchantments());
			sender.sendMessage(ChatColor.GREEN + "  Count: " + ChatColor.WHITE + item.getCount());
		}
	}
}
