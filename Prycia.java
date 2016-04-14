package me.vrekt.prycia;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import me.vrekt.prycia.commands.CCBan;
import me.vrekt.prycia.commands.Management;
import me.vrekt.prycia.commands.PReload;
import me.vrekt.prycia.events.PlayerListener;
import me.vrekt.prycia.manage.CheckManager;
import me.vrekt.prycia.manage.AdapterManager;
import me.vrekt.prycia.manage.PryciaManager;
import me.vrekt.prycia.manage.UserManager;
import me.vrekt.prycia.net.BaseListener;
import me.vrekt.prycia.user.User;
import me.vrekt.prycia.util.Config;

public class Prycia extends JavaPlugin {

	private static ProtocolManager packetManager;
	private static AdapterManager adapterManager;
	private static CheckManager checkManager;
	private static UserManager userManager;
	private static PryciaManager manager;
	private static Config config;
	private static Plugin plugin;

	public void onEnable() {
		getLogger().info(": - Prycia is now enabled.");

		// Add reference to everything.

		saveDefaultConfig();

		plugin = this;
		packetManager = ProtocolLibrary.getProtocolManager();
		config = new Config();
		checkManager = new CheckManager();
		userManager = new UserManager();

		manager = new PryciaManager();
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			new User(player);
			userManager.add(new User(player));
		}

		// Register various things.

		this.registerCommands();
		this.registerEvents();

		adapterManager = new AdapterManager();
		new BaseListener().addListeners(this, packetManager);

		// Read the config and set all values.

		getConfigUtil().writeValues();

	}

	public void onDisable() {

		// clean-up.

		for (Player player : Bukkit.getOnlinePlayers()) {
			User user = getUserManager().getUser(player.getUniqueId());
			user.clearData();
			user = null;
		}

		getConfigUtil().cleanup();
		packetManager.removePacketListeners(this);

		plugin = null;
		packetManager = null;
		adapterManager = null;
		checkManager = null;
		userManager = null;

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
		getCommand("preload").setExecutor(new PReload());
	}

	// Get the plugin.

	public static Plugin getPlugin() {
		return plugin;
	}

	// Get user manager.

	public static UserManager getUserManager() {
		return userManager;
	}

	// Get check manager.

	public static CheckManager getCheckManager() {
		return checkManager;
	}

	// get Prycia manager.

	public static PryciaManager getManager() {
		return manager;
	}

	// get ProtocolLib manager.

	public static ProtocolManager getProtocolManager() {
		return packetManager;
	}

	public static AdapterManager getAdapterManager() {
		return adapterManager;
	}

	public static Config getConfigUtil() {
		return config;
	}

}
