package me.vrekt.prycia.checks.fight;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.vrekt.prycia.checks.Category;
import me.vrekt.prycia.checks.Check;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.user.User;
import me.vrekt.prycia.util.Utilities;

public class Reach extends Check {

	private double CREATIVE_DISTANCE = 36D; // sq dist

	public Reach() {
		super(Category.FIGHT, CheckType.REACH, "Checks if the distance between entities is greater than normal.");
	}

	public boolean check(Player player, User user, Location location, Entity damager) {

		double distance = Utilities.get3DSquared(location, damager.getLocation());

		if (player.getGameMode() == GameMode.CREATIVE) {
			if (distance > CREATIVE_DISTANCE + 0.3) {
				executeActions(player, user, " Dist to far in creative: " + distance);
				return true;
			}
		} else if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {

			double ping = ((CraftPlayer) player).getHandle().ping / 150;
			double allowed = distance - 0.5 + ping;

			double legit = 25.425;
			
			if (allowed > legit) {
				executeActions(player, user, " Dist to far in survival/adventure: " + allowed);
				return true;
			}

		}

		return false;

	}

}
