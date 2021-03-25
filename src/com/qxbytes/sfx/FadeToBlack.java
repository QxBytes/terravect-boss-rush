package com.qxbytes.sfx;

import java.awt.Color;
import java.awt.Graphics;

import com.qxbytes.Game;
/**
 * 
 * @author QxBytes
 *
 */
public class FadeToBlack extends Effect {
	private Color c;
	private boolean reverse = false;
	public FadeToBlack(int ttl) {
		super(ttl);
		c = new Color(0,0,0);
	}
	public FadeToBlack(int ttl, Color c) {
		super(ttl);
		this.c = c;
	}
	public FadeToBlack(int ttl, Color c, boolean reverse) {
		super(ttl);
		this.c = c;
		this.reverse = reverse;
	}

	@Override
	public int update() {
		int r = super.update();
		if (r == 2) return 2;
		return this.getOverrideoperation();
	}

	@Override
	public void render(Graphics g) {
		if (reverse) {
			g.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),(int)(this.getTtl()*(255.0/this.getTotalttl()))));
		} else {
			g.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),255-(int)(this.getTtl()*(255.0/this.getTotalttl()))));
		}
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
	}



}
