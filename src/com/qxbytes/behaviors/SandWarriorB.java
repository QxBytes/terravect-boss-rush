package com.qxbytes.behaviors;

import com.qxbytes.Constants;
import com.qxbytes.Game;
import com.qxbytes.audio.Sound;
import com.qxbytes.entities.BManager;
import com.qxbytes.entities.Bullet;
import com.qxbytes.entities.Creature;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Player;
import com.qxbytes.particles.Circle;
/**
 * 
 * @author QxBytes
 *
 */
public class SandWarriorB extends Behavior {
	int move = 0;
	public SandWarriorB(Creature c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	public void update(BManager b, Player p, Creature enemy) {
		super.update(b, p, enemy);
		enemy.getM().setX(1);
		enemy.getPos().setY(Math.sin(lifetime*(Math.PI/180))*60+60);

		if (lifetime % 100 == 0) {
			Bullet bullet = new Bullet(new NBT(Constants.DEFAULT_NBT),enemy);
			bullet.getPos().setX(enemy.getPos().getX());
			bullet.getPos().setY(enemy.getPos().getY());
			bullet.getM().setSpeed(5);
			bullet.getM().goTo(p.getPos().getCenterX(), p.getPos().getCenterY());

			b.add(bullet);
		}
		if (lifetime % 190 == 0) {
			new Sound("hit2.wav",6.0f).play();
			for (int i = 0 ; i < Game.WIDTH ; i+=25) {
				getP().add(new Circle(20,i,enemy.getPos().getCenterY()));
				getP().add(new Circle(40,.5,i,enemy.getPos().getCenterY()));

				Bullet bullet = new Bullet(new NBT(Constants.DEFAULT_NBT),enemy);
				bullet.getPos().setX(i);
				bullet.getPos().setY(enemy.getPos().getY());
				bullet.getM().setSpeed(3);
				bullet.getM().goTo(p.getPos().getCenterX(), p.getPos().getCenterY());

				b.add(bullet);
			}
		}
	}

}
