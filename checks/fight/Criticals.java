package me.vrekt.prycia.checks.fight;

import java.util.ArrayList;
import java.util.List;

import me.vrekt.prycia.checks.Category;
import me.vrekt.prycia.checks.Check;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.user.User;

public class Criticals extends Check {

	private List<Double> patterns = new ArrayList<Double>();

	public Criticals() {
		super(Category.FIGHT, CheckType.CRITICALS, "Checks if the player is using forced criticals.");
	}

	public boolean check(User user, double difference) {

		user.addShouldCheck(CheckType.CRITICALS, false);
		patterns.add(difference);

		if (hasDuplicates()) {
			executeActions(user.getPlayer(), user, "reoccuring values. " + difference);
			patterns.clear();
			setDidFail(true);
		}

		if (difference > 1.002) {
			executeActions(user.getPlayer(), user, "yFallDiff was to high. " + difference);
			patterns.clear();
			setDidFail(true);
		}

		if (difference < 0.014 || difference > 0.048 && difference < 0.050) {
			executeActions(user.getPlayer(), user, "yDiff was to low. " + difference);
			patterns.clear();
			setDidFail(true);
		}

		if (patterns.size() > 4) {
			patterns.clear();
		}
		setDidFail(false);

		return false;
	}

	private boolean hasDuplicates() {
		List<Double> usedValues = new ArrayList<Double>();
		int times = 0;
		for (double i : patterns) {
			if (usedValues.contains(i)) {
				times++;

				if (times > 3) {
					return true;
				}
			}
			usedValues.add(i);
		}
		return false;

	}

}
