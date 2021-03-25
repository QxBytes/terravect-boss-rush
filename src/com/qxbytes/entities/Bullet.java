package com.qxbytes.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.qxbytes.Constants;
import com.qxbytes.Game;
import com.qxbytes.Screen;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class Bullet extends Entity {
	private Creature enemy;
	public Bullet(NBT args,Creature parent) {
		super(args,(World)Screen.states.get(1));
		//usually 12
		this.getPos().setD(args.sI("d"));
		this.setState(6);
		this.enemy =parent;
		int t = parent.getTag().sI("type");
		
			
		if (t == 1) {
			this.setEID(Constants.wbEID);
		} else if (t == 2) {
			this.setEID(Constants.ebEID);
		} else if (t == 3) {
			this.setEID(Constants.fbEID);
		}
	}
	@Override
	public void onDeath() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpawn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHit() {
		// TODO Auto-generated method stub
		
	}
	public void update() {
		try {
			throw new Exception();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void update(BManager bm) {
		super.move(bm);
		setLifetime(getLifetime()+1);

	}

	@Override
	public void render(Graphics g) {

		if (this.getPos().isO()
				&& this.getPos().getX()< Game.WIDTH
				&& this.getPos().getY()< Game.HEIGHT
				&& this.getPos().getX()> 0
				&& this.getPos().getY()> 0
				) {
			
			int x = (int)this.getPos().getX();
			int y = (int)this.getPos().getY();
			int s = (int)this.getPos().getD();//WIDTH AND HEIGHT
			
			int centerx = (int)getPos().getCenterX();
			int centery = (int)getPos().getCenterY();
			
			s*=4;centerx -= s/2 ; centery -= s/2;

			this.getAm().doAnimate(g, centerx, centery, s,this.getState(),this.getLifetime());
			g.setColor(Color.RED);
			
			if (INIT.DEBUG)g.drawOval(x,y,s/4,s/4);
		} else {
			this.setRender(false);
		}
	}

	@Override
	public void onDeath(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpawn(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHit(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	public Creature getEnemy() {
		return enemy;
	}
	public void setEnemy(Creature enemy) {
		this.enemy = enemy;
	}
	
}
