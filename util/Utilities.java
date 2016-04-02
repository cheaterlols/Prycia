package me.vrekt.prycia.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import me.vrekt.prycia.util.permission.Permission;

public class Utilities {

	// Log a message to the staff.
	// TODO: Add a command to notify all staff?

	public static void logMessage(String message) {

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission("prycia.staff") || player.hasPermission("prycia.notify")) {
				player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "Prycia" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + message);
			}
		}

	}

	// Used to check if the player is onground. p.isOnGround or
	// entity.isOnGround can be faked.
	// TODO: Add more checks to this.

	public static boolean isOnGround(Location location) {
		return location.getBlock().getType().isSolid() || location.getBlockY() == location.getY() && !inLiquid(location);
	}

	// Is the player able to bypass moving checks?
	// TODO: Add permissions.

	public static boolean isMovingExempt(Player player) {
		return player.isFlying() || player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR;
	}

	// Get a squared distance from the last to the current.

	public static double gethDistSquared(Location first, Location second) {
		double dX = second.getX() - first.getX();
		double dZ = second.getZ() - first.getZ();
		double distDiff = dX * dX + dZ * dZ;
		return distDiff;
	}

	// Get a squared 3D distance from last to the current.

	public static double get3DSquared(Location first, Location second) {
		double dX = first.getX() - second.getX();
		double dY = first.getY() - second.getY();
		double dZ = first.getZ() - second.getZ();
		return dX * dX + dY * dY + dZ * dZ;
	}

	// Get a 3D distance from last to current.
	// WARNING: PERFORMANCE HEAVY!!!

	public static double getDistance3D(Location first, Location second) {
		double distDiffX = (second.getX() - first.getX()) * (second.getX() - first.getX());
		double distDiffY = (second.getY() - first.getY()) * (second.getY() - first.getY());
		double distDiffZ = (second.getZ() - first.getZ()) * (second.getZ() - first.getZ());
		double distSquared = Math.sqrt(distDiffX + distDiffY + distDiffZ);
		return Math.abs(distSquared);
	}

	// Get the y distance from last to the current.
	// ALSO PERFORMANCE HEAVY!
	// TODO: Add a squared method.

	public static double getYDist(Location first, Location second) {
		double finalDist = 0D;
		double distDiffY = (second.getY() - first.getY()) * (second.getY() - first.getY());
		double distSquared = Math.sqrt(distDiffY);
		finalDist = Math.abs(distSquared);
		return finalDist;
	}

	public static double getyDistSquared(Location first, Location second) {
		double dY = second.getY() - first.getY();
		double distDiff = dY * dY;
		return distDiff;
	}

	// Check if the player has a permission.

	public static boolean hasPermission(Player player, Permission permission) {
		return player.hasPermission(permission.toString().toLowerCase());
	}

	public static boolean isOnClimbable(Location location) {
		return location.getBlock().getType().equals(Material.LADDER) || location.getBlock().getType().equals(Material.VINE);
	}

	public static boolean inLiquid(Location location) {
		return location.getBlock().isLiquid() || location.getBlock().getRelative(BlockFace.UP).isLiquid();
	}

	public static boolean isFood(ItemStack item) {
		return item != null && item.getType().isEdible();
	}

	public static boolean isSword(ItemStack item) {
		if (item != null) {
			return item.getType().equals(Material.WOOD_SWORD) || item.getType().equals(Material.STONE_SWORD) || item.getType().equals(Material.IRON_SWORD) || item.getType().equals(Material.GOLD_SWORD)
					|| item.getType().equals(Material.DIAMOND_SWORD);
		}
		return false;
	}

	public static boolean canCrit(Player player) {
		return !isOnClimbable(player.getLocation()) && !player.isInsideVehicle() && !inLiquid(player.getLocation()) && !player.hasPotionEffect(PotionEffectType.BLINDNESS);

	}

}
