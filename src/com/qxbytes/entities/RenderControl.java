package com.qxbytes.entities;
/**
 * 
 * @author QxBytes
 *
 */
public class RenderControl implements Control {
	private double x;
	private double y;
	private int cx;
	private int cy;
	private double d;
	private boolean o;
	public RenderControl(NBT tag) {
		initComponent(tag);
	}
	@Override
	public void saveComponent(NBT tag) {
		tag.set("x",x);
		tag.set("y", y);
		tag.set("cx", cx);
		tag.set("cy", cy);
		tag.set("d", d);
		tag.set("o", o);
	}
	@Override
	public void initComponent(NBT tag) {
		this.x = tag.sD("x");
		this.y = tag.sD("y");
		this.cx = tag.sI("cx");
		this.cy = tag.sI("cy");
		this.d = tag.sD("d");
		this.o = tag.sB("o");
		
	}
	public double getCenterX() {
		return x+d/2.0;
	}
	public double getCenterY() {
		return y+d/2.0;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getCx() {
		return cx;
	}
	public void setCx(int cx) {
		this.cx = cx;
	}
	public int getCy() {
		return cy;
	}
	public void setCy(int cy) {
		this.cy = cy;
	}
	public double getD() {
		return d;
	}
	public void setD(int d) {
		this.d = d;
	}
	public boolean isO() {
		return o;
	}
	public void setO(boolean o) {
		this.o = o;
	}
	

}
