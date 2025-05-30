package Object;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_Star extends Entity {
    GamePanel gp;
    public OBJ_Star(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Star";
        getImage();
        type = type_star;
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
        dialogues[0][0] = "You have yet to finish all the Trials.";
    }

    public void interact() {
        if(gp.player.hasDoneAllMinigames()){
            gp.gameState = gp.cutsceneState;
            gp.cutsceneManager.scenePhase = 0;
            if(!(gp.trueKing || gp.trueSage || gp.trueWarrior)) {
                gp.cutsceneManager.sceneNum = 1;
            }
            if(gp.trueWarrior){
                gp.cutsceneManager.sceneNum = 2;
                if(gp.trueSage){
                    gp.cutsceneManager.sceneNum = 3;
                    if(gp.trueKing){
                        gp.cutsceneManager.sceneNum = 4;
                    }
                }
            } else {gp.cutsceneManager.sceneNum = 1;}
        }
        else{
            startDialogue(this, 0);
        }
    }

    public void getImage(){
        frame1 = setup("/objects/Sparkle1");
        frame2 = setup("/objects/Sparkle2");

        down1 = frame1; // used by draw()
        down2 = frame2; // used by draw()
    }
}
