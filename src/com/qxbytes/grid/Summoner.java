package com.qxbytes.grid;

import java.util.Random;

import com.qxbytes.Game;
import com.qxbytes.Screen;
import com.qxbytes.behaviors.FearahnaFishB;
import com.qxbytes.behaviors.GillSoldierB;
import com.qxbytes.behaviors.GrassassinB;
import com.qxbytes.behaviors.GrowlemB;
import com.qxbytes.behaviors.LeafieB;
import com.qxbytes.behaviors.MoltronB;
import com.qxbytes.behaviors.PlasmatrixB;
import com.qxbytes.behaviors.SandWarriorB;
import com.qxbytes.behaviors.ShadowNinjaB;
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
import com.qxbytes.states.World;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public class Summoner {
	public static void summon(World w, Grid g) {
		if (Screen.runtime % 240 == 0 && g.getMain().get(0).size() < 100) {
			System.out.println("Attempting to summon a monster in the world.");
			int x = Utils.randomGen.nextInt(Game.TILESX);
			int y = Utils.randomGen.nextInt(Game.TILESY);

			if ((g.getTile(x, y).getTags().sI("l") != 0 || Utils.randomGen.nextBoolean()) ) {
				if ( g.getTile(x, y).getEss().total() > 480 || (Utils.percent(g.getMeta().sI("netchange")+g.getMeta().sI("initialchange"), Game.TILESX*Game.TILESY*765)) > 75) {
					System.out.println("The terrain is too developed to form it!");
					return;
				}

				int mult = 0;
				int raw = (300-g.getTile(x, y).getEss().total())/100;
				if (raw > 0)
					mult += (Math.pow(raw,1.5));

				mult += g.getMeta().sI("height")/64;

				boolean clear = g.areaClear(x, y);
				System.out.println(x + "," + y);
				if (clear) {
					g.getMain().get(0).add(pickMob(g,mult));
					g.getMain().get(0).get(g.getMain().get(0).size()-1).getPos().setX(x * Grid.TILESIZE);
					g.getMain().get(0).get(g.getMain().get(0).size()-1).getPos().setY(y * Grid.TILESIZE);
					g.getMain().get(0).get(g.getMain().get(0).size()-1).getPos().setCx(x );
					g.getMain().get(0).get(g.getMain().get(0).size()-1).getPos().setCy(y );
					g.getMain().get(0).get(g.getMain().get(0).size()-1).getM().goTo(w.getP().getPos().getCenterX(),w.getP().getPos().getCenterY());
				} else {
					System.out.println("The terrain is too hazardous to form it!");
				}
			}

		}
	}
	public static Creature pickMob(Grid g, int multiplier) {
		double height = g.getMeta().sD("height");
		double biome = g.getMeta().sD("biome");
		double localheight = height%90;
		//bonus
		int r = (int)(Math.abs(height-70)+1) * 400;//in addition

		Random rr = new Random();
		int check = rr.nextInt(5);
		System.out.println(check);
		int offs = rr.nextInt(30)-15;
		
		Creature c = null;
		if (biome <= 1) {

			if (check < 1) {
				c = new SandWarrior(g.getW());
			}
			else if (check < 3) {
				c =  new GillSoldier(g.getW());
			}
			else if (check < 5) {
				c = new FearahnaFish(g.getW());
			}
		}

		localheight += offs;
		System.err.println("PUT IN: " + localheight + "H " + biome + "B " + height + "RH");

		if (biome > 1 && biome <= 2) {

			if (localheight > 60) {
				c = new Grassassin(g.getW());
			} else if (localheight >= 30) {
				c =  new Growlem(g.getW());
			} else {
				c = new Leafie(g.getW());
			}
		}
		if (biome > 2) {
			
			if (localheight >= 40) {
				c = new ShadowNinja(g.getW());
			} else if (localheight >= 20) {
				c =  new Plasmatrix(g.getW());
			} else
				c = new Moltron(g.getW());
		}
		
		c.getS().setAtk((int)Math.ceil((c.getS().getAtk()*Math.pow(1.2, multiplier))));
		c.getS().setDef((int)Math.ceil((c.getS().getDef()*Math.pow(1.2, multiplier))));
		c.getS().setMaxhp((int)Math.ceil((c.getS().getMaxhp()*Math.pow(1.2, multiplier))));
		c.getS().setHp((int)Math.ceil((c.getS().getHp()*Math.pow(1.2, multiplier))));
		c.getS().setSpd((int)Math.ceil((c.getS().getSpd()*Math.pow(1.2, multiplier))));
		c.getTag().set("multiplier", multiplier);
		c.getTag().set("localheight", localheight);
		c.getTag().set("check", check);
		c.getTag().set("biome", biome);
		//BONUS
		c.getEss().addEarth(r);
		c.getEss().addFire(r);
		c.getEss().addWater(r);
		return c;
	}
	public static void multiply(Creature c, int multiplier) {
		if (multiplier > 4) multiplier = 4;
		c.getS().setAtk((int)Math.ceil((c.getS().getAtk()*Math.pow(1.05, multiplier))));
		c.getS().setDef((int)Math.ceil((c.getS().getDef()*Math.pow(1.2, multiplier))));
		c.getS().setMaxhp((int)Math.ceil((c.getS().getMaxhp()*Math.pow(1.2, multiplier))));
		c.getS().setHp((int)Math.ceil((c.getS().getHp()*Math.pow(1.2, multiplier))));
		c.getS().setSpd((int)Math.ceil((c.getS().getSpd()*Math.pow(1.2, multiplier))));
		
		c.getTag().set("multiplier", multiplier);
	}
	public static void deriveBehavior(Creature c) {
		double localheight = c.getTag().sD("localheight");
		int check = c.getTag().sI("check");
		double biome = c.getTag().sD("biome");
		System.out.println("DERIVED:" + localheight + "H" + biome + "B");

		if (biome <= 1) {

			if (check < 1) {
				c.setB(new SandWarriorB(c));
			}
			else if (check < 3) {
				c.setB(new GillSoldierB(c));
			}
			else if (check < 5) {
				c.setB(new FearahnaFishB(c));
			}
		}
		//TODO
		if (biome > 1 && biome <= 2) {

			if (localheight > 60) {
				c.setB(new GrassassinB(c));
			} else if (localheight >= 30) {
				c.setB(new GrowlemB(c));
			} else {
				c.setB(new LeafieB(c));
			}
		}
		if (biome > 2) {
			if (localheight >= 40) {
				c.setB(new ShadowNinjaB(c));
			} else {
				if (localheight >= 20) {
					c.setB(new PlasmatrixB(c));
				} else {
					c.setB(new MoltronB(c));
				}

			}
		}
	}
}
