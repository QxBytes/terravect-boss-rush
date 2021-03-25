package com.qxbytes.states;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import com.qxbytes.Game;
import com.qxbytes.Screen;
import com.qxbytes.audio.AChannel;
import com.qxbytes.components.HealthBar;
import com.qxbytes.enemies.Enemy;
import com.qxbytes.enemies.FearahnaFish;
import com.qxbytes.enemies.GillSoldier;
import com.qxbytes.enemies.Grassassin;
import com.qxbytes.enemies.Growlem;
import com.qxbytes.enemies.Leafie;
import com.qxbytes.enemies.Moltron;
import com.qxbytes.enemies.Plasmatrix;
import com.qxbytes.enemies.SandWarrior;
import com.qxbytes.enemies.ShadowNinja;
import com.qxbytes.entities.BManager;
import com.qxbytes.entities.Creature;
import com.qxbytes.entities.HUD;
import com.qxbytes.entities.Player;
import com.qxbytes.grid.Summoner;
import com.qxbytes.rss.ImageUtils;
import com.qxbytes.sfx.FadeToBlack;
import com.qxbytes.sfx.LineText;
import com.qxbytes.sfx.Scripts;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public class Encounter extends State {
	private Creature enemy;
	private Player player;
	private BManager b = new BManager();
	private HUD hud;
	private int select = 0;
	private int lifetime = 0;
	
	private int currentBoss = 0;

	public Encounter(JFrame frame) {
		super(frame);
		hud = new HUD(player);
	}
	@Override
	public void render(Graphics g) {
		//g.setColor(Color.BLUE);
		
		g.drawImage(ImageUtils.getBattleImage(select), 0, (lifetime/5)%Game.HEIGHT, null);
		g.drawImage(ImageUtils.getBattleImage(select), 0, (lifetime/5)%Game.HEIGHT-Game.HEIGHT, null);
		
		player.render(g);
		enemy.getB().render(g);
		hud.render(g);
		
	}

	@Override
	public void update() {
		int i = enemy.getTag().sI("multiplier");
		
		for (; i >= 0 ; i--) {
			enemy.getB().update(b,player,enemy);
			if (i > 0) {
				enemy.getB().setFakeframe(true);
			}
		}
		enemy.getB().setFakeframe(false);
		
		player.update(b);
		//WIN
		if (enemy.getS().getHp() <= 0) {
			
			if (((World)Screen.states.get(1)).getG().getMeta().sB("perm-build") == false) {
				Screen.effects.add(new LineText("Next Boss...                           "
						+ "         ",3,Color.CYAN));
			}

			
			player.setHit(250);
			
			player.getM().reset();
			
			Utils.moveEssenceEarthFrom(enemy, player, 9999999);
			Utils.moveEssenceFireFrom(enemy, player, 9999999);
			Utils.moveEssenceWaterFrom(enemy, player, 9999999);
			
			Runtime.getRuntime().gc();
			
			
			
			Enemy[] bosses = {
					new FearahnaFish((World)Screen.states.get(1)),
					new GillSoldier((World)Screen.states.get(1)),
					new SandWarrior((World)Screen.states.get(1)),
					new Leafie((World)Screen.states.get(1)),
					new Growlem((World)Screen.states.get(1)),
					new Grassassin((World)Screen.states.get(1)),
					new Moltron((World)Screen.states.get(1)),
					new Plasmatrix((World)Screen.states.get(1)),
					new ShadowNinja((World)Screen.states.get(1))};
			currentBoss++;
			if (currentBoss == bosses.length) {
				Screen.setState(2, this.getFrame(), this.getKey());
				return;
			}
			Summoner.multiply(bosses[currentBoss], currentBoss);
			setEnemy(bosses[currentBoss],player);
			Screen.effects.add(new LineText("Lvl " + (currentBoss+1) + "  ", 5, Color.BLACK));
			Screen.effects.add(new FadeToBlack(40, Color.WHITE, false));

		}
		lifetime++;
	}
	public Creature getEnemy() {
		return enemy;
	}
	public void setEnemy(Creature enemy, Player p) {
		p.gT().set("temp-x", p.getPos().getX());
		p.gT().set("temp-y", p.getPos().getY());
		b= new BManager();
		this.enemy = enemy;
		this.player = p;
		p.setHit(120);
		p.setSpawn(10);
		hud.setP(p);
		
		
		//Summoner.deriveBehavior(enemy);
		//System.out.println(enemy.getB());
		enemy.getComponents().add(new HealthBar(enemy));
		enemy.getPos().setX(Game.WIDTH/2);
		enemy.getPos().setY(40);
		enemy.getM().reset();
		enemy.getPos().setD(32);
		
		enemy.getB().update(b,player,enemy);
		
		select = enemy.getTag().sI("type");
		
		Scripts.changeState((World)Screen.states.get(1), 5);
		
		if (enemy.getTag().sI("type") == 1) {
			AChannel.playTrack(10, true);
		} else if (enemy.getTag().sI("type") == 2) {
			AChannel.playTrack(3, true);
		} else if (enemy.getTag().sI("type") == 3) {
			AChannel.playTrack(6, true);
		}

	}
	public void startBossRush(Creature enemy, Player p){
		currentBoss = 0;
		setEnemy(enemy,p);	
	}
}
