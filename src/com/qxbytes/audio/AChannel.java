package com.qxbytes.audio;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author QxBytes
 *
 */
public class AChannel {
	private static Audio mp3;
	/**
	 * -\\\1 - Low Essence - Ars Sonor - Centralstation
	 * -\\\2 - Final Battle - Ars Sonor - The Frequency
	 * -\\\3 - Jungle Battle - Artofescapism - The Water Embers
	 * \\\4 - Final Battle Last Phases - Artofescapism - Where Are Your Friends?
	 * \\\5 - Title - Chris Zabriskie - Air Hockey Saloon
	 * -\\\6 - Fire Battle - Daniel Birch - Caught in the Crossfire
	 * -\\\7 - Jungle Ambient - Daniel Birch - Temple
	 * -\\\8 - Fire Ambient - Daniel Birch - Too Late to Turn Back
	 * -\\\9 - Water Ambient - Lee Osevere - Just Blue Sky
	 * -\\10 - Water Battle - Remain - Acid
	 * -\\11 - Final Battle Last Phase - Remain - The Void
	 * -\\12 - Mystery Zone - Remain - Digimetery
	 * 
	 */
	private static List<Audio> tracks = new ArrayList<Audio>();
	public static void loadAudio() {
		for (int i = 1 ; i < 12 ; i++) {
			tracks.add(new Audio(i + ".mp3",true));
		}
	}
	public static void playTrack(int number, boolean loop) {
		
		if (mp3 != null && mp3.getFilename().equals(number + ".mp3")) {
			return;
		}
		
		if (mp3 != null) {
			mp3.close();
		}
		System.out.println("Playing" + number);
		mp3 = new Audio(number + ".mp3",loop);
	    mp3.play();
	}
	public static void stopTrack() {
		mp3.close();
	}
	
	
	
}
