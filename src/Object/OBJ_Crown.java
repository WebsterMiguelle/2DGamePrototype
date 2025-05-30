package Object;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_Crown extends Entity {
    GamePanel gp;
    public OBJ_Crown(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_crown;

        name = "Crown";
        down1 = setup("/objects/Crown");
        down2 = setup("/objects/Crown");
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
        dialogues[0][0] = "You got the Crown!.";
    }
    public void interact(){
        startDialogue(this, 0);
    }
}
