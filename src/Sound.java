import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.File;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/title.wav");
        soundURL[1] = getClass().getResource("/sound/map1.wav");
        soundURL[2] = getClass().getResource("/sound/hit.wav");
        soundURL[3] = getClass().getResource("/sound/max_hit.wav");
        soundURL[4] = getClass().getResource("/sound/tyler_hit.wav");
        soundURL[5] = getClass().getResource("/sound/max_deathTyler.wav");
        soundURL[5] = getClass().getResource("/sound/jump.wav");
        soundURL[6] = getClass().getResource("/sound/shing.wav");
        soundURL[7] = getClass().getResource("/sound/tyler_lol.wav");
        soundURL[8] = getClass().getResource("/sound/you_died.wav");
        soundURL[10] = getClass().getResource("/sound/wong_atk.wav");
        soundURL[11] = getClass().getResource("/sound/deathtoboss.wav");
    }
    
    public void setFile(int i) {

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void play() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
