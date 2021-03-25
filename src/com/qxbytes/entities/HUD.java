package com.qxbytes.entities;

import java.awt.Color;
import java.awt.Graphics;

import com.qxbytes.Constants;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public class HUD {
	private Player p;
	public HUD(Player p) {
		this.p = p;
	}
	public void render(Graphics g) {
		g.setFont(Constants.LINE_TEXT);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(33, 13, 100, 20);
		
		g.setColor(Color.CYAN);

		double fill = p.getS().getHp();
		double max = p.getS().getMaxhp();
		
		double amount = fill/max;
		
		//System.out.println(amount);
		
		double filled = 100 * amount;
		g.fill3DRect(31,11,(int)filled,20,true);
		
		g.setColor(Color.GRAY);
		g.fillOval(9, 9, 22, 22);
		
		int mode = Math.abs(p.getMode()%4);

		g.setColor(Color.LIGHT_GRAY);
		Utils.drawString(g,""+p.getS().getHp() + "/" +  p.getS().getMaxhp(),40,29);
		g.setColor(Color.GREEN);
		p.drawEarthBar(g);
		g.setColor(Color.RED);
		p.drawFireBar(g);
		g.setColor(Color.BLUE);
		p.drawWaterBar(g);
		g.setFont(Constants.LINE_TEXT);
		Utils.drawString(g, "Grid " + p.getTag().sI("room-x") + " , " + p.getTag().sI("room-y"), 700, 30);
	
		if (mode == 0) {
			g.setColor(Color.RED);
			Utils.drawString(g,"/+ATK [SCROLL]",p.getW().mouseX()+10,p.getW().mouseY()-5);

		} else if (mode == 1) {
			g.setColor(Color.GREEN);
			Utils.drawString(g,"/+SPD [SCROLL]",p.getW().mouseX()+10,p.getW().mouseY()-5);
		} else if (mode == 2){
			g.setColor(Color.BLUE);
			Utils.drawString(g,"/+DEF [SCROLL]",p.getW().mouseX()+10,p.getW().mouseY()-5);
		} else if (mode == 3) {
			g.setColor(this.getP().c);
			Utils.drawString(g,"/+NONE - [SCROLL]",p.getW().mouseX()+10,p.getW().mouseY()-5);

			
		}
		Utils.drawString(g,"",p.getW().mouseX()+10,p.getW().mouseY()+15);

		
		g.fillOval(10, 10, 20, 20);
		
		g.fillRect(p.getW().mouseX()-1, p.getW().mouseY()-10, 2, 20);
		g.fillRect(p.getW().mouseX()-10, p.getW().mouseY()-1, 20, 2);
	}
	public Player getP() {
		return p;
	}
	public void setP(Player p) {
		this.p = p;
	}
	
}
