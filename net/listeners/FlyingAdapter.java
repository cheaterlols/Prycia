package me.vrekt.prycia.net.listeners;

import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayClientFlying;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import me.vrekt.prycia.Prycia;
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

		if (packetFlying.getOnGround()) {
			user.setOnGround(packetFlying.getOnGround());
			// reset data

			user.setPacketTime(event.getPacketType(), System.currentTimeMillis());
			long time = user.getPacketTime(event.getPacketType()) - user.getLastPacketTime(event.getPacketType());

			if (time > 300) {
				user.setLastPacketTime(event.getPacketType(), System.currentTimeMillis());
			}
			user.setPacketAmounts(event.getPacketType(), user.getPacketAmounts(event.getPacketType()) + 1);

		}

	}

	@Override
	public void onPacketSending(PacketEvent event) {
	}

}
