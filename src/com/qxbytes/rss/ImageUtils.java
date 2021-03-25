package com.qxbytes.rss;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
/**
 * 
 * @author QxBytes
 *
 */
public class ImageUtils {
	//DO NOT MODIFY
	static List<SpriteSheet> sheets = new ArrayList<SpriteSheet>();
	static List<BufferedImage> battle = new ArrayList<BufferedImage>();
	public static BufferedImage help = null;
	static boolean loaded = false;
	static public BufferedImage[] test() {
		return getSet(0,0);
	}
	/**
	 * Do not use too often
	 * @param name
	 * @param x
	 * @param y
	 * @param cell
	 * @return
	 */
	static BufferedImage getImage(String name, int x, int y, int cell) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(ImageUtils.class.getResourceAsStream(name));
			image = image.getSubimage(x*cell, y*cell, cell, cell);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
	public static BufferedImage[] getSet(int[] args) {
		
		if (args.length > 3) {
			return getSet(args[0],args[1],args[2],args[3]);
		} else if (args.length > 2) {
			return getSet(args[0],args[1],args[2]);
		} else {
			return getSet(args[0],args[1]);
		}
	}
	static BufferedImage[] getSet(int num, int row) {
		return sheets.get(num).getSet(row);
	}
	static BufferedImage[] getSet(int num, int row, int start) {
		return ImageUtils.imageToarray(Arrays.asList(sheets.get(num).getSet(row)).subList(start, sheets.get(num).getSize()));
	}
	static BufferedImage[] getSet(int num, int row, int start, int end) {
		return ImageUtils.imageToarray(Arrays.asList(sheets.get(num).getSet(row)).subList(start, end));
	}
	static BufferedImage[] imageToarray(List<BufferedImage> x) {
		BufferedImage[] temp = new BufferedImage[x.size()];
		for (int i = 0 ; i < temp.length ; i++) {
			temp[i] = x.get(i);
		}
		return temp;
	}
	static public BufferedImage getLoadedImage(int num, int x, int y) {
		return sheets.get(num).getImage(x, y);
	}
	static public BufferedImage getBattleImage(int num) {
		return battle.get(num);
	}
	//init
	static public void loadImages(String name, int size, int s) {
		sheets.add(new SpriteSheet(name,size,s));
		for (int i = 0; i <= 3 ; i++) {
			try {
				battle.add(ImageIO.read(ImageUtils.class.getResourceAsStream("battle-" + i + ".png")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			help = ImageIO.read(ImageUtils.class.getResourceAsStream("instruction.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean isLoaded() {
		return loaded;
	}
	
}
