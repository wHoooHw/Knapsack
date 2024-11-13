package Frontend;


import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer {
    // private MediaPlayer mediaPlayer;
Clip clip;
    public AudioPlayer(String musicFile) {
        try {
            File musicPath = new File(musicFile);
            AudioInputStream aStream = AudioSystem.getAudioInputStream(musicPath);
            clip = AudioSystem.getClip();
            clip.open(aStream);
           
        } catch (Exception e) {
            System.err.println("Error with playing audio: " + e.getMessage());
        }
    }

    public void play() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    
}
