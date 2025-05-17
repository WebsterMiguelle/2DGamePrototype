package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    //debug
    public boolean showDebugText = false;

    public KeyHandler(GamePanel gp){
            this.gp = gp;
    }

    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if(gp.gameState == gp.titleState) {titleState(code);
            }
            else if (gp.gameState == gp.playState){
                playState(code);
            }
            else if (gp.gameState == gp.pauseState) {
                   pauseState(code);
            }
            else if (gp.gameState == gp.dialogueState) {
                dialogueState(code);
            }
            else if (gp.gameState == gp.optionsState) {
                optionsState(code);
            }
    }
    public void titleState(int code){
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
            if (gp.ui.commandNum == 1) {
                //load game
            }
            if (gp.ui.commandNum == 2) {
                System.exit(0);
            }
        }
    }
    public void playState(int code){
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }

        if(code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }

        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionsState;
        }

        //Debug
        if (code == KeyEvent.VK_T) {
            showDebugText = !showDebugText;
        }
    }
    public void pauseState(int code){
        if(code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.titleState;
            gp.stopMusic();
        }
    }
    public void dialogueState(int code){
        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }
    public void optionsState(int code){
        if(code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = switch (gp.ui.subState) {
            case 0 -> 5;
            case 3 -> 1;
            default -> 0;
        };
        if(code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSE(1);
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if(code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSE(1);//change this to appropriate sound
            if(gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }

        if(code == KeyEvent.VK_A){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(1);
                }
                if(gp.ui.commandNum == 2 && gp.sound.volumeScale > 0) {
                    gp.sound.volumeScale--;
                    gp.playSE(1);
                }
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.subState == 0){
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(1);
                }
                if(gp.ui.commandNum == 2 && gp.sound.volumeScale < 5) {
                    gp.sound.volumeScale++;
                    gp.playSE(1);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
