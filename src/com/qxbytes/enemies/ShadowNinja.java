package com.qxbytes.enemies;

import com.qxbytes.behaviors.ShadowNinjaB;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Stats;
import com.qxbytes.grid.Essence;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class ShadowNinja extends Enemy {

	public ShadowNinja(World w, NBT tags) {
		super(w, tags);
		this.setEID("000|01|4");
	}

	public ShadowNinja(World w) {
		super(w);
		this.setEID("000|01|4");
	}
	public void init() {
		super.init();
		this.getTag().set("type", 3);

		this.setS(new Stats(9));
		
		this.getS().setMaxhp(this.getS().getMaxhp()*6);
		
		this.getS().setHp(this.getS().getMaxhp());

		this.setEss(new Essence(9));
		
		this.setB(new ShadowNinjaB(this));
	}

}
