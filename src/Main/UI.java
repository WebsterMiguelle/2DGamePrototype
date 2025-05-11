package Main;

import java.awt.*;
import java.awt.image.BufferedImage;

import Object.*;

public class UI {

    GamePanel gp;
    Font arial_40, arial_80B;

    Graphics2D g2;
    //BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;
    public boolean gameFinished = false;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        //OBJ_Key key = new OBJ_Key(gp);
        //keyImage = key.image;
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
}
