package com.qxbytes.grid;

import com.qxbytes.entities.Control;
import com.qxbytes.entities.NBT;
/**
 * 
 * @author QxBytes
 *
 */
public class Essence implements Control {
	private int water;
	private int fire;
	private int earth;
	//private int wind;
	private int limit;
	public Essence(NBT tag) {
		initComponent(tag);
	}
	public Essence(int fire, int earth, int water ) {
		this.water = water;
		this.fire = fire;
		this.earth = earth;
	}
	public Essence(int x) {
		limit = 9999999;
		water =(int)( 1000 * Math.pow(1.5, x*2));
		earth =(int)( 1000 * Math.pow(1.5, x*2));
		fire =(int)( 1000 * Math.pow(1.5, x*2));

	}
	@Override
	public void saveComponent(NBT tag) {
		tag.set("ess-limit", limit);
		tag.set("ess-water", water);
		tag.set("ess-fire", fire);
		tag.set("ess-earth", earth);
		//tag.set("ess-wind", wind);
		
	}

	@Override
	public void initComponent(NBT tag) {
		limit = tag.sI("ess-limit");
		water = tag.sI("ess-water");
		fire = tag.sI("ess-fire");
		earth = tag.sI("ess-earth");
		//wind = tag.sI("ess-wind");
	}
	public int getWater() {
		return water;
	}
	public int setWater(int water) {
		this.water = water;
		if (this.water > limit){
			int t = this.water-limit;
			this.water = limit;
			return t;
		} else if (this.water < 0) {
			int t = this.water;
			this.water = 0;
			return t;
		}
		return 0;
	}
	public int getFire() {
		return fire;
	}
	public int setFire(int fire) {
		this.fire = fire;
		if (this.fire > limit){
			int t = this.fire-limit;
			this.fire = limit;
			return t;
		} else if (this.fire < 0) {
			int t = this.fire;
			this.fire = 0;
			return t;
		}
		return 0;
	}
	public int getEarth() {
		return earth;
	}
	public int setEarth(int earth) {
		this.earth = earth;
		if (this.earth > limit){
			int t = this.earth - limit;
			this.earth = limit;
			return t;
		} else if (this.earth < 0) {
			int t = this.earth;
			this.earth = 0;
			return t;
		}
		return 0;
	}

	public int addWater(int w) {
		return setWater(water+w);
	}
	public int addFire(int f) {
		return setFire(fire+f);
	}
	public int addEarth(int e) {
		return setEarth(earth+e);
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int total()	{
		return this.fire + water + earth;
	}
	
}
