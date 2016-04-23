package me.vrekt.prycia.net.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;

import me.vrekt.prycia.net.Adapter;

public class PositionAdapter extends Adapter {

	public PositionAdapter() {
		super(PacketType.Play.Client.POSITION);
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {

	}

	@Override
	public void onPacketSending(PacketEvent event) {
	}

}
