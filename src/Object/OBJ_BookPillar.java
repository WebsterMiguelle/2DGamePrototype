package Object;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_BookPillar extends Entity {
    GamePanel gp;

    public OBJ_BookPillar(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_BookPillar;

        name = "BookPillar";
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
        String line = "";

        if (gp.player.hasBook && !gp.player.bookDone) {
            gp.gameState = gp.dialogueState;
            line = "Place the book on the pillar.";
        } else if (gp.player.bookDone) {
            gp.gameState = gp.dialogueState;
            line = "The book is already placed.";
        } else {
            gp.gameState = gp.dialogueState;
            line = "It seems that this pillar needs its item.";
        }
        dialogues[0][0] = line;
    }

    public void interact() {
        startDialogue(this, 0);
    }
}