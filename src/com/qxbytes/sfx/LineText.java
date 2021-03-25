package com.qxbytes.sfx;

import java.awt.Color;
import java.awt.Graphics;

import com.qxbytes.Constants;
import com.qxbytes.Game;
/**
 * 
 * @author QxBytes
 *
 */
public class LineText extends Effect {
	private String text;
	private int delay;
	private Color c;
	private int current = 0;
	
	public LineText(String words, int delay, Color c) {
		super(999);
		init(words,delay,c);
	}
	public LineText(String words) {
		super(999);
		init(words,10,Color.GREEN);
	}
	
	public void init(String words, int delay, Color c) {
		this.setOverrideoperation(0);
		this.text = words;
		this.delay = delay;
		this.c = c;
	}
	
	public int update() {
		int operation = super.update();
		current = getLifetime()/delay;
		return operation;
	}
	@Override
	public void render(Graphics g) {
		g.setFont(Constants.LINE_TEXT);
		g.setColor(c);
		
		if (current > text.length()) {
			
			this.setTtl(-1);
			this.stopEffect();
			return;
		}
		String display = text.substring(0, current);
		
		g.drawString(display, 20, Game.HEIGHT-20);
	}

}
