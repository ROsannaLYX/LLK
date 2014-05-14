package nec.soft.java.utils;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import sun.audio.AudioPlayer;

public class EffectSound {

	public static final int RESTART = 1;
	public static final int SELECT = 2;
	public static final int BOMB = 3;
	public static final int SCORE = 5;
	public static final int WIN = 6;
	public static final int TIP = 7;
	public static final int CLICK = 8;
	public static final int MOVE = 9;

	private static AudioClip[] audios = new AudioClip[10];

	public static void playAudio(int index) {
		URL url = EffectSound.class.getResource("../music/" + index + ".wav");
		try {
			AudioPlayer.player.start(new FileInputStream(url.getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static AudioClip getAudio(int index) {
		if (audios[index] == null) {
			URL url = EffectSound.class.getResource("../music/" + index + ".wav");
			audios[index] = Applet.newAudioClip(url);
		}
		return audios[index];
	}
}
