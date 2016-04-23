package me.vrekt.prycia.net.listeners;

import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayClientBlockDig;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerDigType;

import me.vrekt.prycia.Prycia;
import me.vrekt.prycia.net.Adapter;
import me.vrekt.prycia.user.User;

public class BlockDigAdapter extends Adapter {

	public BlockDigAdapter() {
		super(PacketType.Play.Client.BLOCK_DIG);
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {
		Player player = event.getPlayer();
		PacketContainer packet = event.getPacket();
		WrapperPlayClientBlockDig packetInfo = new WrapperPlayClientBlockDig(packet);
		User user = Prycia.getUserManager().getUser(player.getUniqueId());

		if (packetInfo.getStatus() == PlayerDigType.RELEASE_USE_ITEM && user.isBlocking()) {
			user.setIsBlocking(false, player.getLocation());
		}

	}

	@Override
	public void onPacketSending(PacketEvent event) {
	}

}
