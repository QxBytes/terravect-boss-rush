package com.qxbytes.particles;

import java.awt.Color;
import java.awt.Graphics;
/**
 * 
 * @author QxBytes
 *
 */
public class Circle extends Particle {
	Color c;
	double size;
	double speed;
	double x;
	double y;
	public void init(Color c, int size, double speed, double x, double y) {
		this.c = c;
		this.size = size;
		this.speed = speed;
		this.x = x;
		this.y = y;
	}
	public Circle(Color c, int size, double speed, double x, double y ) {
		init(c,size,speed,x,y);
	}
	public Circle(int size, double speed, double x, double y) {
		init(Color.RED,size,speed,x,y);
	}
	public Circle(int size, double x, double y) {
		init(Color.RED,size,1,x,y);
	}
	
	public void update() {
		super.update();
		size-=speed;
		if (size <= 0) {
			this.setTombstone(true);
		}
	}
	public void render(Graphics g) {

		int halfsize = (int)(size/2.0);
		g.setColor(this.c);
		g.drawOval((int)(x-halfsize), (int)(y-halfsize), (int)size, (int)size);
	}
}	
