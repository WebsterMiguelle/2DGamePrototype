package Main;

import Entities.Entity;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {

    public Entity npc;
    GamePanel gp;
    Font daydreamFont;

    Graphics2D g2;

    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0; // 0 = title
    public int subState = 0; //

    int  charIndex = 0;
    String combinedText = "";

// Transition control
    public boolean transitioning = false;
    public int transitionAlpha = 0;
    public int transitionPhase = 0; // 0 = None, 1 = Fading Out, 2 = Switching State, 3 = Fading In
    public int nextGameState = -1;


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
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if(gp.gameState == gp.playState) {
            //do play state stuff
        }

        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        if(gp.gameState == gp.dialogueState) {
            drawDialogueScreen();

        }

        if(gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }

        if (gp.gameState == gp.minigameState && gp.currentMinigame != null) {
            gp.currentMinigame.draw(g2);
        }

        drawTransition(g2);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public void drawTitleScreen() {
        if (titleScreenState == 0) {
            g2.setColor(new Color(70, 70, 70));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //Title Name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
            String text = "Rex the Great";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 2;
            //Shadow
            g2.setColor(Color.black);
            g2.drawString(text, x + 5, y + 5);
            //Main Color
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            //Character Image
            x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.standSouth, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 4;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize + 5;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize + 5;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize / 2, y);
            }
        }
    }

    public void drawPauseScreen() {
        // Pause screen
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);

        // Resume text
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20f));
        text = "Press P to continue";
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

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        x+= gp.tileSize;
        y+= gp.tileSize;



        if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
            //currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];
            char[] characters = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
            if(charIndex < characters.length){
                gp.playSE(7);
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialogue = combinedText;
                charIndex++;

            }



            if(gp.keyH.enterPressed){
                charIndex = 0;
                combinedText = "";
                if(gp.gameState == gp.dialogueState){
                    npc.dialogueIndex++;
                    gp.keyH.enterPressed = false;
                }
            }
        } else {
            npc.dialogueIndex = 0;
            if(gp.gameState == gp.dialogueState){
                gp.gameState = gp.playState; // Return to play state if dialogue is finished
            }
        }

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawOptionsScreen(){
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));

        //SubWindow

        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        switch(subState){
            case 0: options_top( frameX, frameY);break;
            case 1: options_fullScreenNotification(frameX, frameY);break;
            case 2: options_controls(frameX,frameY);break;
            case 3: options_endGame(frameX, frameY);break;
        }
        gp.keyH.enterPressed = false;

    }

    public void options_top(int frameX, int frameY){

        int textX;
        int textY;

        //Title
        String text = "OPTIONS";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //Fullscreen
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15f));
        g2.drawString("Fullscreen", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                if(!gp.fullScreenOn){
                    gp.fullScreenOn = true;

                } else {
                   gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }
        //Music
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if(commandNum == 1){g2.drawString(">", textX-25, textY);}

        //Sound Effects
        textY += gp.tileSize;
        g2.drawString("Sounds", textX, textY);
        if(commandNum == 2){g2.drawString(">", textX-25, textY);}

        //Control
        textY += gp.tileSize;
        g2.drawString("Controls", textX, textY);
        if(commandNum == 3){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                subState = 2;
                commandNum = 0;
            }
        }
        //End Game
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if(commandNum == 4){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                subState = 3;
                commandNum = 0;
            }
        }
        //Back
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }

        //FULLSCREEN CHECKBOX
        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize * 3 - 13;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 20, 20);
        if(gp.fullScreenOn){
            g2.fillRect(textX, textY, 20, 20);
        }


        //MUSIC
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 100, 20);
        int volumeWidth = 20 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 20);

        //Sounds
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 100, 20);
        volumeWidth = 20 * gp.sound.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 20);

        gp.config.saveConfig();
    }

    public void options_fullScreenNotification(int frameX, int frameY){
      int textX = frameX + gp.tileSize;
      int textY = frameY + gp.tileSize * 2;

      g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15f));
      currentDialogue = "The change will take \neffect after you \nrestart the game.";

      for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 32;
      }

      textY = frameY + gp.tileSize*9;
      g2.drawString("Back", textX, textY);
      if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
            }
      }
    }

    public void options_controls(int frameX, int frameY){
        int textX;
        int textY;
        //Title
        String text = "CONTROLS";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15f));
        g2.drawString("Move", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Interact", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY);

        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("WASD", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("E", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY);

        //BACK
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
                commandNum = 3;
            }
        }
    }

    public void options_endGame(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 2;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 15f));
        currentDialogue = "Are you sure you \nwant to end the game?";
        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 32;
        }

        // YES
        String text = "YES";
        textX = getXforCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
                gp.gameState = gp.titleState;
                gp.stopMusic();
            }
        }

        // NO
        text = "NO";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX - 25, textY);
            if(gp.keyH.enterPressed){
                subState = 0;
                commandNum = 4; // return focus to "End Game" option
            }
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

    public void drawTransition(Graphics2D g2) {
        if (!transitioning) return;

        switch (transitionPhase) {
            case 1: // Fade out
                transitionAlpha += 10;
                if (transitionAlpha >= 255) {
                    transitionAlpha = 255;
                    gp.gameState = nextGameState;
                    transitionPhase = 3; // Start fading in
                }
                break;
            case 3: // Fade in
                transitionAlpha -= 10;
                if (transitionAlpha <= 0) {
                    transitionAlpha = 0;
                    transitionPhase = 0;
                    transitioning = false;
                }
                break;
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transitionAlpha / 255f));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void startTransition(int nextState) {
        transitioning = true;
        transitionPhase = 1; // Start fading out
        transitionAlpha = 0;
        nextGameState = nextState;
    }

}
