package me.vrekt.prycia.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.vrekt.prycia.Prycia;
import me.vrekt.prycia.user.User;
import me.vrekt.prycia.util.Utilities;
import net.md_5.bungee.api.ChatColor;

public class CCBan implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {

		// Used for canceling a ban.

		if (cmd.getName().equalsIgnoreCase("cancelban")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.DARK_RED + "Invalid argument. use /cancelban <name>");
				return true;
			}

			Player player = Bukkit.getPlayerExact(args[0]);
			if (player != null) {
				User user = Prycia.getUserManager().getUser(player.getUniqueId());
				if (Prycia.getManager().isInBanQueue(user)) {
					Prycia.getManager().cancelBan(user);
					Utilities.logMessage(ChatColor.DARK_GRAY + user.getPlayer().getName() + "'s" + ChatColor.DARK_RED + " ban has been cancelled by " + ChatColor.DARK_GRAY + sender.getName());
					return true;
				} else {
					sender.sendMessage(ChatColor.DARK_GRAY + user.getPlayer().getName() + ChatColor.RED + " is not in the ban queue.");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.DARK_RED + "that user is not in the ban queue or not a valid player.");
				return true;
			}
		}
		return true;
	}

}
