package me.vrekt.prycia.events;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.vrekt.prycia.Prycia;
import me.vrekt.prycia.checks.Category;
import me.vrekt.prycia.checks.Check;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.checks.fight.Reach;
import me.vrekt.prycia.checks.fight.Regeneration;
import me.vrekt.prycia.checks.inventory.FastConsume;
import me.vrekt.prycia.user.User;
import net.md_5.bungee.api.ChatColor;

public class PlayerListener implements Listener {

	// When the player moves we check many things here.

	private final Inventory categoryInventory = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Prycia Categories.");
	private final Inventory fightInventory = Bukkit.createInventory(null, 9, ChatColor.RED + "Prycia Checks. [Fight]");
	private final Inventory movementInventory = Bukkit.createInventory(null, 9, ChatColor.BLUE + "Prycia Checks. [Movement]");
	private final Inventory invInventory = Bukkit.createInventory(null, 9, ChatColor.GREEN + "Prycia Checks. [Inventory]");

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		User user = Prycia.getUserManager().getUser(player.getUniqueId());
		Location from = event.getFrom();
		Location to = event.getTo();

		user.setPlayer(player);

		if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {

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
		user.clearData();
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
	public void onInventoryClick(InventoryClickEvent event) {

		// Check if the name is related to prycia.

		if (!event.getInventory().getName().contains("Prycia")) {
			return;
		}

		if (event.getCurrentItem() == null) {
			return;
		}

		Player player = (Player) event.getWhoClicked();
		User user = Prycia.getUserManager().getUser(player.getUniqueId());

		ItemStack stackItem = event.getCurrentItem();
		ItemMeta stackMeta = event.getCurrentItem().getItemMeta();
		Material clickedItem = event.getCurrentItem().getType();

		// Check if player clicked a barrier.

		if (clickedItem == Material.BARRIER) {
			event.setCancelled(true);
			Prycia.getManager().setIsEnabled(!Prycia.getManager().isEnabled());
			String text = Prycia.getManager().isEnabled() ? ChatColor.GREEN + "enabled." : ChatColor.RED + "disabled.";
			stackMeta.setDisplayName(ChatColor.RED + "Enable or disable the anticheat, currently " + text);
			stackItem.setItemMeta(stackMeta);

			// TODO: Add the code to where it actually disables the anticheat.
			// TODO: Still more things to do for this to work. gotta code it
			// l8ter on.

		}

		if (stackMeta.getDisplayName().contains("Invalid Action")) {
			event.setCancelled(true);
		}
		
		if(clickedItem == Material.RECORD_8){
			event.setCancelled(true);
		}

		if (clickedItem == Material.PAPER) {
			event.setCancelled(true);
			user.setWillDebug(!user.getWillDebug());
			String text = user.getWillDebug() ? ChatColor.GREEN + "enabled." : ChatColor.RED + "disabled.";
			ItemMeta meta = stackItem.getItemMeta();
			meta.setDisplayName(ChatColor.RED + "Enable or disable debug logging, currently " + text);
			stackItem.setItemMeta(meta);
		}

		if (clickedItem == Material.BOOK) {
			event.setCancelled(true);

			// Populate the next inventory with the categories.

			ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
			ItemStack fight = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
			ItemStack moving = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 11);
			ItemStack inventory = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);

			ItemMeta paneMeta = pane.getItemMeta();
			ItemMeta fightMeta = fight.getItemMeta();
			ItemMeta movingMeta = moving.getItemMeta();
			ItemMeta inventoryMeta = inventory.getItemMeta();

			paneMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Invalid Action.");
			fightMeta.setDisplayName(ChatColor.RED + "Fight");
			movingMeta.setDisplayName(ChatColor.BLUE + "Movement");
			inventoryMeta.setDisplayName(ChatColor.GREEN + "Inventory");

			pane.setItemMeta(paneMeta);
			fight.setItemMeta(fightMeta);
			moving.setItemMeta(movingMeta);
			inventory.setItemMeta(inventoryMeta);

			for (int i = 0; i < categoryInventory.getSize(); i++) {
				categoryInventory.setItem(i, pane);
			}

			categoryInventory.setItem(11, fight);
			categoryInventory.setItem(13, moving);
			categoryInventory.setItem(15, inventory);
			player.openInventory(categoryInventory);
		}

		String categoryName = stackMeta.getDisplayName();
		if (categoryName.contains("Fight")) {
			event.setCancelled(true);
			fightInventory.clear();
			for (Check c : Prycia.getCheckManager().getAllChecks().values()) {
				if (c.getCategory() == Category.FIGHT) {

					String name = c.getCheck().toString().toLowerCase();
					name = StringUtils.capitalize(name);

					ItemStack item = new ItemStack(Material.RECORD_8, 1);
					ItemMeta meta = item.getItemMeta();
					meta.addItemFlags(ItemFlag.values());
					meta.setDisplayName(ChatColor.RED + name);
					List<String> lore = new ArrayList<String>();
					lore.add(ChatColor.DARK_PURPLE + c.getDesc());
					meta.setLore(lore);
					item.setItemMeta(meta);
					
					for (int i = 0; i < 9; i++) {
						if(fightInventory.getItem(i) == null){
							fightInventory.setItem(i, item);
							break;
						}
					}

				}
			}
			player.openInventory(fightInventory);
		}

		if (categoryName.contains("Movement")) {
			event.setCancelled(true);
			movementInventory.clear();
			for (Check c : Prycia.getCheckManager().getAllChecks().values()) {
				if (c.getCategory() == Category.MOVING) {

					String name = c.getCheck().toString().toLowerCase();
					name = StringUtils.capitalize(name);

					ItemStack item = new ItemStack(Material.RECORD_8, 1);
					ItemMeta meta = item.getItemMeta();
					meta.addItemFlags(ItemFlag.values());
					meta.setDisplayName(ChatColor.RED + name);
					List<String> lore = new ArrayList<String>();
					lore.add(ChatColor.DARK_PURPLE + c.getDesc());
					meta.setLore(lore);
					item.setItemMeta(meta);

					for (int i = 0; i < 9; i++) {
						if (movementInventory.getItem(i) == null) {
							movementInventory.setItem(i, item);
							break;
						}
					}

				}
			}
			player.openInventory(movementInventory);
		}

		if (categoryName.contains("Inventory")) {
			event.setCancelled(true);
			invInventory.clear();
			for (Check c : Prycia.getCheckManager().getAllChecks().values()) {
				if (c.getCategory() == Category.INVENTORY) {

					String name = c.getCheck().toString().toLowerCase();
					name = StringUtils.capitalize(name);
					// Check if the check is FastConsume. if so fix the name.

					if (c.getCheck() == CheckType.FAST_CONSUME) {
						name = name.replace("_", "");
						name = name.replace("c", "C");
						StringUtils.capitalize(name);
					}

					ItemStack item = new ItemStack(Material.RECORD_8, 1);
					ItemMeta meta = item.getItemMeta();
					meta.addItemFlags(ItemFlag.values());
					meta.setDisplayName(ChatColor.RED + name);
					List<String> lore = new ArrayList<String>();
					lore.add(ChatColor.DARK_PURPLE + c.getDesc());
					meta.setLore(lore);
					item.setItemMeta(meta);

					for (int i = 0; i < 9; i++) {
						if (invInventory.getItem(i) == null ) {
							invInventory.setItem(i, item);
							break;
						}
					}

				}
			}
			player.openInventory(invInventory);
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
			if (event.getItem() != null && event.getItem().getType().isEdible()) {
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
		boolean result = checkInstance.check(user);
		event.setCancelled(result);

		user.setLastRegenerationTime(System.currentTimeMillis());
		
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
