package me.vrekt.prycia.manage;

import java.util.HashMap;

import me.vrekt.prycia.checks.Check;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.checks.fight.Criticals;
import me.vrekt.prycia.checks.fight.Reach;
import me.vrekt.prycia.checks.fight.Regeneration;
import me.vrekt.prycia.checks.inventory.FastConsume;
import me.vrekt.prycia.checks.moving.Phase;
import me.vrekt.prycia.checks.moving.Speed;
import me.vrekt.prycia.checks.moving.NoSlowdown;

public class CheckManager {

	private HashMap<Class, Check> allChecks = new HashMap<Class, Check>();

	// Add all the checks.
	// TODO: Develop new system to register and add checks.

	public CheckManager() {
		addCheck(new FastConsume());
		addCheck(new Regeneration());
		addCheck(new Criticals());
		addCheck(new Reach());
		addCheck(new Phase());
		addCheck(new Speed());
		addCheck(new NoSlowdown());
	
		
	}

	// Get the check thats related to the check-type.

	public Check getCheck(Class clazz) {
		for (Check check : allChecks.values()) {
			if (check.getClass().equals(clazz)) {
				return check;
			}
		}
		return null;
	}

	// Get the description of a check.

	public String getDescription(CheckType ct) {
		for (Check check : allChecks.values()) {
			if (check.getCheck().equals(ct)) {
				return check.getDesc();
			}
		}
		return "(No Description)";
	}

	public boolean getLastCheckResult(Class clazz) {
		for (Check check : allChecks.values()) {
			if (check.getClass().equals(clazz)) {
				return check.didFail();
			}
		}
		return false;
	}
	
	public void setLastCheckResult(Class clazz, boolean result){
		for (Check check : allChecks.values()) {
			if (check.getClass().equals(clazz)) {
				check.setDidFail(result);
			}
		}
	}

	private void addCheck(Check check) {
		allChecks.put(check.getClass(), check);
	}

	public HashMap<Class, Check> getAllChecks() {
		return allChecks;
	}
	
}
