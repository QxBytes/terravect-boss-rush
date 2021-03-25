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
import com.qxbytes.particles.ReverseCircle;
/**
 * 
 * @author QxBytes
 *
 */
public class FearahnaFishB extends Behavior {

	public FearahnaFishB(Creature c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	public void update(BManager b, Player p, Creature e) {
		super.update(b, p, e);

		e.getPos().setX(Game.WIDTH/2.0 - 12.0 + Math.cos(Math.toRadians(lifetime))*70);
		e.getPos().setY(Game.HEIGHT/2.0 - 12.0 + Math.sin(Math.toRadians(lifetime))*70);
		if (this.lifetime % 120 == 0) {
			new Sound("hit1.wav",6.0f).play();
			this.getP().add(new ReverseCircle(Color.BLUE,1200,e.getM().getSpeed()*2,e.getPos().getCenterX(),e.getPos().getCenterY()));
			for (int i = 0 ; i < 360 ; i+=18) {
				Bullet be = new Bullet(new NBT(Constants.DEFAULT_NBT), e);
				be.getPos().setX(e.getPos().getX());
				be.getPos().setY(e.getPos().getY());
				be.setM(new Motion(i,e.getM().getSpeed()));
				b.add(be);
			}
		}
	}

}
