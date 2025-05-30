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

        collision = true;

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

        dialogues[0][0] = "Heya Rex!";
        dialogues[0][1] = "I heard you'll be\nhaving your trial today!";
        dialogues[0][2] = "Good Luck!";

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
