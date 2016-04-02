package me.vrekt.prycia.net.listeners;

import org.bukkit.entity.Player;

import com.comphenix.packetwrapper.WrapperPlayClientUseEntity;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;

import me.vrekt.prycia.Prycia;
import me.vrekt.prycia.checks.CheckType;
import me.vrekt.prycia.net.Adapter;
import me.vrekt.prycia.user.User;
import me.vrekt.prycia.util.Utilities;

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

		if (attackPacket.getType() == EntityUseAction.ATTACK) {
			if (Utilities.canCrit(player) && !user.shouldCheck(CheckType.CRITICALS)) {
				user.addShouldCheck(CheckType.CRITICALS, true);
			}
		}

	}

	@Override
	public void onPacketSending(PacketEvent event) {
	}

}
