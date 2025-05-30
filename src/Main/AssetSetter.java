package Main;

import Entities.*;
import Entities.NPC_Mage;
import Object.*;

public class AssetSetter {

    GamePanel gp;

    public int i = 0;
    public AssetSetter(GamePanel gp){
        this.gp = gp;

    }
        public void setObject(){
        int mapNum = 0;


        mapNum = 1;

        mapNum = 2;

            gp.obj[mapNum][i] = new OBJ_Crown(gp);
            gp.obj[mapNum][i].worldX = 35 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 43 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_Sword(gp);
            gp.obj[mapNum][i].worldX = 46 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 33 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_Book(gp);
            gp.obj[mapNum][i].worldX = 9 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 44 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_CrownPillar(gp);
            gp.obj[mapNum][i].worldX = 28 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 42 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_BookPillar(gp);
            gp.obj[mapNum][i].worldX = 21 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 36 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_SwordPillar(gp);
            gp.obj[mapNum][i].worldX = 34 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 36 * gp.tileSize;
            i++;
            gp.obj[mapNum][i] = new OBJ_Star(gp);
            gp.obj[mapNum][i].worldX = 28 * gp.tileSize;
            gp.obj[mapNum][i].worldY = 36 * gp.tileSize;
            i++;
        }

        public void setNPC(){
            int mapNum = 0;
            gp.npc[mapNum][0] = new NPC_Mage(gp);
            gp.npc[mapNum][0].worldX = 20 * gp.tileSize;
            gp.npc[mapNum][0].worldY = 29 * gp.tileSize;

            mapNum = 1;

            mapNum = 2;

            gp.npc[mapNum][0] = new NPC1(gp);
            gp.npc[mapNum][0].worldX = 35 * gp.tileSize;
            gp.npc[mapNum][0].worldY = 13 * gp.tileSize;
            i++;
        }
}
