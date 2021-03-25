package com.qxbytes.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;

import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import com.qxbytes.Constants;
import com.qxbytes.Game;
import com.qxbytes.Screen;
import com.qxbytes.audio.Sound;
import com.qxbytes.components.HealthBar;
import com.qxbytes.grid.OpenSimplexNoise;
import com.qxbytes.sfx.Scripts;
import com.qxbytes.states.Encounter;
import com.qxbytes.states.World;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public class Player extends Creature {
	public static int reach = 6;
	public static int brush = 4;

	public Color c = new Color(0,0,0);
	public int mode;

	private int flvl = 0;
	private int elvl = 0;
	private int wlvl = 0;
	private int fmax = 0;
	private int emax = 0;
	private int wmax = 0;
	private int fmin = 0;
	private int emin = 0;
	private int wmin = 0;

	//this constructor is used when a player exists already.
	public Player(World w, NBT tags) {
		super(w,tags);
		System.out.println("The player exists");
		OpenSimplexNoise.setSeed(tags.sI("seed"));

		this.getComponents().add(new HealthBar(this));




	}
	public Player(World w) {
		super(w,new NBT(Constants.DEFAULT_NBT));

		this.gT().set("room-x", 5);
		this.gT().set("room-y", 5);

		int seed = 0;
		boolean valid = false;

		do {
			seed = new Random().nextInt();
			OpenSimplexNoise.setSeed(seed);
			double height = OpenSimplexNoise.generateNoise(5, 5) * 256;

			if (height < 70 ) {
				//System.out.println(height);
				valid = true;
			}

		} while (valid == false);
		//REDO
		this.gT().set("seed", seed);

		System.out.println("DEFAULT PLAYER (new player created)");

		getPos().setD( 16);
		getEss().setLimit(9999999);
		getS().setAtk(1);

		this.getComponents().add(new HealthBar(this));

	}
	public void render(Graphics g) {
		//System.out.println(this.getTag());
		if (getHit() > 125) {
			if (this.getHit() > 0 && this.getLifetime() % 50 >= 25) {
				
				return;
			}
		} else
		if (this.getHit() > 0 && this.getLifetime()/(getHit()*2) % 2 == 0) {
			
			return;
		}
		if (this.getTag().sB("flag-noai") == false) {
			g.setColor(Color.RED);
			g.fillOval((int)this.getPos().getX(), (int)this.getPos().getY(), (int)this.getPos().getD(), (int)this.getPos().getD());
		}
		super.render(g);
		if (this.getTag().sB("flag-noai") == false) {
			g.setColor(Color.RED);
			g.drawOval((int)this.getPos().getX(), (int)this.getPos().getY(), (int)this.getPos().getD(), (int)this.getPos().getD());
		}
		if (this.getHit() >= 0) {
			//if (INIT.DEBUG) {Color c = new Color(this.getHit(),0,0);g.setColor(c);g.fillOval((int)getPos().getX(), (int)getPos().getY(), (int)getPos().getD(), (int)getPos().getD());
			//}
		}
		

	}
	public void update() {
		super.update();
		
		if (this.getTag().sB("flag-noai") == true) {
			return;
		}
		
		if (getSpawn() <= 0) doKeyInput();

		
		modifyStats();

		Creature collide = (Creature)this.checkHits(this.getW().getG().getMain());

		setHit(getHit()-1);
		setSpawn(getSpawn()-1);

		if (collide != null) {
			Encounter x = (Encounter)Screen.states.get(5);
			x.setEnemy(collide, this);
			collide.setRender(false);
		}
		if (getPos().getX() < 0 || getPos().getY() < 0 || getPos().getX() > Game.WIDTH || getPos().getY() > Game.HEIGHT) {
			Scripts.changeRoom(this.getW());
		}
		if (this.getLifetime() % 150 == 0) {
			for (int i = 0 ; i < (Utils.percent(this.getW().getG().getMeta().sI("netchange")+this.getW().getG().getMeta().sI("initialchange"), Game.TILESX*Game.TILESY*765)) ; i+=10) {
				this.getS().addHp(1);
			}
		}
		
		this.getW().getG().reveal(this.getPos().getCx(), this.getPos().getCy(), reach);
	}
	/**
	 * BEWARE TWO UPDATE METHODS
	 */
	public void update(BManager b) {
		super.update(b);
		
		if (getSpawn() <= 0)doKeyInput();

		modifyStats();
		int delay = 10;
		if (this.getM().getSpeed() > 5) {
			delay = (int)(10 - (this.getM().getSpeed()-5));
		}
		if (delay <= 0) delay = 1;
		if (this.getLifetime() % delay == 0) {
			Bullet myb = new Bullet(new NBT(Constants.DEFAULT_NBT),this);
			myb.setEID("000|04|6");
			myb.getPos().setX(this.getPos().getX());
			myb.getPos().setY(this.getPos().getY());
			myb.getM().setSpeed(this.getM().getSpeed()*3);
			if (this.getM().getSpeed() > 5) {
				myb.getM().setSpeed(12);
			}
			int adjusteddegrees = myb.getM().angleAt(this.getW().mouseX(), this.getW().mouseY())+90;
			int degrees = (((adjusteddegrees+22))/45)*45;
			degrees-=90;
			System.out.println((myb.getM().angleAt(this.getW().mouseX(), this.getW().mouseY())));
			myb.getM().setAngle(degrees);
			if (myb.getM().angleAt(this.getW().mouseX(), this.getW().mouseY()) > 247 || myb.getM().angleAt(this.getW().mouseX(), this.getW().mouseY()) < -67) {
				//myb.getM().setAngle(270);
			}
			b.addYourbullet(myb);
			new Sound("hit4q.wav",-40.0f).play();
		}
		/**
		 * BEWARE TWO UPDATE METHODS
		 */
		Entity collide = this.checkHits(b.getBullets());
		if (collide != null) {
			Bullet bb = (Bullet)collide;
			this.takeDamage(bb);
			collide.setRender(false);
		}
		setHit(getHit()-1);
		setSpawn(getSpawn()-1);

		RenderControl rc = getPos();

		if (rc.getX() < 0) {
			rc.setX(Game.WIDTH);
		} else if (rc.getY() < 0) {
			rc.setY(Game.HEIGHT);
		} else if (rc.getX() > Game.WIDTH) {
			rc.setX(0);
		} else if (rc.getY() > Game.HEIGHT) {
			rc.setY(0);
		}
	}
	private void modifyStats() {
		int fire = this.getEss().getFire();
		int cum = 0;
		int atk = 2;
		for (int i = 1000 ; fire > 0 ; i*=1.1) {
			atk+=1;
			fire -= i;
			if (fire-(i*1.1) < 0) {
				fmax = cum+i;
				fmin = cum;
			}
			cum+=i;
		}
		flvl = atk-1;

		int water = this.getEss().getWater();
		int hp = 25;
		cum = 0;
		for (int i = 1000 ; water > 0 ; i*=1.03) {
			hp+=1;
			water -= i;
			if (water-(i*1.03) < 0) {
				wmax = cum+i;
				wmin = cum;
			}
			cum+=i;
		}
		wlvl = hp - 24;

		int earth = this.getEss().getEarth();
		int def = 1;
		cum = 0;
		for (int i = 1000 ; earth > 0 ; i *= 2.0) {
			def += 1;
			earth -= i;
			if (earth-(i*2) < 0) {
				emax = cum+i;
				emin = cum;
			}
			cum+=i;
		}
		elvl = def;

		//PASSIVE:
		getS().setAtk(atk + this.getTag().sI("unlock-atk"));
		getS().setMaxhp(hp + this.getTag().sI("unlock-maxhp"));
		getS().setDef(def + this.getTag().sI("unlock-def"));
		this.getM().setSpeed(1.0+this.getEss().getEarth()/1000000.0);
		//System.out.println(this.getM().getSpeed());

		double spd = getM().getSpeed();
		int atk1 = getS().getAtk();
		int def1 = getS().getDef();

		//ACTIVE:
		int mode = Math.abs(getMode()%4);

		if (mode == 0) {
			getS().setAtk(atk1+atk1);
		}
		if (mode == 1) {
			getM().setSpeed(spd+spd);
		}
		if (mode == 2) {
			getS().setDef(def1+(int)(def1*.25));
		} 
		if (getM().getSpeed() > 9.9) {
			getM().setSpeed(9.9);
		}
		
	}
	/**
	 * BEWARE TWO UPDATE METHODS
	 */
	private void doKeyInput() {
		
		
		int netx = 0;
		int nety = 0;
		//37-40
		if (this.getW().getKey().isPressed(65)) {
			netx-=1;
			this.setState(1);
		}
		if (this.getW().getKey().isPressed(87)) {
			nety=-1;
		}
		if(this.getW().getKey().isPressed(68)) {
			netx+=1;
			this.setState(2);
		}
		if(this.getW().getKey().isPressed(83)){
			nety+=1;
		}
		if (this.getW().getKey().isPressed(27)) {
		//	Screen.setState(4, getW().getFrame(), getW().getKey());
		}
		if (netx == 0 && nety == 0) {
			this.setState(0);
		}
		for (int i = 0 ; i < 10 ; i++) {
			if (this.getW().getKey().isPressed(i+49)) {
				Player.brush = i;
			}
		}
		if (this.getW().getKey().isPressed(69)) {

			JColorChooser cc = new JColorChooser();
			cc.setColor(c);

			AbstractColorChooserPanel[] panels = cc.getChooserPanels();
			for (AbstractColorChooserPanel accp : panels) {
				if (accp.getDisplayName().equals("RGB")) {
					JOptionPane.showMessageDialog(this.getW().getFrame(), accp);
					c = cc.getColor();
				}
			}
			this.getW().getKey().reset();
		}
		this.getM().netMove(netx, nety);
		

		addMode(this.getW().getMouse().wheelMotion());
	}
	private Entity checkHits(List<Group> e) {
		if (getHit() >= 0) {
			return null;
		}
		for (int i = 0 ; i < e.size() ; i++) {
			for (int k = 0 ; k < e.get(i).size() ; k++) {
				boolean able = Entity.possCollision(this, e.get(i).get(k));
				if (able && Entity.isCollision(this, e.get(i).get(k))) {

					setHit(240);
					return e.get(i).get(k);
				}
			}
		}
		return null;
	}
	public void takeDamage(Bullet b) {
		if (INIT.OP) return;
		int damage = b.getEnemy().getS().getAtk();
		int def = getS().getDef();
		int net = damage - def;
		if (net <= 0) net = 1;
		this.getS().addHp(-net);
		if (this.getS().getHp() <= 0 ) {
			Scripts.changeState(getW(), 6,Color.RED);
			
		}
		setHit(120);
		new Sound("silencev1.wav",-10.0f).play();
	}
	public void takeDamage(int damage) {
		int net = damage - getS().getDef();
		if (net <= 0) net = 1;
		this.getS().addHp(-net);
		if (this.getS().getHp() <= 0 ) {
			Scripts.changeState(getW(), 6);
		}
		setHit(120);
		new Sound("silencev1.wav",-10.0f).play();
	}
	@Override
	public void setHit(int amount) {
		if (amount > 2500) {
			System.out.println("You cannot be invincible that long");
			return;
		}
		if (amount < 0) {
			super.setHit(-1);
			return;
		}
		super.setHit(amount);
	}
	public int getMode() {
		return mode;
	}
	public void addMode(int num) {
		this.mode = this.mode+num;
	}
	public void drawFireBar(Graphics g) {
		Utils.drawBar(g, 30, 50, this.getEss().getFire()-this.fmin, this.fmax-this.fmin, 100, true);
		Utils.drawString(g, flvl+"",10,60);
	}
	public void drawEarthBar(Graphics g) {
		Utils.drawBar(g, 30, 70, this.getEss().getEarth()-this.emin, this.emax-this.emin, 100, true);
		Utils.drawString(g, elvl+"",10,80);
	}
	public void drawWaterBar(Graphics g) {
		Utils.drawBar(g, 30, 90, this.getEss().getWater()-this.wmin, this.wmax-this.wmin, 100, true);
		Utils.drawString(g, wlvl+"",10,100);
	}
}
