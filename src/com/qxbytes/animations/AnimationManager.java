package com.qxbytes.animations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
/**
 * 
 * @author QxBytes
 *
 */
public class AnimationManager {
	BufferedImage[] sprites;
	//states: 0 = idle 1 = left 2 = right 3 = primary 4 = secondary 5 = collision  6 = spawn 7 = death
	public AnimationManager(BufferedImage[] sprites) {
		this.sprites = sprites;
	}
	public void doAnimate(Graphics g, int x, int y, int s, int state, int lifetime) {
		Animations.doAnimation(g, sprites, x, y, s,state,  lifetime);
	}
	public BufferedImage[] getSprites() {
		return sprites;
	}
	public void setSprites(BufferedImage[] sprites) {
		this.sprites = sprites;
	}
	
}
