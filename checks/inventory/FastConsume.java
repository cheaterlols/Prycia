package me.vrekt.prycia.checks.inventory;

import me.vrekt.prycia.checks.Category;
import me.vrekt.prycia.checks.Check;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.user.User;

public class FastConsume extends Check {

	public FastConsume() {
		super(Category.INVENTORY, CheckType.FAST_CONSUME, "Checks if the player is eating to quickly.");
	}

	public boolean check(User user) {
		long diff = System.currentTimeMillis() - user.getLastUseTime();

		if (diff < 1000) {
			executeActions(user.getPlayer(), user, " Use time was to fast. your time: " + diff + " allowed: 1000");
			return true;
		}

		return false;
	}

}
