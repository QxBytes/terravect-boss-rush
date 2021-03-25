package com.qxbytes.particles;

import java.awt.Color;
import java.awt.Graphics;

import com.qxbytes.Constants;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public class TextAt extends Particle {
	private Color c;
	private String text;
	private double x;
	private double y;
	private int ttl;
	private int maxttl;
	public TextAt(Color c, String text, double x, double y, int ttl) {
		this.c = c;
		this.text = text;
		this.x = x;
		this.y = y;
		this.ttl = ttl;
		this.maxttl = ttl;
	}
	public void update() {
		if (ttl <= 0) {
			this.setTombstone(true);
		}
		
		y-=2;
		ttl--;
	}
	@Override
	public void render(Graphics g) {
		int alpha = (int)(255 - ((ttl*255.0)/(double)maxttl));
		g.setColor(new Color(c.getRed(),c.getGreen(),c.getBlue(),255-alpha));

		g.setFont(Constants.LINE_TEXT);
		Utils.drawString(g, text, (int)x, (int)y);
	}
	
}
