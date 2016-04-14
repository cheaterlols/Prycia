package me.vrekt.prycia.net.listeners;

import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayClientUseEntity;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

import me.vrekt.prycia.Prycia;
import me.vrekt.prycia.net.Adapter;
import me.vrekt.prycia.user.User;

public class UseEntityAdapter extends Adapter {

	public UseEntityAdapter() {
		super(PacketType.Play.Client.USE_ENTITY);
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {
		Player player = event.getPlayer();
		PacketContainer packet = event.getPacket();
		User user = Prycia.getUserManager().getUser(player.getUniqueId());
		WrapperPlayClientUseEntity attackPacket = new WrapperPlayClientUseEntity(packet);

	}

	@Override
	public void onPacketSending(PacketEvent event) {
	}

}
