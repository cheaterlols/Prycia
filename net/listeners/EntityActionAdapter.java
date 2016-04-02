package me.vrekt.prycia.net.listeners;

import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayClientEntityAction;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerAction;

import me.vrekt.prycia.Prycia;
import me.vrekt.prycia.net.Adapter;
import me.vrekt.prycia.user.User;

public class EntityActionAdapter extends Adapter {

	public EntityActionAdapter() {
		super(PacketType.Play.Client.ENTITY_ACTION);
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {

		Player player = event.getPlayer();
		PacketContainer packet = event.getPacket();
		WrapperPlayClientEntityAction packetInfo = new WrapperPlayClientEntityAction(packet);
		User user = Prycia.getUserManager().getUser(player.getUniqueId());

		if (packetInfo.getAction() == PlayerAction.START_SPRINTING) {
			user.setIsSprinting(true);
		} else if (packetInfo.getAction() == PlayerAction.STOP_SPRINTING) {
			user.setIsSprinting(false);
		}

		if (packetInfo.getAction() == PlayerAction.START_SNEAKING) {
			user.setIsSneaking(true);
		} else if (packetInfo.getAction() == PlayerAction.STOP_SNEAKING) {
			user.setIsSneaking(false);
		}

	}

	@Override
	public void onPacketSending(PacketEvent event) {
	}

}
