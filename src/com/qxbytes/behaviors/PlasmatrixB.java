package com.qxbytes.behaviors;

import java.awt.Color;
import java.util.Random;

import com.qxbytes.Game;
import com.qxbytes.audio.Sound;
import com.qxbytes.entities.BManager;
import com.qxbytes.entities.Bullet;
import com.qxbytes.entities.Creature;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Player;
import com.qxbytes.particles.Line;
import com.qxbytes.spells.DispersionS;
/**
 * 
 * @author QxBytes
 *
 */
public class PlasmatrixB extends Behavior {
	int degree = 0;
	int choice = 0;
	Random randnumgen = new Random();
	public PlasmatrixB(Creature c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	public void update(BManager b, Player p, Creature e) {
		super.update(b, p, e);
		e.getPos().setY(100);
	
		if (e.getS().percentHp() > 20) {
			if (this.lifetime % 200 == 0) {
				e.getM().setAngle(90);
			}
			if ((this.lifetime+100) % 200 == 0 ) {
				e.getM().reset();
			}
			if (this.lifetime % 200 <= 20 && lifetime % 3 == 0) {
				new Sound("hit1q.wav",-25.0f).play();
				for (int i = 0 ; i < 360 ; i+=24) {
					Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + 4 +",s:6,cx:0,cy:0,x:"+ e.getPos().getCenterX()+",y:"+e.getPos().getCenterY()+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*1.5), e);
					bb.getM().setAngle(i+degree);
					if (i % 2 == 0)this.getP().add(new Line(Color.YELLOW, 600, bb.getPos().getX(), bb.getPos().getY(), bb.getM().getX()*800, bb.getM().getY()*800, bb.getM()));
					b.add(bb);
				}
				degree++;
			}
		} else {
			e.getPos().setY(Game.HEIGHT/2);
			if ((this.lifetime-100) % 240 <= 100) {
				for (int i = 0 ; i < 10 ; i++) {
					int x = randnumgen.nextInt(200);
					this.getP().add(new Line(Color.RED,10,x+Game.WIDTH/2-100,0,x+Game.WIDTH/2-100,Game.HEIGHT,null));
				}
				for (int i = 0 ; i < 10 ; i++) {
					int x = randnumgen.nextInt(200);
					this.getP().add(new Line(Color.RED,10,0,x+Game.HEIGHT/2-100,Game.WIDTH,x+Game.HEIGHT/2-100,null));
				}
				
			}
			if ((this.lifetime) % 480 <= 20) {
				if (this.lifetime % 20 == 0) {
					new Sound("hit1.wav",6.0f).play();
				}
				for (int i = 0 ; i < 1 ; i++) {
					int x = randnumgen.nextInt(200);
					Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + (randnumgen.nextInt(10)+5) +",s:6,cx:0,cy:0,x:"+ (x+Game.WIDTH/2-100)+",y:"+e.getPos().getCenterY()+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*(randnumgen.nextInt(3)+1)), e);
					bb.getM().setAngle(270);
					b.add(bb);
				}
				for (int i = 0 ; i < 1 ; i++) {
					int x = randnumgen.nextInt(200);
					Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + (randnumgen.nextInt(10)+5) +",s:6,cx:0,cy:0,x:"+ (x+Game.WIDTH/2-100)+",y:"+e.getPos().getCenterY()+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*(randnumgen.nextInt(3)+1)), e);
					bb.getM().setAngle(90);
					b.add(bb);
				}
				for (int i = 0 ; i < 1 ; i++) {
					int x = randnumgen.nextInt(200);
					Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + (randnumgen.nextInt(10)+5) +",s:6,cx:0,cy:0,x:"+ e.getPos().getCenterX()+",y:"+(x+Game.HEIGHT/2-100)+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*(randnumgen.nextInt(3)+1)), e);
					bb.getM().setAngle(180);
					b.add(bb);
				}
				for (int i = 0 ; i < 1 ; i++) {
					int x = randnumgen.nextInt(200);
					Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + (randnumgen.nextInt(10)+5) +",s:6,cx:0,cy:0,x:"+ e.getPos().getCenterX()+",y:"+(x+Game.HEIGHT/2-100)+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*(randnumgen.nextInt(3)+1)), e);
					bb.getM().setAngle(0);
					b.add(bb);
				}
			}
			if (this.lifetime % 840 == 0) {
				new DispersionS(Game.WIDTH/2,Game.HEIGHT/2,true).doSpell(b.getGroup());
				new Sound("silence1.wav",6.0f);
			}
			
		}
	}
}
