package com.qxbytes.physics;

import com.qxbytes.entities.Entity;
import com.qxbytes.grid.Grid;
/**
 * 
 * @author QxBytes
 *
 */
public class StandardPhysics implements Physics{

	@Override
	public void move(Entity e) {
//		int prevcx = e.getPos().getCx();
//		int prevcy = e.getPos().getCy();
//		double prevx = e.getPos().getX();
//		double prevy = e.getPos().getY();

		double x = e.getM().getX();
		double y = e.getM().getY();
		double s = e.getPos().getD();

		double curx = (double)(e.getPos().getX());
		double cury = (double)(e.getPos().getY());
		
		//experimental (it works???!!!)
		double futx = curx+x;
		double futy = cury+y;
		int futcx = (int)(((double)futx+(double)s/2.0)/(double)(Grid.TILESIZE));
		int futcy = (int)(((double)futy+(double)s/2.0)/(double)(Grid.TILESIZE));
		
		for (int tx = futcx-1 ; tx < futcx+2 ; tx++) {
			for (int ty = futcy-1 ; ty < futcy+2 ; ty++) {
				if (e.getW().getG().getTile(tx, ty).getTags().sB("flag-solid") == true) {
					e.getPos().setCx((int)(((double)curx+(double)s/2.0)/(double)(Grid.TILESIZE)));
					e.getPos().setCy((int)(((double)cury+(double)s/2.0)/(double)(Grid.TILESIZE)));
					return;
				}
			}
		}
		//end experimental
		
		//Middle (both require this snippet)
		//update
		e.getPos().setX((curx+x));
		e.getPos().setY((cury+y));
		//System.out.print(		"X:"+
		e.getPos().setCx((int)(((double)curx+(double)s/2.0)/(double)(Grid.TILESIZE)));

		//);
		//System.out.println(" , Y:"+
		e.getPos().setCy((int)(((double)cury+(double)s/2.0)/(double)(Grid.TILESIZE)));
		//end update
		
		
//		//Non Experimental Below
//		int cx = e.getPos().getCx();
//		int cy = e.getPos().getCy();
//		//);
//		/**
//		 * IMPORTANT: This is high-tec collision
//		 */
//		for (int tx = cx-1 ; tx < cx+2 ; tx++) {
//			for (int ty = cy-1 ; ty < cy+2 ; ty++) {
//				if (e.getW().getG().getTile(tx, ty).getTags().sB("flag-solid") == true) {
//					e.getPos().setCx(prevcx);
//					e.getPos().setCy(prevcy);
//					double xoff = 0;
//					double yoff = 0;
//					if (cx - prevcx > 0) {
//						xoff-=(.1);
//					}
//					if (cx - prevcx < 0) {
//						xoff+=(.1);
//					}
//					if (cy - prevcy > 0) {
//						yoff-=(.1);
//					}
//					if (cy - prevcy < 0) {
//						yoff+=(.1);
//					}
//					//xoff = 0;
//					//yoff = 0;
//					e.getPos().setX( prevx+xoff);
//					e.getPos().setY( prevy+yoff);
//					e.getM().reset();
//					return;
//				} else {
//
//				}
//			}		
//		}
	}

}
