package me.vrekt.prycia.checks.moving;

import org.bukkit.Location;

import me.vrekt.prycia.checks.Category;
import me.vrekt.prycia.checks.Check;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.user.User;

public class Phase extends Check {

	public Phase() {
		super(Category.MOVING, CheckType.PHASE, "Checks if the player is clipping inside blocks.");
	}

	public boolean check(User user, Location current) {

		return false;

	}

}
