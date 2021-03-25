package com.qxbytes.states;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import com.qxbytes.Constants;
import com.qxbytes.Game;
import com.qxbytes.Screen;
import com.qxbytes.audio.AChannel;
import com.qxbytes.io.IOUtils;
import com.qxbytes.sfx.FadeToBlack;
import com.qxbytes.sfx.StateChange;
/**
 * 
 * @author QxBytes
 *
 */
public class GameOver extends State {
	private int timer;
	private int pressedtimer;
	private int progresstimer;
	private boolean mode;
	private boolean pressed;
	public GameOver(JFrame frame) {
		super(frame);
	}

	@Override
	public void render(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		if (!pressed) {
			g.setFont(Constants.DEATH_TEXT);

			g.setColor(Color.GRAY);
			g.drawString("Space to Continue", 32, 442);
			g.setColor(new Color(255,0,0));
			g.drawString("Space to Continue", 30, 440);
			
			g.setColor(Color.GRAY);
			g.drawString("Game Over. . . ", 22, 92);
			g.setColor(new Color(255,0,0));
			g.drawString("Game Over. . . ", 20, 90);
			
			g.setColor(new Color(0,0,0,timer%255));
			g.fillRect(0, 380, Game.WIDTH, 100);
			g.fillRect(0, 0, Game.WIDTH, 100);
		} else {
			if (pressedtimer >= 60) {
				pressedtimer = 60;
			}
		}
		
		
		g.setColor(Color.LIGHT_GRAY); 
		g.fillRect(600, 200, Game.WIDTH-600, 20);
		g.setColor(Color.RED);
		g.fillRect(800, 200, Game.WIDTH-800, 20);
		g.setColor(new Color(0,0,0,pressedtimer*4+15));
		g.fillRect(600, 200, Game.WIDTH-600, 20);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 200+pressedtimer, Game.WIDTH-(Game.WIDTH-600), 20);
		
		g.fillRect(600, 260, progresstimer*4, 20);
		
		for (int i = 20 ; i < 591 ; i += 80) {
			g.setColor(Color.GRAY);
			g.fillOval(i-10, 200-10+pressedtimer, 40, 40);
			
			g.setColor(new Color(2,186,247));
			g.fillOval(i, 200+pressedtimer, 20, 20);
		}
		

		

	}

	@Override
	public void update() {
		AChannel.playTrack(9, true);

		if (timer <= 1 || timer >= 254) {
			mode = !mode;
		}
		if (mode) {
			timer+=2;
		} else {
			timer -=2;
		}
		if (this.getKey().isPressed(32)) {
			pressed = true;
		}
		if (pressed) {
			pressedtimer+=4;
		}
		if (pressedtimer >= 60) {
			progresstimer++;
		}
		if (progresstimer * 4 + 600 > Game.WIDTH) {
			
			Screen.effects.add(new StateChange(0, this.getFrame(), this.getKey()));
			Screen.effects.add(new FadeToBlack(60,Color.WHITE,true));
			
			World w = (World)Screen.states.get(1);
			
//			w.getP().getTag().set("room-x", 5);
//			w.getP().getTag().set("room-y", 5);
//			w.getP().getTag().set("x",50);
//			w.getP().getTag().set("y",50);
//			w.getP().setHit(250);
			w.getP().getS().setHp(w.getP().getS().getMaxhp());
//			w.respawn();
			//IOUtils.load("default\\", w);
			
			IOUtils.savePlayer(w);
			
			timer = 0;
			pressedtimer = 0;
			progresstimer = 0;
			mode = false;
			pressed = false;
		}
	}

}
