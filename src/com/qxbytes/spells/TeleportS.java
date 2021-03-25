package com.qxbytes.spells;

import com.qxbytes.entities.Entity;
import com.qxbytes.entities.Group;
/**
 * 
 * @author QxBytes
 *
 */
public class TeleportS implements Spell {
	private double x;
	private double y;
	public TeleportS(double x, double y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public void doSpell(Group b) {
		for (Entity e : b) {
			e.getPos().setX(x);
			e.getPos().setY(y);
		}
	}

}
