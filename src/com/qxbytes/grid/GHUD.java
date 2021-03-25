package com.qxbytes.grid;

import java.awt.Color;
import java.awt.Graphics;

import com.qxbytes.Constants;
import com.qxbytes.Game;
import com.qxbytes.entities.Player;
import com.qxbytes.states.World;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public class GHUD {
	private Grid g;
	private World w;
	public GHUD(World w, Grid g) {
		this.g = g;
		this.w = w;
	}
	public void render(Graphics g) {
		g.setColor(Color.MAGENTA);
		if (Utils.distanceFrom(w.getP().getPos().getCx(), w.getP().getPos().getCy(), this.g.getMouseTile().getPos().getCx(), this.g.getMouseTile().getPos().getCy()) <= Player.reach*2) {
			g.fillRect((int)this.g.getMouseTile().getPos().getCx()*Grid.TILESIZE,(int)this.g.getMouseTile().getPos().getCy()*Grid.TILESIZE,Grid.TILESIZE,Grid.TILESIZE);
		}
		Utils.drawString(g, this.g.getMouseTile().getEss().getFire() + ","
				+  this.g.getMouseTile().getEss().getEarth() + "," 
				+  this.g.getMouseTile().getEss().getWater() + "", 20, Game.HEIGHT-24);
		double above = (Utils.percent(this.g.getMeta().sI("netchange")+this.g.getMeta().sI("initialchange"), Game.TILESX*Game.TILESY*765)-50)/5;

		g.setColor(Color.RED);
		if (this.g.getMeta().sB("perm-build") == true) {
			g.setColor(Color.YELLOW);
		}
		if (above >= 5) {
			g.setColor(Color.CYAN);
		}
		Utils.drawBar(g, Game.WIDTH-150, 40, this.g.getMeta().sI("netchange")+this.g.getMeta().sI("initialchange"), Game.TILESX*Game.TILESY*765, 100, true);

		if (above > 0) {
			g.setColor(Color.YELLOW);
			for (int i = 0 ; i < above; i++) {
				if (i >= 5) {
					g.setColor(Color.CYAN);
				}
				Utils.drawString(g, "|*|", Game.WIDTH-(150-(i*10)), 60);
				g.setColor(g.getColor());
			}
		}
		g.setColor(Color.WHITE);
		g.setFont(Constants.LINE_TEXT);
		if (!w.getKey().isPressed(32)) {
			Utils.drawString(g, "SPACE (Help)", 20, 440);
		}

	}
	public void update() {

	}
}
