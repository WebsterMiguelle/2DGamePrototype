package Main;

import java.io.*;

public class Config {
    GamePanel gp ;

    public Config(GamePanel gp){

        this.gp = gp;

    }

    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
            //Fullscreen
            if (gp.fullScreenOn) {
                bw.write("On");
            }
            if(!gp.fullScreenOn){
                bw.write("Off");
            }
            bw.newLine();
            //Music
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            //Sound Effects
            bw.write(String.valueOf(gp.sound.volumeScale));
            bw.newLine();

            bw.close();
        }
        catch(IOException e){
                e.printStackTrace();
            }
        }
    public void loadConfig(){

        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s = br.readLine();
            //FULLSCREEN
            if(s.equals("On")){
                gp.fullScreenOn = true;
            }
            if(s.equals("Off")){
                gp.fullScreenOn = false;
            }
            //MUSIC
            s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            //SOUND
            s = br.readLine();
            gp.sound.volumeScale = Integer.parseInt(s);

            br.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    }


