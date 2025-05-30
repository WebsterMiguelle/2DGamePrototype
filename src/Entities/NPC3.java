package Entities;

import Main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC3 extends Entity {

    public NPC3(GamePanel gp){
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

        up1 = setup("/NPC/NPC3_Up_1");
        up2 = setup("/NPC/NPC3_Up_2");
        down1 = setup("/NPC/NPC3_Down_1");
        down2 = setup("/NPC/NPC3_Down_2");
        left1 = setup("/NPC/NPC3_Left_1");
        left2 = setup("/NPC/NPC3_Left_2");
        right1 = setup("/NPC/NPC3_Right_1");
        right2 = setup("/NPC/NPC3_Right_2");

        standNorth = setup("/NPC/NPC3_FaceBack");
        standSouth = setup("/NPC/NPC3_FaceFront");
        standEast = setup("/NPC/NPC3_FaceRight");
        standWest = setup("/NPC/NPC3_FaceLeft");

    }

    public void setDialogue(){

        dialogues[0][0] = "Busy ko mag Faci";
        dialogues[0][1] = "I ain't DOST, doing DOST thingz";
        dialogues[0][2] = "nahh nahhh frfr";
        dialogues[0][3] = "*unnecessarily glazes person*";
        dialogues[0][4] = "I swear I can be a good leader!";
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
