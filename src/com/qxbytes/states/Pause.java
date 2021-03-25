package com.qxbytes.states;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import com.qxbytes.Constants;
import com.qxbytes.Screen;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public class Pause extends State {

	public Pause(JFrame frame) {
		super(frame);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(Constants.DEATH_TEXT);
		Utils.drawString(g, "Paused... Press [SPACE] resume or [X] to quit", 10, 60);
		
	}

	@Override
	public void update() {
		if (getKey().isPressed(32)) {
			Screen.setState(1,getFrame(),getKey());
		}
	}

}
