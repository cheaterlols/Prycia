package me.vrekt.prycia.checks.moving;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.vrekt.prycia.checks.Category;
import me.vrekt.prycia.checks.Check;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.user.User;
import me.vrekt.prycia.util.BB;
import me.vrekt.prycia.util.Utilities;

public class Phase extends Check {

	public Phase() {
		super(Category.MOVING, CheckType.PHASE, "Checks if the player is clipping inside blocks.");
	}

	public boolean check(User user, Player player, Location from, Location to) {

		if (to.getWorld() != from.getWorld()) {
			return false;
		}

		if (to.distanceSquared(from) > 156) {
			return false;
		}

		if (to.getY() > 256) {
			return false;
		}

		if (hasMovedIntoBlock(user, player, from, to)) {
			executeActions(player, user, "meme");
			return true;
		} else {
			user.setLastValidLocation(from);
		}

		return false;
	}

	// TODO: IMPROVE !! Better phase check soon.
	
	public boolean hasMovedIntoBlock(User user, Player player, Location from, Location to) {
		BB locationBB = new BB(from.toVector(), to.toVector());

		for (int x = locationBB.minX; x <= locationBB.maxX; x++) {
			for (int z = locationBB.minZ; z <= locationBB.maxZ; z++) {
				for (int y = locationBB.minY; y <= locationBB.maxY; y++) {
					Block currentBlock = from.getWorld().getBlockAt(x, y, z);

					if (!currentBlock.getType().isSolid() || Utilities.canPass(currentBlock.getType())) {
						continue;
					}

					return true;
					
				}
			}
		}

		return false;

	}

	public int floor_double(double d) {
		int d2 = (int) d;
		return d < d2 ? d2 - 1 : d2;
	}

}
