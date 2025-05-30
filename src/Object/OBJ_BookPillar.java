package Object;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_BookPillar extends Entity {
    GamePanel gp;

    private int animationCounter = 0;
    private boolean toggleFrame = false;

    public OBJ_BookPillar(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_BookPillar;

        name = "BookPillar";
        getImage();
        collision = true;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 48;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDialogue();

        direction = "down"; // ensure it uses down1/down2 in draw()
    }

    public void getImage(){
        frame1 = setup("/objects/Sparkle1");
        frame2 = setup("/objects/Sparkle2");

        down1 = frame1; // used by draw()
        down2 = frame2; // used by draw()
    }

    @Override
    public void update() {
        animationCounter++;
        // change to adjust how fast it animates
        int animationSpeed = 30;
        if (animationCounter > animationSpeed) {
            toggleFrame = !toggleFrame;
            animationCounter = 0;

            if (toggleFrame) {
                down1 = frame1;
            } else {
                down1 = frame2;
            }
        }
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
