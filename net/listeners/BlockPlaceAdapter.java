package me.vrekt.prycia.net.listeners;

import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayClientBlockPlace;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import me.vrekt.prycia.Prycia;
import me.vrekt.prycia.net.Adapter;
import me.vrekt.prycia.user.User;
import me.vrekt.prycia.util.Utilities;

public class BlockPlaceAdapter extends Adapter {

	public BlockPlaceAdapter() {
		super(PacketType.Play.Client.BLOCK_PLACE);
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {
		Player player = event.getPlayer();
		PacketContainer packet = event.getPacket();
		WrapperPlayClientBlockPlace packetInfo = new WrapperPlayClientBlockPlace(packet);
		User user = Prycia.getUserManager().getUser(player.getUniqueId());

		boolean isSword = packetInfo.getHeldItem() != null ? Utilities.isSword(packetInfo.getHeldItem()) : false;
		boolean isFood = packetInfo.getHeldItem() != null ? packetInfo.getHeldItem().getType().isEdible() : false;
		boolean itemCheck = isFood || isSword;

		if (packetInfo.getHeldItem() != null && itemCheck) {
			user.setIsBlocking(true, player.getLocation());
		}

	}

	@Override
	public void onPacketSending(PacketEvent event) {
	}

}
