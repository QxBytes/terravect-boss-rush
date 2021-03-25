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
import com.qxbytes.particles.Line;
import com.qxbytes.spells.DispersionS;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public class GrowlemB extends Behavior{
	
	public GrowlemB(Creature c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	public void update(BManager b, Player p, Creature enemy){
		super.update(b, p, enemy);
		
		if (lifetime % 13 == 0) {
			new Sound("hit1q.wav",-20.0f).play();
			b.add(new Bullet(new NBT("d:8,o:true,eid:0|0|0,y:5" + ",x:" + Utils.randomX() + ",motion-speed:3,motion-y:" + enemy.getM().getSpeed()*2),enemy));
		}
		if (lifetime % 500 == 0 || lifetime == 100) {
			new Sound("hit1.wav",6.0f).play();
			enemy.getM().setSpeed(1);
			enemy.getM().goTo(Utils.randomX(), Utils.randomY());
			
			new DispersionS(enemy.getPos().getX(),enemy.getPos().getY(),true).doSpell(b.getGroup());;
			for (int i = 0 ; i < b.getGroup().size() ; i++) {
				this.getP().add(new Line(100, b.getGroup().get(i).getPos().getX(), b.getGroup().get(i).getPos().getY(), enemy.getPos().getX(), enemy.getPos().getY(), b.getGroup().get(i).getM()));
			}
		}
		
		if (enemy.getS().percentHp() < 50 && enemy.getS().percentHp() >= 18) {
			if (lifetime % 160 == 0) {
				Bullet x = new Bullet(new NBT(Constants.DEFAULT_NBT),enemy);
				x.getPos().setX(lifetime%Game.WIDTH);
				x.getPos().setY(3);
				x.setM(new Motion(90,4));
				
				Bullet y = new Bullet(new NBT(Constants.DEFAULT_NBT),enemy);
				y.getPos().setY(3);
				y.getPos().setX(Game.WIDTH-(lifetime%Game.WIDTH));
				y.setM(new Motion(90,4));
				b.setCurrentGroup(1);
				b.add(x);
				b.add(y);
				b.setCurrentGroup(0);
			}
		}
		if (enemy.getS().percentHp() < 28) {
			if (lifetime % 80 == 0) {
				new Sound("hit2.wav",-10.0f).play();
				this.getP().add(new Line(Color.RED,100,0,lifetime%Game.HEIGHT,Game.WIDTH,lifetime%Game.HEIGHT,null));

				Bullet x = new Bullet(new NBT(Constants.DEFAULT_NBT),enemy);
				x.getPos().setX(3);
				x.getPos().setY(lifetime%Game.HEIGHT);
				x.setM(new Motion(0,4));
				
				Bullet y = new Bullet(new NBT(Constants.DEFAULT_NBT),enemy);
				y.getPos().setX(3);
				y.getPos().setY(Game.HEIGHT-(lifetime%Game.HEIGHT));
				y.setM(new Motion(0,4));
				
				Bullet xx = new Bullet(new NBT(Constants.DEFAULT_NBT),enemy);
				xx.getPos().setX(Game.WIDTH-3);
				xx.getPos().setY(lifetime%Game.HEIGHT);
				xx.setM(new Motion(180,4));
				
				Bullet yy = new Bullet(new NBT(Constants.DEFAULT_NBT),enemy);
				yy.getPos().setY(Game.HEIGHT-lifetime%Game.HEIGHT);
				yy.getPos().setX(Game.WIDTH-3);
				yy.setM(new Motion(180,4));
				
				b.setCurrentGroup(1);
				b.add(x);
				b.add(xx);
				b.add(yy);
				b.add(y);
				b.setCurrentGroup(0);
			}
		}
	}

}
