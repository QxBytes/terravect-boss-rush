package com.qxbytes;

import java.awt.Font;

import com.qxbytes.entities.Motion;
import com.qxbytes.entities.NBT;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public interface Constants {
	public static final int DEFAULT_HIT = 100;
	public static final int DEFAULT_SPAWN = 100;
	public static final int DEFAULT_DEATH = 100;
	public static final Font LINE_TEXT = new Font("Pixeled",Font.PLAIN,15);
	public static final Font DEATH_TEXT = new Font("Pixeled",Font.BOLD,45);
	public static final Font SMALL_TEXT = new Font("Pixeled",Font.PLAIN,7);
	public static final Font TITLE_TEXT = new Font("Pixeled",Font.BOLD,83);
	public static final int bAtk = 4;
	public static final int bDef = 3;
	public static final int bMaxhp = 40;
	public static final int bSpd = 1;
	public static final String VERSION = "Alpha 1.0.3";
	
	public static final String wbEID = "000|04|0";
	public static final String ebEID = "000|04|2";
	public static final String fbEID = "000|04|4";
	

	
	

	/*
	 * Chunks = 60x60
	 * When searching for an NBT it must be lowercase (all tags are lowercase for speed)
	 */
	
	public static final NBT DEFAULT_NBT = Utils.parseNBTRaw("eid:000|00,o:true,d:12,s:6,cx:0,cy:0,x:400,y:50,motion-x:1,motion-y:1,motion-speed:1,stats-hp:15,stats-maxhp:20,stats-atk:4,ess-limit:255");
	public static final Motion DEFAULT_MOTION = new Motion(90,1);
}
