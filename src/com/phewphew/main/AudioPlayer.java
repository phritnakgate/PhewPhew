package com.phewphew.main;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {
	
	File file;
	Clip clip;
	String status = "no";
	AudioInputStream audioInputStream;
	
	public AudioPlayer(String filePath) {
		try {
			file = new File(filePath);
			audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
			clip = AudioSystem.getClip();
		    clip.open(audioInputStream);
		    
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}
	public void play() {
		if(status == "no") {
			
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			status = "play";
		}
        
	}
	public void stop() {
		clip.stop();
        clip.close();
        status = "no";
	}
}
