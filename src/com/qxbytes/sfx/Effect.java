package com.qxbytes.sfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.qxbytes.Screen;
import com.qxbytes.entities.Entity;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public abstract class Effect {
	private int ttl;
	private int totalttl;
	private int lifetime;
	private int overrideoperation = 0;
	
	private World w;
	private Entity e;
	private Screen s;
	private BufferedImage mask;
	
	public Effect(int ttl) {
		this.ttl = ttl;
		this.totalttl = ttl;
	}
	//0 = start (halt thread), 1 = start (continue), 2 = stop
	public int update() {
		this.setTtl(this.getTtl()-1);
		if (this.getTtl() <= 0) {
			stopEffect();
			return 2;
		}
		lifetime++;
		return this.getOverrideoperation();
	}
	public abstract void render(Graphics g);
	
	public void stopEffect() {
		Screen.effects.remove();
	}
	
	
	public int getTotalttl() {
		return totalttl;
	}
	public void setTotalttl(int totalttl) {
		this.totalttl = totalttl;
	}
	public BufferedImage getMask() {
		return mask;
	}
	public void setMask(BufferedImage mask) {
		this.mask = mask;
	}
	public Screen getS() {
		return s;
	}
	public void setS(Screen s) {
		this.s = s;
	}
	public int getLifetime() {
		return lifetime;
	}
	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	public int getOverrideoperation() {
		return overrideoperation;
	}
	public void setOverrideoperation(int overrideoperation) {
		this.overrideoperation = overrideoperation;
	}
	public World getW() {
		return w;
	}
	public void setW(World w) {
		this.w = w;
	}
	public Entity getE() {
		return e;
	}
	public void setE(Entity e) {
		this.e = e;
	}
	
}
