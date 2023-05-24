package net.dkcraft.uniqueitemidentifiers;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UIICommand implements CommandExecutor {

	public Main plugin;

	public Util util;

	public UIICommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("uii")) {

			Player player = (Player) sender;

			if (args.length == 0) {
				player.sendMessage(ChatColor.GREEN + "Available UII Commands:");
				player.sendMessage(ChatColor.GREEN + " /uii give <player> <item> <amount>");
				player.sendMessage(ChatColor.GREEN + " /uii list");

			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("list")) {
					util.listCustomItems(player);
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
							player.sendMessage(ChatColor.GREEN + "Gave " + ChatColor.WHITE + amount + ChatColor.GREEN + " of " + ChatColor.WHITE + itemName + ChatColor.GREEN + " to player " + ChatColor.WHITE + target);

						} else {
							player.sendMessage(ChatColor.RED + itemName + " does not exist.");
						}
					} else {
						player.sendMessage(ChatColor.RED + target + " is not online.");
					}
				}
			}
		}
		return true;
	}
}
