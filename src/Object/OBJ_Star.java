package Object;

import Entities.Entity;
import Main.GamePanel;

public class OBJ_Star extends Entity {
    GamePanel gp;
    public OBJ_Star(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Star";
        down1 = setup("/objects/Blue_Key");
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
            if(!(gp.trueKing || gp.trueSage || gp.trueWarrior)) {
                gp.cutsceneManager.sceneNum = 1;
            }
            else if(gp.trueWarrior){
                gp.cutsceneManager.sceneNum = 2;
                if(gp.trueSage){
                    gp.cutsceneManager.sceneNum = 3;
                    if(gp.trueKing){
                        gp.cutsceneManager.sceneNum = 4;
                    }
                }
            }
        }
        else{
            startDialogue(this, 0);
        }
    }
}
