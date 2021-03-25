package com.qxbytes.states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JFrame;

import com.qxbytes.Game;
import com.qxbytes.Screen;
import com.qxbytes.entities.HUD;
import com.qxbytes.entities.Player;
import com.qxbytes.grid.Grid;
import com.qxbytes.grid.Tile;
import com.qxbytes.io.IOUtils;
/**
 * 
 * @author QxBytes
 *
 */
public class World extends State{
	private Grid g;
	private Player p;
	private  int roomx;
	private  int roomy;
	private HUD hud;
	public World(JFrame frame) {
		super(frame);

		boolean success = IOUtils.load("default\\", this);
		if (success == false) {
			p = new Player(this);
			g = new Grid(this,5,5);
			this.roomx = p.getTag().sI("room-x");
			this.roomy = p.getTag().sI("room-y");
		}
		
		//p.getEss().addFire(1000000);
		
		List<Tile> clear = getG().inrange(getP().getPos().getCx(), getP().getPos().getCy(), 4);
		
		for (Tile t : clear) {
			t.getTags().set("flag-solid", false);
		}
		hud = new HUD(p);
	}
	public void respawn() {
		//		boolean success = IOUtils.load("default\\", this);
		//		if (success == false) {
		//			p = new Player(this);
		//			g = new Grid(this,5,5);
		//			this.roomx = p.getTag().sI("room-x");
		//			this.roomy = p.getTag().sI("room-y");
		//		}
		IOUtils.swapGrid(this, 5, 5);

	}
	public void render(Graphics g) {
		g.setColor(new Color(28,228,255));
		g.fillRect(0, 0, Game.WIDTH	+2, Game.HEIGHT);
		this.g.render(g);

		//last
		p.render(g);

		hud.setP(p);
		hud.render(g);
	}
	boolean x = false;
	public void update() {
		g.update();
		Screen.log.addMessage(getP().getPos().getCx()+","+getP().getPos().getCy(),20);
		Screen.log.addMessage(getP().getPos().getX()+","+getP().getPos().getY(),20);
		if (x == false) {

			x = true;
		}
		p.update();
	}

	public void setG(Grid g) {
		this.g = g;
	}
	public int getRoomx() {
		return roomx;
	}
	public void setRoomx(int roomx) {
		this.roomx = roomx;
	}
	public int getRoomy() {
		return roomy;
	}
	public void setRoomy(int roomy) {
		this.roomy = roomy;
	}
	/**
	 * @return the p
	 */
	public Player getP() {
		return p;
	}
	/**
	 * @param p the p to set
	 */
	public void setP(Player p) {
		this.p = p;
	}

	/**
	 * @return the g
	 */
	public Grid getG() {
		return g;
	}

	public void changeRoom() {
		//TODO:
	}

}
