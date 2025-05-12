package Main;

import java.awt.*;
import java.awt.image.BufferedImage;

import Object.*;

public class UI {

    GamePanel gp;
    Font arial_40, arial_80B;

    Graphics2D g2;

    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;
    public boolean gameFinished = false;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if(gp.gameState == gp.playState) {
            //do play state stuff
        }

        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        if(gp.gameState == gp.dialogueState) {
            drawDialogueScreen();

        }
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    public void drawPauseScreen() {
        // Pause screen
        g2.setFont(arial_80B);
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);

        // Resume text
        g2.setFont(arial_40);
        text = "Press ENTER to continue";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);

        // Exit text
        text = "Press ESC to return to menu";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {

        //WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width= gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize*5;

        drawSubWindow(x,y,width,height);
    }

    public void drawSubWindow(int x, int y, int width, int height){
    Color c = new Color (0,0,0);
    g2.setColor(c);
    g2.fillRoundRect(x,y,width,height,35,35);
    }

}
