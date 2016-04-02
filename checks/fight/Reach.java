package me.vrekt.prycia.checks.fight;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.vrekt.prycia.checks.Category;
import me.vrekt.prycia.checks.Check;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.user.User;
import me.vrekt.prycia.util.Utilities;

public class Reach extends Check {

	private double CREATIVE_DISTANCE = 6D;
	private double LEGIT_DISTANCE = 4.425;

	public Reach() {
		super(Category.FIGHT, CheckType.REACH, "Checks if the distance between entities is greater then normal.");
	}

	public boolean check(Player player, User user, Location location, Entity damager) {
		
		Location thisMove = location.add(0, player.getEyeHeight(), 0);
		LivingEntity tmpEnt = (LivingEntity) damager;
		Location entityMove = damager.getLocation().add(0, tmpEnt.getEyeHeight(), 0);

		double distFrom = Utilities.getDistance3D(thisMove, entityMove);
		
		if (distFrom > LEGIT_DISTANCE && !player.getGameMode().equals(GameMode.CREATIVE)) {
			 executeActions(player, user, " Reach distance to far: (allowed):" + LEGIT_DISTANCE + " your distance: " + distFrom);
			setDidFail(true);
			return true;
		} else if (player.getGameMode().equals(GameMode.CREATIVE)) {
			if (distFrom > CREATIVE_DISTANCE + 0.3) {
				setDidFail(true);
				return true;
			}
		}

		setDidFail(false);
		return false;

	}

}
