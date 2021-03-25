package com.qxbytes.enemies;

import java.awt.Graphics;

import com.qxbytes.behaviors.MoltronB;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Stats;
import com.qxbytes.grid.Essence;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class Moltron extends Enemy {

	public Moltron(World w, NBT tags) {
		super(w,tags);
		setEID("000|01|00");
	}
	public Moltron(World w) {
		super(w);
		setEID("000|01|00");

	}
	@Override
	public void init() {
		super.init();
		this.getTag().set("type", 3);

		this.setS(new Stats(6));
		
		this.getS().setMaxhp(this.getS().getMaxhp()*3);
		
		this.getS().setHp(this.getS().getMaxhp());

		this.setEss(new Essence(6));
		
		this.setB(new MoltronB(this));

	}
	public void render(Graphics g) {
		super.render(g);
	}
	public void update() {
		super.update();
	}

}
