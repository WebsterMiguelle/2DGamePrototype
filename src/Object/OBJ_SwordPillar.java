package Object;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_SwordPillar extends Entity {
    GamePanel gp;

    public OBJ_SwordPillar(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_SwordPillar;

        name = "SwordPillar";
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

    public void setDialogue(){
        String Line = "";


    if (gp.player.hasSword && !gp.player.swordDone) {
            gp.gameState = gp.dialogueState;
            Line = "Place the sword on the pillar.";
        } else if (gp.player.swordDone) {
            gp.gameState = gp.dialogueState;
            Line = "The sword is already placed.";
        } else {
            gp.gameState = gp.dialogueState;
            Line = "It seems that this\npillar needs its item.";
        }
        dialogues[0][0] = Line;
    }

    public void interact() {
        startDialogue(this,0);

    }

    public void getImage(){
        frame1 = setup("/objects/Sparkle1");
        frame2 = setup("/objects/Sparkle2");

        down1 = frame1; // used by draw()
        down2 = frame2; // used by draw()
    }

}