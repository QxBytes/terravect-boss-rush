package com.qxbytes.behaviors;

import java.awt.Color;

import com.qxbytes.Constants;
import com.qxbytes.Game;
import com.qxbytes.audio.Sound;
import com.qxbytes.entities.BManager;
import com.qxbytes.entities.Bullet;
import com.qxbytes.entities.Creature;
import com.qxbytes.entities.Motion;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Player;
import com.qxbytes.particles.Circle;
import com.qxbytes.particles.Line;
import com.qxbytes.particles.ReverseCircle;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public class GrassassinB extends Behavior{
	int multiplier;
	boolean swap = true;
	public GrassassinB(Creature c) {
		super(c);
		multiplier = c.getTag().sI("multiplier");
	}
	public void update(BManager b, Player p, Creature e) {
		super.update(b, p, e);

		if ((this.getLifetime()+200) % 400 == 0) {
			getP().add(new Circle(Color.BLUE, 200, 3, e.getPos().getCenterX(), e.getPos().getCenterY()));
		}
		if ((this.getLifetime()+170) % 400 < 170 && this.getLifetime()%2 == 0) {
			Bullet be = new Bullet(new NBT(Constants.DEFAULT_NBT),e);
			be.getPos().setX(x);
			be.getPos().setY(y);
			be.setM(new Motion(0,7));
			be.getM().setE(e);
			be.getM().goTo(p.getPos().getX(), p.getPos().getY());
			b.add(be);
			getP().add(new Line(200, e.getPos().getCenterX(), e.getPos().getCenterY(), p.getPos().getCenterX(), p.getPos().getCenterY(), be.getM()));
			if (this.getLifetime()%4 == 0) new Sound("hit4q.wav",-35.0f).play();
		}
		if (this.getLifetime() % 400 == 0) {
			if (e.getS().percentHp() < 30) {
				for (int i = 0 ; i < 360 ; i+=40) {
					Bullet be = new Bullet(new NBT(Constants.DEFAULT_NBT),e);
					be.getPos().setX(x);
					be.getPos().setY(y);
					be.setM(new Motion(i,2));
					be.getM().setE(e);
					b.add(be);

				}
				getP().add(new ReverseCircle(Color.RED, 800, 4, x, y));

			}
			swap = !swap;
			if (swap) {
				e.getPos().setX(Utils.randomGen.nextInt(40)+10);
			} else {
				e.getPos().setX(Game.WIDTH-(Utils.randomGen.nextInt(40)+10));
			}
			e.getPos().setY(Utils.randomY());
			new Sound("hit3q.wav",-10.0f).play();
		}
		if (e.getS().percentHp() < 30) {
			e.getTag().set("multiplier", multiplier+1);
		}
		if (e.getS().percentHp() < 15) {
			e.getTag().set("multiplier", multiplier+2);
		}
	}

}
