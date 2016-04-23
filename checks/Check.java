package me.vrekt.prycia.checks;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.vrekt.prycia.Prycia;
import me.vrekt.prycia.user.User;
import me.vrekt.prycia.util.Utilities;
import net.md_5.bungee.api.ChatColor;

public abstract class Check {

	private Category cc;
	private CheckType check;
	private String description;
	private boolean shouldCancel;
	private boolean isBannable;
	private boolean isEnabled;
	private boolean result;
	private int threshold;

	public Check(Category cc, CheckType check, String description) {
		this.cc = cc;
		this.check = check;
		this.description = description;
		this.shouldCancel = Prycia.getConfigUtil().willCheckCancel(getCheck());
		this.isBannable = Prycia.getConfigUtil().isCheckBannable(getCheck());
		this.isEnabled = Prycia.getConfigUtil().isCheckEnabled(getCheck());
		this.threshold = Prycia.getConfigUtil().getThreshold(getCheck());
	}

	public Category getCategory() {
		return cc;
	}

	public CheckType getCheck() {
		return check;
	}

	public String getDesc() {
		return description;
	}

	public boolean willCancel() {
		return shouldCancel;
	}

	public boolean isBannable() {
		return isBannable;
	}

	public int getThreshold() {
		return threshold;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public boolean isMet(User user) {
		return user.getViolationLevel(getCheck()) >= threshold;
	}

	public boolean didFail() {
		return result;
	}

	public void setDidFail(boolean result) {
		this.result = result;
	}

	public void setEnabled(boolean enabled){
		this.isEnabled = enabled;
	}
	
	public void executeActions(Player player, User user, String tag) {

		boolean debugLogging = user.getWillDebug();
		int currentThreshold = user.getViolationLevel(getCheck());

		String checkName = getCheck().toString().toLowerCase();
		checkName = StringUtils.capitalize(checkName);

		if (getCheck().equals(CheckType.FAST_CONSUME)) {
			// Fix the _ and format it to FastConsume.
			checkName = checkName.replace("_", "");
			checkName = checkName.replace("c", "C");
		}

		if (debugLogging) {
			Utilities.logMessage(player.getName() + " could possibly be using: §3" + checkName + "\n " + ChatColor.GRAY + tag);
		} else {
			Utilities.logMessage(player.getName() + " could possible be using: §3" + checkName);
		}

		if (currentThreshold == 0) {
			user.setViolationLevel(getCheck(), 1);
		} else if (currentThreshold != 0 && !Prycia.getManager().isInBanQueue(user)) {
			user.setViolationLevel(getCheck(), currentThreshold + 1);
		}

		if (isMet(user) && !Prycia.getManager().isInBanQueue(user)) {
			user.setViolationLevel(getCheck(), 0);
			// Prycia.getManager().scheduleBan(user);
		}

		if (getCategory().equals(Category.MOVING)) {
			player.teleport(user.getValidLocation(), TeleportCause.PLUGIN);
		}

	}

}
