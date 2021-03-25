package com.qxbytes.states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.qxbytes.Constants;
import com.qxbytes.Game;
import com.qxbytes.SColor;
import com.qxbytes.Screen;
import com.qxbytes.audio.AChannel;
import com.qxbytes.audio.Audio;
import com.qxbytes.audio.Sound;
import com.qxbytes.enemies.FearahnaFish;
import com.qxbytes.enemies.GillSoldier;
import com.qxbytes.enemies.Grassassin;
import com.qxbytes.enemies.Growlem;
import com.qxbytes.enemies.Leafie;
import com.qxbytes.enemies.Moltron;
import com.qxbytes.enemies.Plasmatrix;
import com.qxbytes.enemies.SandWarrior;
import com.qxbytes.enemies.ShadowNinja;
import com.qxbytes.entities.Creature;
import com.qxbytes.entities.INIT;
import com.qxbytes.entities.Player;
import com.qxbytes.rss.ImageUtils;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public class Title extends State {
	private int[] cloudpos = new int[5];
	private List<Creature> actors = new ArrayList<Creature>();
	int position = 00;
	int keycooldown = 100;
	public Title(JFrame frame) {
		super(frame);
		for (int i = 0 ; i < cloudpos.length ; i++) {
			cloudpos[i] = Utils.randomX()-100;
		}

		actors.add(new ShadowNinja(null));
		actors.add(new Plasmatrix(null));
		actors.add(new Moltron(null));
		actors.add(new Grassassin(null));
		actors.add(new Growlem(null));
		actors.add(new Leafie(null));
		actors.add(new SandWarrior(null));
		actors.add(new GillSoldier(null));
		actors.add(new FearahnaFish(null));
		actors.add(new Player(null));


		for (int i = 0 ; i < actors.size() ; i++) {
			actors.get(i).getTag().set("flag-noai", true);
			actors.get(i).getPos().setX(Game.WIDTH/((double)actors.size()*1.5) * i + 000);
			actors.get(i).getPos().setY(360);
		}

	}

	@Override
	public void render(Graphics g) {
		g.setColor(SColor.LIGHT_BLUE);
		g.fillRect(0, 0, Game.WIDTH	, Game.HEIGHT);


		g.drawImage(ImageUtils.getBattleImage(0), position%3000, 0, null);
		g.drawImage(ImageUtils.getBattleImage(0), (position)%3000+3000, 0, null);
		//g.drawImage(ImageUtils.getBattleImage(0), (position)%3000, 0, null);


		g.setColor(SColor.DARK_GREEN);
		g.setFont(Constants.TITLE_TEXT);
		Utils.drawString(g,"TERRAVECT", 10, 105);

		for (int i = 0 ; i < cloudpos.length ; i++) {
			drawCloud(g,cloudpos[i],100);
		}

		g.setFont(Constants.LINE_TEXT);

		g.setColor(Color.RED);
		Utils.drawString(g, "Press: ", 10, 150);

		g.setColor(SColor.DARK_RED);
		Utils.drawString(g, "[P]", 10, 170);
		Utils.drawString(g, "[N]", 10, 190);
		Utils.drawString(g, "[M]", 10, 210);
		Utils.drawString(g, "[I]", 10, 230);
		g.setColor(Color.BLUE);
		Utils.drawString(g, " - Play", 80, 170);
		Utils.drawString(g, " - Unmute Sounds", 80, 190);
		Utils.drawString(g, " - Unmute Music", 80, 210);
		Utils.drawString(g, " - Credits", 80, 230);
		g.setColor(SColor.DARK_GRAY);
		Utils.drawString(g, Constants.VERSION, 5, Game.HEIGHT-5);

		for (Creature c : actors) {
			c.render(g);
		}
	}
	public void drawCloud(Graphics g,int x, int y) {
		g.setColor(SColor.WHITE);
		g.fillOval(x-1, y-6, 102, 27);
		g.fillOval(x-21, y+1, 42, 22);
		g.fillOval(x+81, y+1, 42, 22);
		g.setColor(new Color(255,255,255));
		g.fillOval(x, y-5, 100, 25);
		g.fillOval(x-20, y, 40, 20);
		g.fillOval(x+80, y, 40, 20);
	}
	@Override
	public void update() {
		if (Screen.runtime % 10 == 0) {
			//AChannel.playTrack(5, true);
		}
		if (Screen.runtime % 2 == 0) {
			position-=1;
		}
		for (int i = 0 ; i < cloudpos.length ; i++) {
			if (Screen.runtime % 3 == 0) {
				cloudpos[i] -=1;
			}

			if (cloudpos[i] < -140) {
				cloudpos[i] = Game.WIDTH+100;
			}
		}
		for (Creature c : actors) {
			c.update();
		}
		if (this.getKey().isPressed(80) || this.getKey().isPressed(32)) {
			FearahnaFish enemy = new FearahnaFish((World)Screen.states.get(1));
			((Encounter)Screen.states.get(5)).startBossRush(enemy, ((World)Screen.states.get(1)).getP());
		}
		if (this.getKey().isPressed(73)) {
			/*
			 * -\\\1 - Low Essence - Ars Sonor - Centralstation
			 * -\\\2 - Final Battle - Ars Sonor - The Frequency
			 * -\\\3 - Jungle Battle - Artofescapism - The Water Embers
			 * \\\4 - Final Battle Last Phases - Artofescapism - Where Are Your Friends?
			 * \\\5 - Title - Chris Zabriskie - Air Hockey Saloon
			 * -\\\6 - Fire Battle - Daniel Birch - Caught in the Crossfire
			 * -\\\7 - Jungle Ambient - Daniel Birch - Temple
			 * -\\\8 - Fire Ambient - Daniel Birch - Too Late to Turn Back
			 * -\\\9 - Water Ambient - Lee Osevere - Just Blue Sky
			 * -\\10 - Water Battle - Remain - Acid
			 * -\\11 - Final Battle Last Phase - Remain - The Void
			 * -\\12 - Mystery Zone - Remain - Digimetery
			 * 
			 */
			Screen.setState(2, this.getFrame(), this.getKey());
			
		}
		if (keycooldown < 0) {
			if (this.getKey().isPressed(78)) {
				Sound.muteSounds = !Sound.muteSounds;
				keycooldown = 100;
			}
			if (this.getKey().isPressed(77)) {
				Audio.muteMusic = !Audio.muteMusic;
				if (Audio.muteMusic == true) {
					AChannel.stopTrack();
				} else {
					System.out.println(Audio.muteMusic);
					AChannel.playTrack(5, true);
				}
			
				keycooldown = 100;
			}
			if (this.getKey().isPressed(79)) {
				INIT.OP = !INIT.OP;
				keycooldown = 100;
			}
		}
		

		keycooldown--;
	}

}
