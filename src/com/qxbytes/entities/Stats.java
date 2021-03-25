package com.qxbytes.entities;

import com.qxbytes.Constants;
/**
 * 
 * @author QxBytes
 *
 */
public class Stats implements Control, Constants {
	int hp;
	int maxhp;
	int def;
	int spd;
	int atk;
	public Stats(NBT tag) {
		initComponent(tag);
	}
	public Stats(int hp, int maxhp, int def, int spd, int atk) {
		this.hp = hp;
		this.maxhp = maxhp;
		this.def = def;
		this.spd = spd;
		this.atk = atk;
	}
	public Stats(int x) {
		setAtk((int)(bAtk*Math.pow(1.4,x)));
		setDef((int)(bDef*Math.pow(1.1,x)));
		setMaxhp((int)(bMaxhp*Math.pow(1.75,x)));
		setHp((int)(bMaxhp*Math.pow(1.75,x)));
		setSpd((int)(bSpd*Math.pow(1.5,x)));
	}
	@Override
	public void saveComponent(NBT tag) {
		tag.set("stats-hp", hp);
		tag.set("stats-maxhp", maxhp);
		tag.set("stats-def", def);
		tag.set("stats-spd", spd);
		tag.set("stats-atk", atk);
	}

	@Override
	public void initComponent(NBT tag) {
		hp = tag.sI("stats-hp");
		maxhp = tag.sI("stats-maxhp");
		def = tag.sI("stats-def");
		spd = tag.sI("stats-spd");
		atk = tag.sI("stats-atk");

	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
		if (this.hp > maxhp) {
			this.hp = maxhp;
		}
		if (this.hp < 0) {
			this.hp = 0;
		}
	}

	public int getMaxhp() {
		return maxhp;
	}
	public void setMaxhp(int maxhp) {
		this.maxhp = maxhp;
		if (maxhp < hp) {
			this.setHp(maxhp);
		}
	}
	public int percentHp() {
		return (hp*100)/maxhp;
	}
	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int getSpd() {
		return spd;
	}

	public void setSpd(int spd) {
		this.spd = spd;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}
	public void addHp(int hp) {
		setHp(this.hp + hp);
	}
	
	
}
