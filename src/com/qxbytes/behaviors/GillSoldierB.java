package com.qxbytes.behaviors;

import java.awt.Color;
import java.util.Random;

import com.qxbytes.Constants;
import com.qxbytes.Game;
import com.qxbytes.audio.Sound;
import com.qxbytes.entities.BManager;
import com.qxbytes.entities.Bullet;
import com.qxbytes.entities.Creature;
import com.qxbytes.entities.Motion;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Player;
import com.qxbytes.particles.ReverseCircle;
/**
 * 
 * @author QxBytes
 *
 */
public class GillSoldierB extends Behavior {

	public GillSoldierB(Creature c) {
		super(c);
	}
	public void update(BManager b, Player p, Creature enemy) {
		super.update(b, p, enemy);
		if (lifetime < 200) {
			enemy.setM(new Motion(new Random().nextInt(60)+1,1));
		}
		if (enemy.getPos().getX() > Game.WIDTH-50 || enemy.getPos().getX() < 50) {
			enemy.getM().setX(-enemy.getM().getX());
			this.getP().add(new ReverseCircle(Color.BLUE,1200,enemy.getM().getSpeed()*2,enemy.getPos().getCenterX(),enemy.getPos().getCenterY()));
			new Sound("hit1.wav",6.0f).play();
			for (int i = 0 ; i < 360 ; i+=8) {
				Bullet be = new Bullet(new NBT(Constants.DEFAULT_NBT),enemy);
				be.getPos().setX(x);
				be.getPos().setY(y);
				be.setM(new Motion(i+lifetime,enemy.getM().getSpeed()));
				b.add(be);
			}
		}
		if (enemy.getPos().getY() > Game.HEIGHT-12 || enemy.getPos().getY() < 12) {
			new Sound("hit1.wav",6.0f).play();
			enemy.getM().setY(-enemy.getM().getY());
			this.getP().add(new ReverseCircle(Color.BLUE,1200,enemy.getM().getSpeed()*2,enemy.getPos().getCenterX(),enemy.getPos().getCenterY()));

			for (int i = 0 ; i < 360 ; i+=8) {
				Bullet be = new Bullet(new NBT(Constants.DEFAULT_NBT),enemy);
				be.getPos().setX(x);
				be.getPos().setY(y);
				be.setM(new Motion(i+lifetime,enemy.getM().getSpeed()));
				b.add(be);
			}
		}

	}
}
