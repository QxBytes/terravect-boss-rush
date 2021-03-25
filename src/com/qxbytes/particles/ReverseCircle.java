package com.qxbytes.particles;

import java.awt.Color;
import java.awt.Graphics;
/**
 * 
 * @author QxBytes
 *
 */
public class ReverseCircle extends Particle {
	Color c;
	double maxsize;
	double size;
	double speed;
	double x;
	double y;
	public void init(Color c, int size, double speed, double x, double y) {
		this.c = c;
		this.maxsize = size;
		this.speed = speed;
		this.x = x;
		this.y = y;
	}
	public ReverseCircle(Color c, int size, double speed, double x, double y ) {
		init(c,size,speed,x,y);
	}
	public ReverseCircle(int size, double speed, double x, double y) {
		init(Color.RED,size,speed,x,y);
	}
	public ReverseCircle(int size, double x, double y) {
		init(Color.RED,size,1,x,y);
	}
	
	public void update() {
		super.update();
		size+=speed;
		if (size >= maxsize) {
			this.setTombstone(true);
		}
	}
	public void render(Graphics g) {
		int halfsize = (int)(size/2.0);
		g.setColor(this.c);
		g.drawOval((int)(x-halfsize), (int)(y-halfsize), (int)size, (int)size);
	}
}	
