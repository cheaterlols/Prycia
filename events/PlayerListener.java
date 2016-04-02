package me.vrekt.prycia.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.vrekt.prycia.Prycia;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.checks.fight.Reach;
import me.vrekt.prycia.checks.fight.Regeneration;
import me.vrekt.prycia.checks.inventory.FastConsume;
import me.vrekt.prycia.checks.moving.Speed;
import me.vrekt.prycia.user.User;
import me.vrekt.prycia.util.Utilities;
import net.md_5.bungee.api.ChatColor;

public class PlayerListener implements Listener {

	private int last = 0;

	// When the player moves we check many things here.

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerMove(PlayerMoveEvent event) {

		Player player = event.getPlayer();
		User user = Prycia.getUserManager().getUser(player.getUniqueId());
		Location from = event.getFrom();
		Location to = event.getTo();

		user.setPlayer(player);
		user.setPreviousLocation(from);

		if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
			// Do speed checks here and other movement memes.
			if (!Utilities.isMovingExempt(player)) {
				Speed speedCheck = (Speed) Prycia.getCheckManager().getCheck(Speed.class);
				speedCheck.check(user, to, from);
			}
		}

	}

	// Add new user on join.

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		new User(event.getPlayer());
		Prycia.getUserManager().add(new User(event.getPlayer()));
	}

	// Cleanup player objects and user objects.

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		User user = Prycia.getUserManager().getUser(event.getPlayer().getUniqueId());
		user.setFieldsNull();
		Prycia.getUserManager().remove(user);
		user = null;
	}

	@EventHandler
	public void onKick(PlayerKickEvent event) {
		User user = Prycia.getUserManager().getUser(event.getPlayer().getUniqueId());
		user.setFieldsNull();
		Prycia.getUserManager().remove(user);
		user = null;
	}

	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		User user = Prycia.getUserManager().getUser(event.getPlayer().getUniqueId());
		user.setLastTeleportTime(System.currentTimeMillis());
	}

	// Used for GUI handling.
	// TODO: Cleanup.

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {

		User user = Prycia.getUserManager().getUser(e.getWhoClicked().getUniqueId());

		if (e.getClickedInventory() == null || !e.getClickedInventory().getName().contains("Prycia")) {
			return;
		}

		if (e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) || e.getCurrentItem().getType().equals(Material.GREEN_RECORD)) {
			e.setCancelled(true);
		}

		if (e.getCurrentItem().getType() == Material.BOOK) {
			e.setCancelled(true);
			Inventory gui = Bukkit.createInventory(null, 18, "Prycia - Checks");
			ItemStack checkItem = new ItemStack(Material.GREEN_RECORD, 1);
			ItemMeta meta = checkItem.getItemMeta();

			for (CheckType ct : CheckType.values()) {
				String name = ct.toString();
				List<String> lore = new ArrayList<String>();
				lore.add(Prycia.getCheckManager().getDescription(ct));
				meta.setDisplayName(ChatColor.RED + name);
				meta.setLore(lore);
				meta.addItemFlags(ItemFlag.values());
				checkItem.setItemMeta(meta);
				gui.setItem(last++, checkItem);
				if (last == CheckType.values().length) {
					last = 0;
				}
			}

			e.getWhoClicked().openInventory(gui);

		} else if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
			e.setCancelled(true);
			Prycia.getManager().isEnabled = !Prycia.getManager().isEnabled();

			Inventory gui = e.getInventory();
			ItemStack item = gui.getItem(13);
			ItemMeta meta = item.getItemMeta();
			String text = Prycia.getManager().isEnabled() ? ChatColor.GREEN + "enabled." : ChatColor.RED + "disabled.";
			meta.setDisplayName(ChatColor.RED + "Enable or disable the anticheat, currently " + text);
			item.setItemMeta(meta);

			if (Prycia.getManager().isEnabled()) {
				// TODO: remove packet listeners here.
			} else {
				// TODO: Enable packet lsiteners here.
			}

		} else if (e.getCurrentItem().getType().equals(Material.PAPER)) {
			e.setCancelled(true);
			Prycia.getManager().isDebuggingEnabled = !Prycia.getManager().isDebuggingEnabled();
			Inventory gui = e.getInventory();
			ItemStack item = gui.getItem(16);
			ItemMeta meta = item.getItemMeta();
			String text = Prycia.getManager().isDebuggingEnabled() ? ChatColor.GREEN + "enabled." : ChatColor.RED + "disabled.";
			meta.setDisplayName(ChatColor.RED + "Enable or disable debug logging, currently " + text);
			item.setItemMeta(meta);
			user.setWillDebug(Prycia.getManager().isDebuggingEnabled());
		}

	}

	// Once an item is consumed then we check for fast consume.

	@EventHandler
	public void onConsume(PlayerItemConsumeEvent event) {
		FastConsume checkInstance = (FastConsume) Prycia.getCheckManager().getCheck(FastConsume.class);
		User user = Prycia.getUserManager().getUser(event.getPlayer().getUniqueId());
		if (checkInstance.check(user)) {
			event.setCancelled(true);
		}
	}

	// If the item is edible then set there start time to now. Once consumed we
	// can check the time.

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
			if (event.getItem().getType().isEdible()) {
				User user = Prycia.getUserManager().getUser(event.getPlayer().getUniqueId());
				user.setLastUseTime(System.currentTimeMillis());
			}
		}
	}

	// Check for regeneration here.

	@EventHandler
	public void onRegain(EntityRegainHealthEvent event) {

		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		if (!(event.getRegainReason() == RegainReason.SATIATED)) {
			return;
		}

		User user = Prycia.getUserManager().getUser(event.getEntity().getUniqueId());
		Regeneration checkInstance = (Regeneration) Prycia.getCheckManager().getCheck(Regeneration.class);
		boolean result = checkInstance.check(user, false);
		event.setCancelled(result);

		user.setLastRegenerationTime(System.currentTimeMillis());

	}

	@EventHandler
	public void onVelocity(PlayerVelocityEvent event) {
		User user = Prycia.getUserManager().getUser(event.getPlayer().getUniqueId());

		user.setVelocity(event.getVelocity());

	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {

		if (event.getDamager() instanceof Player) {
			User user = Prycia.getUserManager().getUser(event.getDamager().getUniqueId());
			Reach checkInstance = (Reach) Prycia.getCheckManager().getCheck(Reach.class);
			if (checkInstance.check((Player) event.getDamager(), user, event.getDamager().getLocation(), event.getEntity())) {
				event.setCancelled(true);
			}
		}

	}

}
