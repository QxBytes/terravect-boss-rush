package com.qxbytes.particles;

import java.awt.Graphics;
/**
 * 
 * @author QxBytes
 *
 */
public abstract class Particle {
	private int lifetime = 0;
	private boolean isTombstone = false;
	public Particle() {
		
	}
	public void update() {
		lifetime++;
	}
	public abstract void render(Graphics g);
	public int getLifetime() {
		return lifetime;
	}
	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}
	public boolean isTombstone() {
		return isTombstone;
	}
	public void setTombstone(boolean isTombstone) {
		this.isTombstone = isTombstone;
	}
	
}
