package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[]=new URL[30];

    public Sound() {
        soundURL[0]=getClass().getResource("/sound/bg.wav");
        soundURL[1]=getClass().getResource("/sound/key.wav");// when key is acquired
        soundURL[2]=getClass().getResource("/sound/unlock.wav");
        soundURL[3]=getClass().getResource("/sound/fanfare.wav");
        soundURL[4]=getClass().getResource("/sound/titlescreen.wav");
    }
    public void setFile(int i){
        try{
            AudioInputStream ais= AudioSystem.getAudioInputStream(soundURL[i]);
            clip=AudioSystem.getClip();
            clip.open(ais);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // Adjust volume based on the sound type
            if (i == 0) {  // bg.wav (make it quieter)
                gainControl.setValue(-10.0f);  // Reduce volume (lower dB)
            }
            else if(i==4){
                gainControl.setValue(-15.0f);
            }else {  // key.wav and unlock.wav (make them louder)
                gainControl.setValue(+6.0f);  // Increase volume (higher dB)
            }
        }catch(Exception e){

        }
    }

    public void play(){
        clip.start();

    }

    public void loop(){
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }

}
