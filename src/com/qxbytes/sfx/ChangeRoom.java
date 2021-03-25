package com.qxbytes.sfx;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import com.qxbytes.Game;
import com.qxbytes.entities.RenderControl;
import com.qxbytes.grid.Grid;
import com.qxbytes.grid.Tile;
import com.qxbytes.io.IOUtils;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class ChangeRoom extends Effect {
	private World w;
	public ChangeRoom(World w) {
		super(2);
		this.w = w;
	}
	public int update() {
		int r = super.update();
		if (r == 2) return 2;
		
		RenderControl rc = (w.getP().getPos());
	
		if (rc.getX() < 0) {

			IOUtils.swapGrid(w, w.getRoomx()-1, w.getRoomy());
			rc.setX(Game.WIDTH);

		} else if (rc.getY() < 0) {
			IOUtils.swapGrid(w, w.getRoomx(), w.getRoomy()-1);
			rc.setY(Game.HEIGHT);

			
		} else if (rc.getX() > Game.WIDTH) {
			
			
			IOUtils.swapGrid(w, w.getRoomx()+1, w.getRoomy());
			rc.setX(0);

		
		} else if (rc.getY() > Game.HEIGHT) {
			
			
			IOUtils.swapGrid(w, w.getRoomx(), w.getRoomy()+1);
			rc.setY(0);
			
		}
		//IOUtils.save(w);
		
		rc.setCx((int)(((double)rc.getX()+(double)rc.getD()/2.0)/(double)(Grid.TILESIZE)));
		
		rc.setCy((int)(((double)rc.getY()+(double)rc.getD()/2.0)/(double)(Grid.TILESIZE)));
		
		List<Tile> clear = w.getG().inrange(rc.getCx(), rc.getCy(), 4);
		
		for (Tile t : clear) {
			t.getTags().set("flag-solid", false);
		}
		
		return this.getOverrideoperation();
	}
	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

	}

}
