package me.vrekt.prycia.net.listeners;

import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayClientPosition;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import me.vrekt.prycia.Prycia;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.checks.fight.Criticals;
import me.vrekt.prycia.net.Adapter;
import me.vrekt.prycia.user.User;

public class PositionAdapter extends Adapter {

	public PositionAdapter() {
		super(PacketType.Play.Client.POSITION);
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {
		Player player = event.getPlayer();
		PacketContainer packet = event.getPacket();
		User user = Prycia.getUserManager().getUser(player.getUniqueId());
		WrapperPlayClientPosition positionPacket = new WrapperPlayClientPosition(packet);

		double yDiff = positionPacket.getY() - player.getLocation().getBlockY();
		double yFallDiff = player.getFallDistance();

		if (user.shouldCheck(CheckType.CRITICALS) && !positionPacket.getOnGround()) {
			Criticals checkInstance = (Criticals) Prycia.getCheckManager().getCheck(Criticals.class);
			if (yDiff != 0 && !(yDiff < 0)) {
				checkInstance.check(user, yDiff);
			} else if (yDiff == 0) {
				if (yFallDiff != 0) {
					checkInstance.check(user, yFallDiff);
				}
			}
		}

	}

	@Override
	public void onPacketSending(PacketEvent event) {
	}

}
