package com.qxbytes.sfx;

import java.awt.Graphics;

import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class Action extends Effect {
	private World w;
	public Action(World w) {
		super(2);
		this.w = w;
	}
	public int update() {
		
		return super.update();
	}
	@Override
	public void render(Graphics g) {

	}
	public World getW() {
		return w;
	}
	public void setW(World w) {
		this.w = w;
	}
	

}
