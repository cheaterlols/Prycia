package me.vrekt.prycia.checks.fight;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;

import me.vrekt.prycia.checks.Category;
import me.vrekt.prycia.checks.Check;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.user.User;

public class Regeneration extends Check {

	public Regeneration() {
		super(Category.FIGHT, CheckType.REGENERATION, "Checks if the player is regenerating health faster then normal.");
	}

	public boolean check(User user, boolean packetCheck) {

		if (packetCheck) {
			PacketType packet = PacketType.Play.Client.FLYING;
			Player player = user.getPlayer();
			int currentPackets = user.getPacketAmount(packet);
			int lastPackets = user.getLastPacketAmount(packet);
			int packetDiff = currentPackets - lastPackets;

			long now = System.currentTimeMillis();
			long last = user.getLastPacketTime(packet);
			long timeDiff = now - last;
			
			int ping = ((CraftPlayer) user.getPlayer()).getHandle().ping;
			
			if (packetDiff > 65 && timeDiff < 2300 && !(ping > 180)) {
				executeActions(player, user, " To many packets in short amount of time. packetdiff: " + packetDiff + " timediff:" + timeDiff);
				user.setPacketAmount(packet, 0);
				user.setLastPacketAmount(packet, 0);
				user.setPacketTime(packet, 0);
				user.setLastPacketTime(packet, 0);
			}
		} else {

			if (user.getLastRegenerationTime() == 0) {
				return false;
			}
			long timeDiff = System.currentTimeMillis() - user.getLastRegenerationTime();

			if (timeDiff < 3400) {
				executeActions(user.getPlayer(), user, " Regeneration time to fast.");
				return true;
			}

		}

		return false;
	}

}
