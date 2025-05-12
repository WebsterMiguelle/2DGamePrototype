package Entities;

import Main.GamePanel;

import java.util.Random;

public class NPC_Mage extends Entity {

    public NPC_Mage(GamePanel gp){
        super(gp);

        direction = "StandDown";
        speed = 1;
        getImage();

    }
    public void getImage() {

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
}
