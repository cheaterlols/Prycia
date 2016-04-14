package me.vrekt.prycia.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.vrekt.prycia.Prycia;
import net.md_5.bungee.api.ChatColor;

public class PReload implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("preload")) {
			if (sender.hasPermission("prycia_staff")) {
				Prycia.getPlugin().reloadConfig();
				Prycia.getConfigUtil().writeValues();
				sender.sendMessage(ChatColor.RED + "Configuration reloaded!");
			}
		}

		return true;
	}

}
