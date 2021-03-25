package com.qxbytes.enemies;

import com.qxbytes.behaviors.PlasmatrixB;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Stats;
import com.qxbytes.grid.Essence;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class Plasmatrix extends Enemy{

	public Plasmatrix(World w, NBT tags) {
		super(w, tags);
		setEID("000|01|2");
	}

	public Plasmatrix(World w) {
		super(w);
		setEID("000|01|2");
	}
	public void init() {
		super.init();
		this.getTag().set("type", 3);

		this.setS(new Stats(7));
		
		this.setEss(new Essence(7));
		
		this.setB(new PlasmatrixB(this));
	}
	
}
