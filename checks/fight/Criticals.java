package me.vrekt.prycia.checks.fight;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.vrekt.prycia.Prycia;
import me.vrekt.prycia.checks.Category;
import me.vrekt.prycia.checks.Check;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.user.User;
import me.vrekt.prycia.util.Utilities;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class Criticals extends Check {

	public Criticals() {
		super(Category.FIGHT, CheckType.CRITICALS, "Checks if the player is using forced criticals.");
	}

	// NOTE: NOT DONE. I have a better method to check it, just havent had time to implement it.
	
	public boolean check(Player player) {

		EntityPlayer ep = ((CraftPlayer) player).getHandle();
		double fd = ep.fallDistance;

		double altfd = player.getLocation().getY() - fd;
		
		User user = Prycia.getUserManager().getUser(player.getUniqueId());
		Utilities.logMessage("CRIT: " + fd + " : " + altfd);

		if (fd <= 0.06950) {
			executeActions(player, user, "fd to small, fd: " + fd);
			return true;
		}

		if (fd >= 0.078095 && fd < 0.078140) {
			executeActions(player, user, "fd to small, fd: " + fd);
			return true;
		}

		if (fd == 0.5 || fd == 1.0) {
			executeActions(player, user, "fd is at a specific value: " + fd);
			return true;
		}

		return false;
	}

}
