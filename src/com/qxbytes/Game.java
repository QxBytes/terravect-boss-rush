package com.qxbytes;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.qxbytes.animations.Animations;
import com.qxbytes.grid.Grid;
import com.qxbytes.io.IOUtils;
import com.qxbytes.rss.ImageUtils;
import com.qxbytes.rss.RSS;
/**
 * 
 * @author QxBytes
 *
 */
public class Game implements Runnable {
	public static final String TITLE = "Terra Vect";
	public static final long NS_PER_UPDATE = 1000000000/60L;
	
	public static int REAL_WIDTH = 854;//current 854
	
	public static int WIDTH = 854;//854 Before 852 (current)
	public static int HEIGHT = 480;//480
	public static int TILESX = WIDTH/Grid.TILESIZE;
	public static int TILESY = HEIGHT/Grid.TILESIZE;
	public static Insets insets;
	public static int lag = 0;
	JPanel drawPane;
	Thread t;
	JFrame frame;
	boolean running = false;
	BufferStrategy bs;
	public Game() {
		t = new Thread(this);
		initialize();
	}
	public synchronized void start() {
		running = true;
		t.start();
	}
	public synchronized void stop() {
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		running = false;
	}
	public void initialize() {
		//JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("Terra_Vect");
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(REAL_WIDTH,HEIGHT));
		frame.pack();
		insets = frame.getInsets();
		frame.setPreferredSize(new Dimension(REAL_WIDTH + insets.left + insets.right, HEIGHT + insets.top + insets.bottom));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.createBufferStrategy(2);

		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		frame.getContentPane().setCursor(blankCursor);
		
		bs = frame.getBufferStrategy();
		
		IOUtils.prepEnvironment();
		ImageUtils.loadImages("sheet_0.png",8,24);
		Screen.init(frame);
		RSS.init();
		Animations.init();
		
	}
	public void update() {
		Screen.update();
	}
	public void render() {
		Graphics g = bs.getDrawGraphics();
		
		g.translate(insets.left, insets.top);

		Screen.render(g);
		//Warning: !
		g.dispose();
		bs.show();
	}
	@Override
	public void run() {
		long previous = System.nanoTime();
		lag = 0;

		int updates = 0;
		int renders = 0;

		long lastSecond = System.currentTimeMillis();

		while (running) {
			long current = System.nanoTime();
			long elapsed = current - previous;
			previous = current;
			lag += elapsed;

			while (lag >= NS_PER_UPDATE) {
				updates++;
				update();
				lag -= NS_PER_UPDATE;
				renders++;
				render();
			}

			

			if (System.currentTimeMillis() - lastSecond >= 1000) {
				//System.out.println("ups: " + updates + ", fps: " + renders);
				frame.setTitle(TITLE + " | ups: " + updates + ", fps: " + renders);
				updates = 0;
				renders = 0;
				lastSecond += 1000;
			}
		}
	}
	
}
