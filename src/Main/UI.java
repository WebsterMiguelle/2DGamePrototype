package Main;

import java.awt.*;

import java.io.IOException;
import java.io.InputStream;



public class UI {

    GamePanel gp;
    Font daydreamFont;

    Graphics2D g2;

    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";

    public UI(GamePanel gp){
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Daydream.ttf");
            assert is != null;
            daydreamFont = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch(FontFormatException | IOException e){
            e.printStackTrace();
        }
    }



    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(daydreamFont);
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
        g2.setFont(daydreamFont);
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);

        // Resume text
        g2.setFont(daydreamFont);
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

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 12F));
        x+= gp.tileSize;
        y+= gp.tileSize;

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 32;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height){
    Color c = new Color (0,0,0,210);
    g2.setColor(c);
    g2.fillRoundRect(x,y,width,height,35,35);

    c = new Color(255,255,255);
    g2.setColor(c);
    g2.setStroke(new BasicStroke(5));
    g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

}
