package net.dkcraft.uniqueitemidentifiers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

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

		//meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', item.getName().replace("%id%", String.valueOf(item.getCount()))));
		meta.setDisplayName(translateHexColorCodes("&#", "", item.getName().replace("%id%", String.valueOf(item.getCount()))));

		List<String> loreList = new ArrayList<String>();
		for (String loreLine : item.getLore()) {
			if (loreLine.contains("%id%")) {
				loreLine = loreLine.replace("%id%", String.valueOf(count));
			}
			//loreList.add(ChatColor.translateAlternateColorCodes('&', loreLine));
			loreList.add(translateHexColorCodes("&#", "", loreLine));
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
			sender.sendMessage(ChatColor.GREEN + "  Name: " + translateHexColorCodes("&#", "", item.getName()));
			sender.sendMessage(ChatColor.GREEN + "  Lore: " + ChatColor.WHITE + "[" + translateHexColorCodes("&#", "", String.join(ChatColor.WHITE + ", ", item.getLore())) + ChatColor.WHITE + "]");
			sender.sendMessage(ChatColor.GREEN + "  Enchantments: " + ChatColor.WHITE + item.getEnchantments());
			sender.sendMessage(ChatColor.GREEN + "  Count: " + ChatColor.WHITE + item.getCount());
		}
	}
	
	public String translateHexColorCodes(String startTag, String endTag, String message) {
		final Pattern hexPattern = Pattern.compile(startTag + "([A-Fa-f0-9]{6})" + endTag);
		Matcher matcher = hexPattern.matcher(message);
		StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
		while (matcher.find()) {
			String group = matcher.group(1);
			matcher.appendReplacement(buffer, ChatColor.COLOR_CHAR + "x"
					+ ChatColor.COLOR_CHAR + group.charAt(0) + ChatColor.COLOR_CHAR + group.charAt(1)
					+ ChatColor.COLOR_CHAR + group.charAt(2) + ChatColor.COLOR_CHAR + group.charAt(3)
					+ ChatColor.COLOR_CHAR + group.charAt(4) + ChatColor.COLOR_CHAR + group.charAt(5)
					);
		}
		return matcher.appendTail(buffer).toString();
	}
}
