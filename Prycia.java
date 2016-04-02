package me.vrekt.prycia;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.commands.CCBan;
import me.vrekt.prycia.commands.Management;
import me.vrekt.prycia.events.PlayerListener;
import me.vrekt.prycia.manage.CheckManager;
import me.vrekt.prycia.manage.PacketManager;
import me.vrekt.prycia.manage.PryciaManager;
import me.vrekt.prycia.net.BaseListener;
import me.vrekt.prycia.user.User;
import me.vrekt.prycia.user.UserManager;
import me.vrekt.prycia.util.Config;

public class Prycia extends JavaPlugin {

	private static ProtocolManager packetManager;
	private static Plugin plugin;

	public void onEnable() {
		getLogger().info(": - Prycia is now enabled.");

		// Add reference to everything.

		saveDefaultConfig();
		
		plugin = this;
		packetManager = ProtocolLibrary.getProtocolManager();

		new PacketManager();
		new BaseListener().addListeners(this, packetManager);
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			new User(player);
			getUserManager().add(new User(player));
		}

		// Register various things.

		this.registerCommands();
		this.registerEvents();

		// Read the config and set the values in Config class.

		for (CheckType ct : CheckType.values()) {
			boolean cancel = getConfig().getBoolean(ct.toString().toLowerCase() + ".enabled.auto-ban.cancel");
			boolean ban = getConfig().getBoolean(ct.toString().toLowerCase() + ".enabled.auto-ban");
			boolean enabled = getConfig().getBoolean(ct.toString().toLowerCase() + ".enabled");
			int threshold = getConfig().getInt(ct.toString().toLowerCase() + ".enabled.auto-ban.cancel.threshold");

			if (ct.equals(CheckType.REGENERATION)) {
				boolean doEventCheck = getConfig().getBoolean(ct.toString().toLowerCase() + ".enabled.auto-ban.cancel.threshold.packet-check.event-check");
				boolean doPacketCheck = getConfig().getBoolean(ct.toString().toLowerCase() + ".enabled.auto-ban.cancel.threshold.packet-check");

				getConfigUtil().setRegenMode(doEventCheck, doPacketCheck);

			}

			getConfigUtil().setCheckEnabled(ct, enabled);
			getConfigUtil().setCheckBannable(ct, ban);
			getConfigUtil().setCheckCancellable(ct, cancel);
			getConfigUtil().setCheckThreshold(ct, threshold);
		}

	}

	public void onDisable() {

		// clean-up.

		for (Player player : Bukkit.getOnlinePlayers()) {
			User user = getUserManager().getUser(player.getUniqueId());
			user.setFieldsNull();
			user = null;
		}

		getConfigUtil().cleanup();
		packetManager.removePacketListeners(this);

		plugin = null;
		packetManager = null;

	}

	// Register events.

	public void registerEvents() {
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	}

	// Register commands.
	// TODO: Add a commmand to notify staff?

	public void registerCommands() {
		getCommand("management").setExecutor(new Management());
		getCommand("cancelban").setExecutor(new CCBan());
	}

	// Get the plugin.

	public static Plugin getPlugin() {
		return plugin;
	}

	// Get user manager.

	public static UserManager getUserManager() {
		return new UserManager();
	}

	// Get check manager.

	public static CheckManager getCheckManager() {
		return new CheckManager();
	}

	// get Prycia manager.

	public static PryciaManager getManager() {
		return new PryciaManager();
	}

	// get ProtocolLib manager.

	public static ProtocolManager getProtocolManager() {
		return packetManager;
	}

	public static PacketManager getPacketManager() {
		return new PacketManager();
	}

	public static Config getConfigUtil() {
		return new Config();
	}

}
