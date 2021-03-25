package com.qxbytes.states;

import java.awt.Graphics;

import javax.swing.JFrame;

import com.qxbytes.listeners.KeyRecorder;
import com.qxbytes.listeners.MouseRecorder;
/**
 * 
 * @author QxBytes
 *
 */
public abstract class State {
	private JFrame f;
	private KeyRecorder k;
	private MouseRecorder m;
	public State(JFrame frame) {
		f = frame;
		k = new KeyRecorder();
		m = new MouseRecorder();
		System.out.println(frame);
		f.addMouseMotionListener(m);
		f.addMouseListener(m);
		f.addMouseWheelListener(m);
		
		f.addKeyListener(k);
	}
	public abstract void render(Graphics g);
	public abstract void update();
	public int mouseX() {
		return (int)m.getX();
	}
	public int mouseY() {
		return (int)m.getY();
	}
	public JFrame getFrame() {
		return f;
	}
	public KeyRecorder getKey() {
		return k;
	}
	public MouseRecorder getMouse() {
		return m;
	}
	
}
