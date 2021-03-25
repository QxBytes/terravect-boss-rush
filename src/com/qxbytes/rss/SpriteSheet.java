package com.qxbytes.rss;

import java.awt.image.BufferedImage;
/**
 * 
 * @author QxBytes
 *
 */
public class SpriteSheet {
	private BufferedImage[][] images;
	private int size;
	public SpriteSheet(String name, int size, int s ) {
		images = new BufferedImage[size][size];
		this.size = size;		
		for (int x = 0 ; x < size ; x++) {
			for (int y = 0 ; y < size ; y++) {
				images[x][y] = ImageUtils.getImage(name,x,y,s);
			}
		}
	}
	public BufferedImage[] getSet(int y) {
		BufferedImage[] temp = new BufferedImage[size];
		for (int i = 0 ; i < size ; i++) {
			temp[i] = images[i][y];
		}
		return temp;
	}
	public BufferedImage getImage(int x, int y) {
		return images[x][y];
	}
	public int getSize() {
		return size;
	}
}
