package com.qxbytes.components;

import java.awt.Color;
import java.awt.Graphics;

import com.qxbytes.entities.Creature;
import com.qxbytes.entities.NBT;
/**
 * 
 * @author QxBytes
 *
 */
public class HealthBar extends EComp {

	public HealthBar(Creature e) {
		super(e);
	}
	@Override
	public void modify() {
		
	}
	@Override
	public void modifyGFX(Graphics g) {
		double fill = getE().getS().getHp();
		double max = getE().getS().getMaxhp();
		
		double amount = fill/max;
		double d = getE().getPos().getD();
		
		
		double filled = d * amount;
		
		int x = (int)getE().getPos().getX();
		int y = (int)getE().getPos().getY();
		g.setColor(Color.RED);
		g.fillRect(x, (int)(y-d*3), (int)d, 3);
		
		g.setColor(Color.CYAN);
		g.fillRect(x, (int)(y-d*3), (int)filled, 3);

	}
	@Override
	public void premodifyGFX(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void saveComponent(NBT tag) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void initComponent(NBT tag) {
		// TODO Auto-generated method stub
		
	}

}
