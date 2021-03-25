package com.qxbytes.audio;

import javazoom.jl.player.Player;

/**
 * 
 * @author QxBytes
 *
 */
public class Audio {
	private String filename;
	public static boolean muteMusic = true;
	private Player player; 
	private boolean loop;

	// constructor that takes the name of an MP3 file
	public Audio(String filename, boolean loop) {
		this.filename = filename;
		this.loop = loop;
	}

	public void close() { 
		if (player != null) {
			loop = false;
			player.close(); 
		} 
	}

	// play the MP3 file to the sound card
	public void play() {
		if (muteMusic == true) return;
		System.out.println("Playing: " + filename);
		try {
			
			player = new Player(this.getClass().getResourceAsStream(filename));

		}
		catch (Exception e) {
			System.out.println("Problem playing file " + filename);
			System.out.println(e);
		}

		// run in new thread to play in background
		new Thread() {
			public void run() {

				while (loop == true) {

					try { 
						player.play(); 
					
						player = new Player(this.getClass().getResourceAsStream(filename));
						System.out.println("Looping");
					
					}
					catch (Exception e) { System.out.println(e); }
				}
			}
		}.start();




	}
	public String getFilename() {
		return filename;
	}

	// test client
//	public static void main(String[] args) {
//
//		Audio mp3 = new Audio(filename);
//		mp3.play();
//
//		// do whatever computation you like, while music plays
//		int N = 4000;
//		double sum = 0.0;
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < N; j++) {
//				sum += Math.sin(i + j);
//			}
//		}
//		System.out.println(sum);
//
//		// when the computation is done, stop playing it
//		mp3.close();
//
//		// play from the beginning
//		mp3 = new Audio(filename);
//		mp3.play();
//
//	}

}
