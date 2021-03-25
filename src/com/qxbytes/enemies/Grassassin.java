package com.qxbytes.enemies;

import com.qxbytes.behaviors.GrassassinB;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Stats;
import com.qxbytes.grid.Essence;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class Grassassin extends Enemy {

	public Grassassin(World w, NBT tags) {
		super(w, tags);
		this.setEID("000|02|04");
		try {
			throw new Exception();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Grassassin(World w) {
		super(w);
		this.setEID("000|02|04");
		
	}
	public void init() {
		super.init();
		this.getTag().set("type", 2);

		
		this.setS(new Stats(5));

		this.setEss(new Essence(5));
		
		this.setB(new GrassassinB(this));
	}

}
