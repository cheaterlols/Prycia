package me.vrekt.prycia.manage;

import java.util.ArrayList;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.vrekt.prycia.Prycia;
import me.vrekt.prycia.user.User;
import me.vrekt.prycia.util.Utilities;
import net.md_5.bungee.api.ChatColor;

public class PryciaManager {

	public boolean isDebuggingEnabled = false;
	public boolean isEnabled = true;
	private ArrayList<User> userBanQueue = new ArrayList<User>();
	// schedule a ban for a player.

	public void scheduleBan(User user) {

		Utilities.logMessage(ChatColor.DARK_GRAY + user.getPlayer().getName() + ChatColor.DARK_RED + " is set to be banned in 30 seconds.");
		userBanQueue.add(user);

		new BukkitRunnable() {
			public void run() {
				if (!userBanQueue.contains(user)) {
					this.cancel();
					return;
				} else {
					Player player = user.getPlayer();
					userBanQueue.remove(user);
					player.kickPlayer(ChatColor.RED + "Banned by Prycia.");
					Bukkit.getBanList(Type.NAME).addBan(player.getName(), ChatColor.RED + "Banned by Prycia.", null, null);
				}
			}
		}.runTaskLater(Prycia.getPlugin(), 600);
	}

	// is the anti-cheat enabled?

	public boolean isEnabled() {
		return isEnabled;
	}

	// is debugging enabled?

	public boolean isDebuggingEnabled() {
		return isDebuggingEnabled;
	}

	// Is a user scheduled for a ban?

	public boolean isInBanQueue(User user) {
		return userBanQueue.contains(user);
	}

	// Cancel a ban.

	public void cancelBan(User user) {
		if (userBanQueue.contains(user)) {
			userBanQueue.remove(user);
		}
	}

}
