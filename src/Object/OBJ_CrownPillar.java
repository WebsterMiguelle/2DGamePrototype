package Object;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_CrownPillar extends Entity {
    GamePanel gp;
    private int animationCounter = 0;
    private boolean toggleFrame = false;
    public OBJ_CrownPillar(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_CrownPillar;

        name = "CrownPillar";
        getImage();
        collision = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDialogue();
    }
    @Override
    public void update() {
        animationCounter++;
        // change to adjust how fast it animates
        int animationSpeed = 30;
        if (animationCounter > animationSpeed) {
            toggleFrame = !toggleFrame;
            animationCounter = 0;

            if (toggleFrame) {
                down1 = frame1;
            } else {
                down1 = frame2;
            }
        }
    }
    public void setDialogue() {
        String Line = "";

        if (gp.player.hasCrown && !gp.player.crownDone) {
            gp.gameState = gp.dialogueState;
            Line = "Place the crown on the pillar.";
        } else if (gp.player.crownDone) {
            gp.gameState = gp.dialogueState;
            Line = "The crown is already placed.";
        } else {
            gp.gameState = gp.dialogueState;
            Line = "It seems that this pillar needs its item.";
        }
        dialogues[0][0] = Line;
    }
    public void getImage(){
        frame1 = setup("/objects/Sparkle1");
        frame2 = setup("/objects/Sparkle2");

        down1 = frame1; // used by draw()
        down2 = frame2; // used by draw()
    }

    public void interact() {
        startDialogue(this,0);
    }
}
