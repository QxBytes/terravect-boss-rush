package com.qxbytes.enemies;

import com.qxbytes.behaviors.SandWarriorB;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Stats;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class SandWarrior extends Enemy {

	public SandWarrior(World w, NBT tags) {
		super(w, tags);
		this.setEID("000|03|4");
	}

	public SandWarrior(World w) {
		super(w);
		this.setEID("000|03|4");
	}
	public void init() {
		super.init();
		this.getTag().set("type", 1);

		this.setS(new Stats(2));
		
		this.getEss().setWater(2000);
		this.getEss().setEarth(600);
		this.getEss().setFire(100);
		
		this.setB(new SandWarriorB(this));
	}
}
