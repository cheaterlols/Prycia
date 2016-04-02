package me.vrekt.prycia.net.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayClientFlying;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import me.vrekt.prycia.Prycia;
import me.vrekt.prycia.checks.fight.Regeneration;
import me.vrekt.prycia.net.Adapter;
import me.vrekt.prycia.user.User;

public class FlyingAdapter extends Adapter {

	public FlyingAdapter() {
		super(PacketType.Play.Client.FLYING);
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {
		Player player = event.getPlayer();
		PacketContainer packet = event.getPacket();
		User user = Prycia.getUserManager().getUser(player.getUniqueId());
		WrapperPlayClientFlying packetFlying = new WrapperPlayClientFlying(packet);

		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) {
			return;
		}

		boolean onGround = packetFlying.getOnGround();
		Regeneration checkInstance = (Regeneration) Prycia.getCheckManager().getCheck(Regeneration.class);

		if (onGround) {

			if (System.currentTimeMillis() - user.getLastPacketTime(event.getPacketType()) > 3000 || user.getLastPacketTime(event.getPacketType()) == 0) {
				user.setLastPacketTime(event.getPacketType(), System.currentTimeMillis());
				user.setLastPacketAmount(event.getPacketType(), user.getPacketAmount(event.getPacketType()));
			}

			user.setPacketTime(event.getPacketType(), System.currentTimeMillis());
			user.setPacketAmount(event.getPacketType(), user.getPacketAmount(event.getPacketType()) + 1);

			if (user.getPacketAmount(event.getPacketType()) - user.getLastPacketAmount(event.getPacketType()) > 20) {
				checkInstance.check(user, true);
			}

		}

		if (user.getPacketAmount(event.getPacketType()) > 400) {
			// Reset data after the packets start to accumlate.
			// TODO: Possibly? could cause a bypass by spamming
			// packets that they build up quickly over 400 and data
			// is reset.
			user.setPacketAmount(event.getPacketType(), 0);
			user.setLastPacketAmount(event.getPacketType(), 0);
			user.setPacketTime(event.getPacketType(), 0);
			user.setLastPacketTime(event.getPacketType(), 0);
		}

	}

	@Override
	public void onPacketSending(PacketEvent event) {
	}

}
