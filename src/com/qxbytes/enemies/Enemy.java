package com.qxbytes.enemies;

import java.awt.Graphics;

import com.qxbytes.Screen;
import com.qxbytes.entities.Creature;
import com.qxbytes.entities.NBT;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class Enemy extends Creature {

	public Enemy(World w) {
		super(w);
		// TODO Auto-generated constructor stub
	}

	public Enemy(World w, NBT tags) {
		super(w, tags);
		// TODO Auto-generated constructor stub
	}
	public void render(Graphics g) {
		if (this.getTag().sB("flag-noai") == false) {
			if (Screen.state != 5 &&(this.getCurrentTile().getTags().sI("l") != 0 )) {
				return;
			}
		}
		super.render(g);
		//		int m = this.getTag().sI("multiplier");
		//		g.setColor(Color.RED);
		//		g.setFont(Constants.LINE_TEXT);
		//		Utils.drawString(g,""+m,(int)this.getPos().getX()-20,(int)this.getPos().getY()-5);
		//		
	}
	public void update() {
		super.update();
		this.getM().setSpeed((this.getTag().sI("multiplier")/4.0+.7));
	}
	public void init() {
		super.init();
	}
}
