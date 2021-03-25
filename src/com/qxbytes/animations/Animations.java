package com.qxbytes.animations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author QxBytes
 *
 */
public class Animations {
	static List<Animation> animations = new ArrayList<Animation>();
	//states: 0 = idle 1 = left 2 = right 3 = primary 4 = secondary 5 = collision  6 = spawn 7 = death

	public static void init() {
		animations.add(new Animation("0,1",20,true));
		animations.add(new Animation("2,4,3,4",16,true));
		animations.add(new Animation("6,5,7,5",16,true));
		animations.add(new Animation("0,1",60,true));
		animations.add(new Animation("0,1",60,true));
		animations.add(new Animation("0,1",60,true));
		animations.add(new Animation("0,1",60,true));
		
		animations.add(new Animation("0,1",60,true));
		

	}
	public static void doAnimation(Graphics g, BufferedImage[] sprites, int x, int y, int s, int state, int lifetime) {
		//set as STATE instead of 0 (this is temporary)
		animations.get(state).doAnimation(g, sprites, x, y, s,lifetime);
	}
}
