package Entities;

import Main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_Mother extends Entity {

    public NPC_Mother(GamePanel gp){
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
//IMAGENOTSET
        up1 = setup("/NPC/Mage_WalkUp1");
        up2 = setup("/NPC/Mage_WalkUp2");
        down1 = setup("/NPC/Mage_WalkDown1");
        down2 = setup("/NPC/Mage_WalkDown2");
        left1 = setup("/NPC/Mage_WalkLeft1");
        left2 = setup("/NPC/Mage_WalkLeft2");
        right1 = setup("/NPC/Mage_WalkRight1");
        right2 = setup("/NPC/Mage_WalkRight2");

        standNorth = setup("/NPC/Mage_FaceBack");
        standSouth = setup("/NPC/Mage_FaceFront");
        standEast = setup("/NPC/Mage_FaceRight");
        standWest = setup("/NPC/Mage_FaceLeft");

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

//        if(actionLockCounter == 120) {
//            Random random = new Random();
//            int i = random.nextInt(100) + 1; // Random number between 1 and 100
//
//            if (i <= 25) {
//                direction = "up";
//            }
//            if (i > 25 && i <= 50) {
//                direction = "down";
//            }
//            if (i > 50 && i <= 75) {
//                direction = "left";
//            }
//            if (i > 75) {
//                direction = "right";
//            }
//            actionLockCounter = 0;
//        }
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
