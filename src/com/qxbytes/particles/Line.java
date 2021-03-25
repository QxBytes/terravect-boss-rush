package com.qxbytes.particles;

import java.awt.Color;
import java.awt.Graphics;

import com.qxbytes.entities.Motion;
/**
 * 
 * @author QxBytes
 *
 */
public class Line extends Particle {
	double x1;
	double x2;
	double y1;
	double y2;
	Motion link;
	Color c;
	int ttl;
	public void init(Color c, int ttl, double x1, double y1, double x2, double y2, Motion link) {
		this.c = c;
		this.ttl = ttl;
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.link = link;
	}
	public Line(int ttl, double x1, double y1, double x2, double y2, Motion link) {
		init(Color.BLUE,ttl,x1,y1,x2,y2,link);
		
	}
	public Line(Color c,int ttl, double x1, double y1, double x2, double y2, Motion link) {
		init(c,ttl,x1,y1,x2,y2,link);
		
	}
	public void update() {
		super.update();
		
		if (link == null) {
			
		} else {
			x1 += link.getX();
			y1 += link.getY();
		}
		ttl--;
		if (ttl <= 0) {
			this.setTombstone(true);
		}
	}
	@Override
	public void render(Graphics g) {
		g.setColor(c);
		g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	}

}
