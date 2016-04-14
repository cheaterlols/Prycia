package me.vrekt.prycia.user;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;

import me.vrekt.prycia.checks.CheckType;

public class User {

	// Everything in this class is pretty self-explanatory. Not going to
	// comment.
	// TODO: Update and remove things. Also maybe change this class a bit.

	private Player player;
	private UUID uuid;
	private Location location, previousLocation, blockingLocation;
	private long lastRegenerationTime, lastTeleportTime, lastUseTime;
	private boolean isSprinting, isBlocking, isSneaking, onGround, willDebug = false;
	private HashMap<CheckType, Integer> violationData = new HashMap<CheckType, Integer>();

	private HashMap<PacketType, Long> lastPacketTimes = new HashMap<PacketType, Long>();
	private HashMap<PacketType, Integer> packetAmounts = new HashMap<PacketType, Integer>();
	private HashMap<PacketType, Long> packetTimes = new HashMap<PacketType, Long>();

	public User(Player player) {
		this.player = player;
		this.uuid = player.getUniqueId();
		this.location = player.getLocation();
		this.previousLocation = player.getLocation();
	}

	public Player getPlayer() {
		return player;
	}

	public UUID getUUID() {
		return uuid;
	}

	public Location getLocation() {
		return location;
	}

	public Location getPreviousLocation() {
		return previousLocation;
	}

	public Location getBlockingLocation() {
		return blockingLocation;
	}

	public long getLastRegenerationTime() {
		return lastRegenerationTime;
	}

	public long getLastTeleportTime() {
		return lastTeleportTime;
	}

	public long getLastUseTime() {
		return lastUseTime;
	}

	public long getPacketTime(PacketType pt) {
		return packetTimes.containsKey(pt) ? packetTimes.get(pt) : 0;
	}

	public long getLastPacketTime(PacketType pt) {
		return lastPacketTimes.containsKey(pt) ? lastPacketTimes.get(pt) : 0;
	}

	public int getViolationLevel(CheckType ct) {
		return violationData.containsKey(ct) ? violationData.get(ct) : 0;
	}

	public int getPacketAmounts(PacketType pt) {
		return packetAmounts.containsKey(pt) ? packetAmounts.get(pt) : 0;
	}

	public boolean isSprinting() {
		return isSprinting;
	}

	public boolean isBlocking() {
		return isBlocking;
	}

	public boolean isSneaking() {
		return isSneaking;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public boolean getWillDebug() {
		return willDebug;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setPreviousLocation(Location location) {
		this.previousLocation = location;
	}

	public void setLastRegenerationTime(long time) {
		this.lastRegenerationTime = time;
	}

	public void setLastTeleportTime(long time) {
		this.lastTeleportTime = time;
	}

	public void setLastUseTime(long time) {
		this.lastUseTime = time;
	}

	public void setPacketTime(PacketType pt, long time) {
		this.packetTimes.put(pt, time);
	}

	public void setLastPacketTime(PacketType pt, long time) {
		this.lastPacketTimes.put(pt, time);
	}

	public void setIsSprinting(boolean sprinting) {
		this.isSprinting = sprinting;
	}

	public void setIsBlocking(boolean blocking, Location location) {
		this.isBlocking = blocking;
		this.blockingLocation = location;
	}

	public void setIsSneaking(boolean sneaking) {
		this.isSneaking = sneaking;
	}

	public void setOnGround(boolean og) {
		this.onGround = og;
	}

	public void setWillDebug(boolean debug) {
		this.willDebug = debug;
	}

	public void setViolationLevel(CheckType ct, int vl) {
		this.violationData.put(ct, vl);
	}

	public void setPacketAmounts(PacketType pt, int amt) {
		packetAmounts.put(pt, amt);
	}

	public void clearData() {
		this.player = null;
		this.uuid = null;
		this.location = null;
		this.previousLocation = null;
		this.onGround = false;
		this.lastUseTime = 0l;
		this.lastTeleportTime = 0l;
		this.lastRegenerationTime = 0l;
		this.isSprinting = false;
		this.isBlocking = false;
		this.isSneaking = false;
		this.willDebug = false;
		violationData.clear();
	}

}
