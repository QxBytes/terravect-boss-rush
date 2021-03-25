package com.qxbytes.states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.qxbytes.Constants;
import com.qxbytes.Game;
import com.qxbytes.Screen;
import com.qxbytes.sfx.FadeToBlack;
import com.qxbytes.utils.Utils;

public class ExpositionCard extends State {
	private List<String> x = new ArrayList<String>();
	private List<Color> c = new ArrayList<Color>();
	int cooldown = 30;
	public ExpositionCard(JFrame frame) {
		super(frame);
	}
	boolean midtransfer = false;
	@Override
	public void render(Graphics g) {
		if (!midtransfer) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			if (x.size() != 0) {
				g.setColor(c.get(0));
				g.setFont(Constants.LINE_TEXT);
				
				Utils.autoWrapText(g, x.get(0), 50, 50,Game.WIDTH);
				
				g.setColor(Color.DARK_GRAY);
				Utils.drawString(g, "[SPACE]", 10, Game.HEIGHT-10);
			} else {
				Screen.setState(1, getFrame(), getKey());
			}
			
		}
	}

	@Override
	public void update() {
		cooldown--;

		if (midtransfer) {
			x.remove(0);
			c.remove(0);
			Screen.effects.add(new FadeToBlack(60,Color.BLACK,true));


			midtransfer = false;
		}

		if (cooldown <= 0 && (this.getKey().isPressed(32) || this.getMouse().isPressed())) {

			cooldown = 30;

			Screen.effects.add(new FadeToBlack(60,Color.BLACK));
			midtransfer = true;
		}

	}
	public void addCard(String x, Color c) {
		this.x.add(x);
		this.c.add(c);
		Screen.setState(7, this.getFrame(), this.getKey());

	}
}
