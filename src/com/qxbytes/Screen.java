package com.qxbytes;

import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.swing.JFrame;

import com.qxbytes.listeners.KeyRecorder;
import com.qxbytes.log.GLog;
import com.qxbytes.sfx.Effect;
import com.qxbytes.states.Custom;
import com.qxbytes.states.Encounter;
import com.qxbytes.states.ExpositionCard;
import com.qxbytes.states.GameOver;
import com.qxbytes.states.Pause;
import com.qxbytes.states.End;
import com.qxbytes.states.State;
import com.qxbytes.states.Title;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class Screen {
	public static int runtime = 0;
	public static int state = 0;
	public static List<State> states = new ArrayList<State>();
	public static Queue<Effect> effects = new ArrayDeque<Effect>();
	public static GLog log = new GLog(true);
	public static void init(JFrame f) {
		states.add(new Title(f));
		states.add(new World(f));
		states.add(new End(f));
		states.add(new Custom(f));
		states.add(new Pause(f));
		states.add(new Encounter(f));
		states.add(new GameOver(f));
		states.add(new ExpositionCard(f));
		
		//effects.add(new FadeToBlack(60, new Color(0,0,0),true));
		
	}
	/**
	 * Important: Only use the screen class for testing. Do not put
	 * any main code here.
	 * @param g
	 */
	public static void render(Graphics g) {

		Effect current = effects.peek();

		states.get(state).render(g);
		
		if (current != null) effects.peek().render(g);

	}
	public static void update() {
		runtime++;

		Effect current = effects.peek();

		if (current != null) {
			int operation = effects.peek().update();

			if (operation == 0 || operation == 2) return;
		}

		states.get(state).update();


	}
	public static void setState(int s, JFrame f, KeyRecorder r) {
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//r.reset();
		Screen.state = s;


	}
}
