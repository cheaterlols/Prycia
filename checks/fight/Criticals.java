package me.vrekt.prycia.checks.fight;

import me.vrekt.prycia.checks.Category;
import me.vrekt.prycia.checks.Check;
import me.vrekt.prycia.checks.CheckType;

public class Criticals extends Check {

	public Criticals() {
		super(Category.FIGHT, CheckType.CRITICALS, "Checks if the player is using forced criticals.");
	}


}
