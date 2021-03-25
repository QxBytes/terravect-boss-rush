package com.qxbytes.audio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Handles playing, stoping, and looping of sounds for the game.
 * @author Tyler Thomas
 *
 */
public class Sound {
    private Clip clip;
    private float volumeChange;
    public static boolean muteSounds = true;
    /**
     * 
     * @param fileName
     * @param volumeChange softer (-20.0f --> +20.0f) louder
     */
    public Sound(String fileName, float volumeChange) {
    	this.volumeChange = volumeChange;
        // specify the sound to play
        // (assuming the sound can be played by the audio system)
        // from a wave File
    	
        try {
            //File file = new File(fileName);
            //if (file.exists()) {
        	//add buffer for mark/reset support
			InputStream bufferedIn = new BufferedInputStream(this.getClass().getResourceAsStream(fileName));
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);             
			// load the sound into memory (a Clip)
                clip = AudioSystem.getClip();
                clip.open(audioStream);
            //}
            //else {
              //  throw new RuntimeException("Sound: file not found: " + fileName);
            //}
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }

    // play, stop, loop the sound clip
    }
    public void play(){
    	if (muteSounds == true) return;
    	FloatControl gainControl = 
    		    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    		gainControl.setValue(volumeChange);
        clip.setFramePosition(0);  // Must always rewind!
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
            clip.stop();
        }
    }
