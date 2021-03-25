package com.qxbytes.sfx;

import java.awt.Color;

import com.qxbytes.Game;
import com.qxbytes.Screen;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class Scripts {
	public static void changeRoom(World w) {
		//If you are off the screen, it will keep adding this event until its done,
		//So, make sure it gets priority!
		Screen.effects.clear();
		Screen.effects.add(new FadeToBlack(10));
		Screen.effects.add(new ChangeRoom(w));
		Screen.effects.add(new FadeToBlack(10,Color.BLACK,true));
		Game.lag = 0;
	}
	public static void changeState(World w, int num) {
		Screen.effects.clear();
		Screen.effects.add(new FadeToBlack(20));
		Screen.effects.add(new StateChange(num,w.getFrame(),w.getKey()));
		Screen.effects.add(new FadeToBlack(20,Color.BLACK,true));
		Game.lag = 0;
	}
	public static void changeState(World w, int num, Color c) {
		Screen.effects.clear();
		Screen.effects.add(new FadeToBlack(20,c));
		Screen.effects.add(new StateChange(num,w.getFrame(),w.getKey()));
		Screen.effects.add(new FadeToBlack(20,c,true));
		Game.lag = 0;
	}
}
