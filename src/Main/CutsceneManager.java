package Main;

import java.awt.*;

public class CutsceneManager {
    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase = 0;
    int counter = 0;
    float alpha = 0f;
    int y;

    String introAndCredits =
            "This land was once whole.\n\n"
                    +"A true king held a sword, word, and crown...\n\n"
                    +"And all was light.\n\n"
                    +"But pride broke the throne.\n\n"
                    +"The court turned.\n\n"
                    +"The world fell.\n\n"
                    +"Now, it waits.\n\n"
                    +"Not for strength.\n\n"
                    +"Not for wisdom.\n\n"
                    +"Not for power.\n\n\n"
                    +"For balance.\n\n"
                    +"Three trials await:\n\n"
                    +"Warrior. Poet. King.\n\n"
                    +"Pass them... and rise.\n"
                    +"or\n"
                    +"Fail... and vanish.\n\n\n\n\n\n\n"
                    +"Press Enter";

    String endCredit = "Credits:\n\n"
            +"\n\n\n\n\n\n\n"
            + "Game Design: Your Name\n"
            + "Programming: Your Name\n"
            + "Graphics: Your Name\n"
            + "Music: Your Name\n"
            + "Special Thanks: Everyone who played the game!";

    String trueWarriorEnding ="You proved yourself in battle.\n\n"
            +"Strong, fearless, and unyielding.\n\n"
            +"Though you could not complete\n\n" +
            "the other trials, your strength changed the land.\n\n"
            +"You fought back the warlords and\n\n" +
            "gave the next king a fighting chance.\n\n"
            +"History remembers you as the\n\n" +
            "blade that cleared the path.";

    String trueSageEnding = "You mastered the sword,\nand your words held power.\n\n"
            +"But the final trial was beyond your reach.\n\n"
            +"Still, your journey wasn't wasted.\n\n"
            +"When the true king rose after you,\n\n" +
            "you stood by their side.\n\n"
            +" Not as a ruler, but as the voice\n\n" +
            "of wisdom, guiding the kingdom into peace.";

    String trueKingEnding ="You faced every trial.\n\n" +
            "With strength, with wisdom, and with heart.\n\n" +
            "No part of you was missing.\n\n" +
            "The spirits saw it.\n\n" +
            "The land felt it.\n\n" +
            "You are the one.\n\n" +
            "The true king.\n\n" +
            "And with you, the kingdom is reborn.";
    //Scene

    public CutsceneManager(GamePanel gp) {
        this.gp = gp;
    }

   public void draw (Graphics2D g2){
    this.g2 = g2;
    switch (sceneNum) {
        case 0 -> openingScene();
        case 1 -> nullEnding();
        case 2 -> warriorEnding();
        case 3 -> sageEnding();
        case 4 -> kingEnding();
        default -> {nullEnding();}
    }
   }
   public void openingScene(){
         if (scenePhase == 0) {

             drawBlackScreen(1f);
                if (counterReached(180) || gp.keyH.enterPressed) {
                    scenePhase++;
                    y = gp.screenHeight;
                    gp.playMusic(14);
                }
         } if (scenePhase == 1) {

             drawBlackScreen(0.8f);


             y--;
             drawString(1f, 20, y, introAndCredits, 70);
                if (y < -5000 || gp.keyH.enterPressed) {
                    scenePhase++;
                }
         }
         if(scenePhase == 2) {
             gp.gameState = gp.playState; // Change game state to playState
             gp.stopMusic();
             gp.playMusic(0);
         }
   }
   public void nullEnding() {
          if (scenePhase == 0) {
                gp.stopMusic();
                gp.playMusic(14);
                //play null ending music
              scenePhase++;

          }
          if (scenePhase == 1) {
            //Screen goes black
            alpha += 0.005f; // Increase alpha for fade-in effect
            if (alpha > 1f) {
                alpha = 1f; // Cap alpha at 1
            }
            drawBlackScreen(alpha);
            if(alpha == 1f){
                alpha = 0f;
                scenePhase++;
            }
          }

            if (scenePhase == 2) {
               drawBlackScreen(1f);

              alpha += 0.005f; // Increase alpha for fade-in effect
              if (alpha > 1f) {
                  alpha = 1f; // Cap alpha at 1
              }
              String text = "It seems you were not\nthe Rex we needed.";
                drawString(alpha, 30, 200, text, 70);
                if(counterReached(600)||gp.keyH.enterPressed) {
                    //play ending music
                    scenePhase++;
                }
            }

            if (scenePhase == 3) {
                drawBlackScreen(1f);

                drawString(1f, 50, gp.screenHeight/2, "Rex Ending", 70);

                if(counterReached(180)||gp.keyH.enterPressed) {
                    scenePhase++;
                }
          }
            if (scenePhase == 4) {
                drawBlackScreen(1f);

                y = gp.screenHeight/2;
                drawString(1f, 20, y, endCredit, 70);

                if(counterReached(180)||gp.keyH.enterPressed) {
                    scenePhase++;
                }
            }
            if (scenePhase == 5) {
                drawBlackScreen(1f);
                y--;
                drawString(1f,20,y, endCredit,70);
                if(y < -1000||gp.keyH.enterPressed) {
                    scenePhase++;
                }
            }
            if(scenePhase == 6) {
                drawBlackScreen(1f);
                //Reset the game
                drawString(1f, 50, gp.screenHeight/2, "Thank you\nfor Playing!", 70);
                if(counterReached(2400)) {
                 System.exit(0);
                }
            }

    }
   public void warriorEnding(){
        if (scenePhase == 0) {
       gp.stopMusic();
            gp.playMusic(14);
       //play null ending music
       scenePhase++;

   }
        if (scenePhase == 1) {
               //Screen goes black
               alpha += 0.005f; // Increase alpha for fade-in effect
               if (alpha > 1f) {
                   alpha = 1f; // Cap alpha at 1
               }
               drawBlackScreen(alpha);
               if(alpha == 1f){
                   alpha = 0f;
                   scenePhase++;
                   y = gp.screenHeight;
               }
           }
        if (scenePhase == 2) {
               drawBlackScreen(1f);

               alpha += 0.005f; // Increase alpha for fade-in effect
               if (alpha > 1f) {
                   alpha = 1f; // Cap alpha at 1
               }
               y--;
               drawString(alpha, 20, y, trueWarriorEnding, 70);
               if(y < -1000||gp.keyH.enterPressed) {
                   //play ending music
                   scenePhase++;
               }
           }
        if (scenePhase == 3) {
               drawBlackScreen(1f);

               drawString(1f, 50, gp.screenHeight/2, "True Warrior Ending", 70);

               if(counterReached(180)||gp.keyH.enterPressed) {
                   scenePhase++;
               }
           }
        if (scenePhase == 4) {
               drawBlackScreen(1f);

               y = gp.screenHeight/2;
               drawString(1f, 20, y, endCredit, 70);

               if(counterReached(180)||gp.keyH.enterPressed) {
                   scenePhase++;
               }
           }
        if (scenePhase == 5) {
               drawBlackScreen(1f);
               y--;
               drawString(1f,20,y, endCredit,70);
               if(y < -1000||gp.keyH.enterPressed) {
                   scenePhase++;
               }
           }
        if(scenePhase == 6) {
               drawBlackScreen(1f);
               //Reset the game
               drawString(1f, 50, gp.screenHeight/2, "Thank you\nfor Playing!", 70);
               if(counterReached(240)) {
                   System.exit(0);
               }
           }
    }
   public void sageEnding(){
        if (scenePhase == 0) {
       gp.stopMusic();
            gp.playMusic(14);
       //play null ending music
       scenePhase++;

   }
       if (scenePhase == 1) {
           //Screen goes black
           alpha += 0.005f; // Increase alpha for fade-in effect
           if (alpha > 1f) {
               alpha = 1f; // Cap alpha at 1
           }
           drawBlackScreen(alpha);
           if(alpha == 1f){
               alpha = 0f;
               scenePhase++;
                y = gp.screenHeight;
           }
       }
       if (scenePhase == 2) {
           drawBlackScreen(1f);

           alpha += 0.005f; // Increase alpha for fade-in effect
           if (alpha > 1f) {
               alpha = 1f; // Cap alpha at 1
           }
           y--;
           drawString(alpha, 20, y, trueSageEnding, 70);
           if(y < -1000||gp.keyH.enterPressed) {
               //play ending music
               scenePhase++;
           }
       }
       if (scenePhase == 3) {
           drawBlackScreen(1f);

           drawString(1f, 50, gp.screenHeight/2, "True Sage Ending", 70);

           if(counterReached(180)||gp.keyH.enterPressed) {
               scenePhase++;
           }
       }
       if (scenePhase == 4) {
           drawBlackScreen(1f);

           y = gp.screenHeight/2;
           drawString(1f, 20, y, endCredit, 70);

           if(counterReached(180)||gp.keyH.enterPressed) {
               scenePhase++;
           }
       }
       if (scenePhase == 5) {
           drawBlackScreen(1f);
           y--;
           drawString(1f,20,y, endCredit,70);
           if(y < -1000||gp.keyH.enterPressed) {
               scenePhase++;
           }
       }
       if (scenePhase == 6) {
           drawBlackScreen(1f);
           //Reset the game
           drawString(1f, 50, gp.screenHeight/2, "Thank you\nfor Playing!", 70);
           if(counterReached(240)) {
               System.exit(0);
           }
       }
    }
   public void kingEnding(){
        if(scenePhase == 0) {
       gp.stopMusic();
            gp.playMusic(14);
       //play null ending music
       scenePhase++;

   }
       if (scenePhase == 1) {
           //Screen goes black
           alpha += 0.005f; // Increase alpha for fade-in effect
           if (alpha > 1f) {
               alpha = 1f; // Cap alpha at 1
           }
           drawBlackScreen(alpha);
           if(alpha == 1f){
               alpha = 0f;
               scenePhase++;
                y = gp.screenHeight;
           }
       }
       if (scenePhase == 2) {
           drawBlackScreen(1f);

           alpha += 0.005f; // Increase alpha for fade-in effect
           if (alpha > 1f) {
               alpha = 1f; // Cap alpha at 1
           }
           y--;
           drawString(alpha, 20, y, trueKingEnding, 70);
           if(y < -1000||gp.keyH.enterPressed) {
               //play ending music
               scenePhase++;
           }
       }
       if (scenePhase == 3) {
           drawBlackScreen(1f);

           drawString(1f, 50, gp.screenHeight/2, "True King Ending\n\nCongratulations!", 70);

           if(counterReached(180)||gp.keyH.enterPressed) {
               scenePhase++;
           }
       }
       if (scenePhase == 4) {
           drawBlackScreen(1f);

           y = gp.screenHeight/2;
           drawString(1f, 20, y, endCredit, 70);

           if(counterReached(180)||gp.keyH.enterPressed) {
               scenePhase++;
           }
       }
       if (scenePhase == 5) {
           drawBlackScreen(1f);
           y--;
           drawString(1f,20,y, endCredit,70);
           if(y < -1000||gp.keyH.enterPressed) {
               scenePhase++;
           }
       }
       if (scenePhase == 6) {
           drawBlackScreen(1f);
           //Reset the game
           drawString(1f, 50, gp.screenHeight/2, "Thank you\nfor Playing!", 70);
           if(counterReached(240)) {
               System.exit(0);
           }
       }
    }

    public boolean counterReached(int target) {
        //To add delay in cutscenes, use this method and call it with the target number of frames 60 frames = 1 second
       boolean counterReached = false;
        counter++;
        if (counter >= target) {
            counter = 0;

            counterReached = true;
        }
        return counterReached;
    }

    public void drawBlackScreen(float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // Reset alpha
    }

    public void drawString(float alpha, int fontSize, int y, String text, int lineHeight){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, fontSize));

        for(String line : text.split("\n")) {
            int x = gp.ui.getXforCenteredText(line);
            g2.drawString(line, x, y);
            y += lineHeight;
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // Reset alpha
    }
}
