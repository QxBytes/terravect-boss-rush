package com.qxbytes.behaviors;

import com.qxbytes.Game;
import com.qxbytes.audio.Sound;
import com.qxbytes.entities.BManager;
import com.qxbytes.entities.Bullet;
import com.qxbytes.entities.Creature;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Player;
import com.qxbytes.spells.DispersionS;
/**
 * 
 * @author QxBytes
 *
 */
public class MoltronB extends Behavior{
	boolean swap = false;
	int loc = 100;
	public MoltronB(Creature c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	public void update(BManager b, Player p, Creature enemy) {
		super.update(b, p, enemy);
		enemy.getPos().setX(Game.WIDTH/2-enemy.getPos().getD());
		enemy.getPos().setY(loc%Game.HEIGHT);
		if (this.lifetime % 1000 == 0) {
			//enemy.getM().goTo(Utils.randomX(), loc+50);
			
			//loc+=50;
		}
		//if ((this.lifetime+200) % 1000 == 0) {
			enemy.getM().reset();
		//}
		if ((this.lifetime+500) % 1000 <= 800 && this.lifetime % 20 == 0) {
			new Sound("hit1q.wav",-20.0f).play();
		}
		if ((this.lifetime+300) % 1000 <= 500 && this.lifetime % 20 == 0) {
			
			for (int i = 0 ; i < 360 ; i+=20) {
				Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + (((this.lifetime+300) % 1000)/25) +",s:6,cx:0,cy:0,x:400,y:50,motion-x:1,motion-y:1,motion-speed:" + enemy.getM().getSpeed()*1.5), enemy);
				bb.getM().setAngle(i);
				b.add(bb);
			}
		}
		if ((this.lifetime+500) % 1000 <= 500 && this.lifetime % 20 == 0) {

			for (int i = 0 ; i < 360 ; i+=20) {
				Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + (((this.lifetime+800) % 1000)/25) +",s:6,cx:0,cy:0,x:400,y:50,motion-x:1,motion-y:1,motion-speed:"+ enemy.getM().getSpeed()*1.5), enemy);
				bb.getM().setAngle(i+10);
				b.add(bb);
			}
		}
		if ((this.lifetime+500) % 2000 == 0) {
			new DispersionS(enemy.getPos().getCenterX(), enemy.getPos().getCenterY(),true).doSpell(b.getGroup());
			
		}
		if (enemy.getS().percentHp() < 25 && this.lifetime % 10 == 0) {
			for (int i = 0 ; i < 360 ; i+=120) {
				Bullet bb = new Bullet(new NBT("eid:000|00,o:true,d:" + (6) +",s:6,cx:0,cy:0,x:400,y:50,motion-x:1,motion-y:1,motion-speed:"+ enemy.getM().getSpeed()*1.5), enemy);
				bb.getM().setAngle((this.lifetime+i)%360);
				b.add(bb);
			}

		}
		
	}
}
