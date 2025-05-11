package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound(){

        soundURL[0] = getClass().getResource(("/sound/mixkit-game-level-music-689.wav"));
        soundURL[1] = getClass().getResource(("/sound/mixkit-winning-a-coin-video-game-2069.wav"));
        soundURL[2] = getClass().getResource(("/sound/mixkit-train-door-open-1637.wav"));
        soundURL[3] = getClass().getResource(("/sound/mixkit-arcade-retro-game-over-213.wav"));
    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
