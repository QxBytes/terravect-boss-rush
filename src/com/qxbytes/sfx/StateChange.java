package com.qxbytes.sfx;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import com.qxbytes.Game;
import com.qxbytes.Screen;
import com.qxbytes.listeners.KeyRecorder;
/**
 * 
 * @author QxBytes
 *
 */
public class StateChange extends Effect {
	private int s;
	private JFrame f;
	private KeyRecorder k;
	public StateChange(int s, JFrame f, KeyRecorder r) {
		super(2);
		
		this.s = s;
		this.f = f;
		this.k = r;
	}

	@Override
	public int update() {
		int r = super.update();
		if (r == 2) return 2;
		Screen.setState(s, f, k);
		return 0;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
	}

}
