package com.qxbytes.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.text.WordUtils;

import com.qxbytes.Game;
import com.qxbytes.entities.NBT;
import com.qxbytes.grid.Extractable;

/**
 * 
 * @author QxBytes
 *
 */
@SuppressWarnings("deprecation")
public class Utils {
	public static Random randomGen = new Random();
	/**
	 * String in format Name:Value
	 * To NBT Tag
	 */
	public static NBT parseNBTTag(String tag) {
		String name = tag.substring(0,tag.indexOf(":"));
		String value = tag.substring(tag.indexOf(":")+1);

		return new NBT(name,value);
	}
	public static NBT parseNBTRaw(String x) {
		NBT root = new NBT();
		List<String> cooked = parseValues(x,',');

		for (int i = 0 ; i < cooked.size() ; i++) {
			root.addTag(parseNBTTag(cooked.get(i)));
		}

		return root;
	}
	/*
	 * Conservation of mass? Yeah, that's it.
	 */
	public static int moveEssenceFireFrom(Extractable from, Extractable to, int amount) {
		int a = from.getEss().getFire();
		//enough
		if (a >= amount) {
			from.getEss().addFire(-amount);
			int overflow = to.getEss().addFire(amount);
			from.getEss().addFire(overflow);
			return amount - overflow;
		}	else  {
			//not enough
			from.getEss().addFire(-a);
			int overflow = to.getEss().addFire(a);
			from.getEss().addFire(overflow);
			return a - overflow;
		}
	}
	public static int moveEssenceEarthFrom(Extractable from, Extractable to, int amount) {
		int a = from.getEss().getEarth();
		if (a >= amount) {
			from.getEss().addEarth(-amount);
			int overflow = to.getEss().addEarth(amount);
			from.getEss().addEarth(overflow);
			return amount - overflow;
		}	else  {
			from.getEss().addEarth(-a);
			int overflow = to.getEss().addEarth(a);
			from.getEss().addEarth(overflow);
			return a - overflow;
		}
	}
	public static int moveEssenceWaterFrom(Extractable from, Extractable to, int amount) {
		int a = from.getEss().getWater();
		if (a >= amount) {
			from.getEss().addWater(-amount);
			int overflow = to.getEss().addWater(amount);
			from.getEss().addWater(overflow);
			return amount - overflow;
		}	else  {
			from.getEss().addWater(-a);
			int overflow = to.getEss().addWater(a);
			from.getEss().addWater(overflow);
			return a - overflow;
		}
	}
	public static int penTool(Extractable from, Extractable to, Color c) {
		int firereq = c.getRed() -  to.getEss().getFire();
		int earthreq = c.getGreen() - to.getEss().getEarth();
		int waterreq = c.getBlue() - to.getEss().getWater();

		int moved = 0;
		//from player to tile
		//negative = essence moves from the tile
		moved += Utils.moveEssenceFireFrom(from, to, firereq);
		moved += Utils.moveEssenceEarthFrom(from, to, earthreq);
		moved += Utils.moveEssenceWaterFrom(from, to, waterreq);
		return moved;
	}
	/*
	 * BELOW HERE ARE GENERAL UTILITIES
	 */
	public static double distanceFrom(double x1, double y1, double x2, double y2) {
		double ans = Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2));
		return ans;
	}
	public static void drawString(Graphics g, String text, int x, int y) {
		Color temp = g.getColor();
		Color c = new Color(Color.GRAY.getRed(),Color.GRAY.getGreen(),Color.GRAY.getBlue(),g.getColor().getAlpha());
		g.setColor(c);

		g.drawString(text, x+2, y+2);
		g.setColor(temp);
		g.drawString(text, x, y);
	}
	public static void wrapText(Graphics g, String text, int x, int y, int charsinoneline) {
		String wrapped = WordUtils.wrap(text, charsinoneline);
		String[] lines = wrapped.split("\\r?\\n");
		for (int i = 0 ; i < lines.length ; i++) {
			drawString(g,lines[i],x,y+i*(g.getFont().getSize()+g.getFont().getSize()/2));
		}
	}
	public static void autoWrapText(Graphics g, String text, int x, int y, int sx) {
		wrapText(g,text,x,y,sx/g.getFont().getSize());
	}
	public static void drawBar(Graphics g, int x, int y, double num, double maxnum, int space, boolean horizontal) {
		Color temp = g.getColor();

		double fill = num;
		double max = maxnum;

		if (fill > max) {
			fill = max;
		}

		double amount = fill/max;

		double filled = space * amount;

		if (!horizontal) {
			g.setColor(temp.darker().darker());
			g.fillRect(x, y, 4, space);
			g.setColor(temp);
			g.fillRect(x, y + space, 4, -(int)filled);
		} else {
			g.setColor(temp.darker().darker());
			g.fillRect(x, y, (int)space, 4);
			g.setColor(temp);
			g.fillRect(x, y, (int)filled, 4);
		}

		g.setColor(temp);
	}
	public static void dumpData(List<String> x) {
		System.out.println("====DUMPED DATA====");
		for (int i = 0 ; i < x.size() ; i++	 ) {
			System.out.println(x.get(i));
		}
	}
	public static boolean possibleDistance(int x1, int y1, int x2, int y2, double r) {
		if (Math.abs(x2 - x1) > r || Math.abs(y2-y1) > r) {
			return false;
		}
		return true;
	}
	public static boolean withinDistance(int x1, int y1, int x2, int y2, double r) {
		double distance = Math.sqrt(Math.pow((x2)-(x1),2)+Math.pow((y2)-(y1), 2));
		if (distance > r ) {
			return false;
		}
		return true;
	}
	public static Color randomColor() {
		Random r = new Random();
		int x = r.nextInt(255);
		int y = r.nextInt(255);
		int z = r.nextInt(255);
		return new Color(x,y,z);
	}
	public static void wait(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int randomX() {
		return new Random().nextInt(Game.WIDTH);
	}
	public static int randomY() {
		return new Random().nextInt(Game.HEIGHT)/2;
	}
	public static int[] parseInt(String[] string) {
		int[] x = new int[string.length];
		for (int i = 0 ; i < string.length ; i++) {
			x[i] = Integer.parseInt(string[i]);
		}
		return x;
	}
	public static List<String> parseValues(String x, char separator) {
		List<String> values = new ArrayList<String>();
		String addto = "";
		if (x == null) {
			return values;
		}
		for (int i = 0 ; i < x.length() ; i++) {
			if (x.charAt(i) == separator) {
				//new
				if (addto.equals("") || addto == null) {
					continue;
				}
				values.add(addto);
				addto = "";
			} else {
				addto += x.charAt(i);
			}
		}
		if (!addto.equals("")) {
			values.add(addto);
		}
		return values;
	}
	
	public static double percent(double num, double total) {
		return (double)((double)num/(double)total)*100;
	}


}