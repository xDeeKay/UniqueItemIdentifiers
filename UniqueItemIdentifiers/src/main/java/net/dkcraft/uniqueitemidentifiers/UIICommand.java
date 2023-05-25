package net.dkcraft.uniqueitemidentifiers;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UIICommand implements CommandExecutor {

	public Main plugin;
	
	public Config config;

	public Util util;

	public UIICommand(Main plugin) {
		this.plugin = plugin;
		this.config = this.plugin.config;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("uii")) {

			if (args.length == 0) {
				sender.sendMessage(ChatColor.GREEN + "Available UII Commands:");
				sender.sendMessage(ChatColor.GREEN + " /uii give <player> <item> <amount> - Give player a custom item");
				sender.sendMessage(ChatColor.GREEN + " /uii list - List available custom items");
				sender.sendMessage(ChatColor.GREEN + " /uii reload - Reload config");

			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("list")) {
					util.listCustomItems(sender);
				}
				if (args[0].equalsIgnoreCase("reload")) {
					config.loadItems();
					sender.sendMessage(ChatColor.GREEN + "Reloaded config.");
				}
				
			} else if (args.length == 4) {
				if (args[0].equalsIgnoreCase("give")) {

					String target = args[1];
					if (plugin.getServer().getPlayer(target) != null) {
						Player targetPlayer = plugin.getServer().getPlayer(target);

						String itemName = args[2];
						if (plugin.items.containsKey(itemName)) {

							int amount = Integer.parseInt(args[3]);
							util.giveCustomItem(targetPlayer, itemName, amount);
							sender.sendMessage(ChatColor.GREEN + "Gave " + ChatColor.WHITE + amount + ChatColor.GREEN + " of " + ChatColor.WHITE + itemName + ChatColor.GREEN + " to player " + ChatColor.WHITE + target);

						} else {
							sender.sendMessage(ChatColor.RED + itemName + " does not exist.");
						}
					} else {
						sender.sendMessage(ChatColor.RED + target + " is not online.");
					}
				}
			}
		}
		return true;
	}
}
