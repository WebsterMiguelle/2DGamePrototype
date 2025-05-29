package Object;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_Pillar extends Entity {
    GamePanel gp;
    public OBJ_Pillar(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pillar;

        name = "Pillar";
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
        dialogues[0][0] = "This is a pillar.";
    }

    public void interact(){
        startDialogue(this, 0);
    }
}
