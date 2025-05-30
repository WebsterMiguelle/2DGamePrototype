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
    public void setDialogue() {
        dialogues[0][0] = "You got the Crown!.";
    }
    public void interact(){
        startDialogue(this, 0);
        gp.playSE(10);
    }
    public void getImage(){
        frame1 = setup("/objects/Crown1");
        frame2 = setup("/objects/Crown2");

        down1 = frame1; // used by draw()
        down2 = frame2; // used by draw()
    }
}
