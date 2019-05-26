package com.biblitek.service.codigoqr;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Sounds {
	public static Clip getSound(String file) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
					new File("sounds" + System.getProperty("file.separator") + file).getAbsoluteFile());
			AudioFormat format = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip sound = (Clip) AudioSystem.getLine(info);
			sound.open(audioInputStream);
			return sound;
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean playSound(Clip clip) {
		
		clip.stop();
		clip.setFramePosition(0);
		
		clip.start();
		
		return true;
	}

}