package me.vrekt.prycia.net.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayClientPosition;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import me.vrekt.prycia.Prycia;
import me.vrekt.prycia.net.Adapter;
import me.vrekt.prycia.user.User;
import me.vrekt.prycia.util.Utilities;

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

		Location loc = new Location(player.getWorld(), positionPacket.getX(), positionPacket.getY(), positionPacket.getZ());
		if (user.getPreviousLocation() == null || Utilities.get3DSquared(user.getPreviousLocation(), loc) >= 4) {
			user.setPreviousLocation(loc);
		}

	}

	@Override
	public void onPacketSending(PacketEvent event) {
	}

}
