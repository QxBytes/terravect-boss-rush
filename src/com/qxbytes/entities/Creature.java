package com.qxbytes.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.qxbytes.Constants;
import com.qxbytes.Game;
import com.qxbytes.Screen;
import com.qxbytes.behaviors.Behavior;
import com.qxbytes.components.EComp;
import com.qxbytes.grid.Tile;
import com.qxbytes.states.World;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public class Creature extends Entity implements Constants {
	private List<EComp> components;
	private Stats s;
	private Behavior b;
	public Creature( World w, NBT tags) {
		super(tags,w);
		init();

	}
	public Creature(World w) {
		super(new NBT(Constants.DEFAULT_NBT),w);

		init();

		this.getPos().setX( 20);
		this.getPos().setY( Utils.randomY());


	}
	public void init() {
		this.s = new Stats(this.getTag());
		this.components = new ArrayList<EComp>();

	}
	public String toString() {
		s.saveComponent(this.getTag());
		return super.toString();
	}
	@Override
	public void onDeath() {
	}

	@Override
	public void onSpawn() {
	}

	@Override
	public void onHit() {
	}

	@Override
	public void update() {
		this.setLifetime(this.getLifetime()+1);
		onSpawn();
		onHit();
		onDeath();
		
		if (this.getTag().sB("flag-noai") == true) {
			return;
		}
		
		super.move();

		for (int i = 0 ; i < components.size() ; i++) {
			components.get(i).modify();
		}
	}
	public void update(BManager b) {
		this.setLifetime(this.getLifetime()+1);
		onSpawn();
		onHit();
		onDeath();
			super.move(b);

		for (int i = 0 ; i < components.size() ; i++) {
			components.get(i).modify();
		}
	}

	@Override
	public void render(Graphics g) {
		if (this.getPos().isO()
				&& this.getPos().getX()< Game.WIDTH
				&& this.getPos().getY()< Game.HEIGHT
				&& this.getPos().getX()> 0
				&& this.getPos().getY()> 0
				) {
			if (this.getTag().sB("flag-noai") == false) {
				if (Screen.state != 5 &&(this.getCurrentTile().getTags().sI("l") != 0 && !(this instanceof Player))) {
					return;
				}
			}
			for (int i = 0 ; i < components.size() ; i++) {
				components.get(i).premodifyGFX(g);
			}

			//int x = (int)this.getPos().getX();
			//int y = (int)this.getPos().getY();
			int s = (int)this.getPos().getD();//WIDTH AND HEIGHT

			int centerx = (int)getPos().getCenterX();
			int centery = (int)getPos().getCenterY();

			s*=4;centerx -= s/2 ; centery -= s/2;
			if (s >= 100) {
				s = (int)this.getPos().getD()*2;//WIDTH AND HEIGHT

				centerx = (int)getPos().getCenterX()-s/2;
				centery = (int)getPos().getCenterY()-s/2;
			}
			centery -= s/4;//footing
			//g.drawImage(temp, x, y, s, s, null);
			//MILIS AND STATE
			this.getAm().doAnimate(g, centerx, centery, s,this.getState(),this.getLifetime());
			g.setColor(Color.RED);
			//g.drawRect((int)this.getTag().searchDouble("x"), (int)this.getTag().searchDouble("y"), (int)this.getTag().searchDouble("d"), (int)this.getTag().searchDouble("d"));
			for (int i = 0 ; i < components.size() ; i++) {
				components.get(i).modifyGFX(g);
			}

			//if (INIT.DEBUG)g.drawOval(x,y,s/4,s/4);

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

	public Tile getCurrentTile() {
		return this.getW().getG().getTile(this.getPos().getCx(), this.getPos().getCy());
	}
	public Stats getS() {
		return s;
	}
	public void setS(Stats s) {
		this.s = s;
	}
	public Behavior getB() {
		return b;
	}
	public void setB(Behavior b) {
		this.b = b;
	}
	/**
	 * @return the components
	 */
	public List<EComp> getComponents() {
		return components;
	}
	/**
	 * @param components the components to set
	 */
	public void setComponents(List<EComp> components) {
		this.components = components;
	}

}
