package me.vrekt.prycia.util;

import org.bukkit.util.Vector;

public class BB {

	private Vector max;
	private Vector min;

	public int maxX = 0;
	public int maxY = 0;
	public int maxZ = 0;

	public int minX = 0;
	public int minY = 0;
	public int minZ = 0;

	public BB(Vector v, Vector v2) {
		this.min = new Vector(Math.min(v.getX(), v2.getX()), Math.min(v.getY(), v2.getY()), Math.min(v.getZ(), v2.getZ()));
		this.max = new Vector(Math.max(v.getX(), v2.getX()), Math.max(v.getY(), v2.getY()), Math.max(v.getZ(), v2.getZ()));

		maxX = floor_double(max.getX());
		maxY = floor_double(max.getY());
		maxZ = floor_double(max.getZ());

		minX = floor_double(min.getX());
		minY = floor_double(min.getY());
		minZ = floor_double(min.getZ());

	}

	public boolean intersectsWith(BB other) {
		return other.maxX > minX && other.minX < maxX ? (other.maxY > minY && other.minY < maxY ? other.maxZ > minZ && other.minZ < maxZ : false) : false;
	}

	public int floor_double(double d) {
		int d2 = (int) d;
		return d < d2 ? d2 - 1 : d2;
	}

}
