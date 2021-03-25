package com.qxbytes.enemies;

import com.qxbytes.behaviors.FearahnaFishB;
import com.qxbytes.entities.NBT;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class FearahnaFish extends Enemy {

	public FearahnaFish(World w, NBT tags) {
		super(w, tags);
		this.setEID("000|03|0");
	}

	public FearahnaFish(World w) {
		super(w);
		this.setEID("000|03|0");
	}
	public void init() {
		super.init();
		this.getTag().set("type", 1);

		this.getS().setAtk(bAtk);
		this.getS().setDef(bDef);
		this.getS().setMaxhp(bMaxhp);
		this.getS().setHp(bMaxhp);
		this.getS().setSpd(bSpd);
		
		this.getEss().setWater(1000);
		this.getEss().setEarth(300);
		this.getEss().setFire(50);
		
		this.setB(new FearahnaFishB(this));
	}

}
