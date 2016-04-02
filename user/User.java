package me.vrekt.prycia.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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
	private int positionPackets, onGroundPackets = 0;
	private boolean isSprinting, isBlocking, isSneaking, onGround, willDebug = false;
	private HashMap<PacketType, Integer> lastPacketAmounts = new HashMap<PacketType, Integer>();
	private HashMap<PacketType, Long> lastPacketTimes = new HashMap<PacketType, Long>();
	private HashMap<PacketType, Integer> packetAmounts = new HashMap<PacketType, Integer>();
	private HashMap<CheckType, Integer> violationData = new HashMap<CheckType, Integer>();
	private HashMap<PacketType, Long> packetTimes = new HashMap<PacketType, Long>();

	private ArrayList<CheckType> checkList = new ArrayList<CheckType>();
	private Vector lastVelocity;

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

	public Location getBlockingLocation(){
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

	public long getPacketTime(PacketType type) {
		return packetTimes.containsKey(type) ? packetTimes.get(type) : 0L;
	}

	public long getLastPacketTime(PacketType type) {
		return lastPacketTimes.containsKey(type) ? lastPacketTimes.get(type) : 0L;
	}

	public int getPositionPacketsAmount() {
		return positionPackets;
	}

	public int getOnGroundPacketsAmount() {
		return onGroundPackets;
	}

	public int getViolationLevel(CheckType ct) {
		return violationData.containsKey(ct) ? violationData.get(ct) : 0;
	}

	public int getPacketAmount(PacketType type) {
		return packetAmounts.containsKey(type) ? packetAmounts.get(type) : 0;
	}

	public int getLastPacketAmount(PacketType type) {
		return lastPacketAmounts.containsKey(type) ? lastPacketAmounts.get(type) : 0;
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

	public boolean shouldCheck(CheckType ct) {
		return checkList.contains(ct);
	}

	public Vector getLastVelocity() {
		return lastVelocity;
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

	public void setPacketTime(PacketType type, long time) {
		this.packetTimes.put(type, time);
	}

	public void setLastPacketTime(PacketType type, long time) {
		this.lastPacketTimes.put(type, time);
	}

	public void setPackets(int amount) {
		this.positionPackets = amount;
	}

	public void setGroundPackets(int amount) {
		this.onGroundPackets = amount;
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

	public void setVelocity(Vector v) {
		this.lastVelocity = v;
	}

	public void addShouldCheck(CheckType ct, boolean check) {
		if (check) {
			checkList.add(ct);
		}

		if (!check) {
			checkList.remove(ct);
		}
	}

	public void setViolationLevel(CheckType ct, int vl) {
		this.violationData.put(ct, vl);
	}

	public void setPacketAmount(PacketType type, int amount) {
		this.packetAmounts.put(type, amount);
	}

	public void setLastPacketAmount(PacketType type, int amount) {
		this.lastPacketAmounts.put(type, amount);
	}

	public void setFieldsNull() {
		this.player = null;
		this.uuid = null;
		this.location = null;
		this.previousLocation = null;
		this.positionPackets = 0;
		this.onGroundPackets = 0;
		this.onGround = false;
		this.lastUseTime = 0l;
		this.lastTeleportTime = 0l;
		this.lastRegenerationTime = 0l;
		this.isSprinting = false;
		this.isBlocking = false;
		this.isSneaking = false;
		this.willDebug = false;
		violationData.clear();
		packetAmounts.clear();
		packetTimes.clear();
		checkList.clear();
	}

}
