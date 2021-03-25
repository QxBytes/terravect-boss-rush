package com.qxbytes.animations;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import com.qxbytes.Screen;
import com.qxbytes.entities.INIT;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public class Animation {
	int[] frames;
	int delay; //(length of one frame)
	boolean loop;
	/**
	 * Choreograph: [numberinspritearray],[nextnumberinspritearray]- Cannot be zero/null
	 * Delay: Between two frames in amount of updates
	 * Loop: Loop when done?
	 * @param choreograph
	 * @param delay
	 * @param loop
	 */
	public Animation(String choreograph, int delay, boolean loop) {
		List<String> rawframes = Utils.parseValues(choreograph, ',');
		frames = new int[rawframes.size()];
		for (int i = 0 ; i < rawframes.size() ; i++) {
			frames[i] = Integer.parseInt(rawframes.get(i));
		}
		this.delay = delay;
		this.loop = loop;
	}
	public void doAnimation(Graphics g, BufferedImage[] sprites, int x, int y, int s, int lifetime) {
		int totaltime = frames.length * delay;
		int currentframe = Screen.runtime % totaltime / delay;
		//System.out.println(s);
		if (INIT.DEBUG)g.drawRect(x, y, s, s);
		g.drawImage(sprites[frames[currentframe]], x, y, s,s,null);
		
	}
}
