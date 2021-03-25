package com.qxbytes.grid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.qxbytes.SColor;
import com.qxbytes.animations.AnimationManager;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.RenderControl;
import com.qxbytes.rss.ImageUtils;
import com.qxbytes.states.World;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public class Tile implements Extractable {
	private String EID;
	private int lifetime = 0;
	private BufferedImage[] sprites;
	private AnimationManager am;
	private World w;
	private NBT tags;
	private RenderControl pos;
	private Essence ess;
	public Tile(World w, int x, int y, int s, int val, int biome) {
		NBT tag = new NBT("eid:000|00,o:true" + ",cy:"+ y + ",cx:" + x + ",x:" + x + ",y:" + y + ",d:" + s + ",ess-limit:255");
		tag.set("l", 255);
		int b = biome;
		if (b == 0) {
			tag.set("ess-water", val);
		} else if (b == 1) {
			tag.set("ess-earth", val);
		} else if (b == 2) {
			tag.set("ess-fire", val);
		}

		init (tag,w);

		getEss().addWater(val/2);
		getEss().addEarth(val/2);
		getEss().addFire(val/2);
		
		this.getTags().set("flag-solid", false);
		if (this.ess.getFire() > 200 || this.ess.getEarth() > 200 || this.ess.getWater() > 200) {
			this.getTags().set("flag-solid", true);
		}
	}
	public Tile(World w, NBT tag) {
		init (tag,w);
	}
	public Tile(World w) {
		//only used for border control
		this.w = w;
		tags = new NBT("eid:000|00,flag-solid:false");
		pos = new RenderControl(tags);
		ess = new Essence(tags);
	}
	private void init(NBT tag, World w) {
		//all tiles go thru here

		this.EID = tag.s("eid");
		this.sprites = ImageUtils.getSet(Utils.parseInt((String[])Utils.parseValues(EID, '|').toArray(new String[0])));
		this.tags = tag;
		this.ess = new Essence(tag);
		this.pos = new RenderControl(tag);
		this.w = w;
		this.am = new AnimationManager(this.getSprites());

	}
	public void reload() {
		init(new NBT(this.toString()),w);
	}
	//full alpha
	public void render(Graphics g) {


		int d = (int)getPos().getD();

		g.setColor(new Color(ess.getFire(),ess.getEarth(),ess.getWater()));

		//		if (this.ess.getFire() < 60 || this.ess.getEarth() < 60 || this.ess.getWater() < 60) {
		int b = this.getW().getG().getMeta().sI("biome");
		//			if (b == 0) g.setColor(SColor.BEIGE); else if (b == 1) g.setColor(Color.GRAY); else if (b == 2) g.setColor(Color.DARK_GRAY);if (b == 1 && (this.ess.getFire() < 45 || this.ess.getEarth() < 45 || this.ess.getWater() < 45)) g.setColor(Color.LIGHT_GRAY);
		//			tags.set("flag-solid", true);
		//		} else {
		//			tags.set("flag-solid", false);
		//		}
		//g.fillRect((int)getPos().getX()*d, (int)getPos().getY()*d, d,d);
		if (this.getTags().sB("flag-solid") == true ) {
			if (this.ess.getFire() > 220 || this.ess.getEarth() > 220 || this.ess.getWater() > 220) {
				if (b == 0) g.setColor(SColor.BEIGE); else if (b == 1) g.setColor(Color.GRAY); else if (b == 2) g.setColor(Color.DARK_GRAY);if (b == 1 && (this.ess.getFire() < 45 || this.ess.getEarth() < 45 || this.ess.getWater() < 45)) g.setColor(Color.LIGHT_GRAY);
			}
			g.fill3DRect((int)getPos().getX()*d, (int)getPos().getY()*d, d,d,true);
		} else {
			g.fill3DRect((int)getPos().getX()*d, (int)getPos().getY()*d, d,d,false);

		}
		g.setColor(new Color(0,0,0,this.getTags().sI("l")));
		g.fillRect((int)getPos().getX()*d, (int)getPos().getY()*d, d,d);
	}
	/**
	 * Memory Consumption:
	 * First 3 lines: 200 MB
	 * Last Line: 1000 MB
	 */
	public String toString() {
		this.getTags().set("eid", EID);
		ess.saveComponent(tags);
		pos.saveComponent(tags);
		
		return tags.toString();
	}
	public void update() {
		lifetime++;
	}

	public String getEID() {
		return EID;
	}
	public void setEID(String eID) {
		EID = eID;
	}
	public BufferedImage[] getSprites() {
		return sprites;
	}
	public void setSprites(BufferedImage[] sprites) {
		this.sprites = sprites;
	}
	public AnimationManager getAm() {
		return am;
	}
	public void setAm(AnimationManager am) {
		this.am = am;
	}
	public Essence getEss() {
		return ess;
	}

	public int getLifetime() {
		return lifetime;
	}
	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}
	public void setEss(Essence ess) {
		this.ess = ess;
	}
	/**
	 * @return the w
	 */
	public World getW() {
		return w;
	}
	/**
	 * @param w the w to set
	 */
	public void setW(World w) {
		this.w = w;
	}
	/**
	 * @return the tags
	 */
	public NBT getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(NBT tags) {
		this.tags = tags;
	}
	public RenderControl getPos() {
		return pos;
	}
	public void setPos(RenderControl pos) {
		this.pos = pos;
	}
	@Deprecated
	public void test() {
		tags.set("l", new Random().nextInt(256));
	}

}
