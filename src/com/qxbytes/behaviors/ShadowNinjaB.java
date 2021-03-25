package com.qxbytes.behaviors;

import java.awt.Color;
import java.util.Random;

import com.qxbytes.Game;
import com.qxbytes.Screen;
import com.qxbytes.audio.AChannel;
import com.qxbytes.entities.BManager;
import com.qxbytes.entities.Bullet;
import com.qxbytes.entities.Creature;
import com.qxbytes.entities.Entity;
import com.qxbytes.entities.Motion;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Player;
import com.qxbytes.particles.Line;
import com.qxbytes.particles.ReverseCircle;
import com.qxbytes.particles.TextAt;
import com.qxbytes.sfx.FadeToBlack;
import com.qxbytes.sfx.LineText;
import com.qxbytes.spells.DispersionS;
/**
 * 
 * @author QxBytes
 *
 */
public class ShadowNinjaB extends Behavior{
	Random randnumgen = new Random();
	public ShadowNinjaB(Creature c) {
		super(c);

	}
	int ph = 1;
	int prevph = 1;
	public void update(BManager b, Player p, Creature e) {
		super.update(b, p, e);

		if (lifetime == 2) {
			Screen.effects.add(new LineText("It seems as if you made it this far.        ", 5, Color.BLACK));
			Screen.effects.add(new LineText("...", 20, Color.BLACK));
			Screen.effects.add(new LineText("Goodbye   ", 5, Color.BLACK));
			Screen.effects.add(new FadeToBlack(40, Color.WHITE, false));
		}


		prevph = ph;
		if (e.getS().percentHp() > 85) {
			AChannel.playTrack(2, true);
			P1(b,p,e);
			ph=1;
		} else if (e.getS().percentHp() > 70) {
			AChannel.playTrack(2, true);
			P2(b,p,e);
			ph=2;
		} else if (e.getS().percentHp() > 55) {
			AChannel.playTrack(2, true);
			P3(b,p,e);
			ph=3;
		} else if (e.getS().percentHp() > 40) {
			AChannel.playTrack(4, true);
			P4(b,p,e);
			ph=4;
		} else if (e.getS().percentHp() > 25) {
			AChannel.playTrack(4, true);
			P5(b,p,e);
			ph=5;
		} else if (e.getS().percentHp() > 10) {
			AChannel.playTrack(4, true);
			P6(b,p,e);
			ph=6;
		} else {
			//AChannel.playTrack(11, true);
			P7(b,p,e);
			ph=7;
		}
		if (prevph != ph) {
			for (Entity ee : b.getGroup()) {
				this.getP().add(new TextAt(Color.GRAY,"+",ee.getPos().getCenterX(),ee.getPos().getCenterY(),50));
			}
			b.getGroup().clear();
			p.setHit(250);
			p.getS().addHp(p.getS().getMaxhp()/5);
		}
	}

	int count = 0;


	public void P1(BManager b, Player p, Creature e) {

		if (lifetime % 120 == 0) {
			for (int i = 0 ; i < 360 ; i+=48) {
				Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + 8 +",s:6,cx:0,cy:0,x:"+ e.getPos().getCenterX()+",y:"+e.getPos().getCenterY()+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*.5), e);
				bb.getM().setAngle(i+count);
				b.add(bb);
			}
			count+=8;
		}
		if (lifetime % 200 <= 1) {
			for (int i = 0 ; i < 360 ; i+=48) {
				Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + 8 +",s:6,cx:0,cy:0,x:"+ (e.getPos().getCenterX()+200)+",y:"+e.getPos().getCenterY()+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*1), e);
				bb.getM().setAngle(i+count);
				b.add(bb);
				Bullet bb1 = new Bullet(new NBT("eid:000|00,o:true,d:" + 8 +",s:6,cx:0,cy:0,x:"+ (e.getPos().getCenterX()-200)+",y:"+e.getPos().getCenterY()+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*1), e);
				bb1.getM().setAngle(i+count);
				b.add(bb1);
			}
			count+=8;
		}
	}
	int targetx;
	int targety;
	public void P2(BManager b, Player p, Creature e) {

		if ((this.lifetime-101) % 480 == 0) {
			targetx = (int) p.getPos().getX();
			targety = (int) p.getPos().getY();
		}
		if ((this.lifetime-100) % 240 <= 100) {
			for (int i = 0 ; i < 1 ; i++) {
				int x = randnumgen.nextInt(100);
				this.getP().add(new Line(Color.RED,50,x+targetx-50,0,x+targetx-50,Game.HEIGHT,null));
			}
			for (int i = 0 ; i < 1 ; i++) {
				int x = randnumgen.nextInt(100);
				this.getP().add(new Line(Color.RED,50,0,x+targety-50,Game.WIDTH,x+targety-50,null));
			}

		}
		if ((this.lifetime-1) % 480 <= 50) {
			for (int i = 0 ; i < 1 ; i++) {
				int x = randnumgen.nextInt(50);
				Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + (randnumgen.nextInt(10)+5) +",s:6,cx:0,cy:0,x:"+ (x+targetx-25)+",y:"+1+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*(3)), e);
				bb.getM().setAngle(90);
				b.add(bb);
			}
			for (int i = 0 ; i < 1 ; i++) {
				int x = randnumgen.nextInt(50);
				Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + (randnumgen.nextInt(10)+5) +",s:6,cx:0,cy:0,x:"+1 +",y:"+(x+targety-25)+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*(3)), e);
				bb.getM().setAngle(0);
				b.add(bb);
			}
		}
	}

	public void P3(BManager b, Player p, Creature e) {
		if (this.lifetime % 2000 <= 400 && lifetime % 6 == 0) {
			for (int i = 0 ; i < 360 ; i+=12) {
				Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + 4 +",s:6,cx:0,cy:0,x:"+ e.getPos().getCenterX()+",y:"+e.getPos().getCenterY()+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*2), e);
				bb.getM().setAngle(i+count);
				b.add(bb);
			}
			count+=3;
		}
		if (((this.lifetime+1100) % (2000)) == 0) {
			for (Entity x : b.getGroup()) {
				x.getM().reset();
			}
		}

		if (((this.lifetime+900) % (2000)) == 0) {
			targetx = (int) p.getPos().getCenterX();
			targety = (int) p.getPos().getCenterY();
			for (Entity x : b.getGroup()) {
				this.getP().add(new Line(Color.YELLOW , 100, x.getPos().getCenterX(),x.getPos().getCenterY(),p.getPos().getCenterX(),p.getPos().getCenterY(),null));
			}
		}

		if ((this.lifetime+700) % 2000 == 0) {
			for (Entity x : b.getGroup()) {
				x.getM().setSpeed(e.getM().getSpeed()*4);
			}
			new DispersionS(targetx,targety,true).doSpell(b.getGroup());
		}
		if ((this.lifetime + 100) % 2000 == 0) {
			new DispersionS(Game.WIDTH/2,Game.HEIGHT/2,true).doSpell(b.getGroup());
		}

	}
	public void P4(BManager b, Player p, Creature e) {
		e.getPos().setX(Game.WIDTH/2-e.getPos().getD()/2);
		e.getPos().setY(Game.HEIGHT/2-e.getPos().getD()/2);

		if (lifetime % 240 <= 1 ) {
			for (int i = 0 ; i < 360 ; i+=60) {
				Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + 8 +",s:6,cx:0,cy:0,x:"+ (e.getPos().getCenterX()+200)+",y:"+e.getPos().getCenterY()+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*.5), e);
				bb.getM().setAngle(i+count);
				b.add(bb);
				Bullet bb1 = new Bullet(new NBT("eid:000|00,o:true,d:" + 8 +",s:6,cx:0,cy:0,x:"+ (e.getPos().getCenterX()-200)+",y:"+e.getPos().getCenterY()+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*.5), e);
				bb1.getM().setAngle(i-count);
				b.add(bb1);
			}

		}
		if (lifetime %25 == 0) {
			count+=1;
		}
	}
	public void P5(BManager b, Player p, Creature e) {
		e.getPos().setX(Game.WIDTH/2-e.getPos().getD()/2);
		e.getPos().setY(Game.HEIGHT/2-e.getPos().getD()/2);
		if (lifetime%400 == 0) {
			count++;
		}
		if (lifetime%400 == 0 ||(lifetime)%700 <= 2) {
			for (int i = 0 ; i < 360 ; i+=22) {
				Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + 8 +",s:6,cx:0,cy:0,x:"+ (e.getPos().getCenterX())+",y:"+e.getPos().getCenterY()+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*.5), e);
				bb.getM().setAngle(i+count);
				b.add(bb);
				getP().add(new Line(Color.WHITE, 700, bb.getPos().getCenterX(), bb.getPos().getCenterY(), bb.getM().getX()*2800, bb.getM().getY()*2800, bb.getM()));
			}


		}
		if ((lifetime+100)%400 == 0 || (lifetime+100)%700 <= 2) {
			for (int i = 0 ; i < 360 ; i+=22) {
				Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + 8 +",s:6,cx:0,cy:0,x:"+ (e.getPos().getCenterX())+",y:"+e.getPos().getCenterY()+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*.5), e);
				bb.getM().setAngle(i-count);
				b.add(bb);
				getP().add(new Line(Color.BLACK, 700, bb.getPos().getCenterX(), bb.getPos().getCenterY(), bb.getM().getX()*2800, bb.getM().getY()*2800, bb.getM()));
			}
		}
	}
	int angle = 0;
	public void P6(BManager b, Player p, Creature e) {
		e.getPos().setX(Game.WIDTH/2-e.getPos().getD()/2);
		e.getPos().setY(Game.HEIGHT/4-e.getPos().getD()/2);


		if ((lifetime+10) % 20 == 0) {
			angle = randnumgen.nextInt(360);
			Motion m = new Motion(angle,10);
			for (int i = 0 ; i < 1 ; i++) {
				getP().add(new Line(Color.BLUE, 50, e.getPos().getCenterX(), e.getPos().getCenterY(), m.getX()*2800, m.getY()*2800, null));
			}
			m = new Motion(angle-180,10);
			for (int i = 0 ; i < 1 ; i++) {
				getP().add(new Line(Color.BLUE, 50, e.getPos().getCenterX(), e.getPos().getCenterY(), m.getX()*2800, m.getY()*2800, null));
			}
		}
		if (lifetime % 20 <= 2) {

			Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + 10 +",s:6,cx:0,cy:0,x:"+ (e.getPos().getCenterX())+",y:"+e.getPos().getCenterY()+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*2), e);
			bb.getM().setAngle(angle);
			b.add(bb);
			getP().add(new Line(Color.YELLOW, 100, bb.getPos().getCenterX(), bb.getPos().getCenterY(), bb.getM().getX()*2800, bb.getM().getY()*2800, bb.getM()));


			Bullet xxx = new Bullet(new NBT("eid:000|00,o:true,d:" + 10 +",s:6,cx:0,cy:0,x:"+ (e.getPos().getCenterX())+",y:"+e.getPos().getCenterY()+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*2), e);
			xxx.getM().setAngle(angle-180);
			b.add(xxx);
			getP().add(new Line(Color.YELLOW, 100, bb.getPos().getCenterX(), xxx.getPos().getCenterY(), xxx.getM().getX()*2800, xxx.getM().getY()*2800, xxx.getM()));

		}

	}
	
	public void P7(BManager b, Player p, Creature e) {
		e.getPos().setX((Game.WIDTH/2) + (Math.cos(Math.toRadians(lifetime/4.0))-.5)*40);
		e.getPos().setY((Game.HEIGHT/2) + (Math.sin(Math.toRadians(lifetime/4.0))-.5)*40);
		e.getPos().setD(40);
		if (lifetime % 5 == 0) {
			this.getP().add(new ReverseCircle(Color.BLACK, 1200, 3, e.getPos().getCenterX(), e.getPos().getCenterY()));
		}
		if (lifetime % 5 == 0) {
			Motion temp = new Motion(0, .25);
			temp.setE(p);
			temp.goFrom(e.getPos().getCenterX(), e.getPos().getCenterY());
			p.getM().addMotion(temp);
		}
		if (lifetime %200 == 0) {
			Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + 20 +",s:6,cx:0,cy:0,x:"+ 1+",y:"+1+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*2), e);
			bb.getM().setAngle(90);
			b.add(bb);
			Bullet bb2 = new Bullet(new NBT("eid:000|00,o:true,d:" + 20 +",s:6,cx:0,cy:0,x:"+ 1+",y:"+1+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*2), e);
			bb2.getM().setAngle(0);
			b.add(bb2);
			Bullet bb3 = new Bullet(new NBT("eid:000|00,o:true,d:" + 20 +",s:6,cx:0,cy:0,x:"+ (Game.WIDTH-19)+",y:"+(Game.HEIGHT-19)+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*2), e);
			bb3.getM().setAngle(180);
			b.add(bb3);
			Bullet bb4 = new Bullet(new NBT("eid:000|00,o:true,d:" + 20 +",s:6,cx:0,cy:0,x:"+ (Game.WIDTH-19)+",y:"+(Game.HEIGHT-19)+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*2), e);
			bb4.getM().setAngle(270);
			b.add(bb4);
		}
		if (lifetime % 10 == 0) {
			Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + 10 +",s:6,cx:0,cy:0,x:"+ e.getPos().getCenterX()+",y:"+e.getPos().getCenterY()+",motion-x:1,motion-y:1,motion-speed:"+ (3.0/2)), e);
			bb.getM().setAngle(lifetime/4);
			b.add(bb);
		}
		if (lifetime % 300 == 0) {
			for (int i = 0 ; i < 360 ; i+=45) {
				Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + 8 +",s:6,cx:0,cy:0,x:"+ e.getPos().getCenterX()+",y:"+e.getPos().getCenterY()+",motion-x:1,motion-y:1,motion-speed:"+ e.getM().getSpeed()*5), e);
				bb.getM().setAngle(i);
				b.add(bb);
				getP().add(new Line(Color.YELLOW, 200, bb.getPos().getCenterX(), bb.getPos().getCenterY(), bb.getM().getX()*2800, bb.getM().getY()*2800, bb.getM()));

			}

		}
		if (lifetime % 10 == 0) {
			for (int i = 0 ; i < 360 ; i+=45) {
				Motion m = new Motion(i,10);

				getP().add(new Line(Color.RED, 2, e.getPos().getCenterX(), e.getPos().getCenterY(), m.getX()*2800, m.getY()*2800, null));

			}
		}
	}
}
