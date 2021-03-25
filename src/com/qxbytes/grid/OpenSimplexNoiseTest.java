package com.qxbytes.grid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.qxbytes.Game;

public class OpenSimplexNoiseTest
{
	private static final int WIDTH = Game.WIDTH/Grid.TILESIZE;
	private static final int HEIGHT = Game.HEIGHT/Grid.TILESIZE;
	private static final double FEATURE_SIZE = 3;

	public static void main(String[] args)
		throws IOException {
		
		OpenSimplexNoise noise = new OpenSimplexNoise();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < HEIGHT; y++)
		{
			for (int x = 0; x < WIDTH; x++)
			{
				double value = noise.eval(x / FEATURE_SIZE, y / FEATURE_SIZE, 0.0);
				System.out.print(((value + 1)/2) + "|");
				int rgb = 0x010101 * (int)((value + 1) * 127.5);
				image.setRGB(x, y, rgb);
			}
			System.out.println();

		}
		ImageIO.write(image, "png", new File("test.png"));
	}
}
