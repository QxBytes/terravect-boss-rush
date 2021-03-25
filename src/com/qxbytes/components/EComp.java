package com.qxbytes.components;

import java.awt.Graphics;

import com.qxbytes.entities.Control;
import com.qxbytes.entities.Creature;
/**
 * 
 * @author QxBytes
 *
 */
public abstract class EComp implements Control {
	private Creature e;
	public EComp(Creature e) {
		this.e = e;
	}
	public abstract void premodifyGFX(Graphics g);
	public abstract void modifyGFX(Graphics g);
	public abstract void modify();
	/**
	 * @return the e
	 */
	public Creature getE() {
		return e;
	}
	/**
	 * @param e the e to set
	 */
	public void setE(Creature e) {
		this.e = e;
	}
	
}
