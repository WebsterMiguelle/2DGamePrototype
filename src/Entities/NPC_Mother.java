package Entities;

import Main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Mother extends Entity {

    private boolean moveAfterSpeak = false;
    private int moveCounter = 0;
    public NPC_Mother(GamePanel gp){
        super(gp);

        direction = "StandDown";
        speed = 4;
        getImage();
        setDialogue();

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 12;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        dialogueSet = -1;
    }
    public void getImage() {
//IMAGENOTSET
        up1 = setup("/NPC/MotherSprite");
        up2 = setup("/NPC/MotherSprite");
        down1 = setup("/NPC/MotherSprite");
        down2 = setup("/NPC/MotherSprite");
        left1 = setup("/NPC/MotherSprite");
        left2 = setup("/NPC/MotherSprite");
        right1 = setup("/NPC/MotherSprite");
        right2 = setup("/NPC/MotherSprite");

        standNorth = setup("/NPC/MotherSprite");
        standSouth = setup("/NPC/MotherSprite");
        standEast = setup("/NPC/MotherSprite");
        standWest = setup("/NPC/MotherSprite");

    }

    public void setDialogue(){
//DIALOGUE NOT SET
        dialogues[0][0] = "You were tossing all night, sweetheart\nAnother dream?";
        dialogues[0][1] = "The star again, wasn't it?";
        dialogues[0][2] = "Your father used to say the\ntrials chose those with something\nmore...not just strength, but heart.";
        dialogues[0][3] = "If you're going...\ngo with your whole self.";
        dialogues[0][4] = "Not just your hands";
        dialogues[0][5] = "Not just your voice";
        dialogues[0][6] = "Not just your name";
        dialogues[0][7] = "Go as you. All of you";
        dialogues[0][8] = "And maybe... just maybe...\nthe kingdom will rise again.";
        dialogues[0][9] = "You do remember where\nthe trials are right?";
        dialogues[0][10] = "Its just north of the village";
        dialogues[0][11] = "Come back to me, alright?";
        dialogues[0][12] = "Trials or not... you're still my child.";
    }

    @Override
    public void setAction() {
        if (moveAfterSpeak) {
            moveCounter++;
            collisionOn = false; // <--- ADD THIS to ensure she keeps moving

            if (moveCounter <= 20) {
                direction = "right";
            } else {
                moveAfterSpeak = false;
                direction = "StandRight";
                System.out.println("Mother finished moving.");
            }
            if (!moveAfterSpeak) {
                gp.cChecker.checkTile(this);
                gp.cChecker.checkObject(this, false);
                gp.cChecker.checkPlayer(this);
            }

            return;
        }
    }


    @Override
    public void speak() {
        facePlayer();
        startDialogue(this, dialogueSet);

        moveAfterSpeak = true;
        direction = "right";
        moveCounter = 0;

        dialogueSet++;

        if (dialogueSet >= dialogues.length || dialogues[dialogueSet][0] == null) {
            dialogueSet--;
        }
    }

}
