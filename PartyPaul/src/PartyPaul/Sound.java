package PartyPaul;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	public static Sound coin = new Sound("coin.wav");
	public static Sound die = new Sound("die.wav");
	public static Sound win = new Sound("win.wav");	
	
	String name;

	private AudioClip clip;

	private Sound(String name) {
		name = "/" + name;
		
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void play() {
		if(!PartyPaul.sounds)
			return;
		
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
