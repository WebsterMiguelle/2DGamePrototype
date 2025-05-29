package Main;

import Entities.NPC_Mage;
import Object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;

    }
        public void setObject(){
        int mapNum = 0;
        int i = 0;
        gp.obj[mapNum][i] = new OBJ_Pillar(gp);
        gp.obj[mapNum][i].worldX = 16 * gp.tileSize;
        gp.obj[mapNum][i].worldY = 33 * gp.tileSize;

        mapNum = 1;
            gp.obj[mapNum][i] = new OBJ_Pillar(gp);
            gp.obj[mapNum][i].worldX = 16 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 33 * gp.tileSize;
        i++;
        }

        public void setNPC(){
            int mapNum = 0;
            gp.npc[mapNum][0] = new NPC_Mage(gp);
            gp.npc[mapNum][0].worldX = 20 * gp.tileSize;
            gp.npc[mapNum][0].worldY = 29 * gp.tileSize;

        }
}
