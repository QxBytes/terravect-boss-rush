package com.qxbytes.enemies;

import com.qxbytes.behaviors.LeafieB;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Stats;
import com.qxbytes.grid.Essence;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class Leafie extends Enemy {

	public Leafie(World w, NBT tags) {
		super(w, tags);
		this.setEID("000|02|0");
	}

	public Leafie(World w) {
		super(w);
		this.setEID("000|02|0");
	}
	public void init() {
		super.init();
		//System.out.println("This is a leafie");
		this.getTag().set("type", 2);

		this.setS(new Stats(3));
		
		this.setEss(new Essence(3));
		
		this.setB(new LeafieB(this));

	}
	
}
