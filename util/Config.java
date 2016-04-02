package me.vrekt.prycia.util;

import java.util.ArrayList;
import java.util.HashMap;

import me.vrekt.prycia.checks.CheckType;

public class Config {

	private static ArrayList<CheckType> checksEnabled = new ArrayList<CheckType>();
	private static ArrayList<CheckType> checksBannable = new ArrayList<CheckType>();
	private static ArrayList<CheckType> checksCancellable = new ArrayList<CheckType>();
	private static HashMap<CheckType, Integer> checkThreshold = new HashMap<CheckType, Integer>();
	private static boolean regenEventCheck;
	private static boolean regenPacketCheck;

	public boolean isCheckEnabled(CheckType ct) {
		return checksEnabled.contains(ct);
	}

	public boolean isCheckBannable(CheckType ct) {
		return checksBannable.contains(ct);
	}

	public boolean willCheckCancel(CheckType ct) {
		return checksCancellable.contains(ct);
	}

	public int getThreshold(CheckType ct) {
		return checkThreshold.containsKey(ct) ? checkThreshold.get(ct) : 0;
	}

	public void setCheckEnabled(CheckType ct, boolean enabled) {
		if (enabled) {
			checksEnabled.add(ct);
		} else {
			if (checksEnabled.contains(ct)) {
				checksEnabled.remove(ct);
			}
		}
	}

	public void setCheckBannable(CheckType ct, boolean ban) {
		if (ban) {
			checksBannable.add(ct);
		} else {
			if (checksBannable.contains(ct)) {
				checksBannable.remove(ct);
			}
		}
	}

	public void setCheckCancellable(CheckType ct, boolean cancel) {
		if (cancel) {
			checksCancellable.add(ct);
		} else {
			if (checksCancellable.contains(ct)) {
				checksCancellable.remove(ct);
			}
		}
	}

	public void setCheckThreshold(CheckType ct, int threshold) {
		checkThreshold.put(ct, threshold);
	}

	public void cleanup() {
		checksEnabled.clear();
		checksBannable.clear();
		checksCancellable.clear();
		checkThreshold.clear();
	}

	public void setRegenMode(boolean val, boolean val2) {
		regenEventCheck = val;
		regenPacketCheck = val2;
	}

	public boolean getRegenState(String mode) {
		if (mode.equalsIgnoreCase("event")) {
			return regenEventCheck;
		} else if (mode.equalsIgnoreCase("packet")) {
			return regenPacketCheck;
		}
		return false;
	}

}
