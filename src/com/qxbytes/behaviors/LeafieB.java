package com.qxbytes.behaviors;

import com.qxbytes.Game;
import com.qxbytes.audio.Sound;
import com.qxbytes.entities.BManager;
import com.qxbytes.entities.Bullet;
import com.qxbytes.entities.Creature;
import com.qxbytes.entities.Motion;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Player;
/**
 * 
 * @author QxBytes
 *
 */
public class LeafieB extends Behavior {
	int barrier = 20;
	boolean angered = false;
	public LeafieB(Creature c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	public void update(BManager b, Player p, Creature enemy) {
		super.update(b, p, enemy);

		if (lifetime % 5 == 0) {
			Bullet be = new Bullet(new NBT ("d:12,o:true,eid:000|0|0,x:" + 1 + ",y:" + (barrier%Game.HEIGHT-28) + ",motion-speed:1,motion-x:" + 8 + ",motion-y:" + 0), enemy);
			b.add(be);
			Bullet be1 = new Bullet(new NBT ("d:12,o:true,eid:000|0|0,x:" + (Game.WIDTH-1) + ",y:" + (barrier%Game.HEIGHT) + ",motion-speed:1,motion-x:" + -8 + ",motion-y:" + 0), enemy);
			b.add(be1);
			Bullet be2 = new Bullet(new NBT ("d:12,o:true,eid:000|0|0,x:" + 1 + ",y:" + (barrier%Game.HEIGHT+28) + ",motion-speed:1,motion-x:" + 8 + ",motion-y:" + 0), enemy);
			b.add(be2);
		}
		if (lifetime % 16 == 0) {
			barrier++;
		}
		if (lifetime % 120 == 0) {
			new Sound("hit1.wav",6.0f).play();
			for (int i = 0 ; i < 360 ; i+=18) {
				Bullet be = new Bullet(new NBT ("d:12,o:true,eid:0|0|0,x:" + x + ",y:" + y + ""),enemy);
				be.setM(new Motion(i,1));
				b.add(be);
			}
		}
		enemy.getPos().setY(barrier%Game.HEIGHT);
		if (enemy.getS().getHp() < enemy.getS().getMaxhp()/3) {
			
			if (!angered) {
				enemy.setM(new Motion(180,3));
			}
			angered = true;
			
			if (enemy.getPos().getX() > Game.WIDTH-100) {
				enemy.setM(new Motion(180,3));
			} else if (enemy.getPos().getX() < 100){
				enemy.setM(new Motion(0,3));
			}

		}
	}
}
