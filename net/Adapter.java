package me.vrekt.prycia.net;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;

public abstract class Adapter {

	private PacketType type;
	
	public Adapter(PacketType type){
		this.type = type;
	}
	
	public abstract void onPacketReceiving(PacketEvent event);
	public abstract void onPacketSending(PacketEvent event);
	
	public PacketType getType() {
		return type;
	}
	
}
