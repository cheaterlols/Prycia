package me.vrekt.prycia.checks.fight;

import com.comphenix.protocol.PacketType;

import me.vrekt.prycia.checks.Category;
import me.vrekt.prycia.checks.Check;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.user.User;

public class Regeneration extends Check {

	public Regeneration() {
		super(Category.FIGHT, CheckType.REGENERATION, "Checks if the player is regenerating health faster then normal.");
	}

	public boolean check(User user) {
		long diff = System.currentTimeMillis() - user.getLastRegenerationTime();

		PacketType pt = PacketType.Play.Client.FLYING;
		long time = System.currentTimeMillis() - user.getLastPacketTime(pt);
		long packets = user.getPacketAmounts(pt);

		if (diff < 3400 || packets > 85 && !(diff > 3350) && time < 200) {
			executeActions(user.getPlayer(), user, "Regeneration time to fast. diff: " + diff + " time: " + time + " packets: " + packets);
			user.setPacketAmounts(pt, 0);
			return true;
		}

		user.setPacketAmounts(pt, 0);

		return false;
	}

}
