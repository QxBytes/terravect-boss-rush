package com.qxbytes.enemies;

import com.qxbytes.behaviors.GrowlemB;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Stats;
import com.qxbytes.grid.Essence;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class Growlem extends Enemy {

	public Growlem(World w, NBT tags) {
		super(w, tags);
		this.setEID("000|02|2");
	}

	public Growlem(World w) {
		super(w);
		this.setEID("000|02|2");
	}
	public void init() {
		super.init();
		this.getTag().set("type", 2);

		this.setS(new Stats(4));
		
		this.setEss(new Essence(4));
		
		this.setB(new GrowlemB(this));
	}
}
