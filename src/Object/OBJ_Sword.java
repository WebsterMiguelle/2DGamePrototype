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
        dialogues[0][0] = "You got the Sword!.";
    }
    public void interact(){
        startDialogue(this,0);
        gp.playSE(10);
    }
    public void getImage(){
        frame1 = setup("/objects/Sword1");
        frame2 = setup("/objects/Sword2");

        down1 = frame1; // used by draw()
        down2 = frame2; // used by draw()
    }
}
