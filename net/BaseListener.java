package me.vrekt.prycia.net;

import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import me.vrekt.prycia.Prycia;

public class BaseListener {

	// Base class for listening for packets then firing the packet-event.
	
	public void addListeners(Plugin plugin, ProtocolManager manager) {
		manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.POSITION) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				if (event.getPacketType() == PacketType.Play.Client.POSITION) {
					Prycia.getPacketManager().fireAdapter(event.getPacketType(), event);
				}
			}

		});

		manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.ENTITY_ACTION) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				if (event.getPacketType() == PacketType.Play.Client.ENTITY_ACTION) {
					Prycia.getPacketManager().fireAdapter(event.getPacketType(), event);
				}
			}
		});

		manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.BLOCK_PLACE) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				if (event.getPacketType() == PacketType.Play.Client.BLOCK_PLACE) {
					Prycia.getPacketManager().fireAdapter(event.getPacketType(), event);
				}
			}
		});
		
		manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.FLYING) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				if (event.getPacketType() == PacketType.Play.Client.FLYING) {
					Prycia.getPacketManager().fireAdapter(event.getPacketType(), event);
				}
			}
		});
		
		manager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
					Prycia.getPacketManager().fireAdapter(event.getPacketType(), event);
				}
			}
		});
		
	}

}
