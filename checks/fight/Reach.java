package me.vrekt.prycia.checks.fight;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.vrekt.prycia.checks.Category;
import me.vrekt.prycia.checks.Check;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.user.User;

public class Reach extends Check {

	public Reach() {
		super(Category.FIGHT, CheckType.REACH, "Checks if the distance between entities is greater than normal.");
	}

	public boolean check(Player player, User user, Location location, Entity damager) {
		return false;
		
	}

}
