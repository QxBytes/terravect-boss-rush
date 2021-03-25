package com.qxbytes.spells;

import com.qxbytes.entities.Group;
/**
 * 
 * @author QxBytes
 *
 */
public class DispersionS implements Spell {
	double x;
	double y;
	boolean reverse;
	public DispersionS(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public DispersionS(double x, double y,boolean r) {
		this.x = x;
		this.y = y;
		this.reverse = r;
	}
	@Override
	public void doSpell(Group b) {
		for (int i = 0 ; i < b.size() ; i++) {
			if (reverse) {
				b.get(i).getM().goTo(x, y);
			} else {
				b.get(i).getM().goFrom(x, y);
			}
		}
	}

}
