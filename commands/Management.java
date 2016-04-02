package me.vrekt.prycia.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.vrekt.prycia.Prycia;
import net.md_5.bungee.api.ChatColor;

public class Management implements CommandExecutor {

	private int last = 0;
	private String text;
	private String dtext;

	// When a player executes /management. Create the gui that is displayed.
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be an in-game player to execute this command.");
			return false;
		}

		Player player = (Player) sender;
		Inventory gui = Bukkit.createInventory(null, 27, "Prycia");
		ItemStack book = new ItemStack(Material.BOOK, 1);
		ItemStack paper = new ItemStack(Material.PAPER, 1);
		ItemStack barrier = new ItemStack(Material.BARRIER, 1);
		ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);

		text = Prycia.getManager().isEnabled() ? ChatColor.GREEN + "enabled." : ChatColor.RED + "disabled.";
		dtext = Prycia.getManager().isDebuggingEnabled() ? ChatColor.GREEN + "enabled." : ChatColor.RED + "disabled.";
		ItemMeta metaBook = book.getItemMeta();
		ItemMeta metaPaper = paper.getItemMeta();
		ItemMeta metaBarrier = barrier.getItemMeta();
		ItemMeta metaPane = pane.getItemMeta();

		metaBook.setDisplayName(ChatColor.RED + "View all violations.");
		metaPaper.setDisplayName(ChatColor.RED + "Enable or disable debug logging, currently " + dtext);
		metaBarrier.setDisplayName(ChatColor.RED + "Enable or disable the anticheat, currently " + text);
		metaPane.setDisplayName(ChatColor.LIGHT_PURPLE + "Invalid Action.");

		book.setItemMeta(metaBook);
		paper.setItemMeta(metaPaper);
		barrier.setItemMeta(metaBarrier);
		pane.setItemMeta(metaPane);

		for (ItemStack i : gui.getContents()) {
			i = pane;
			gui.setItem(last++, i);
			if (last == 27) {
				last = 0;
			}
		}
		gui.setItem(10, book);
		gui.setItem(16, paper);
		gui.setItem(13, barrier);

		player.openInventory(gui);

		return true;

	}

}
