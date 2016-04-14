package me.vrekt.prycia.checks.moving;

import org.bukkit.Location;

import me.vrekt.prycia.checks.Category;
import me.vrekt.prycia.checks.Check;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.user.User;

public class Speed extends Check {

	public Speed() {
		super(Category.MOVING, CheckType.SPEED, "Checks if the player is moving to quickly.");
	}

	public boolean check(User user, Location to, Location from) {

		return false;

	}

}
