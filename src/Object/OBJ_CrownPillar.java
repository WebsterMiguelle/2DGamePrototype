package Object;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_CrownPillar extends Entity {
    GamePanel gp;

    public OBJ_CrownPillar(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_CrownPillar;

        name = "CrownPillar";
        down1 = setup("/objects/Pillar");
        collision = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDialogue();
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

    public void interact() {
        startDialogue(this,0);
    }
}
