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
        down1 = setup("/objects/Pillar");
        collision = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void interact() {
        if (!gp.player.hasSword) {
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "It seems that this pillar needs its item.";
        }
    }
}