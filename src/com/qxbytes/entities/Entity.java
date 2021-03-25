package com.qxbytes.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.qxbytes.animations.AnimationManager;
import com.qxbytes.grid.Essence;
import com.qxbytes.grid.Extractable;
import com.qxbytes.grid.Grid;
import com.qxbytes.physics.Physics;
import com.qxbytes.physics.StandardPhysics;
import com.qxbytes.rss.ImageUtils;
import com.qxbytes.states.World;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public abstract class Entity implements Extractable{
	private int lifetime = 0;
	private int state = 0;
	private boolean isRender;
	private RenderControl pos;
	private Physics ph;
	private String EID;
	private AnimationManager am;
	private Motion m;
	private BufferedImage[] sprites;
	private NBT tag;
	private World w;
	private Essence ess;
	public String toString() {
		pos.saveComponent(tag);
		tag.set("EID", EID);
		m.saveComponent(tag);
		ess.saveComponent(tag);
		return tag.toString();
	}
	//millisecond states
	private int hit;//invincibility, animation
	private int spawn;//time until spawn, animation
	private int death;//time until particles no longer visible, animation
	public Entity (NBT tag, World w) {

		init(tag,w);
		//if (INIT.DEBUG)System.out.println(tag);
	}
	public Entity(World w) {
		System.out.println("Do not use this tag.");
		System.exit(-1);
	}
	private void init(NBT tag, World w) {
		this.EID = tag.s("eid");
		this.sprites = ImageUtils.getSet(Utils.parseInt((String[])Utils.parseValues(EID, '|').toArray(new String[0])));
		this.tag = tag;
		this.ess = new Essence(tag);
		this.spawn = -1;
		this.death = -1;
		this.hit = -1;
		this.m = new Motion(tag);
		this.pos = new RenderControl(tag);
		this.w = w;
		this.am = new AnimationManager(this.getSprites());
		this.isRender = true;
		this.ph = new StandardPhysics();
		m.setE(this);
	}
	public void reload() {
		init(new NBT(this.toString()),w);
	}
	public void setEID(String eid) {
		this.EID = eid;
		this.sprites = ImageUtils.getSet(Utils.parseInt((String[])Utils.parseValues(eid, '|').toArray(new String[0])));
		this.am = new AnimationManager(sprites);

		this.gT().set("eid", eid);
	}
	public void refresh() {
		this.EID = tag.s("eid");
		this.sprites = ImageUtils.getSet(Utils.parseInt((String[])Utils.parseValues(EID, '|').toArray(new String[0])));
		this.ess = new Essence(tag);
		this.spawn = -1;
		this.death = -1;
		this.hit = -1;
		this.m = new Motion(tag);
		this.pos = new RenderControl(tag);
		this.am = new AnimationManager(this.getSprites());
		this.isRender = true;

		m.setE(this);
	}
	public abstract void onDeath();
	public abstract void onSpawn();
	public abstract void onHit();
	public abstract void update();
	public abstract void render(Graphics g);
	public abstract void onDeath(Graphics g);
	public abstract void onSpawn(Graphics g);
	public abstract void onHit(Graphics g);
	protected void move(BManager bm) {
		//ONLY IF IN ENCOUNTER MODE (SEE PH.MOVE BELOW FOR USUAL COLLISION)
		double x = this.getM().getX();
		double y = this.getM().getY();
		double s = this.getPos().getD();

		double curx = (double)(this.getPos().getX());
		double cury = (double)(this.getPos().getY());
		//update
		this.getPos().setX((curx+x));
		this.getPos().setY((cury+y));
		//System.out.print(		"X:"+
		this.getPos().setCx((int)(((double)curx+(double)s/2.0)/(double)(Grid.TILESIZE)));
		//System.out.println(" , Y:"+

		this.getPos().setCy((int)(((double)cury+(double)s/2.0)/(double)(Grid.TILESIZE)));
	}
	protected void move() {
		ph.move(this);
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public boolean isRender() {
		return isRender;
	}
	public void setRender(boolean isRender) {
		this.isRender = isRender;
	}
	public AnimationManager getAm() {
		return am;
	}
	public void setAm(AnimationManager am) {
		this.am = am;
	}
	public RenderControl getPos() {
		return pos;
	}
	public void setPos(RenderControl pos) {
		this.pos = pos;
	}
	public Essence getEss() {
		return ess;
	}
	public void setEss(Essence ess) {
		this.ess = ess;
	}
	/**
	 * @return the m
	 */
	public Motion getM() {
		return m;
	}

	/**
	 * @param m the m to set
	 */
	public void setM(Motion m) {
		this.m = m;
	}
	/**
	 * @return the lifetime
	 */
	public int getLifetime() {
		return lifetime;
	}
	/**
	 * @param lifetime the lifetime to set
	 */
	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}

	/**
	 * @return the hit
	 */
	public int getHit() {
		return hit;
	}
	/**
	 * @param hit the hit to set
	 */
	public void setHit(int hit) {
		this.hit = hit;
	}
	/**
	 * @return the spawn
	 */
	public int getSpawn() {
		return spawn;
	}
	/**
	 * @param spawn the spawn to set
	 */
	public void setSpawn(int spawn) {
		this.spawn = spawn;
	}
	/**
	 * @return the death
	 */
	public int getDeath() {
		return death;
	}
	/**
	 * @param death the death to set
	 */
	public void setDeath(int death) {
		this.death = death;
	}
	/**
	 * @return the sprites
	 */
	public BufferedImage[] getSprites() {
		return sprites;
	}
	/**
	 * @param sprites the sprites to set
	 */
	public void setSprites(BufferedImage[] sprites) {
		this.sprites = sprites;
	}
	/**
	 * @return the tag
	 */
	public NBT getTag() {
		return tag;
	}
	public NBT gT() {
		return tag;
	}
	/**
	 * @return the w
	 */
	public World getW() {
		return w;
	}
	/**
	 * Possible (is candidate) collision of d with e
	 * @param d
	 * @param e
	 * @return
	 */
	public static boolean possCollision(Entity d,Entity e) {
		if (!e.getPos().isO() || !d.getPos().isO()) {
			return false;
		}
		int epx = e.getPos().getCx();
		int epy = e.getPos().getCy();
		int px = d.getPos().getCx();
		int py = d.getPos().getCy();
		if (epx >= px-5 && epy >= py-5 && epx <= px+5 && epy <= py+5) {
			return true;
		}
		return false;
	}
	public static boolean isCollision(Entity e1, Entity e2) {
		double r1 = e1.getPos().getD()/2;
		double r2 = e2.getPos().getD()/2;

		double x1 = e1.getPos().getX();
		double x2 = e2.getPos().getX();

		double y1 = e1.getPos().getY();
		double y2 = e2.getPos().getY();

		double distance = Math.sqrt(Math.pow((x2+r2)-(x1+r1),2)+Math.pow((y2+r2)-(y1+r1), 2));

		if (distance < r1+r2) {
			//System.out.println("Collision!");
			return true;
		}

		return false;
	}

}
