package Object;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_Book extends Entity {
    GamePanel gp;
    public OBJ_Book(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_book;

        name = "book";
        down1 = setup("/objects/book");
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
        dialogues[0][0] = "You got the Book!.";
    }
    public void interact(){
        startDialogue(this, 0);
    }
}
