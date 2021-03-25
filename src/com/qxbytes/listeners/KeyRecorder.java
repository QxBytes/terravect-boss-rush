package com.qxbytes.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * 
 * @author QxBytes
 *
 */
public class KeyRecorder implements KeyListener {
	private boolean[] keys = new boolean[512];
	@Override
	public void keyPressed(KeyEvent e) {
			keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	public boolean isPressed(int keyCode) {
		return keys[keyCode];
	}
	public void reset() {
		keys = new boolean[512];
	}
}
