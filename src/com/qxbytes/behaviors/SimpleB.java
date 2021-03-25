package com.qxbytes.behaviors;

import java.awt.Graphics;

import com.qxbytes.Constants;
import com.qxbytes.entities.BManager;
import com.qxbytes.entities.Bullet;
import com.qxbytes.entities.Creature;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Player;
/**
 * 
 * @author QxBytes
 *
 */
public class SimpleB extends Behavior {
	private int locallifetime = 0;
	public SimpleB(Creature c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(BManager b, Player p, Creature enemy) {
		super.update(b, p,enemy);
		if (locallifetime % 2 == 0) {
			Bullet bullet = new Bullet(new NBT(Constants.DEFAULT_NBT),enemy);
			bullet.getPos().setX(enemy.getPos().getX());
			bullet.getPos().setY(enemy.getPos().getY());
			bullet.getM().setSpeed(5);
			bullet.getM().goTo(p.getPos().getCenterX(), p.getPos().getCenterY());
			
			b.add(bullet);
		}
		locallifetime++;
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

	}

}
