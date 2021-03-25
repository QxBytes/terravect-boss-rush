package com.qxbytes.enemies;

import com.qxbytes.behaviors.GillSoldierB;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Stats;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class GillSoldier extends Enemy {

	public GillSoldier(World w, NBT tags) {
		super(w, tags);
		this.setEID("000|03|2");
	}

	public GillSoldier(World w) {
		super(w);
		this.setEID("000|03|2");
	}
	public void init() {
		super.init();
		this.getTag().set("type", 1);
		this.setS(new Stats(4));
		
		this.getS().setMaxhp(this.getS().getMaxhp()*2);
		
		this.getS().setHp(this.getS().getMaxhp());
		
		//this.getTag().set("multiplier", 7);
		
		this.getEss().setWater((int)(1500));
		this.getEss().setEarth((int)(450));
		this.getEss().setFire((int)(75));
		
		this.setB(new GillSoldierB(this));
	}
	
}
