package com.qxbytes.physics;

import com.qxbytes.entities.Entity;
import com.qxbytes.grid.Grid;
/**
 * 
 * @author QxBytes
 *
 */
public class NoPhysics implements Physics{
	public void move(Entity e) {

		double x = e.getM().getX();
		double y = e.getM().getY();
		double s = e.getPos().getD();

		double curx = (double)(e.getPos().getX());
		double cury = (double)(e.getPos().getY());

		e.getPos().setX((curx+x));
		e.getPos().setY((cury+y));

		e.getPos().setCx((int)(((double)curx+(double)s/2.0)/(double)(Grid.TILESIZE)));

		e.getPos().setCy((int)(((double)cury+(double)s/2.0)/(double)(Grid.TILESIZE)));
	}
}
