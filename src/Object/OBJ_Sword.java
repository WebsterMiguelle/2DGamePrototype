package Object;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_Sword extends Entity {
    GamePanel gp;
    public OBJ_Sword(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_sword;

        name = "Sword";
        down1 = setup("/objects/Sword");
        collision = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void interact(){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You got the Sword!.";
    }
}
