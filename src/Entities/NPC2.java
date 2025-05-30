package Entities;

import Main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC2 extends Entity {

    public NPC2(GamePanel gp){
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

        up1 = setup("/NPC/NPC2_Up_1");
        up2 = setup("/NPC/NPC2_Up_2");
        down1 = setup("/NPC/NPC2_Down_1");
        down2 = setup("/NPC/NPC2_Down_2");
        left1 = setup("/NPC/NPC2_Left_1");
        left2 = setup("/NPC/NPC2_Left_2");
        right1 = setup("/NPC/NPC2_Right_1");
        right2 = setup("/NPC/NPC2_Right_2");

        standNorth = setup("/NPC/NPC2_FaceBack");
        standSouth = setup("/NPC/NPC2_FaceFront");
        standEast = setup("/NPC/NPC2_FaceRight");
        standWest = setup("/NPC/NPC2_FaceLeft");

    }

    public void setDialogue(){

        dialogues[0][0] = "I tried doing the trial";
        dialogues[0][1] = "I just realized, the items\ncan only be taken from below";
        dialogues[0][2] = "Please Save Us!";

    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(2) + 1;  // Random number between 1 and 100

            if (i == 1) {
                direction = "up";
            }
            if (i == 2) {
                direction = "down";

                actionLockCounter = 0;
            }
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
