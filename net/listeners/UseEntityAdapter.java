package me.vrekt.prycia.net.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;

import me.vrekt.prycia.net.Adapter;

public class UseEntityAdapter extends Adapter {

	public UseEntityAdapter() {
		super(PacketType.Play.Client.USE_ENTITY);
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {
		

	}

	@Override
	public void onPacketSending(PacketEvent event) {
	}

}
