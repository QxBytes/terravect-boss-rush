package com.qxbytes.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.qxbytes.Game;
/**
 * 
 * @author QxBytes
 *
 */
public class MouseRecorder implements MouseListener, MouseMotionListener, MouseWheelListener {
	private double x = 50;
	private double y = 50;
	private int wheelmotion;
	private boolean pressed = false;
	private int buttonpress;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		 
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
		buttonpress = e.getButton();
		System.out.println(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		pressed = true;
		x = arg0.getX();
		y = arg0.getY();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		x = arg0.getX();
		y = arg0.getY();
	}

	public int getButton() {
		return buttonpress;
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x - Game.insets.left;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y - Game.insets.top;
	}
	public void reset() {
		pressed = false;
	}
	public boolean isPressed() {
		return pressed;
	}
	public int wheelMotion() {
		int temp = wheelmotion;
		wheelmotion = 0;
		return temp;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		wheelmotion = arg0.getWheelRotation();
		//works
	}

}
