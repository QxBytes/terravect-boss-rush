package com.qxbytes.states;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import com.qxbytes.Constants;
import com.qxbytes.Game;
import com.qxbytes.SColor;
import com.qxbytes.Screen;
import com.qxbytes.utils.Utils;

public class End extends State {

	public End(JFrame frame) {
		super(frame);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(SColor.GRAY);
		g.setFont(Constants.LINE_TEXT);
		Utils.autoWrapText(g, "'Programming' and visuals: QxBytes", 50, 50, Game.WIDTH);
		
		Utils.autoWrapText(g, " Low Essence - Ars Sonor - Centralstation // Final Battle - Ars Sonor - The Frequency // Jungle Battle - Artofescapism - The Water Embers // Final Battle Last Phases - Artofescapism - Where Are Your Friends? // Title - Chris Zabriskie - Air Hockey Saloon \r\n" + 
				"					+   // Fire Battle - Daniel Birch - Caught in the Crossfire // Jungle Ambient - Daniel Birch - Temple // Fire Ambient - Daniel Birch - Too Late to Turn Back \r\n" + 
				"					+   // Water Ambient - Lee Osevere - Just Blue Sky // Water Battle - Remain - Acid // Final Battle Last Phase - Remain - The Void // Mystery Zone - Remain - Digimetery", 50, 100,Game.WIDTH);
		Utils.autoWrapText(g, "Press [X] to continue", 50, 400, Game.WIDTH);
		
		g.setColor(Color.DARK_GRAY);
		
	}

	@Override
	public void update() {
		if (this.getKey().isPressed(88)) {
			Screen.setState(0, getFrame(), this.getKey());
		}
	}

}
