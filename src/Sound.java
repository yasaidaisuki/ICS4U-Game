// Assignment: ISU
// Name: Max Luo and Dami Peng
// Date: June 22, 2022
// Description: a class for handling sounds

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.File;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    // method: Sound
    // Purpose: preset sound wav files
    // Param: n/a
    // Return: n/a
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

    // method: setFile
    // Purpose: reads in file path and sets it as clip
    // Param: int
    // Return: n/a
    public void setFile(int i) {

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // method: play
    // Purpose: plays file
    // Param: n/a
    // Return: void
    public void play() {
        clip.start();
    }

    // method: stop
    // Purpose: stops file
    // Param: n/a
    // Return: void
    public void stop() {
        clip.stop();
    }

    // method: loop
    // Purpose: loops file
    // Param: n/a
    // Return: void
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
