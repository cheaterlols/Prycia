package me.vrekt.prycia.checks.moving;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.vrekt.prycia.checks.Category;
import me.vrekt.prycia.checks.Check;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.user.User;
import me.vrekt.prycia.util.Utilities;

public class Speed extends Check {

	public Speed() {
		super(Category.MOVING, CheckType.SPEED, "Checks if the player is moving to quickly.");
	}

	public boolean check(User user, Location to, Location from) {
		Player player = user.getPlayer();

		Location prev = user.getPreviousLocation();

		double hDist = from.distanceSquared(to);
		double hDistAction = user.isBlocking() ? prev.distanceSquared(user.getBlockingLocation()) : 0;
		double vDistDown = to.getY() - from.getY();

		// TODO: Fix? If your fast enough this can be bypassed, all you need is
		// a speed bypass for noslow to work.
		// Not that big of a deal. but should probably work on this.

		if (hDistAction > 0.078 && hDistAction < 0.080 && hDist > 0.0076) {
			executeActions(user.getPlayer(), user, " hDistAction to small, " + hDistAction + " (checked from hDist: " + hDist);
			return true;
		} else if (!user.isSprinting() && hDistAction > 0.043 && hDistAction < 0.047 && hDist > 0.0076) {
			executeActions(user.getPlayer(), user, " hDistAction to small, " + hDistAction + " (checked from hDist: " + hDist);
			return true;
		}

		player.sendMessage("down: " + vDistDown);

		// TODO: Improve later on currently flags when sprint jumping then
		// stopping.
		// Can be simply fixed but I need to get more deltas and other movement
		// related data so I can implement it without breaking other checks
		// just commenting this for the commit to github.
		
		// Simple speed check if the distance they moved was to big.
		if (user.isSprinting() || player.isSprinting()) {
			// Check if were not actually sprint jumping.
			if (Utilities.isOnGround(from) && Utilities.isOnGround(to)) {
				// were not.
				// Check for a negative lift here, speed causes flags when
				// sprint-jumping then stopping.
				if (vDistDown > .001 && !(vDistDown > .490)) {
					if (hDist > 0.079) {
						executeActions(player, user, "hDist to high: " + hDist + " DOWN: " + vDistDown);
						return true;
					}

				} else if (vDistDown < 0) {
					// we are going down, ignore it for now...
					// TODO: this could cause a bypass, not sure what goes here.
				} else if (vDistDown == 0) {
					// Check again here..
					double y = from.getBlockY() - to.getBlockY();
					if (hDist > 0.079 && !(y < 0)) {
						executeActions(player, user, "hDist to high: " + hDist + " DOWN: " + vDistDown);
						return true;
					}
				}
			} else {
				// we are, increase the hAllowed
				// we can go up to about .55 here sprint jumping.
				if (hDist > .56) {
					executeActions(player, user, "hDist to high:1 " + hDist);
					return true;
				}
			}
		}
		user.getPlayer().sendMessage("DIST : " + hDist);

		return false;

	}

}
