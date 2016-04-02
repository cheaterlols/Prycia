package me.vrekt.prycia.manage;

import java.util.ArrayList;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;

import me.vrekt.prycia.net.Adapter;
import me.vrekt.prycia.net.listeners.BlockDigAdapter;
import me.vrekt.prycia.net.listeners.BlockPlaceAdapter;
import me.vrekt.prycia.net.listeners.EntityActionAdapter;
import me.vrekt.prycia.net.listeners.FlyingAdapter;
import me.vrekt.prycia.net.listeners.PositionAdapter;
import me.vrekt.prycia.net.listeners.UseEntityAdapter;

public class PacketManager {

	private static ArrayList<Adapter> allListeners = new ArrayList<Adapter>();

	public PacketManager() {
		addListener(new PositionAdapter());
		addListener(new UseEntityAdapter());
		addListener(new FlyingAdapter());
		addListener(new EntityActionAdapter());
		addListener(new BlockPlaceAdapter());
		addListener(new BlockDigAdapter());
	}

	public Adapter getAdapter(Class clazz) {
		for (Adapter a : allListeners) {
			if (a.getClass().equals(clazz)) {
				return a;
			}
		}
		return null;
	}

	public void fireAdapter(PacketType pt, PacketEvent event) {
		for (Adapter a : allListeners) {
			if (a.getType().equals(pt)) {
				a.onPacketReceiving(event);
				a.onPacketSending(event);
			}
		}
	}

	public void addListener(Adapter a) {
		allListeners.add(a);
	}
}
