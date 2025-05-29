package Entities;

import Main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC1 extends Entity {

    public NPC1(GamePanel gp){
        super(gp);

        direction = "StandDown";
        speed = 1;
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

        up1 = setup("/NPC/NPC1_Up_1");
        up2 = setup("/NPC/NPC1_Up_2");
        down1 = setup("/NPC/NPC1_Down_1");
        down2 = setup("/NPC/NPC1_Down_2");
        left1 = setup("/NPC/NPC1_Left_1");
        left2 = setup("/NPC/NPC1_Left_2");
        right1 = setup("/NPC/NPC1_Right_1");
        right2 = setup("/NPC/NPC1_Right_2");

        standNorth = setup("/NPC/NPC1_StandBack");
        standSouth = setup("/NPC/NPC1_StandFront");
        standEast = setup("/NPC/NPC1_StandRight");
        standWest = setup("/NPC/NPC1_StandLeft");

    }

    public void setDialogue(){

        dialogues[0][0] = "Heed my warnings, traveler.";
        dialogues[0][1] = "Your adventure will not be easy \nfor you will face many foes.";
        dialogues[0][2] = "But you shall prevail.";
        dialogues[0][3] = "Goodluck Rex";

        dialogues[1][4] = "I am the Mage of this realm.";
        dialogues[1][5] = "I can help you on your journey.";
        dialogues[1][6] = "If you need assistance, just ask.";
        dialogues[1][7] = "Remember, knowledge is power.";

        dialogues[2][8] = "I can teach you spells to aid you in battle.";
        dialogues[2][9] = "But first, you must prove your worth.";
        dialogues[2][10] = "Return to me when you are ready.";


    }

    public void setAction(){
        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // Random number between 1 and 100
    
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    public void speak(){
        facePlayer();
        startDialogue(this,dialogueSet);

        dialogueSet++;

        if(dialogues[dialogueSet][0] == null){
            //dialogueSet = 0;
            dialogueSet --;
        }
    }
}
