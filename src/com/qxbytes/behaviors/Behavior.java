package com.qxbytes.behaviors;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.qxbytes.Game;
import com.qxbytes.audio.Sound;
import com.qxbytes.entities.BManager;
import com.qxbytes.entities.Bullet;
import com.qxbytes.entities.Creature;
import com.qxbytes.entities.Entity;
import com.qxbytes.entities.Player;
import com.qxbytes.entities.RenderControl;
import com.qxbytes.particles.Particle;
import com.qxbytes.particles.ReverseCircle;
import com.qxbytes.particles.TextAt;
/**
 * 
 * @author QxBytes
 *
 */
public abstract class Behavior {
	private Creature c;
	public double x;
	public double y;
	public volatile int lifetime;
	public boolean fakeframe = false;
	private BManager bm;
	private List<Particle> particles = new ArrayList<Particle>();
	public Behavior(Creature c) {
		this.c = c;
	}
	public void update(BManager b, Player p, Creature enemy) {
		bm = b;

		if (!fakeframe) {
			for (List<Entity> e : bm.getBullets()) {
				for (Entity x : e) {
					Bullet y = (Bullet) x;
					y.update(bm);
				}
			}
			for (int i = particles.size()-1 ; i >= 0 ; i--) {
				particles.get(i).update();
				if (particles.get(i).isTombstone()) {
					particles.remove(i);
				}
			}
			//if hit
			if (b.getYourbullets().size() != 0) {
				for (int i = b.getYourbullets().get(0).size() -1 ; i >= 0 ; i--) {
					Bullet y = (Bullet) b.getYourbullets().get(0).get(i);
					y.update(bm);
					boolean coll = Entity.possCollision(y, enemy);
					if (coll) {
						boolean ee = Entity.isCollision(y, enemy);
						if (ee) {
							//System.out.println(p.getS().getAtk());
							//System.out.println(enemy.getS().getHp());
							if (this instanceof ShadowNinjaB && p.getS().getAtk() > 100) {
								enemy.getS().setHp(enemy.getS().getHp()-100);
								particles.add(new TextAt(Color.RED, "-100", x, this.y+20, 100));
							} else {
								enemy.getS().setHp(enemy.getS().getHp()-p.getS().getAtk());
								particles.add(new TextAt(Color.RED, "-"+p.getS().getAtk(), x, this.y+20, 100));
							}
							b.getYourbullets().get(0).remove(i);
							particles.add(new ReverseCircle(Color.DARK_GRAY, 100, 5, this.x, this.y));
							
							new Sound("hit3.wav",-20.0f).play();
						}
					}
				}

			}
			
		}
		RenderControl rc = enemy.getPos();

		if (rc.getX() < 0) {
			rc.setX(Game.WIDTH);
		} else if (rc.getY() < 0) {
			rc.setY(Game.HEIGHT);
		} else if (rc.getX() > Game.WIDTH) {
			rc.setX(0);
		} else if (rc.getY() > Game.HEIGHT) {
			rc.setY(0);
		}
		x = enemy.getPos().getX();
		y = enemy.getPos().getY();
		enemy.update(bm);
		bm.cleanUp();
		lifetime+=1;
	}
	public void render(Graphics g) {
		for (Particle e : particles) {
			e.render(g);
		}
		for (List<Entity> e : bm.getBullets()) {
			for (Entity x : e) {
				Bullet y = (Bullet) x;
				y.render(g);
			}
		}
		for (List<Entity> e : bm.getYourbullets()) {
			for (Entity x : e) {
				Bullet y = (Bullet) x;
				y.render(g);
			}
		}
		c.render(g);
	}
	public List<Particle> getP() {
		return particles;
	}
	public boolean isFakeframe() {
		return fakeframe;
	}
	public void setFakeframe(boolean fakeframe) {
		this.fakeframe = fakeframe;
	}
	public Entity getC() {
		return c;
	}
	public void setC(Creature c) {
		this.c = c;
	}
	public int getLifetime() {
		return lifetime;
	}
	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}
	public BManager getBm() {
		return bm;
	}
	public void setBm(BManager bm) {
		this.bm = bm;
	}

}
