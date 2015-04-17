package ClientSide;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// ---DISCLAIMER!!!!
//The methods in this class are not entirely self-constructed and have been inspired by examples provided online.
//The contents and use of this class is only cosmetic and should not be considered an actual part of the project.
//We do not claim the code in this class to have been created by us!
//We do not intend for anything contained within this class to be a part of the grading process of our project.

public class Utilities {
	
	public static boolean isMusicPlaying = false;
	public static boolean stopMusicRequest = false;
	
	public static synchronized void playSound(final String url) {
		 // new Thread(new Runnable() {
		   // public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(Utilities.class.getResourceAsStream(url));
		        //System.out.println("SOUND PLAYING");
		        clip.open(inputStream);
		        clip.start(); 
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		        System.out.println("Sound error");
		      }
		   // }
		 // }).start();
		}
	
	public static synchronized void music(final String fileName) {
		new Thread(new Runnable() {
			public void run() {
				while(true){
				if(Utilities.stopMusicRequest){
					break;
				}
				if(!Utilities.isMusicPlaying) {
					Utilities.isMusicPlaying = true;
					try {
						System.out.println("Playing music");
						Clip clip = AudioSystem.getClip();
						AudioInputStream inputStream = AudioSystem.getAudioInputStream(Utilities.class.getResourceAsStream("/resources/"+fileName));
						clip.open(inputStream);
						clip.start();
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
			}
			}
		}).start();
	}

	public boolean isMusicPlaying() {
		return isMusicPlaying;
	}

	public void setMusicPlaying(boolean isMusicPlaying) {
		this.isMusicPlaying = isMusicPlaying;
	}

}
